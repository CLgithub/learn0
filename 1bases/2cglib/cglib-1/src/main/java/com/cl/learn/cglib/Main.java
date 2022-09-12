package com.cl.learn.cglib;

import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

/**
 * @Author l
 * @Date 2022/9/12 14:56
 */
public class Main {

    public static void main(String[] args) {

        // 执行原始对象方法
        UserService userService = new UserServiceImpl();
        userService.test();
        userService.test2();

        // 执行代理对象方法
        UserService userService_proxy = userCglib(UserServiceImpl.class);
        userService_proxy.test();
        userService_proxy.test2();

    }

    // 使用CGLib生成代理对象
    private static <T> T userCglib(Class<T> clazz) {
        // 1 创建一个 Enhancer 对象 enhancer
        Enhancer enhancer=new Enhancer();
        // 2 设置要代理的原始类
        enhancer.setSuperclass(clazz);
        // 3 设置回调方法 方法拦截
        enhancer.setCallback(new MyMethodInterceptor());
        // 4 利用enhancer 创建代理对象
        T t= (T) enhancer.create();
        return t;
    }


}
