package com.cl.learn.kafka.kafkademo1.test;

import com.cl.learn.kafka.kafkademo1.test.KafkaProConsu;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @Author l
 * @Date 2022/6/4 13:38
 */
@Component
public class TemplateTest implements CommandLineRunner {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ConsumerFactory consumerFactory;

    @Autowired
    private KafkaProConsu kafkaProConsu;

    public static String topic="topic2";

    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    public void run(String... args) throws Exception {
        for(int i=0;i<100;i++){
            kafkaProConsu.testSend1();

        }
//        kafkaProConsu.testSend2();
//        kafkaProConsu.testReceive();
//        testSend1_template();
//        testReceive1_template();
    }


    // 同步向kafka发送消息
    public void testSend1_template() throws ExecutionException, InterruptedException {
        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topic, sdf.format(new Date()));
        SendResult<String, String> sendResult = listenableFuture.get();
        RecordMetadata recordMetadata = sendResult.getRecordMetadata();
        System.out.println("成功写入到："+"topic="+recordMetadata.topic()+",partition="+recordMetadata.partition()+",offset="+recordMetadata.offset());
    }

    // 接收指定topic，指定分区，指定偏移量的数据
    public void testReceive1_template() throws ExecutionException, InterruptedException {
        Consumer consumer = consumerFactory.createConsumer();
        Map<String, List<PartitionInfo>> map = consumer.listTopics();
        Set<Map.Entry<String, List<PartitionInfo>>> entries = map.entrySet();
        System.out.println("--------------------");
        for(Iterator<Map.Entry<String, List<PartitionInfo>>> iterator = entries.iterator();iterator.hasNext();){
            Map.Entry<String, List<PartitionInfo>> entry= iterator.next();
            String key = entry.getKey();
            System.out.println("----"+key);
            if(!"__consumer_offsets".equals(key)){
                for (PartitionInfo partitionInfo : entry.getValue()) {
                    System.out.println(partitionInfo);
                }
            }
        }
        System.out.println("--------------------");

        kafkaTemplate.setConsumerFactory(consumerFactory);

        // 接收指定topic，指定分区，指定偏移量的数据
////        ConsumerRecord<String, String> consumerRecord = kafkaTemplate.receive(topic, 0, 1);
//        System.out.println("成功接收到到："+"topic="+consumerRecord.topic()+",partition="+consumerRecord.partition()+",offset="+consumerRecord.offset()+",value="+consumerRecord.value());
        for(int i=0;i<2;i++){
            for(int j=0;j<9;j++){
                ConsumerRecord<String, String> consumerRecord = kafkaTemplate.receive(topic, i, j);
                System.out.println("成功接收到到："+"topic="+consumerRecord.topic()+",partition="+consumerRecord.partition()+",offset="+consumerRecord.offset()+",value="+consumerRecord.value());
            }
        }
    }


    // 监听该topic的消息，默认分区
    @KafkaListener(topics = "topic1" )
    public void listen1(String content){
        System.out.println("1接收到消息："+content);
    }

    // 监听该topic的消息，默认分区
    @KafkaListener(topics = "topic1" )
    public void listen2(String content){
        System.out.println("2接收到消息："+content);
    }

    // 监听该topic的消息，默认分区
    @KafkaListener(topics = "topic1" )
    public void listen3(String content){
        System.out.println("3接收到消息："+content);
    }


}
