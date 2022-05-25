package com.cl.learn.redis.redisdemo2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 redis锁应用测试
 不加锁时，在命令行中输入 ab -n 1000 -c 100 http://127.0.0.1:8080/testLock
 模拟高并发
 正常业务逻辑，num应该为10000，却只为30

 加锁
 业务逻辑正常，num=1000

 但存在问题：代码异常导致锁不能释放
 解决：设置过期时间
 新问题：未执行完，锁自动释放，别人加锁，执行完成后，把别人的锁释放了
 解决：uuid实现唯一值，若释放时不是该值，就不释放，就不会释放别人的锁
 提前释放问题依然未解决，且删除操作需要先判断，再删除，非原子性，也存在问题
 lua可以解决原子性问题

 自己的思路：用finally来保证释放

 * @Author l
 * @Date 2022/5/25 23:27
 */
@RestController
public class TestController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/testLock")
    public void testLock(){
        // 设置一个锁 为了避免代码异常导致锁不能释放，设置过期时间
        // 但若还未执行完成，就提前释放了锁，
        String uuid = UUID.randomUUID().toString();
//        System.out.println(uuid);
        Boolean lock= redisTemplate.opsForValue().setIfAbsent("lock", uuid,3, TimeUnit.MILLISECONDS);
        if(lock){ // 若获取到锁
            Object value = redisTemplate.opsForValue().get("num");
            if(ObjectUtils.isEmpty(value)){
                return;
            }
            int num=Integer.parseInt(value+"");
            num+=1;
            redisTemplate.opsForValue().set("num",num);

            if(uuid.equals(redisTemplate.opsForValue().get("lock"))){
                redisTemplate.delete("lock"); // 释放锁
            }
        }else{
            try {
                Thread.sleep(100);
                testLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    @GetMapping("/testLock2")
    public void testLock2(){
        Boolean lock= redisTemplate.opsForValue().setIfAbsent("lock", "aaa");
        if(lock){ // 若获取到锁
            try {
                Object value = redisTemplate.opsForValue().get("num");
                if(ObjectUtils.isEmpty(value)){
                    return;
                }
                int num=Integer.parseInt(value+"");
                num+=1;
                redisTemplate.opsForValue().set("num",num);
            }catch (Exception e){
                e.printStackTrace();
            } finally {
                redisTemplate.delete("lock"); // 释放锁
            }
        }else{
            try {
                Thread.sleep(100);
                testLock2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }


}
