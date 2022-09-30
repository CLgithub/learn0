package com.cl.learn.kafka.kafkademo3.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.BatchMessageListener;
import org.springframework.stereotype.Component;

/**
 * 当使用自动提交(接收记录？), Kafka consumer poll()操作收到的所有ConsumerRecord实例，(onMessage(Object data)方法能没收到)
 * @Author l
 * @Date 2022/9/30 16:47
 */
@Component
public class My5BatchMessageListener implements BatchMessageListener {
    Logger logger= LoggerFactory.getLogger(My1MessageListener.class);

    @Override
    public void onMessage(Object data) {
//        ConsumerRecord consumerRecord= (ConsumerRecord) data;
        logger.info("MessageListener_listener1:"+data);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
