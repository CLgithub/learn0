package com.cl.learn.threadpool;

import com.cl.learn.threadpool.demo1.Thread1;

import java.util.List;
import java.util.concurrent.*;

/**
 * corePool -> 核心线程池
 * BlockQueue -> 队列
 * maximumPool -> 最大线程池
 * RejectedExecutionHandler -> 拒绝策略
 *
 * Executors是一个线程池工厂，提供了很多的工厂方法
 * 核心构造方法
 *  public ThreadPoolExecutor(int corePoolSize,             // 核心线程数
 *                               int maximumPoolSize,       // 最大线程数
 *                               long keepAliveTime,        // 空闲时间，超过核心线程数的线程的存活时间
 *                               TimeUnit unit,             // 空闲时间单位
 *                               BlockingQueue<Runnable> workQueue,     // 等待队列，当线程池中的数量超过核心线程数
 *                               ThreadFactory threadFactory,           // 线程工程
 *                               RejectedExecutionHandler handler)      // 拒绝策略，当线程池和等待队列都满后，通过该对象的回调函数进行回调处理
 *
 * 重点看 等待队列 线程工程 拒绝策略
 * 等待队列 BlockingQueue 只要是它的子类，都可以用来做等待队列
 *      jdk内部自带一些阻塞队列
 *          ArrayBlockingQueue  队列是有界的，基于数组实现的阻塞队列
 *          LinkedBlockingQueue 队列可以有界，也可以无界。基于链表实现的阻塞队列
 *          SynchronousQueue    不存储元素的阻塞队列，每个插入操作必须等到另一个线程调用移除操作，否则插入操作将一直处于阻塞状态. newCachedThreadPool 默认使用该队列
 *          PriorityBlockingQueue   带优先级的无界阻塞队列
 *  通常情况，我们需要指定阻塞队列的上界
 * 线程工程 ThreadFactory
 *      一个接口，作用是创建线程，Runnable子类最终要执行，需要 new Thread(Runnable r)，该参数即做这事
 *      不指定时，默认使用Executors#DefaultThreadFactory
 *              默认线程名为：pool-{poolNum}-thread-{threadNum}
 * 拒绝策略 RejectedExecutionHandler
 *      当线程池满，队列也满，需要拒绝策略
 *      ThreadPoolExecutor 中 自带4种拒绝策略
 *          1. CallerRunsPolicy     在调用者线程执行
 *          2. AbortPolicy          直接抛出RejectedExecutionException异常
 *          3. DiscardPolicy        不做任何处理
 *          4. DiscardOldestPolicy  丢弃队列里最旧的那个任务，再尝试执行当前任务
 *
 * 提交任务
 *      execute()   用于提交不需要返回结果的任务
 *      submit()    用于提交需要返回结果的任务
 *          返回 Future<?> future
 *              Object o=future.get(long timeout, TimeUnit unit) 该方法为阻塞方法，参数设置超时时间
 *
 * 关闭线程
 *      shutdown()    会将线程池状态置为SHUTDOWN，不再接受新的任务，同时会等待线程池中已有的任务执行完成再结束
 *      shutdownNow()   会将线程池状态置为SHUTDOWN，对所有线程执行interrupt()操作，清空队列，并将队列中的任务返回回来
 *
 * isShutdown() 和 isTerminated，分别表示是否关闭和是否终止
 *
 * int cpuNum = Runtime.getRuntime().availableProcessors();    // 获取cpu个数
 *
 * 线程池监控
 *      ThreadPoolExecutor 自带的一些方法
 *          1. long getTaskCount()，获取已经执行或正在执行的任务数
 *          2. long getCompletedTaskCount()，获取已经执行的任务数
 *          3. int getLargestPoolSize()，获取线程池曾经创建过的最大线程数，根据这个参数，我们可以知道线程池是否满过
 *          4. int getPoolSize()，获取线程池线程数
 *          5. int getActiveCount()，获取活跃线程数（正在执行任务的线程数)
 *
 */
public class ExecutorsTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executorService = createMyPool1();
        for (int i = 0; i < 10; i++) {
            Thread.sleep(100);
            Future<?> future = executorService.submit(new Thread1(i));
        }
        System.out.println("准备关闭");
        executorService.shutdown();
    }

    /**
     *
     */
    public static ThreadPoolExecutor createMyPool1(){
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(2,
                3,
                30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        return threadPoolExecutor;
        // 111 --- 0(2) 1(4) 3(6) 5(8) 7 9
        // 112 --- 0(3) 1(5) 2(7) 4(9) 6 8
        // 113 --- 0(4) 1(6) 2(8) 3 5 7 9
        // 114 --- 0(5) 1(7) 2(9) 3 4 6 8
        // ...
        // 121 --- 02(3) 14(6) 57(9) 8
        // 122 --- 03(4) 12(7) 56 89
        // 123 --- 04(5) 12(8) 36 79
        // 127 --- 08(9) 12 34 56 7
        // ...
        // 131 --- 023(4) 156(8) 7 9
        // 132 --- 034(5) 126(9) 78
        // ...
        // 231 --- 013(4) 256(8) 79
        // 222 --- 01(4) 23(7) 56 89
        // 352 --- 01256(7) 3489
        // 232 --- 014(5) 236(9) 78

    }




    // 单线程线程池
    // 最大线程池maximumPool=1，等待队列BlockQueue=Integer.MAX_VALUE 0x7fffffff = 0111 1111 1111 1111 1111 1111 1111 1111 = 2147483647 =2^31-1
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
