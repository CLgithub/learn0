package com.cl.learn.kafka.kafkademo3.listener;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.listener.ConsumerAwareMessageListener;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 当使用自动提交(接收记录？), Kafka consumer poll()操作收到的单个ConsumerRecord实例，提供对Consumer对象的访问权限，(onMessage(ConsumerRecord data, Consumer consumer))方法能接收到
 * @Author l
 * @Date 2022/9/30 11:49
 */
@Component
public class My3ConsumerAwareMessageListener implements ConsumerAwareMessageListener {
    @Override
    public void onMessage(ConsumerRecord data, Consumer consumer) {
//        ConsumerRecords poll = consumer.poll(Duration.ofSeconds(1));
//        for (Object o : poll) {
//            System.out.println("ConsumerAwareMessageListener_consumer_poll:"+o);
//        }
        System.out.println("ConsumerAwareMessageListener_consumer:"+consumer);
        System.out.println("ConsumerAwareMessageListener_listener2:"+data);
    }

    @Override
    public void onMessage(Object data) {
    }





    /*
consumer1:ConsumerRecord(topic = topicA, partition = 0, leaderEpoch = 48, offset = 8143, CreateTime = 1664510572158, serialized key size = -1, serialized value size = 23, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = 2022-09-30 12:02:51---3)
consumer1:ConsumerRecord(topic = topicA, partition = 0, leaderEpoch = 48, offset = 8144, CreateTime = 1664510572159, serialized key size = -1, serialized value size = 23, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = 2022-09-30 12:02:51---6)
consumer1:ConsumerRecord(topic = topicA, partition = 0, leaderEpoch = 48, offset = 8145, CreateTime = 1664510572159, serialized key size = -1, serialized value size = 23, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = 2022-09-30 12:02:51---9)
consumer2:ConsumerRecord(topic = topicA, partition = 1, leaderEpoch = 64, offset = 6319, CreateTime = 1664510572059, serialized key size = -1, serialized value size = 23, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = 2022-09-30 12:02:51---1)
consumer2:ConsumerRecord(topic = topicA, partition = 1, leaderEpoch = 64, offset = 6320, CreateTime = 1664510572159, serialized key size = -1, serialized value size = 23, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = 2022-09-30 12:02:51---4)
consumer2:ConsumerRecord(topic = topicA, partition = 1, leaderEpoch = 64, offset = 6321, CreateTime = 1664510572159, serialized key size = -1, serialized value size = 23, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = 2022-09-30 12:02:51---7)
ConsumerAwareMessageListener_consumer:org.apache.kafka.clients.consumer.KafkaConsumer@126d43a
ConsumerAwareMessageListener_listener2:ConsumerRecord(topic = topicA, partition = 2, leaderEpoch = 61, offset = 6297, CreateTime = 1664510572157, serialized key size = -1, serialized value size = 23, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = 2022-09-30 12:02:51---2)
ConsumerAwareMessageListener_consumer:org.apache.kafka.clients.consumer.KafkaConsumer@126d43a
ConsumerAwareMessageListener_listener2:ConsumerRecord(topic = topicA, partition = 2, leaderEpoch = 61, offset = 6298, CreateTime = 1664510572159, serialized key size = -1, serialized value size = 23, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = 2022-09-30 12:02:51---5)
ConsumerAwareMessageListener_consumer:org.apache.kafka.clients.consumer.KafkaConsumer@126d43a
ConsumerAwareMessageListener_listener2:ConsumerRecord(topic = topicA, partition = 2, leaderEpoch = 61, offset = 6299, CreateTime = 1664510572159, serialized key size = -1, serialized value size = 23, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = 2022-09-30 12:02:51---8)
 */
}
