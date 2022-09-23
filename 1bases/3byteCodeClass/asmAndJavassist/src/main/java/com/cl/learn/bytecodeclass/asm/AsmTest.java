package com.cl.learn.bytecodeclass.asm;


import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author l
 * @Date 2022/9/23 10:47
 */
public class AsmTest {

    public static void main(String[] args) throws IOException {
        ClassWriter classWriter=new ClassWriter(0);
        // 通过visit方法确定类的头部信息    visit 访问拜访
        classWriter.visit(Opcodes.V1_8, // java版本
                Opcodes.ACC_PUBLIC,     // 类修饰符
                "Person",       // 类名
                null, "java/lang/Object", null
                );

        // 创建构造函数
        MethodVisitor mv = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD,0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(1,1);
        mv.visitEnd();

        // 定义test方法
        MethodVisitor methodVisitor=classWriter.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream");
        methodVisitor.visitLdcInsn("hello aaa");
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/PrintStream", "println", "(Ljava/lang/String;)V");
        methodVisitor.visitInsn(Opcodes.RETURN);
        methodVisitor.visitMaxs(2,2);
        methodVisitor.visitEnd();

        classWriter.visitEnd();

        byte[] bytes = classWriter.toByteArray();
        File classFile=new File("/Users/l/develop/clProject/0-java/0-intellij/Learn0/1bases/3byteCodeClass/asmAndJavassist/out/Person.class");
        FileOutputStream fileOutputStream=new FileOutputStream(classFile);
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();

    }
}
