package com.cl.learn.factorypattern.abstractfactory.product;

/**
 * @Author l
 * @Date 2022/3/19 21:13
 */
public class MacMouse implements Mouse {
    @Override
    public void click() {
        System.out.println("Mac 鼠标");
    }
}
