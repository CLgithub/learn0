package com.cl.learn.jvm.reference;

import java.lang.ref.SoftReference;

/**
 *
 * 软引用：不是那么强硬的引用，当堆空间不足时，即使对象还被引用着，也可以被回收
 */
public class T02_SoftReference {
    public static void main(String[] args) {
        SoftReference softReference=new SoftReference(new byte[1024*1024*10]); // softReference--->new byte[1024*1024*10]
        System.out.println(softReference.get());    // [B@66d3c617
        System.gc();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(softReference.get());    // [B@66d3c617
        byte[] b=new byte[1024*1024*10];
        System.out.println(softReference.get());    // 空间不足被回收 null


    }
}
