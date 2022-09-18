package com.cl.learn.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLib自定义方法拦截器
 * @Author l
 * @Date 2022/9/12 15:14
 */
public class MyMethodInterceptor implements MethodInterceptor {
    /**
     * 对方法进行拦截
     * @param o 代理对象 class com.cl.learn.cglib.UserService$$EnhancerByCGLIB$$2947ea24
     * @param method 原始方法
     * @param objects 方法参数
     * @param methodProxy 方法代理
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if(method.getName().equals("test")){
            System.out.println(o.getClass());
            System.out.println("before... by CGLib");
            // 注意需要 方法代理对象 调用 原始方法 ，否则会无限循环
            // 正常invoke时，正常时走反射，比较慢
            // methodProxy.invoke 或 methodProxy.invokeSuper，最根本都是去执行 Fast类的invoke
            // UserServiceImpl$$FastClassByCGLIB$$cf78a71b fast(快速)，
            // methodProxy.invoke时，并没有真的去走反射，而是根据方法下标找到方法直接执行
            Object o1 = methodProxy.invokeSuper(o, objects);
            System.out.println("after... by CGLig");
            return o1;
        }
        return methodProxy.invokeSuper(o,objects);
    }
}
