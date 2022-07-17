package com.cl.learn.kafka.kafkademo2.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaResourceFactory;

import java.util.HashMap;
import java.util.Map;
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
    public KafkaProducer kafkaProducer(){
        Map configurationProperties = defaultKafkaProducerFactory.getConfigurationProperties(); // 获取读取到的配置

        HashMap<String, Object> map= new HashMap<>();
        map.putAll(configurationProperties); // 由于DefaultKafkaProducerFactory 中，配置map是final的，所以需要复制一份
        map.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartitioner.class.getName());  // 添加自定义分区气

//        Producer producer = defaultKafkaProducerFactory.createProducer();
        KafkaProducer kafkaProducer = new KafkaProducer<>(map);
        return kafkaProducer;
    }


}
