package com.cl.bases.a;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.cl.bases.utils.Utils;

public class A{
    public static void main(String[] args) throws InterruptedException{
        while(true){
            System.out.println(Utils.SDF1.format(new Date()));
            Thread.sleep(1000);
        }
    }
}
