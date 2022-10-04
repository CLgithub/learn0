package com.cl.learn.jvm.reference;

/**
 * @Author l
 * @Date 2022/10/4 20:23
 */
public class M {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize");
        super.finalize();
    }


}
