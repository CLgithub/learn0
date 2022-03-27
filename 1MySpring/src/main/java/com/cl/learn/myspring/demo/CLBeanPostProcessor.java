package com.cl.learn.myspring.demo;

import com.cl.learn.myspring.spring.BeanPostProcessor;
import com.cl.learn.myspring.spring.annotation.Component;
import com.sun.org.apache.regexp.internal.REUtil;
import sun.management.Agent;

import javax.xml.ws.spi.Invoker;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author l
 * @Date 2022/3/27 20:19
 */
@Component
public class CLBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) {
        if(beanName.equals("userService")){
            System.out.println("userService初始化前处理。。。");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) {
        if(beanName.equals("userService")){
            System.out.println("userService初始化后处理。。。");
            // 创建代理对象
            ClassLoader classLoader = Test.class.getClassLoader();  // classLoader
            Class<?>[] interfaces = bean.getClass().getInterfaces();    // bean对应的类所实现的接口

            // 代理对象，其实只是实现了bean的父接口
            Object proxyInstance = Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
                // 在执行方法时，会在这里执行
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                    if (method.getName().equals("test")) {
                        System.out.println("AOP切面逻辑"+method.getName());
//                    }
                    return method.invoke(bean, args);
                }
            });

            return proxyInstance;
        }
        return bean;
    }
}
