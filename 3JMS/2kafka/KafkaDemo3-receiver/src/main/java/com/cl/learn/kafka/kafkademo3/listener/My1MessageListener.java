package com.cl.learn.kafka.kafkademo3.listener;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.BatchConsumerAwareMessageListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 当使用自动提交(接收记录？), Kafka consumer poll()操作收到的单个ConsumerRecord实例，(onMessage(Object data)方法能没收到)
 * @Author l
 * @Date 2022/8/28 11:10
 */
@Component
public class My1MessageListener implements MessageListener {

    Logger logger=LoggerFactory.getLogger(My1MessageListener.class);

    @Override
    public void onMessage(Object data) {
        ConsumerRecord consumerRecord= (ConsumerRecord) data;
        logger.info("MessageListener_listener1:"+consumerRecord.value());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



/*
consumer1:ConsumerRecord(topic = topicA, partition = 0, leaderEpoch = 48, offset = 8127, CreateTime = 1664509635756, serialized key size = -1, serialized value size = 23, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = 2022-09-30 11:47:15---3)
consumer1:ConsumerRecord(topic = topicA, partition = 0, leaderEpoch = 48, offset = 8128, CreateTime = 1664509635757, serialized key size = -1, serialized value size = 23, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = 2022-09-30 11:47:15---6)
consumer1:ConsumerRecord(topic = topicA, partition = 0, leaderEpoch = 48, offset = 8129, CreateTime = 1664509635757, serialized key size = -1, serialized value size = 23, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = 2022-09-30 11:47:15---9)
MessageListener_listener1:ConsumerRecord(topic = topicA, partition = 2, leaderEpoch = 61, offset = 6281, CreateTime = 1664509635754, serialized key size = -1, serialized value size = 23, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = 2022-09-30 11:47:15---2)
MessageListener_listener1:ConsumerRecord(topic = topicA, partition = 2, leaderEpoch = 61, offset = 6282, CreateTime = 1664509635757, serialized key size = -1, serialized value size = 23, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = 2022-09-30 11:47:15---5)
MessageListener_listener1:ConsumerRecord(topic = topicA, partition = 2, leaderEpoch = 61, offset = 6283, CreateTime = 1664509635757, serialized key size = -1, serialized value size = 23, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = 2022-09-30 11:47:15---8)
consumer2:ConsumerRecord(topic = topicA, partition = 1, leaderEpoch = 64, offset = 6303, CreateTime = 1664509635647, serialized key size = -1, serialized value size = 23, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = 2022-09-30 11:47:15---1)
consumer2:ConsumerRecord(topic = topicA, partition = 1, leaderEpoch = 64, offset = 6304, CreateTime = 1664509635757, serialized key size = -1, serialized value size = 23, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = 2022-09-30 11:47:15---4)
consumer2:ConsumerRecord(topic = topicA, partition = 1, leaderEpoch = 64, offset = 6305, CreateTime = 1664509635757, serialized key size = -1, serialized value size = 23, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = 2022-09-30 11:47:15---7)
 */
}
