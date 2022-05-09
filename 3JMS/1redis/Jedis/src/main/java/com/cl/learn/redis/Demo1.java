package com.cl.learn.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author l
 * @Date 2022/5/6 15:57
 */
public class Demo1 {

    public static void main(String[] args) {
        // 获取连接池
        JedisPool pool = new JedisPool("homeUbuntu", 6379);
        // 获取jedis客户端
        Jedis jedis = pool.getResource();
        String select = jedis.select(2);

//        testKey(jedis);
//        testString(jedis);
//        testList(jedis);
//        testSet(jedis);
//        testHash(jedis);
        testZset(jedis);

    }

    // 有序不可重复
    private static void testZset(Jedis jedis) {
        jedis.zadd("zset01", 100d, "z3");
        jedis.zadd("zset01", 90d, "l4");
        jedis.zadd("zset01", 80d, "w5");
        jedis.zadd("zset01", 70d, "z6");
        jedis.zadd("zset01", 70, "z7");

        List<String> zset01 = jedis.zrange("zset01", 0, -1);
        System.out.println(zset01);
        List<String> zset011 = jedis.zrevrangeByScore("zset01", 90, 70);
        System.out.println(zset011);

    }

    // 存储对象
    private static void testHash(Jedis jedis){
        jedis.hset("hash1","userName","lisi");
        System.out.println(jedis.hget("hash1","userName"));
        Map<String,String> map = new HashMap<String,String>();
        map.put("telphone","133xxxxxxxx");
        map.put("address","atguigu");
        map.put("email","abc@163.com");
        jedis.hmset("hash2",map);
        List<String> result = jedis.hmget("hash2", "telphone","email");
        for (String element : result) {
            System.out.println(element);
        }

    }

    // set
    private static void testSet(Jedis jedis) {
        jedis.sadd("set1","a");
        jedis.sadd("set1","b");
        Set<String> smembers = jedis.smembers("set1");
        System.out.println(smembers);
    }

    // list
    private static void testList(Jedis jedis) {
        jedis.lpush("list1","a","b","c");
        jedis.lpush("list1","a","b2","c2");
        List<String> list1 = jedis.lrange("list1", 0, -1);
        System.out.println(list1);
    }

    // 一次设置多个 k-v
    private static void testString(Jedis jedis) {
        jedis.mset("str1","v1","str2","v2","str3","v3");
        System.out.println(jedis.mget("str1","str2","str3"));
    }

    // k-v
    public static void testKey(Jedis jedis){
        jedis.set("k1","v1");
        jedis.set("k2","v2");
        jedis.set("k3","v3");

        Set<String> keys = jedis.keys("*"); // 得到所有key
        for (String key : keys) {
            System.out.println(key);
        }

        System.out.println(jedis.exists("k1")); // 判断k1是否存在
        System.out.println(jedis.ttl("k1"));    // 获取k1的有效时间
        System.out.println(jedis.get("k1"));         // 获取k1的值

    }
}
