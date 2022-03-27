package com.cl.learn.myspring.demo;

import com.cl.learn.myspring.spring.MySpringApplicationContext;

/**
 * 手动实现spring
 * 1 模拟底层扫描
 * 2 模拟BeanDefinition生成
 * 3 模拟getBean方法底层实现
 * 4 模拟依赖注入
 * 5 模拟Aware回调机制
 * 6 模拟初始化机制
 * 7 模拟BeanPostProcessor机制
 * 8 模拟SpringAOP机制
 *
 *
 * @Author l
 * @Date 2022/3/26 11:53
 */
public class Test {
    public static void main(String[] args) {
        MySpringApplicationContext mySpringApplicationContext=new MySpringApplicationContext(AppConfig.class);
        UserServiceInterface userService = (UserServiceInterface) mySpringApplicationContext.getBean("userService");
        System.out.println(userService);
        userService.test();

    }
}
