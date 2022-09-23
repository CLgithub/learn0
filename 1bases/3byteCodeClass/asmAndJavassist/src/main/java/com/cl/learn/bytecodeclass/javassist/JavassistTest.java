package com.cl.learn.bytecodeclass.javassist;

import javassist.*;

import java.io.IOException;

/**
 * @Author l
 * @Date 2022/9/23 11:26
 */
public class JavassistTest {
    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException {
        ClassPool classPool=ClassPool.getDefault();

        // 1. 创建一个空类
        CtClass person2_CtClass = classPool.makeClass("com.cl.Person2");

        // 2. 新建一个字段 private String name;
        // 设置变量名为name 从属于person2_CtClass
        CtField name_CtField=new CtField(classPool.get("java.lang.String"), "name", person2_CtClass);
        // 设置访问级别
        name_CtField.setModifiers(Modifier.PRIVATE);
        // 初始化值
        person2_CtClass.addField(name_CtField, CtField.Initializer.constant("aaa"));

        // 3. 生成getter、setter方法
        person2_CtClass.addMethod(CtNewMethod.setter("setName", name_CtField));
        person2_CtClass.addMethod(CtNewMethod.getter("setName", name_CtField));

        // 4. 添加无参数构造方法
        CtConstructor ctConstructor=new CtConstructor(new CtClass[]{}, person2_CtClass);
        ctConstructor.setBody("{name=\"aaa\";}");
        person2_CtClass.addConstructor(ctConstructor);

        // 5. 添加有参构造函数
        ctConstructor=new CtConstructor(new CtClass[]{classPool.get("java.lang.String")}, person2_CtClass);
        ctConstructor.setBody("{$0.name=$1;}");
        person2_CtClass.addConstructor(ctConstructor);

        // 6. 创建一个名为printNmae方法，无参数，无返回值，输出name的值
        CtMethod ctMethod=new CtMethod(CtClass.voidType, "printName", new CtClass[]{}, person2_CtClass);
        ctMethod.setModifiers(Modifier.PUBLIC);
        ctMethod.setBody("{System.out.println(name);}");
        person2_CtClass.addMethod(ctMethod);

        // 添加main方法
        ctMethod=new CtMethod(CtClass.voidType, "main", new CtClass[]{classPool.get("java.lang.String[]")}, person2_CtClass);
        ctMethod.setModifiers(Modifier.PUBLIC+Modifier.STATIC);
        ctMethod.setBody("{System.out.println(\"abc\");}");
        person2_CtClass.addMethod(ctMethod);


        // 写出到class文件
        person2_CtClass.writeFile("/Users/l/develop/clProject/0-java/0-intellij/Learn0/1bases/3byteCodeClass/asmAndJavassist/out/Person2.class");
    }
}
