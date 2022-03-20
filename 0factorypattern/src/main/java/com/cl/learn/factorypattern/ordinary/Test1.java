package com.cl.learn.factorypattern.ordinary;

/**
 * 定义一个计数器，算法之间解耦
 *
 * 0 不使用设计模式的一般写法
 *
 *
 * @Author l
 * @Date 2022/3/18 17:25
 */
public class Test1 {
    public static void main(String[] args) {
        //计算两数之和
        OperationAdd operationAdd = new OperationAdd();
        operationAdd.setValue1(1);
        operationAdd.setValue2(2);
        System.out.println("sum:"+operationAdd.getResule());
        //计算两数乘积
        OperationMul operationMul = new OperationMul();
        operationMul.setValue1(3);
        operationMul.setValue2(5);
        System.out.println("multiply:"+operationMul.getResule());
        //计算两数之差。。。
    }
}
