package com.cl.learn.springdemo1.config;

import com.cl.learn.springdemo1.server.UserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Author l
 * @Date 2022/5/17 11:40
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    /**
     * 前置处理器
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof UserService) {
            System.out.println("bean----------");
            System.out.println("BeanPostProcessor bean初始化前置处理器");
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    /**
     * 后置处理器
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof UserService) {
            System.out.println("BeanPostProcessor bean初始化后置处理器");
            System.out.println("bean----------");
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
