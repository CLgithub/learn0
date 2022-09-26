package com.cl.learn.threadpool.demo1;

import java.util.concurrent.*;

/**
 * @Author l
 * @Date 2022/9/23 14:31
 */
public class Demo1 {
    public static void main(String[] args) {

        // 例子：创建一个固定线程数的线程池
        ExecutorService executorService=Executors.newFixedThreadPool(5); // fixed固定的
//        ThreadPoolExecutor executorService= (ThreadPoolExecutor) Executors.newFixedThreadPool(5); // fixed固定的
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Thread1(i));
//            System.out.println(executorService.getQueue().size());
        }
        System.out.println("abc");
    }



}
