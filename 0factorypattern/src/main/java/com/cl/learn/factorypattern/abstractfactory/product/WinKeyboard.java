package com.cl.learn.factorypattern.abstractfactory.product;

/**
 * @Author l
 * @Date 2022/3/19 21:14
 */
public class WinKeyboard implements Keyboard {
    @Override
    public void input() {
        System.out.println("win 鼠标");
    }
}
