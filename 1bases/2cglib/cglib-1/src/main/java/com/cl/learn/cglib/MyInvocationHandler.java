package com.cl.learn.cglib;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author l
 * @Date 2022/9/12 16:22
 */
public class MyInvocationHandler implements InvocationHandler {


    private final Object o;

    public <T> MyInvocationHandler(Object o) throws InstantiationException, IllegalAccessException {
        this.o=o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().equals("test")){
            System.out.println("before... by Proxy");
            Object o1 = method.invoke(o, args);
            System.out.println("after... by Proxy");
            return o1;
        }
        return method.invoke(o, args);
    }

}
