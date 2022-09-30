package com.cl.learn.kafka.kafkademo3.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * 单个ConsumerRecord 手动提交接收记录
 * @Author l
 * @Date 2022/9/30 11:13
 */
@Component
public class My2AcknowledgingMessageListener implements AcknowledgingMessageListener {

    @Override
    public void onMessage(Object data) {
        System.out.println("AcknowledgingMessageListener_listener1:"+data);
    }

    @Override
    public void onMessage(ConsumerRecord data, Acknowledgment acknowledgment) {
        // acknowledgment=null
        System.out.println("AcknowledgingMessageListener_listener2:"+data);
        System.out.println("AcknowledgingMessageListener_acknowledgment:"+acknowledgment);
    }

    /*
    AcknowledgingMessageListener_listener2:ConsumerRecord(topic = topicA, partition = 2, leaderEpoch = 61, offset = 6269, CreateTime = 1664508224195, serialized key size = -1, serialized value size = 23, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = 2022-09-30 11:23:43---2)
    AcknowledgingMessageListener_acknowledgment:null
    AcknowledgingMessageListener_listener2:ConsumerRecord(topic = topicA, partition = 2, leaderEpoch = 61, offset = 6270, CreateTime = 1664508224196, serialized key size = -1, serialized value size = 23, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = 2022-09-30 11:23:43---5)
    AcknowledgingMessageListener_acknowledgment:null
    AcknowledgingMessageListener_listener2:ConsumerRecord(topic = topicA, partition = 2, leaderEpoch = 61, offset = 6271, CreateTime = 1664508224197, serialized key size = -1, serialized value size = 23, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = 2022-09-30 11:23:43---8)
    AcknowledgingMessageListener_acknowledgment:null
     */

}
