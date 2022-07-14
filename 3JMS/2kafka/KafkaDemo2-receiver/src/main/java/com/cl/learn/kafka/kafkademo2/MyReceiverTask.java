package com.cl.learn.kafka.kafkademo2;

import org.apache.kafka.clients.admin.ConsumerGroupListing;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.TopicPartitionOffset;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author l
 * @Date 2022/7/11 10:43
 */
@Component
public class MyReceiverTask implements CommandLineRunner {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    private ConsumerFactory consumerFactory;

    @Value("${topic}")
    private String topic;

    @Override
    public void run(String... args) throws Exception {
        long offset=getOffset();
        kafkaTemplate.setConsumerFactory(consumerFactory);


        for(ConsumerRecord<String, String> consumerRecord = kafkaTemplate.receive(topic, 0, offset); consumerRecord!=null; ){
            System.out.println(consumerRecord);
            offset++;
            consumerRecord = kafkaTemplate.receive(topic, 0, offset);
        }

        System.out.println(offset);





//        ConsumerRecords<String, String> consumerRecords = kafkaTemplate.receive(list);
//        for(Iterator<ConsumerRecord<String, String>> iterator = consumerRecords.iterator();iterator.hasNext();){
//            ConsumerRecord<String, String> next = iterator.next();
//            System.out.println(next);
//        }
    }

    private long getOffset() {
        return 0;
    }
}
