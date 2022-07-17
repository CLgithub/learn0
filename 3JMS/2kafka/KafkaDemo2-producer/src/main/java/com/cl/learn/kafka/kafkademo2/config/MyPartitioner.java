package com.cl.learn.kafka.kafkademo2.config;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;

/**
 * @Author l
 * @Date 2022/6/6 23:10
 */
//@Component
public class MyPartitioner implements Partitioner {

    private Random r;

    @Override
    public void configure(Map<String, ?> configs) {
        r=new Random();
    }

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        String s = value.toString();
        s=s.substring(s.length()-1);    //取出最后的数字
        int i = Integer.parseInt(s);    //变成整型
        return i % cluster.partitionCountForTopic(topic); // 对分区数取余，得到该放哪
    }

    @Override
    public void close() {

    }
}
