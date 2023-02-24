package com.cl.learn.bases1.lambda;

import java.util.function.Function;

/**
 * @Author l
 * @Date 2023/2/24 11:34
 */
public class MyInterface3 {

    /**
     * 定义一个方法，第一个参数是一个方法，
     * @Author chenlei
     * @updateDate 2023/2/24 11:42
	 * @param function
	 * @param i
     * @return
     */
    public int m3(Function<Integer, Integer> function, int i){
        return function.apply(i);
    }

    public static Integer m3_(Integer i){
        return i+1;
    }

}
