package com.cl.learn.threadpool;

import java.util.concurrent.*;

/**
 * 奇怪的问题：
 *      当使用submit提交时，一定注意从get中获取返回值才能查看异常
 *
 * @Author l
 * @Date 2022/9/27 12:01
 */
public class ExecutorsTest2 {
    public static void main(String[] args) {
        ExecutorService executorService= Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
//            Future<?> submit = executorService.submit(new DivTask(100, i));
//            try {
//                System.out.println(submit.get(10, TimeUnit.SECONDS));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            executorService.execute(new DivTask(100,i));
        }
        executorService.shutdown();
    }


    static class DivTask implements Runnable {
        int a,b;

        public DivTask(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            double r=a/b;
            System.out.println(r);
        }
    }
}
