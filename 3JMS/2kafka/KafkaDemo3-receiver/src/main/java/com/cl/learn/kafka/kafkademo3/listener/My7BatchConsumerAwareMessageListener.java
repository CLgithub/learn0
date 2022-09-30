package com.cl.learn.kafka.kafkademo3.listener;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.listener.BatchConsumerAwareMessageListener;
import org.springframework.kafka.listener.BatchMessageListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 当使用自动提交或容器管理的提交方法之一时，使用此界面处理从Kafka consumer poll()操作收到的所有ConsumerRecord实例。AckMode.RECORD当您使用此接口时不受支持，因为侦听器获得了完整的批处理。提供对Consumer对象的访问权限
 * 详情参考 https://docs.spring.io/spring-kafka/docs/current/reference/html/#message-listeners
 * @Author l
 * @Date 2022/8/28 11:10
 */
@Component
public class My7BatchConsumerAwareMessageListener implements BatchConsumerAwareMessageListener {

    @Override
    public void onMessage(Object data) {
        System.out.println("BatchConsumerAwareMessageListener_listener1:"+data);
    }

    @Override
    public void onMessage(List data, Consumer consumer) {
        System.out.println("BatchConsumerAwareMessageListener_消费者："+consumer);
        System.out.println("BatchConsumerAwareMessageListener_listener2:"+data);
    }

}
