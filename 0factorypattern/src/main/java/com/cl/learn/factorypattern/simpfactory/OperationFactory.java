package com.cl.learn.factorypattern.simpfactory;

import com.cl.learn.factorypattern.ordinary.*;

/**
 * 工厂类，用于创建对象
 *
 * @Author l
 * @Date 2022/3/18 17:36
 */
public class OperationFactory {
    public static Operation createOperation(String operation){
        Operation oper = null;
        switch (operation){
            case "add":
                oper = new OperationAdd();
                break;
            case "sub":
                oper = new OperationSub();
                break;
            case "mul":
                oper = new OperationMul();
                break;
            case "div":
                oper = new OperationDiv();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + operation);
        }
        return oper;
    }
}
