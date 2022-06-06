package com.cl.learn.kafka.kafkademo1.config;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;
import java.util.Random;

/**
 * @Author l
 * @Date 2022/6/6 23:10
 */
public class MyPartitioner implements Partitioner {

    private Random r;

    @Override
    public void configure(Map<String, ?> configs) {
        r=new Random();
    }

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {

        return 1;
    }

    @Override
    public void close() {

    }
}
