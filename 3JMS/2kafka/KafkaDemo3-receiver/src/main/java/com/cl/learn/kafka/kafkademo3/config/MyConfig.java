package com.cl.learn.kafka.kafkademo3.config;

import com.cl.learn.kafka.kafkademo3.listener.My1MessageListener;
import com.cl.learn.kafka.kafkademo3.listener.My2AcknowledgingMessageListener;
import com.cl.learn.kafka.kafkademo3.listener.My3ConsumerAwareMessageListener;
import com.cl.learn.kafka.kafkademo3.listener.My5BatchMessageListener;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.RoundRobinAssignor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
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
//    @Bean
//    public KafkaConsumer kafkaConsumer1(){
//        Map<String, Object> map = kafkaProperties.buildConsumerProperties();
//        KafkaConsumer kafkaConsumer=new KafkaConsumer(map);
//        List<String> list = Arrays.asList(topics);
//        kafkaConsumer.subscribe(list);
//        return kafkaConsumer;
//    }
//
//    /**
//     * 通过spring-kafka 创建kafkaConsumer
//     * @return
//     */
//    @Bean
//    public KafkaConsumer kafkaConsumer2(){
//        Map<String, Object> map = kafkaProperties.buildConsumerProperties();
//        DefaultKafkaConsumerFactory<Integer, String> dkConsumerFactory = new DefaultKafkaConsumerFactory<>(map);
//        KafkaConsumer kafkaConsumer = (KafkaConsumer) dkConsumerFactory.createConsumer();
//        List<String> list = Arrays.asList(topics);
//        kafkaConsumer.subscribe(list);
//        return kafkaConsumer;
//    }

    /**
     * 通过kafka消息监听容器 KafkaMessageListenerContainer 配置自定义监听器
     * 详情参考 https://docs.spring.io/spring-kafka/docs/current/reference/html/#message-listeners
     * https://docs.spring.io/spring-kafka/docs/current/reference/html/#message-listener-container
     */
    @Autowired
    private My1MessageListener myListener;

//    @Bean
//    public KafkaMessageListenerContainer kafkaMessageListenerContainer(){
//        ContainerProperties containerProps = new ContainerProperties(topics);
//        containerProps.setMessageListener(myListener);
//        Map<String, Object> map = kafkaProperties.buildConsumerProperties();
//        DefaultKafkaConsumerFactory<Integer, String> dkConsumerFactory = new DefaultKafkaConsumerFactory<>(map);
//        KafkaMessageListenerContainer<Integer, String> kmListenerContainer = new KafkaMessageListenerContainer<>(dkConsumerFactory, containerProps);
//        return kmListenerContainer;
//    }

    // 多线程接收多主题多分区，每个主题每个分区一个线程，大大提升收取效率
    @Bean
    public ConcurrentMessageListenerContainer concurrentMessageListenerContainer(){
        ContainerProperties containerProps = new ContainerProperties(topics);
        containerProps.setMessageListener(myListener);
        Map<String, Object> map = kafkaProperties.buildConsumerProperties();
        // 要实现一个主题一个分区一个线程，必须这样配置 参考 https://docs.spring.io/spring-kafka/docs/current/reference/html/#message-listener-container When listening to multiple topics, the default ...
        map.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, RoundRobinAssignor.class.getName());

        DefaultKafkaConsumerFactory<Integer, String> dkConsumerFactory = new DefaultKafkaConsumerFactory<>(map);
        ConcurrentMessageListenerContainer concurrentMessageListenerContainer = new ConcurrentMessageListenerContainer(dkConsumerFactory, containerProps);

        concurrentMessageListenerContainer.setConcurrency(9); // 允许的最大现场数
        int concurrency = concurrentMessageListenerContainer.getConcurrency();
        System.out.println("concurrency:"+concurrency);
        return concurrentMessageListenerContainer;
    }

//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory(ConsumerFactory<Integer, String> consumerFactory) {
//        ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory);
//        factory.setConcurrency(15);
//        return factory;
//    }



}
