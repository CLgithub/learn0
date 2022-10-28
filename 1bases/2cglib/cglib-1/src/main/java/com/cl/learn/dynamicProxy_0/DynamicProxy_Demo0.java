package com.cl.learn.dynamicProxy_0;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 动态代理
    什么是动态代理：在运行期间，动态创建某个 interface 实例
    相对于静态代理，要创建某个interface实例，需要编写其实现类

 动态代理：实际上是在 jvm 运行期间，动态创建class字节码，加载并运行的过程



 */
public class DynamicProxy_Demo0 {
    public static void main(String[] args) {
        // 静态代理
        MyInterface1 myInterface1=new MyIc1();
        myInterface1.method1();;
        // JDK动态代理
        MyInterface1 myInterface2= (MyInterface1) Proxy.newProxyInstance(DynamicProxy_Demo0.class.getClassLoader(), new Class[]{MyInterface1.class}, new MyIc2(myInterface1));
        myInterface2.method1();
        // cglib动态代理
        MyInterface1 myInterface3 = userCglib(MyIc1.class);
        myInterface3.method1();
    }

    // 使用CGLib生成代理对象
    private static <T> T userCglib(Class<T> clazz) {
        // 1 创建一个 Enhancer 对象 enhancer 增强器
        Enhancer enhancer=new Enhancer();
        // 2 设置要代理的原始类
        enhancer.setSuperclass(clazz);
        // 3 设置回调方法 方法拦截
        enhancer.setCallback(new MyIC3());
        // 4 利用enhancer 创建代理对象
        T t= (T) enhancer.create();
        return t;
    }

}


interface MyInterface1{
    void method1();
}

class MyIc1 implements MyInterface1{
    @Override
    public void method1() {
        System.out.println("静态代理 hello");
    }
}

class MyIc2 implements InvocationHandler{
    private Object o;
    public MyIc2(Object o) {
        this.o = o;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("JDK动态代理 hello");
        method.invoke(o,args);
        return null;
    }
}

class MyIC3 implements MethodInterceptor{

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("CGLIB动态代理 hello");
        methodProxy.invokeSuper(o,objects);
        return null;
    }
}
