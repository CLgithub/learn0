package com.cl.learn.springboot.springbootdemo3mystarter.config;

import com.cl.learn.springboot.springbootdemo3mystarter.server.Service1;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

/**
 * @Author l
 * @Date 2022/5/13 14:07
 */
@SpringBootConfiguration
public class MyStarterAutoConfiguration {

    @Bean
    @ConditionalOnWebApplication
    public Service1 service1(){
        return new Service1();
    }
}
