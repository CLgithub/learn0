package com.cl.learn.springdemo1.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * @Author l
 * @Date 2022/5/17 14:29
 */
@Component
@Aspect // 方面
public class Aspect1 {

    // AOP前置
    @Before("execution(* com.cl.learn.springdemo1.server.*.insert*())")
    public void myBefore(JoinPoint joinPoint){
        System.out.println("AOP------");
        System.out.println("AOP 前置通知："+joinPoint);
    }

    // AOP环绕，覆盖原方法，通过反射调用原方法
    @Around("execution(* com.cl.learn.springdemo1.server.*.insert*())")
    public void myAround(ProceedingJoinPoint joinPoint){
        try {
            // 调用原方法
            Object proceed = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    // AOP后置
    @After("execution(* com.cl.learn.springdemo1.server.*.insert*())")
    public void myAfter(JoinPoint joinPoint){
        System.out.println("AOP 后置通知："+joinPoint);
        System.out.println("AOP------");
    }

    // AOP返回通知
    @AfterReturning("execution(* com.cl.learn.springdemo1.server.*.insert*())")
    public void myAfterReturning(JoinPoint joinPoint){
        System.out.println("AOP 正常返回通知");
    }

    @AfterThrowing("execution(* com.cl.learn.springdemo1.server.*.insert*())")
    public void myAfterThrowing(JoinPoint joinPoint){
        System.out.println("AOP 异常通知");
    }



}
