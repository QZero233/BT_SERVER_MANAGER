package com.nasa.bt.server.manager.utils;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class StdioUtils {

    private static final Logger log=Logger.getLogger(StdioUtils.class);

    public static void writeStdio(String msg){
        System.out.println(msg);
    }

    public static String readStdio(){
        try {
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            return br.readLine();
        }catch (Exception e){
            log.error("读取标准输入流时错误",e);
            return null;
        }

    }

}
