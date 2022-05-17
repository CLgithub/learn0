package com.cl.learn.springdemo1;

import com.cl.learn.springdemo1.config.MyConfig;
import com.cl.learn.springdemo1.server.UserService;
import com.cl.learn.springdemo1.server.UserServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// 事务 ✅
// Aware机制 ✅

// BeanPostProcessor bean初始化前后置处理器 ✅
//  1 初始化前
//  2 构造
//  3 初始化
//  4 初始化后
// bean初始化 处理器 ✅
// AOP
//  1 环绕，会覆盖原方法，但可以去调用原方法
//  2 前置
//  3 后置
//  4 5

/**
 原理：产生代理对象，在代理对象中进行加强
 *
 */

/**
 * @Author l
 * @Date 2022/5/17 11:11
 */
public class Demo1 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext(MyConfig.class);
        test1(applicationContext);
    }

    private static void test1(AnnotationConfigApplicationContext applicationContext) {
        UserService bean = (UserService) applicationContext.getBean("userService");
        bean.insertUser();
    }
}
