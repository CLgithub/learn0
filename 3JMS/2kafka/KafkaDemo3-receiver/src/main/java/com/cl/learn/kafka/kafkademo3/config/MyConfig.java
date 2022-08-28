package com.cl.learn.kafka.kafkademo3.config;

import com.cl.learn.kafka.kafkademo3.MyMessageListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author l
 * @Date 2022/8/27 21:05
 */
@SpringBootConfiguration
public class MyConfig {

    @Autowired
    private KafkaProperties kafkaProperties;
    @Value("${spring.kafka.topics}")
    private String[] topics;

    /**
     * 通过kafka-clients 直接创建kafkaConsumer
     * @return
     */
    @Bean
    public KafkaConsumer kafkaConsumer1(){
        Map<String, Object> map = kafkaProperties.buildConsumerProperties();
        KafkaConsumer kafkaConsumer=new KafkaConsumer(map);
        List<String> list = Arrays.asList(topics);
        kafkaConsumer.subscribe(list);
        return kafkaConsumer;
    }

    /**
     * 通过spring-kafka 创建kafkaConsumer
     * @return
     */
    @Bean
    public KafkaConsumer kafkaConsumer2(){
        Map<String, Object> map = kafkaProperties.buildConsumerProperties();
        DefaultKafkaConsumerFactory<Integer, String> dkConsumerFactory = new DefaultKafkaConsumerFactory<>(map);
        KafkaConsumer kafkaConsumer = (KafkaConsumer) dkConsumerFactory.createConsumer();
        List<String> list = Arrays.asList(topics);
        kafkaConsumer.subscribe(list);
        return kafkaConsumer;
    }

    /**
     * 通过kafka消息监听容器 KafkaMessageListenerContainer 配置自定义监听器
     * 详情参考 https://docs.spring.io/spring-kafka/docs/current/reference/html/#message-listeners
     * https://docs.spring.io/spring-kafka/docs/current/reference/html/#message-listener-container
     */
    @Autowired
    private MyMessageListener myMessageListener;
    @Bean
    public KafkaMessageListenerContainer kafkaMessageListenerContainer(){
        ContainerProperties containerProps = new ContainerProperties(topics);
        containerProps.setMessageListener(myMessageListener);
        Map<String, Object> map = kafkaProperties.buildConsumerProperties();
        DefaultKafkaConsumerFactory<Integer, String> dkConsumerFactory = new DefaultKafkaConsumerFactory<>(map);
        KafkaMessageListenerContainer<Integer, String> kmListenerContainer = new KafkaMessageListenerContainer<>(dkConsumerFactory, containerProps);
        return kmListenerContainer;
    }



}
