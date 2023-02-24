package com.cl.learn.bases1.lamdba1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;

/**
 * @Author l
 * @Date 2023/2/24 13:43
 *
 * 参考：https://www.liaoxuefeng.com/wiki/1252599548343744/1305207799545890
 * lambda 表达式
 *   核心：函数作为基本运算单元，函数可以作为变量，可以接收函数，还可以返回函数
 * 很多单方法接口
 * 如：
 *  Comparator
 *  Runnable
 *  Callable
 *
 * 只有一个方法的接口，称为FunctionalInterface 用@FunctionalInterface标记
 *
 */
public class LambdaDemo1 {

    public static void main(String[] args) {
        test2();
    }


    // 1、用lambda表达式代替匿名内部类，很多但方法接口，可以自动推测
    public static void test1(){
        String[] arr={"a","c","b"};
//        Arrays.sort(arr, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o1.compareTo(o2);
//            }
//        });
//        Arrays.sort(arr,(o1,o2)->{return o1.compareTo(o2);}); // lambda 表达式 代替匿名内部类
        Arrays.sort(arr,(o1,o2)-> o1.compareTo(o2));          // lambda 表达式简写

        System.out.println(Arrays.toString(arr));

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("abc");
//            }
//        }).start();
        new Thread(()-> System.out.println("abc")).start(); // lambda 表达式简写

        MyInterface2 myInterface2=new MyInterface2();
//        int i = myInterface2.m2(new MyInterface() {
//            @Override
//            public int m1(Integer i1, Integer i2) {
//                return i1+i2;
//            }
//        }, 1, 2);
//        int i=myInterface2.m2((s1,s2)->{return s1+s2;},1,2); // lambda 表达式 代替匿名内部类
        int i=myInterface2.m2((s1,s2)-> s1+s2,1,2);    // lambda 表达式简写
        System.out.println(i);
    }

    private static int compareTo(String s1, String s2) {
        return s1.compareTo(s2);
    }

    // 2.方法的引用
    public static void test2(){
        LambdaTest<Person> lambdaTest = new LambdaTest();
        Person p1 = new Person("a", 1);
        lambdaTest.like(Person::getName,p1);
//        lambdaTest.like((Person person) -> person.getName(),p1);
//        lambdaTest.like((Person person) -> {return person.getName();}, p1);
//        lambdaTest.like(new Function<Person, Object>() { // apply中调用了person.getName()，但like中做了说明，还有待like中定义
//            @Override
//            public Object apply(Person person) {
//                return person.getName();
//            }
//        }, p1);
        // like方法的参数(F)
        // 指定了F是一个函数f，该函数的传入参数类型是Person，即object=f(Person p)，该函数不是getName()方法
        // 该函数 object=f(Person p)，在函数的apply中调用了p.getName()方法，并非说当前就调用了p.getName()方法，所以getName()不一定是static
        // 但like方法中做了说明，需要在like方法中定义


        String[] arr={"a","c","b"};
//        Arrays.sort(arr,(String s1, String s2)-> s1.compareTo(s2));     // lambda 表达式 代替匿名内部类
//        Arrays.sort(arr,LambdaDemo1::compareTo);                // 静态方法引用
//        Arrays.sort(arr,String::compareTo);                       // 非静态方法引用
//        Arrays.sort(arr,(String s1, String s2)-> {return s1.compareTo(s2);});     // lambda
        Arrays.sort(arr, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);                                // 跟compareTo方法是否是静态没有关系
            }
        });     // 匿名内部类

        System.out.println(Arrays.toString(arr));

    }




}
