package com.cl.learn.myspring.spring.annotation;

import com.cl.learn.myspring.spring.Scope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Scan {
    Scope value() default Scope.singleton;
}
