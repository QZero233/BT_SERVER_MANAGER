package com.nasa.bt.server.manager;

import com.nasa.bt.server.manager.commands.CommandFactory;
import com.nasa.bt.server.manager.commands.ManagerCommand;
import com.nasa.bt.server.manager.utils.StdioUtils;
import org.hibernate.Session;

import java.util.Map;
import java.util.Set;

public class ManagerMain {

    public static void main(String[] args) {
        System.out.println("欢迎使用BugTelegram服务器管理程序");
        System.out.println("此程序为仿终端版");
        System.out.println("Author QZero\nAll Rights Reserved");

        initDb();

        while(true){
            System.out.print("请输入操作名称：");
            String action= StdioUtils.readStdio();
            if(action.equalsIgnoreCase("exit"))
                break;
            ManagerCommand managerCommand= CommandFactory.getCommand(action);
            if(managerCommand==null){
                System.out.println("不存在此操作，请重试");
                continue;
            }

            System.out.println("当前操作 "+action);
            Map<String,String> params=managerCommand.getParams();
            Set<String> keySet=params.keySet();
            for(String key:keySet){
                System.out.print("请输入参数 "+params.get(key)+" ：");
                String input=StdioUtils.readStdio();
                params.put(key,input);
            }

            System.out.println("所有参数输入完成，开始处理......");
            boolean success;
            try {
                success=managerCommand.process(params);
                if(success){
                    System.out.println("操作处理成功");
                    managerCommand.after(true);
                }else{
                    System.out.print("操作处理失败");
                    managerCommand.after(false);
                }
            }catch (Exception e){
                System.out.print("操作处理异常");
                e.printStackTrace();
            }


            System.out.println("\n");
        }
        System.out.println("再会！");
    }

    public static void initDb(){
        System.out.println("正在初始化数据库......");
        Session session=ConfigurationInstance.openSession();
        session.close();
        //TODO 自动建表
        System.out.println("初始化成功");
    }

}
