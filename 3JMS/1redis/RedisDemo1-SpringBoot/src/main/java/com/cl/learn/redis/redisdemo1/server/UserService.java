package com.cl.learn.redis.redisdemo1.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author l
 * @Date 2022/5/17 22:22
 */
@Service
public class UserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CacheManager cacheManager;

    public void test1(){
        redisTemplate.opsForValue().set("key1","val1");
    }

}
