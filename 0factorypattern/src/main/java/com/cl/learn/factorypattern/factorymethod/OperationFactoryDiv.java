package com.cl.learn.factorypattern.factorymethod;

import com.cl.learn.factorypattern.ordinary.Operation;
import com.cl.learn.factorypattern.ordinary.OperationDiv;

/**
 * @Author l
 * @Date 2022/3/18 18:07
 */
public class OperationFactoryDiv implements OperationFactory{
    @Override
    public Operation createOperation() {
        return new OperationDiv();
    }
}
