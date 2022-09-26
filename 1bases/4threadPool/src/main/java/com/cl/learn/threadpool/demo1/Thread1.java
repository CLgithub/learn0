package com.cl.learn.threadpool.demo1;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author l
 * @Date 2022/9/23 14:34
 */
public class Thread1 implements Runnable{
    @Override
    public void run() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date())+"\t业务流程："+Thread.currentThread().getId());

        try {
            Thread.sleep(1000*1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
