package com.cl.learn.spring0.springmvc.init;

import com.cl.learn.spring0.servlet.Servlet1;
import com.cl.learn.spring0.springmvc.config.AppConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @Author l
 * @Date 2022/3/13 15:29
 */
public class MyWebApplicationInitializer implements WebApplicationInitializer {

    /**
     * 当web容器启动的时候，这个方法回被调用
     * 为什么回被调用：
     *  servlet3 SPI service privider interface
     *  SpringServletContainerInitializer 是spring实现servlet规范的一个类（实现ServletContainerInitializer接口）
     *      这样spring就实现了servlet规范
     *  而tomcat也实现了servlet规范
     *  SpringServletContainerInitializer  中的 onstart方法就会被tomcat调用
     *  tomcat会对@HandlesTypes(WebApplicationInitializer.class)接口的实现，扫描到SpringServletContainerInitializer 的onstart方法参数的set里
     *  onstart启动时，把set中的都事例话，并调用其中的onstartup方法，即此方法
     * @param servletContext
     * @throws ServletException
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.out.println("容器启动时，被调用");
        // Load Spring web application configuration 加载springweb 项目的配置
        // 相当于创建web.xml文件
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);

        // Create and register the DispatcherServlet   创建和注册 DispatcherServlet
        // 相当于在web.xml中注册DispatcherServlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic registration = servletContext.addServlet("app", dispatcherServlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("*.do");

//        Servlet1 servlet1 = new Servlet1();
//        ServletRegistration.Dynamic servlet11 = servletContext.addServlet("servlet1", servlet1);
//        servlet11.setLoadOnStartup(1);
//        servlet11.addMapping("/servlet1");
    }
}
