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
        System.out.println(configurationProperties);
        HashMap<String, Object> map= new HashMap<>();
        map.putAll(configurationProperties); // 由于DefaultKafkaProducerFactory 中，配置map是final的，所以需要复制一份
        // 自定义分区器
        map.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartitioner.class.getName());
        // 提高吞吐量相关配置配置
        map.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 1024*1024*32); // 总缓存区 32M
        map.put(ProducerConfig.BATCH_SIZE_CONFIG, 1024*16); // 每批次 16k
        map.put(ProducerConfig.LINGER_MS_CONFIG, 1);        // 每1ms push一次
        map.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");  // 压缩类型
        // acks
        map.put(ProducerConfig.ACKS_CONFIG, "-1"); // 1不等回应直接下一条 0leader节点回应 -1所有都
        map.put(ProducerConfig.RETRIES_CONFIG,3);   // 该配置最大可配置int最大，2^31
        // 开启幂等性
        map.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        // 指定事务id
        map.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transactional_id_01");

//        Producer producer = defaultKafkaProducerFactory.createProducer();
        KafkaProducer kafkaProducer = new KafkaProducer<>(map);
        return kafkaProducer;
    }


}
