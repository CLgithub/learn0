package com.cl.learn.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

/**

 java反射：
    程序在运行过程中，拿到类的所有信息

 从Class去获取类的信息
    getMethods          拿到所有public的，包括父类的
    getDeclaredMethods  拿到类自身声明的

 获取一个类的Class，三种方法：
    1. 类直接获取
        Class<Class1> clazz= Class1.class;
        Class<String> clazz= String.class;
    2. 对象.getClass()方法
        Class<? extends Class1> clazz=new Class1().getClass();
    3. 完整类名获取
        Class<?> clazz= Class.forName("com.cl.learn.reflection.Class1");
 三种方法获取的对象是同一个对象 是==的

 Object o = clazz.newInstance(); 只能调用 public 无参构造方法










 */
public class ReflectionDemo {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // 运行过程中，获取到类A 的类型
        Class<?> clazz= Class.forName("com.cl.learn.reflection.Class1");
        Object o = clazz.newInstance();

        Class<? extends Class1> aClass = new Class1().getClass();

        //  拿到关于类A 的所有信息
        String name = clazz.getName();
        Package aPackage = clazz.getPackage();
        Class<?> superclass = clazz.getSuperclass();
        Field[] fields = clazz.getDeclaredFields();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        Method[] methods = clazz.getMethods();

        System.out.println(declaredMethods[0].getName());
        Method myMethod = clazz.getDeclaredMethod("myMethod");
        Object invoke = myMethod.invoke(o);
        System.out.println(invoke);


    }
}

class Class1{
    public Class1() {
        System.out.println("无参构造方法");
    }

    private String str1;

    void myMethod(){
        System.out.println("myMethod被调用");
    }

    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }
}