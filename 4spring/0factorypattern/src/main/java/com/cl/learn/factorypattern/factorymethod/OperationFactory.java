package com.cl.learn.factorypattern.factorymethod;

import com.cl.learn.factorypattern.ordinary.Operation;

/**
 * @Author l
 * @Date 2022/3/18 18:02
 */
public interface OperationFactory {

    Operation createOperation();

}
