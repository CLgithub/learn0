package com.cl.learn.factorypattern.abstractfactory3.product;

/**
 * @Author l
 * @Date 2022/10/28 12:06
 */
public class Piano implements AbstractProduct{

    @Override
    public void run() {
        System.out.println("钢琴演奏");
    }
}
