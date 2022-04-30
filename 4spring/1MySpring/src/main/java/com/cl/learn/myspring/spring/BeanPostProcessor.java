package com.cl.learn.myspring.spring;

/**
 * bean后置处理器
 * 用于想要在bean创建过程中，干预某些事
 *
 */
public interface BeanPostProcessor {

    // 初始化前
    public Object postProcessBeforeInitialization(String beanName, Object bean);

    // 初始化后
    public Object postProcessAfterInitialization(String beanName, Object bean);


}
