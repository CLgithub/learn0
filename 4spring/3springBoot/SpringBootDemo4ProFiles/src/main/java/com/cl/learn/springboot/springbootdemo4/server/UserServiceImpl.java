package com.cl.learn.springboot.springbootdemo4.server;

import com.cl.learn.springboot.springbootdemo4.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author l
 * @Date 2022/5/14 23:10
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private User user;

    @Override
    public String getName() {
        return user.getName();
    }
}
