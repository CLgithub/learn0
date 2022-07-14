package com.cl.learn.kafka.kafkademo2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

/**
 * 利用@KafkaListener注解接收消息
 * @Author l
 * @Date 2022/7/8 10:07
 */
@Component
public class MyReceiver {

    Logger logger= LoggerFactory.getLogger(this.getClass());

//    @KafkaListener(
////            topics = "${topic}",
////            autoStartup = "${listen.auto.start:false}",   // 是否自动开始，默认true
//            concurrency = "${listen.concurrency:3}",    // 设置消费实例数，通常一个实例对应一个分区
//            topicPartitions ={
//                    @TopicPartition(topic = "${topic}", partitions = { "0","1","2","3" } // 接收哪个分区
////                            partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "0") //接收哪个分区，重启时从哪里开始
//                    )
//            }
//    )
//    private void listener1(String content){
//        logger.info("接收到消息："+content);
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }



}
