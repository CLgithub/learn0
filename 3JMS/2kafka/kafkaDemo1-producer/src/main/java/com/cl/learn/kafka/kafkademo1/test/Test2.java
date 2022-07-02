package com.cl.learn.kafka.kafkademo1.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

/**
 * @Author l
 * @Date 2022/7/2 22:55
 */
@Component
public class Test2  {

    @Autowired
    private KafkaProConsu kafkaProConsu;

    @Scheduled(cron = "*/1 * * * * ?")
    public void send() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 1; i++) {
            kafkaProConsu.testSend2();

        }
    }

    @Scheduled(cron = "*/1 * * * * ?")
    public void receive(){
        kafkaProConsu.testReceive();
    }
}
