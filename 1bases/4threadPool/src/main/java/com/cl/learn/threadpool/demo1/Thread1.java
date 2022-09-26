package com.cl.learn.threadpool.demo1;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author l
 * @Date 2022/9/23 14:34
 */
public class Thread1 implements Runnable{

    private int num;

    public Thread1(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date())+"\t业务流程-"+num+"："+Thread.currentThread().getId()+":"+Thread.currentThread().getName());

        try {
            Thread.sleep(1000*4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
