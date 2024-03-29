package com.cl.learn.kafka.kafkademo3;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.internals.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author l
 * @Date 2022/8/28 13:08
 */
@Component
public class Task implements CommandLineRunner {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private DefaultKafkaProducerFactory defaultKafkaProducerFactory;

    @Value("${spring.kafka.topics}")
    private String[] topics;

    @Override
    public void run(String... args) throws Exception {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(new Date());
//        test1(dateStr);
//        Producer producer = defaultKafkaProducerFactory.createProducer();
        test2(dateStr,  kafkaProducer);
        defaultKafkaProducerFactory.closeThreadBoundProducer();
    }

    private void test1(String dateStr) {
        try {
            String value = dateStr + "---2" ;
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topics[0], value);
            ListenableFuture<SendResult<String,String>> listenableFuture = kafkaTemplate.send(producerRecord);
            SendResult<String, String> sendResult = listenableFuture.get(10, TimeUnit.SECONDS);
            RecordMetadata recordMetadata = sendResult.getRecordMetadata();
            System.out.println("通过kafkaTemplate成功写入：topic="+recordMetadata.topic()+",partition="+recordMetadata.partition()+",offset="+recordMetadata.offset()+",value="+value);
        } catch (Exception e) {
            System.out.println("写入有误"+e);
        }

    }

    private void test2(String dateStr, Producer kafkaProducer){
        kafkaProducer.initTransactions(); //1 初始化事务
        kafkaProducer.beginTransaction(); //2 开启事务
        try {
            for(int i=1; i<10; i++){
                String value=dateStr+"---"+i;
                for(String topic:topics){
                    String finalValue =topic+"_"+value;
                    kafkaProducer.send(new ProducerRecord(topic, finalValue), new Callback() {
                        @Override
                        public void onCompletion(RecordMetadata metadata, Exception exception) {
                            if (exception==null){
                                System.out.println("通过kafkaProducer成功写入：topic="+metadata.topic()+",partition="+metadata.partition()+",offset="+metadata.offset()+",value="+ finalValue);
                            }else {
                                System.out.println("通过kafkaProducer写入有误："+exception);
                            }
                        }
                    });   // 发送数据

                }
//                if(i%10==0){
//                    kafkaProducer.commitTransaction();
//                    kafkaProducer.beginTransaction(); //2 开启事务
//                }
//                Thread.sleep(1000);
            }
//            int x=1/0;
            kafkaProducer.commitTransaction();  // 3 提交事务
        } catch (Exception e){
            System.out.println("写入有误："+e);
            kafkaProducer.abortTransaction();   // 4 若有异常 回滚
        } finally {
//            kafkaProducer.close();
        }
    }




}
