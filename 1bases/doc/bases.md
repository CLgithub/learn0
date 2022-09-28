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
    1. 更新清单文件
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
1. 运行
    各个参数结合JVM
    * JVM（Java Virtual Machine）java虚拟机
    <img src='./images/2.png'>

    * 大概流程：
        1. `类加载器`加载`Class`字节码文件到JVM中
        2. 类信息、静态变量、常量放到`方法区`
        3. 类产生的实例对象、数组放到`堆`
        4. `堆`中的对象调用方法时，会用到`程序计数器`、`虚拟机栈`、`本地方法栈`
            1. 某线程执行，会开辟一个该线程的`虚拟机栈`
            2. 调用方法m1，会在`虚拟机栈`中开辟一个栈帧，包含局部变量表、操作数、动态链接、方法返回等信息，将栈帧压入栈
            3. 方法m1调用方法m2，m2对应的栈帧也会压入栈中
        5. 方法执行时需要用到`执行引擎`
            1. 代码由`执行引擎`中的`解释器`执行
            2. 方法中的热点代码需要用`即时编译器`编译，相当于一个优化的过程
            3. `执行引擎`中有`GC`，对堆中的不再被引用的对象进行垃圾回收♻️
            4. 某些功能需要调用`本地方法接口`
    * 各内存区域作用：
        * `方法区`：存放 类信息、静态变量、常量
        * `堆`：存放 对象引用
        * `程序计数器`：某线程内，记录当前虚拟机真正执行的线程指令地址
        * `虚拟机栈`：每个线程，都会在该区域开辟一个属于自己的线程栈，线程中的每个方法对应一个栈帧，方法中包含局部变量表、操作数、动态链接、方法返回等
        * `本地方法栈`：某线程内，某些方法，并非使用java编写，而是C/C++编写，`本地方法栈`是这些方法运行区域，如`Object.clone()`方法
        
    * 问题：
        * `虚拟机栈`内存溢出 `StackOverflowError`（`-Xss`一个线程栈大小，默认1024k）：
            * 栈帧过多，方法循环递归调用
            * 栈帧过大，局部变量占用内存过大
        * `堆`内存溢出：
            * 引用过多，如两个对象相互应用，类似循环以来

    
    
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