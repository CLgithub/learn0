package com.cl.learn.jvm.stack;

/**
 * @Author l
 * @Date 2022/10/3 23:12
 */
public class StackOverFlowTest2 {
    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        test1();
    }
}
