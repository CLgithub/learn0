package com.cl.learn.jvm.stack;

/**
 * 栈内存溢出：stackOverFlowError
 *      栈帧太多: 方法递归调用，不断压栈
 */
public class StackOverFlowTest2 {
    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        test1();
    }
}
