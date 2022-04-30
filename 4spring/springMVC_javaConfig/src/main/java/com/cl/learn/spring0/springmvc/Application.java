package com.cl.learn.spring0.springmvc;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.startup.Tomcat;

/**
 * @Author l
 * @Date 2022/3/13 21:16
 */
public class Application {
    public static void run() throws Exception {
        Tomcat tomcat =new Tomcat();
        tomcat.setPort(8080);
        Context context=tomcat.addContext("/", App.class.getResource("/").getPath().replaceAll("/","/"));
        context.addLifecycleListener((LifecycleListener) Class.forName(tomcat.getHost().getConfigClass()).newInstance());
        tomcat.start();
        tomcat.getServer().await();

    }
}
