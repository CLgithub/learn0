package com.cl.learn.factorypattern.abstractfactory.product;

/**
 * @Author l
 * @Date 2022/3/19 21:15
 */
public class WinMouse implements Mouse {
    @Override
    public void click() {
        System.out.println("win 鼠标");
    }
}
