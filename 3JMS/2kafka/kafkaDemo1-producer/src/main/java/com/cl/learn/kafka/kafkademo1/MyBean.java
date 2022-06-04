package com.cl.learn.kafka.kafkademo1;

import jdk.management.resource.internal.inst.SocketOutputStreamRMHooks;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * @Author l
 * @Date 2022/6/4 13:38
 */
@Component
public class MyBean implements CommandLineRunner {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public String topic="topic1";


    @Override
    public void run(String... args) throws Exception {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 同步发送消息
        ListenableFuture<SendResult<String, String>> topic1 = kafkaTemplate.send(topic, simpleDateFormat.format(new Date()));
    }

    @KafkaListener(topics = "topic1")
    public void processMessage(String content){
        System.out.println("接收到消息："+content);
    }


}
