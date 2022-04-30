package com.cl.learn.factorypattern.simpfactory;

import com.cl.learn.factorypattern.ordinary.Operation;

/**
 * 简单工厂模式
 * 优点：同意一个工厂创建
 * 缺点：若想扩充其他方法，必须要改工厂的创建产品方法，若又条件创建，会很复制，不便于扩展
 *
 * @Author l
 * @Date 2022/3/18 17:41
 */
public class Test {
    public static void main(String[] args) throws IllegalAccessException {
        Operation operation1 = OperationFactory.createOperation("add");
        operation1.setValue1(1);
        operation1.setValue2(2);
        System.out.println(operation1.getResule());

        Operation operation2 = OperationFactory.createOperation("div");
        operation2.setValue1(1);
        operation2.setValue2(2);
        System.out.println(operation2.getResule());


    }
}
