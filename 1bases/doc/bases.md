# JAVA基础

## 1.手动从源文件到jar
1. 手动编写java源文件，放入src目录下
    源文件：`com/cl/bases/a/A.java`
    ```
    package com.cl.bases.a;
    
    import java.util.Date;
    import com.cl.bases.utils.Utils;
        
    public class A{    
        public static void main(String[] args) throws InterruptedException{
            while(true){
                System.out.println(sdf.format(new Date()));
                Thread.sleep(1000);
            }
        }
    }
    ```
    源文件：`com/cl/bases/ultras/Utils.java`
    ```
    package com.cl.bases.utils;

    import java.text.SimpleDateFormat;
    
    public class Utils{
        public static SimpleDateFormat SDF1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    ```
    
2. 编译源文件为class
    1. 不指定编译目录：
        ```
        javac -classpath ./src src/com/cl/bases/a/A.java
        # 编译A.java
        # -classpath ./src 指定类路径
        # A中`import com.cl.bases.utils.Utils`自定义类，编译时需要指定「查找用户类文件和注释处理程序的位置」
        # 可简写为 javac -cp ./src src/com/cl/bases/a/A.java
        
        # 编译后生成的`.class`文件在src中
        java -cp ./src com.cl.bases.a.A     # 运行
        ```
    2. 指定编译目录为`target`：
        ```
        javac -cp ./src -d ./target src/com/cl/bases/a/A.java
        # 将.class文件生成到`./target`下
        
        java -cp ./target com.cl.bases.a.A  # 运行
        ```
3. 打包class为jar
    1. 不指定清单文件打包
        ```
        jar -cvf ./out/my.jar -C ./target .
        # -cvf 创建新文件 显示详细 指定文件名
        # -C ./target . 指定target目录下所有文件
        
        java -cp ./out/my.jar com.cl.bases.a.A
        # 不指定清单文件，运行时需要指定类路径 ./out/my.jar com.cl.bases.a.A
        ```
    2. 指定清单文件打包
        1. 编写清单文件`MANIFEST.MF`，内容如下，注意⚠️Main-Class后一定要有下一空行
        ```
        Manifest-Version: 1.0
        Created-By: 1.8.0_161 (Oracle Corporation)
        Main-Class: com.cl.bases.a.A

        ```
        2. 执行命令
        ```
        jar -cvfm out/my.jar MANIFEST.MF -C ./target/ .
        # 指定清单文件打包
        
        java -cp ./out/my.jar com.cl.bases.a.A  # 运行
        java -jar ./out/my.jar  # 运行
        ```
    3. 更新清单文件
        ```
        jar -uvfm ./out/my.jar MANIFEST.MF
        ```
    * 拓展
        * SpringBoot启动：
            * springboot打包的jar，清单文件中主启动类`org.springframework.boot.loader.JarLauncher`
            * 直接指定类路径，也可以运行
                ```
                java -cp xxx-1.0-SAPSHOT1.jar org.springframework.boot.loader.JarLauncher
                # 
                ```
            * 但不能指定自己的主启动类，因为主启动类在xxx/BOOT-INF/classes 下，二不在xxx根下，需解压后，也可以分别指定所有类路径，才可运行
                ```
                java -cp \
                    xxx/:\
                    xxx/BOOT-INF/classes/:\
                    xxx/BOOT-INF/lib/spring-boot-autoconfigure-2.6.6.jar:\
                    xxx/BOOT-INF/lib/spring-boot-2.6.6.jar:\
                    ...\
                     com.cl.learn.Application
                ```
        * [包含自定义jar打包](https://github.com/CLgithub/Intellij_SpringBoot_Rebuild-Lib)
4. 运行
    各个参数结合jvm（待填）
    
## 2.JDK 与 CGLIB 动态代理
[参考](https://www.yuque.com/renyong-jmovm/dadudu/bnfwbc)
### CGLIB
* 使用：
    ```
    // 1 创建增强器
    Enhancer enhancer=new Enhancer();
    // 2 设置要被代理的类
    enhancer.setSuperclass(clazz);
    // 3 设置自定义方法拦截器
    enhancer.setCallback(new MyMethodInterceptor());
    // 4 创建代理对象
    T t= (T) enhancer.create();
    ```
* 原理：
整体思路：
    * 创建增强器，将 被代理对象 自定义方法拦截器 封装为一个key
    * 根据key，确定代理类前缀等信息，创建代理类 `data = new ClassLoaderData(loader);`
    * 得到代理类的`CGLIB$SET_THREAD_CALLBACKS`方法 并用`null 去 invoke`该方法(static的)
        * 代理类的`CGLIB$SET_THREAD_CALLBACKS`方法，对`ThreadLocal进行了set(callback[])`设置自定义方法拦截器，备用
    * 创建代理类 `ReflectUtils.newInstance(type);` asm相关知识
    * 从而在代理对象调用test方法时，能解释获取到自定义的callback，并执行其中的intercept方法，从而实现增强
        * 自定义方法拦截器中执行方法
        * 正常invoke需要走反射，比较慢
        * methodProxy.invoke 或 methodProxy.invokeSuper，最根本都是去执行 Fast类的invoke

        * Fast类中对方法标注下标，invoke时不走反射，直接根据下标去找到对应方法执行

## 3.反射以及字节码
### asm与javassist
### 反射（待填）

## 4.线程池
* 线程池线程获取流程，[详情](../4threadPool/src/main/java/com/cl/learn/threadpool/ExecutorsTest.java)
<img src='./images/1.png'>