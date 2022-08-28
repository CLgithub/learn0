package com.cl.learn.kafka.kafkademo3;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * @Author l
 * @Date 2022/8/27 21:05
 */
@Component
public class Task implements CommandLineRunner {

    @Resource(name = "kafkaConsumer1")
    private KafkaConsumer kafkaConsumer1;
    @Resource(name = "kafkaConsumer2")
    private KafkaConsumer kafkaConsumer2;

    @Override
    public void run(String... args) throws Exception {
       test1();
    }

    private void test1() {
        while (true){
            ConsumerRecords poll = kafkaConsumer1.poll(Duration.ofSeconds(1));
            for (Object o : poll) {
                System.out.println("consumer1:"+o);
            }
            ConsumerRecords poll2 = kafkaConsumer2.poll(Duration.ofSeconds(1));
            for (Object o : poll2) {
                System.out.println("consumer2:"+o);
            }
        }
    }

}
