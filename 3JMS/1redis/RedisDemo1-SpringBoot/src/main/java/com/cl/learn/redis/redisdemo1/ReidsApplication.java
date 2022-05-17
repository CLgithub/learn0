package com.cl.learn.redis.redisdemo1;

import com.cl.learn.redis.redisdemo1.server.UserService;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author l
 * @Date 2022/5/17 16:11
 */
@SpringBootApplication
public class ReidsApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext context= SpringApplication.run(ReidsApplication.class, args);
        UserService bean = context.getBean(UserService.class);
        bean.test1();
    }


}
