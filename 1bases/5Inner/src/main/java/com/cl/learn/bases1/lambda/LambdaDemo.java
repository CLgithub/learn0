package com.cl.learn.bases1.lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Currency;

/**
 lambda 表达式
    核心：函数作为基本运算单元，函数可以作为变量，可以接收函数，还可以返回函数
 很多单方法接口
 如：
 Comparator
 Runnable
 Callable

 只有一个方法的接口，称为FunctionalInterface 用@FunctionalInterface标记

 参考：https://www.liaoxuefeng.com/wiki/1252599548343744/1305158055100449

 */
public class LambdaDemo {
    public static void main(String[] args) {
        test3();
    }

    public static void test1(){
        String[] arr={"a","c","b"};
        // 反向排序 使用sort方法，传入排序器对象
//        Arrays.sort(arr, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return o2.compareTo(o1);
//            }
//        });
        // 反向排序 使用lambda表达式 只需要写出方法定义，将该方法当作参数传入到Arrays.sort(T[] a, Comparator<? super T> c)的第二个参数
//        Arrays.sort(arr, (o1,o2)->{return o2.compareTo(o1);});
        Arrays.sort(arr, (o1,o2)->o2.compareTo(o1));
        System.out.println(Arrays.toString(arr));

    }

    public static void test2(){
//        Runnable runnable= new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("abc");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("def");
//            }
//        };
//        for (int i = 0; i < 3; i++) {
//            new Thread(runnable).start();
//        }

        for (int i = 0; i < 3; i++) {
            new Thread(()->{
                System.out.println("abc");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("def");
            }).start();
        }
    }

    public static void test3(){
        MyInterface2 myInterface2 = new MyInterface2();
//        int i = myInterface2.m2(new MyInterface() {
//            @Override
//            public int m1() {
//                return 1;
//            }
//        }, 2);

//        int i=myInterface2.m2(()->{return 2;},1);
        int i=myInterface2.m2(()->2,1);
        System.out.println(i);

    }
}

