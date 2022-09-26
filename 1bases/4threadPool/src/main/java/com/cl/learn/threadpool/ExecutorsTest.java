package com.cl.learn.threadpool;

import com.cl.learn.threadpool.demo1.Thread1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * corePool -> 核心线程池
 * maximumPool -> 线程池
 * BlockQueue -> 队列
 * RejectedExecutionHandler -> 拒绝策略
 *
 * Executors是一个线程池工厂，提供了很多的工厂方法
 * 核心构造方法
 *  public ThreadPoolExecutor(int corePoolSize,
 *                               int maximumPoolSize,
 *                               long keepAliveTime,
 *                               TimeUnit unit,
 *                               BlockingQueue<Runnable> workQueue,
 *                               ThreadFactory threadFactory,
 *                               RejectedExecutionHandler handler)
 *
 *
 */
public class ExecutorsTest {
    public static void main(String[] args) {
        ExecutorService executorService = newFixedThreadPool();
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Thread1());
        }
        System.out.println("准备关闭");
        executorService.shutdown();
    }


    // 单线程线程池
    // 最大线程池maximumPool=1，等待队列BlockQueue=Integer.MAX_VALUE
    // 当线程空闲的时候，按照FIFO的方式进行处理
    public static ExecutorService newSingleThreadExecutor(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        return executorService;
    }

    // 固定数量线程池
    // 最大线程池maximumPool=指定，等待队列BlockQueue=Integer.MAX_VALUE
    // 如果线程的数量未达到指定数量，则创建新线程来执行任务
    // 如果线程池的数量达到了指定数量，并且有线程是空闲的，则取出空闲线程执行任务
    // 若无空闲线程，则将任务缓存到队列，待有空闲线程时执行 FIFO
    public static ExecutorService newFixedThreadPool(){
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        return executorService;
    }

    // 带缓存线程池
    // 核心线程池corePool=0，最大线程池maximumPool=Integer.MAX_VALUE, 等待队列BlockQueue=SynchronousQueue
    // 由于使用SynchronousQueue(同步队列)，导致每往队列例插入一个元素，必须等待另一个线程从这个队列删除一个元素
    public static ExecutorService newCachedThreadPool(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        return executorService;
    }
    // 定时调度线程池
    public static ScheduledExecutorService newScheduledThreadPool(){
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        return scheduledExecutorService;
    }
    // 流式（fork-join）线程池
    public static ExecutorService newWorkStealingPool(){
        ExecutorService executorService = Executors.newWorkStealingPool();
        return executorService;
    }


}
