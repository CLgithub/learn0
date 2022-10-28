package com.cl.learn.factorypattern.factory_3factorymethod;

import com.cl.learn.factorypattern.factory_0ordinary.Operation;

/**
 * 工厂方法模式，又叫「虚拟构造器」模式
 *
 * 定义一个创建对象的接口工厂，但让实现这个接口工厂的类工厂来决定实例化哪个产品（创建哪个类），工厂方法模式让产品实例化推迟到子工厂中去进行
 *
 * 优点：若扩充其他方法，只需要定义方法，并实现工厂接口即可
 *      使用者可以不需要知道产品类的类名（比如OperationAdd），只需要所对应的工厂（OperationFactoryAdd）即可
 *
 * @Author l
 * @Date 2022/3/18 17:56
 */
public class Test {

    public static void main(String[] args) throws IllegalAccessException {

        OperationFactory operationFactory=new OperationFactoryAdd();
        Operation operation = operationFactory.createOperation();
        operation.setValue1(1);
        operation.setValue2(1);
        System.out.println(operation.getResule());

        OperationFactory operationFactory1 = new OperationFactorySub();
        Operation operation1 = operationFactory1.createOperation();
        operation1.setValue1(1);
        operation1.setValue2(2);
        System.out.println(operation1.getResule());



    }

}
