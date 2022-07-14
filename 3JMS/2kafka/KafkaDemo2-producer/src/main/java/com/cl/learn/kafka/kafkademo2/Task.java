package com.cl.learn.kafka.kafkademo2;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author l
 * @Date 2022/7/8 09:46
 */
@Component
public class Task implements CommandLineRunner {

    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Value("${topic}")
    private String topic;

    @Override
    public void run(String... args) throws Exception {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(new Date());
        for (int i = 0; i < 100; i++) {
            String value=dateStr+"---"+i;
            ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, value);
            SendResult<String, String> sendResult = send.get();
            RecordMetadata recordMetadata = sendResult.getRecordMetadata();
            logger.info("成功写入：topic="+recordMetadata.topic()+",partition="+recordMetadata.partition()+",offset="+recordMetadata.offset()+",value="+value);
        }


    }
}
