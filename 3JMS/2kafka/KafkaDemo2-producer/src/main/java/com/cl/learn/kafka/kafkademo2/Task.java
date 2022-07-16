package com.cl.learn.kafka.kafkademo2;

import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * @Author l
 * @Date 2022/7/8 09:46
 */
@Component
public class Task implements CommandLineRunner {

    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    private Producer producer;


    @Value("${topic}")
    private String topic;

    @Override
    public void run(String... args) throws Exception {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(new Date());
        for (int i = 0; i < 10; i++) {
            String value=dateStr+"---"+i;
//            test1(value);
            test2(value);
        }

    }

    /**
     * 利用kafkaTemplate 同步发送
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void test1(String value) throws ExecutionException, InterruptedException {
        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, value);
        SendResult<String, String> sendResult = send.get();
        RecordMetadata recordMetadata = sendResult.getRecordMetadata();
        logger.info("通过kafkaTemplate成功写入：topic="+recordMetadata.topic()+",partition="+recordMetadata.partition()+",offset="+recordMetadata.offset()+",value="+value);
    }


    /**
     * 通过 kafkaProducer 异步发送，并调用回调方法
     * 使用默认分区规则
     * @param value
     */
    private void test2(String value) {
        producer.send(new ProducerRecord<>(topic, 2, "", value), new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception==null){
                    logger.info("通过kafkaProducer成功写入：topic="+metadata.topic()+",partition="+metadata.partition()+",offset="+metadata.offset()+",value="+value);
                }else {
                    logger.error("通过kafkaProducer写入有误：",exception);
                }
            }
        });
    }
}
