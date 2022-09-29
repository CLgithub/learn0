package com.cl.learn.jvm.head;

import java.io.*;
import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * @Author l
 * @Date 2022/9/29 10:26
 */
public class OutOfMemoryErrorHead {
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static void main(String[] args) {
//        setDate();
        test2();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OutOfMemoryErrorHead.class.getSimpleName() + "[", "]")
                .add("date='" + date + "'")
                .toString();
    }

    /**
     * 对象过多
     */
    private static void test2(){
        ArrayList<String> list= new ArrayList<>();
        String str1="a";
        String str2="b";
        int i=0;
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
        try {
            bufferedReader=new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"GBK"));
            for(String str ="";str!=null;){
                str=bufferedReader.readLine();
                stringBuffer.append(str);
            }
            OutOfMemoryErrorHead bigObject = new OutOfMemoryErrorHead();
            bigObject.setDate(stringBuffer.toString());
            System.out.println(bigObject);

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
