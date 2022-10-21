package com.cl.learn.jvm.head;

import java.io.*;
import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * 堆内存溢出：java.lang.OutOfMemoryError: Java heap space
 *      对象太多
 *      对象太大
 *
 */
public class OutOfMemoryErrorHead {


    public static void main(String[] args) {
//        setDate();
        test2();
    }


    /**
     * 对象过多
     */
    private static void test2(){
        ArrayList<String> list= new ArrayList<>();
        String str1="a";
        String str2="b";
        long i=0;
        while (true){
            String str3=str1+str2;
            list.add(str3);
            System.out.println(i++);
        }

    }

    /**
     * 对象过大
     */
    private static void setDate() {
        String filePath="/Users/l/develop/ultrapower/ultrapowerBin/pmDataUp/log/cmccCollectServer.log";
        BufferedReader bufferedReader=null;
        StringBuffer stringBuffer=new StringBuffer();
        int i=0;
        try {
            bufferedReader=new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"GBK"));
            for(String str ="";str!=null;){
                System.out.println(i++);
                str=bufferedReader.readLine();
                stringBuffer.append(str);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(bufferedReader!=null)bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
