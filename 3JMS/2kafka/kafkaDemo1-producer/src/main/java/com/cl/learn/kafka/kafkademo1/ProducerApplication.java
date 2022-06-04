package com.cl.learn.kafka.kafkademo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 将1-100的数字消息写入到Kafka中
 * @Author l
 * @Date 2022/6/4 13:31
 */
@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);

    }
}
