package com.cl.learn.jvm.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * 一个对象是否有虚引用的存在，完全不会对其生存时间构成影响
 * 也无法通过虚引用来获取一个对象的实例
 * 为一个对象设置虚引用关联的唯一目的就是能在这个对象被收集器回收时收到一个系统通知
 *
 * 虚引用和弱引用对关联对象的回收都不会产生影响，
 *  如果只有虚引用或者弱引用关联着对象，那么这个对象就会被回收，不同之处在于get方法，虚引用的get方法始终返回null，弱引用的get可以返回数据
 * 弱引用可以使用ReferenceQueue，虚引用必须配合referenceQueue使用
 *
 * JDK中直接内存的回收就是虚引用，由于JVM自动内存关联的范围时堆内存
 * 而直接内存是在堆内存之外（其实是内存映射文件，）
 * 所有直接内存的分配和回收都是有Unsafe类区操作，Java在申请一块直接内存之后，会在堆内存分配一个对象，来保存在这个堆外内存的引用
 * 这个对象被垃圾收集器管理，一旦这个对象被回收，相应的用户线程会收到通知，就对直接内存进行清理工作
 *
 * 事实上，虚引用有一个很重要的用途就是做 堆外内存的释放
 * directByteBuffer就是通过虚引用来实现堆外内存的释放的
 *
 * 直接内存：直接去管理的操作系统的内存，不需要拷贝到jvm内存中
 *
 *
 */
public class T04_PhantomReference {
    private static final List<Object> LIST=new LinkedList<>();
    private static final ReferenceQueue<M> QUEUE=new ReferenceQueue<>();

    public static void main(String[] args) {
        // 构建一个虚引用对象phantomReference，并传入queue，一个虚引用指向的对象被回收时，会把信息填入queue队列，当m被回收时，能够知道被回收了
        PhantomReference phantomReference=new PhantomReference(new String("abc"), QUEUE); // phantomReference---> new M()，newM()相当于是对外内存的引用，当new M()被清理时 queue会收到消息

        new Thread(()->{
            while (true){
                LIST.add(new byte[1024 * 1024 *10]);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                System.out.println(phantomReference.get());
            }
        }).start();

        new Thread(()->{
            while (true){
                Reference pool=QUEUE.poll();
                if(pool!=null){
                    System.out.println("虚引用对象"+pool+"被jvm回收了，开始进行堆外内存清理");
                }
            }
        }).start();


    }
}
