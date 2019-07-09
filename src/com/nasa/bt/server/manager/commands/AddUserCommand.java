package com.nasa.bt.server.manager.commands;

import com.nasa.bt.server.manager.crypt.SHA256Utils;
import com.nasa.bt.server.manager.dao.UserInfoDao;
import com.nasa.bt.server.manager.entity.UserInfoEntity;
import com.nasa.bt.server.manager.utils.CodeUtils;
import com.nasa.bt.server.manager.utils.StdioUtils;
import com.nasa.bt.server.manager.utils.UUIDUtils;

import java.util.HashMap;
import java.util.Map;

public class AddUserCommand implements ManagerCommand {

    private boolean inputCodeHash=false;
    private String code;
    private String uid;

    @Override
    public Map<String, String> getParams() {
        Map<String,String> params=new HashMap<>();
        params.put("username","用户名");
        params.put("codeHash","code的Hash值（如果为空系统将自动生成）");
        return params;
    }

    @Override
    public boolean process(Map<String, String> params) {
        String username=params.get("username");
        String codeHash=params.get("codeHash");

        if(codeHash.equals("")){
            code= CodeUtils.genRandomCode();

            codeHash= SHA256Utils.getSHA256InHex(code);
        }else
            inputCodeHash=true;

        uid= UUIDUtils.getRandomUUID();
        UserInfoDao userInfoDao=new UserInfoDao();
        return userInfoDao.addUser(new UserInfoEntity(uid,username),codeHash);
    }

    @Override
    public void after(boolean success) {
        if(success){
            if(!inputCodeHash)
                StdioUtils.writeStdio("系统自动生成的CODE "+code+" 请妥善保存");
            StdioUtils.writeStdio("用户添加成功，ID "+uid);
        }
    }


}
