# JAVA基础

## 手动从源文件到jar
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
4. 运行
    各个参数结合jvm
    
## JDK 与 CGLIB 动态代理