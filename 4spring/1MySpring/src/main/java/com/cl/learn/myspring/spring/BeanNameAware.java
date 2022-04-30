package com.cl.learn.myspring.spring;

/**
 *
 * Aware回调机制理解
 * 一个bean，想要获取自己的某些属性，比如beanName，但beanName其实是由spring来控制的，所以需要spring来给出
 *  就设计了这样一种机制，如果某个bean实现了xxxxAware接口，spring就会去调用该bean中该接口的实现方法，从而给出某个属性给bean
 *
 */
public interface BeanNameAware {

    void setBeanName(String beanName);
}
