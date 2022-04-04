package com.cl.learn.spring.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @Author l
 * @Date 2022/4/4 11:56
 */
@Component
@Aspect // 方面 详情
public class Aspect1 {

    @Before("execution(public void com.cl.learn.spring.demo.service.UserService.test())")
    public void clBefore(JoinPoint joinPoint){
        System.out.println("clBefore");
        Object target = joinPoint.getTarget();  // 获取普通对象
        Object aThis = joinPoint.getThis();     // 获取当前代理对象
        System.out.println(target);
        System.out.println(aThis);
    }
}
