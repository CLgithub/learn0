package com.cl.learn.jvm.methodarea;


import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;


/**
 * 测试方法区内存溢出：加载太多类
 * 1.7
 *  java.lang.OutOfMemoryError: PermGen space
 *  -XX:MaxPermSize=8m
 *
 * 设置方法
 *      command+;   sources>Language level,dependencies>Module SDK
 *      command+,   compiler>Java Compiler>Per-module bytecode version: , Override compiler parameters per-module:
 *
 *
 * 1.8
 *  java.lang.OutOfMemoryError: Metaspace
 *  -XX:MaxMetaspaceSize=8m
 *
 *
 */
public class MethodAreaTest1 {
    public static void main(String[] args) {
        String property = System.getProperty("java.version");
        System.out.println(property);
        MyClassLoader myClassLoader = new MyClassLoader();
        int j=0;
        try {
            for (int i = 0; i < 30000; i++,j++) {
                myClassLoader.myload("Class"+i);
            }
        } finally {
            System.out.println(j);
        }
    }
}

class MyClassLoader extends ClassLoader{

    public void myload(String className){
        ClassWriter classWriter=new ClassWriter(0);
        // 通过visit方法确定类的头部信息    visit 访问拜访
        classWriter.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC, className, null, "java/lang/Object", null );
        byte[] bytes = classWriter.toByteArray();
        this.defineClass(className, bytes, 0, bytes.length);
    }



}