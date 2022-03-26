package com.cl.learn.myspring.demo;

import com.cl.learn.myspring.spring.Scope;
import com.cl.learn.myspring.spring.annotation.Autowird;
import com.cl.learn.myspring.spring.annotation.Component;
import com.cl.learn.myspring.spring.annotation.ComponentScan;
import com.cl.learn.myspring.spring.annotation.Scan;

/**
 * @Author l
 * @Date 2022/3/26 11:47
 */
@Component
@Scan(Scope.prototype)
public class UserService {

    @Autowird
    private OrderService orderService;

    public void test(){
        System.out.println(orderService);
    }
}
