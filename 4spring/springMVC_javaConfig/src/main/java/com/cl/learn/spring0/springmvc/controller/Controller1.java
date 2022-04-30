package com.cl.learn.spring0.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author l
 * @Date 2022/3/13 14:51
 */
@Controller
public class Controller1 {

    @GetMapping("/c1.do")
    @ResponseBody
    public String c1(){
        return "springMVC1";
    }
}
