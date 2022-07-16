package com.cl.learn.kafka.kafkademo2.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;

import java.util.HashMap;
import java.util.Properties;

/**
 * @Author l
 * @Date 2022/7/16 21:39
 */
@SpringBootConfiguration
public class MyConfig {
    @Autowired
    private DefaultKafkaProducerFactory defaultKafkaProducerFactory;

    @Bean
    public Producer producer(){
        Producer producer = defaultKafkaProducerFactory.createProducer();
        return producer;
    }
}
