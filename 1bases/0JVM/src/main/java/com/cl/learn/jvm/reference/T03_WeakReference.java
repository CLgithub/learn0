package com.cl.learn.jvm.reference;

import java.lang.ref.WeakReference;

/**
 * 弱引用：遭到GC就会回收
 *
 */
public class T03_WeakReference {
    public static void main(String[] args) {
        WeakReference weakReference=new WeakReference(new M());

        System.out.println(weakReference.get());
        System.gc();
        System.out.println(weakReference.get());

        ThreadLocal threadLocal=new ThreadLocal();
        threadLocal.set(new M());
        threadLocal.remove();

    }
}
