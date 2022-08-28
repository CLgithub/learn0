package com.cl.learn.kafka.kafkademo3;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.listener.BatchConsumerAwareMessageListener;
import org.springframework.kafka.listener.BatchMessageListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 详情参考 https://docs.spring.io/spring-kafka/docs/current/reference/html/#message-listeners
 * @Author l
 * @Date 2022/8/28 11:10
 */
@Component
public class MyMessageListener implements BatchConsumerAwareMessageListener {

    @Override
    public void onMessage(Object data) {
        System.out.println("listener:"+data);
    }

    @Override
    public void onMessage(List data, Consumer consumer) {
        System.out.println("提供消费者："+consumer);
        System.out.println("listener1:"+data);
    }

}
