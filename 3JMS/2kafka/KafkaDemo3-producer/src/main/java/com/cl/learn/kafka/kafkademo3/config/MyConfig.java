package com.cl.learn.kafka.kafkademo3.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author l
 * @Date 2022/8/28 12:54
 */
@SpringBootConfiguration
public class MyConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    /**
     * 使用kafkaTemplate
     * @return
     */
    @Bean
    public KafkaTemplate<Integer, String> kafkaTemplate() {
        Map<String, Object> map = kafkaProperties.buildProducerProperties();
        DefaultKafkaProducerFactory<Integer, String> kvDefaultKafkaProducerFactory = new DefaultKafkaProducerFactory(map);
        KafkaTemplate<Integer, String> kafkaTemplate = new KafkaTemplate<>(kvDefaultKafkaProducerFactory);
        return kafkaTemplate;
    }

    /**
     * 直接创建kafkaProducer
     * @return
     */
    @Bean
    public KafkaProducer kafkaProducer(){
        Map<String, Object> map = kafkaProperties.buildProducerProperties();
        // 提高吞吐量相关配置配置
        map.put(ProducerConfig.LINGER_MS_CONFIG, 1);        // 每1ms push一次
        // 自定义分区器
        map.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartitioner.class.getName());
        // 开启幂等性
        map.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        // 指定事务id
        map.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transactional_id_01");
        // 保证单分区内有序
        map.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,5);    // 设置最多缓存5个
        KafkaProducer kafkaProducer=new KafkaProducer<>(map);
        return kafkaProducer;
    }

    /**
     * 使用DefaultKafkaProducerFactory 创建 Producer
     * 需要注意不在需要Producer时，需要调用closeThreadBoundProducer()
     * @return
     */
    @Bean
    public DefaultKafkaProducerFactory defaultKafkaProducerFactory(){
        Map<String, Object> map = kafkaProperties.buildProducerProperties();
        DefaultKafkaProducerFactory<Object, Object> defaultKafkaProducerFactory= new DefaultKafkaProducerFactory<>(map);
        return defaultKafkaProducerFactory;
    }

}
