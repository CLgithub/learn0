package com.cl.learn.springboot.springbootdemo1.service.impl;

import com.cl.learn.springboot.springbootdemo1.entity.User;
import com.cl.learn.springboot.springbootdemo1.mapper.UserMapper;
import com.cl.learn.springboot.springbootdemo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author l
 * @Date 2022/5/9 23:07
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User test() {
        return userMapper.getUser();
    }
}
