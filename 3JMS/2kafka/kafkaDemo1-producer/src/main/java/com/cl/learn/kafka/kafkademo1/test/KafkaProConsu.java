package com.cl.learn.kafka.kafkademo1.test;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author l
 * @Date 2022/6/5 14:19
 */
@Component
public class KafkaProConsu {

    public static String topic="topic2";
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private KafkaProducer producer;

    @Autowired
    private KafkaConsumer kafkaConsumer;



    // 同步向kafka发送
    public void testSend1() throws ExecutionException, InterruptedException {
        ProducerRecord<Object, Object> producerRecord= new ProducerRecord<Object,Object>(topic, null, sdf.format(new Date()));
        Future<RecordMetadata> future= producer.send(producerRecord);
        RecordMetadata recordMetadata = future.get();
        System.out.println("成功写入到："+"topic="+recordMetadata.topic()+",partition="+recordMetadata.partition()+",offset="+recordMetadata.offset());
    }

    // 异步向kafka写入
    public void testSend2() throws ExecutionException, InterruptedException {

        ProducerRecord<Object, Object> producerRecord= new ProducerRecord<Object,Object>(topic, null, sdf.format(new Date()));
        Future<RecordMetadata> future= producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception exception) {
                if(exception == null){ // 发送成功
                    System.out.println("成功写入到："+"topic="+recordMetadata.topic()+",partition="+recordMetadata.partition()+",offset="+recordMetadata.offset());
                }else{
                    System.out.println("写入异常"+exception.getMessage());
                }
            }
        });
    }

    public void testReceive(){
        kafkaConsumer.subscribe(Arrays.asList(topic));
        ConsumerRecords<String,String> consumerRecords= kafkaConsumer.poll(Duration.ofSeconds(5));
        for (ConsumerRecord consumerRecord : consumerRecords) {
            System.out.println("成功接收到到："+"topic="+consumerRecord.topic()+",partition="+consumerRecord.partition()+",offset="+consumerRecord.offset()+",value="+consumerRecord.value());
        }

    }
}
