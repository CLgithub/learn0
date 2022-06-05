package com.cl.learn.kafka.kafkademo1.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Properties;

/**
 * @Author l
 * @Date 2022/6/4 17:44
 */
@SpringBootConfiguration
public class MyConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public KafkaProducer kafkaProducer(){
        System.out.println("---------------"+kafkaProperties.getBootstrapServers());
        Properties properties=new Properties();
        properties.put("bootstrap.servers", kafkaProperties.getBootstrapServers());
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());
        KafkaProducer<Object,Object> kafkaProducer=new KafkaProducer<>(properties);
        return kafkaProducer;
    }

    @Bean
    public KafkaConsumer kafkaConsumer(){
        Properties properties=new Properties();
        properties.put("bootstrap.servers", kafkaProperties.getBootstrapServers());
        properties.put("group.id", kafkaProperties.getConsumer().getGroupId());
        properties.put("key.deserializer", StringDeserializer.class.getName());
        properties.put("value.deserializer", StringDeserializer.class.getName());
        KafkaConsumer<Object,Object> kafkaConsumer = new KafkaConsumer<>(properties);
        return kafkaConsumer;
    }

}
