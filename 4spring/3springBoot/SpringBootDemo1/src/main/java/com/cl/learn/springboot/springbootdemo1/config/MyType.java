package com.cl.learn.springboot.springbootdemo1.config;

import com.cl.learn.springboot.springbootdemo1.service.OrderService;
import com.cl.learn.springboot.springbootdemo1.service.UserService;
import com.cl.learn.springboot.springbootdemo1.service.impl.UserServiceImpl;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author l
 * @Date 2022/5/12 18:59
 */
@Component
public class MyType extends TypeExcludeFilter {

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        // 当扫描到某个类，其类名是order的类名时，进行排除
        return metadataReader.getClassMetadata().getClassName().equals(OrderService.class.getName());
//        return false;
    }
}
