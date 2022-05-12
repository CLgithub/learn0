package com.cl.learn.springboot.springbootdemo1.config;

import com.cl.learn.springboot.springbootdemo1.entity.Order;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author l
 * @Date 2022/5/12 19:18
 */
public class MyApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        applicationContext.getBeanFactory().registerSingleton("MyType",new MyType());
    }
}
