package com.cl.learn.myspring.demo;

import com.cl.learn.myspring.spring.MySpringApplicationContext;

/**
 * @Author l
 * @Date 2022/3/26 11:53
 */
public class Test {
    public static void main(String[] args) {
        MySpringApplicationContext mySpringApplicationContext=new MySpringApplicationContext(AppConfig.class);
        UserService userService = (UserService) mySpringApplicationContext.getBean("userService");
        System.out.println(userService);
        userService.test();

    }
}
