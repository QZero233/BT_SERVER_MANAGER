package com.nasa.bt.server.manager;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ServerDataUtils {

    private static final Logger log=Logger.getLogger(ServerDataUtils.class);

    static{
        new File("data/msg/").mkdirs();
    }

    public static final String MSG_ROOT_PATH="data/msg/";
    public static final String PROPERTIES_FILE_PATH="server.properties";

    /**
     * 把消息写入本地文件
     * @param index 索引
     * @param content 内容
     * @return 是否写入成功
     */
    public static boolean writeLocalMsgContent(String index,String content){
        File file=new File(MSG_ROOT_PATH,index);
        try{
            FileOutputStream fos=new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.flush();
            fos.close();
            return true;
        }catch (Exception e){
            log.error("在将消息内容写入文件时错误",e);
            return false;
        }
    }

    public static boolean deleteLocalMsgContent(String index){
        File file=new File(MSG_ROOT_PATH,index);
        if(!file.exists())
            return false;
        return file.delete();
    }

    /**
     * 根据索引读出本地文件保存的消息内容
     * @param index 索引
     * @return 消息内容，错误返回null
     */
    public static String readLocalMsgContent(String index){
        File file=new File(MSG_ROOT_PATH,index);
        if(!file.exists())
            return null;

        try{
            FileInputStream fis=new FileInputStream(file);
            byte[] buf=new byte[(int) file.length()];
            fis.read(buf);
            fis.close();
            return new String(buf);
        }catch (Exception e){
            log.error("在读取消息文件时错误",e);
            return null;
        }
    }

}
