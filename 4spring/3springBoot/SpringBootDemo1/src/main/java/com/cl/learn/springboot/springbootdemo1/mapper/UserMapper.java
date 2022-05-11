package com.cl.learn.springboot.springbootdemo1.mapper;

import com.cl.learn.springboot.springbootdemo1.entity.User;
import org.apache.ibatis.annotations.Select;

/**
 * @Author l
 * @Date 2022/5/10 11:58
 */
public interface UserMapper {

    @Select("select * from user1 where id=1")
    User getUser();
}
