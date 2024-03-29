package com.nasa.bt.server.manager.dao;


import com.nasa.bt.server.manager.ConfigurationInstance;
import com.nasa.bt.server.manager.entity.UserAuthInfoEntity;
import com.nasa.bt.server.manager.entity.UserInfoEntity;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public class UserInfoDao {

    private static final Logger log=Logger.getLogger(UserInfoDao.class);
    private Session session;

    public UserInfoDao() {
        session= ConfigurationInstance.openSession();
    }

    public UserInfoEntity getUserInfoByUid(String uid){
        Query query=session.createQuery("from UserInfoEntity where id=?1");
        query.setParameter(1,uid);

        Object result=query.uniqueResult();
        if(result==null)
            return null;
        return (UserInfoEntity) result;
    }

    public UserInfoEntity getUserInfoByName(String name){
        Query query=session.createQuery("from UserInfoEntity where name=?1");
        query.setParameter(1,name);

        Object result=query.uniqueResult();
        if(result==null)
            return null;
        return (UserInfoEntity) result;
    }


    public UserInfoEntity checkAuth(String name,String codeHash){
        Query queryAuth=session.createQuery("from UserAuthInfoEntity where name=?1 and codeHash=?2");
        queryAuth.setParameter(1,name);
        queryAuth.setParameter(2,codeHash);

        if(queryAuth.uniqueResult()==null)
            return null;

        return getUserInfoByName(name);
    }

    public boolean addUser(UserInfoEntity userInfoEntity,String codeHash){
        if(!checkUserInfoLegal(userInfoEntity))
            return false;

        session.beginTransaction();
        UserAuthInfoEntity authInfoEntity=new UserAuthInfoEntity(userInfoEntity.getName(),codeHash);
        session.save(userInfoEntity);
        session.save(authInfoEntity);
        session.getTransaction().commit();
        if(session.getTransaction().getStatus().equals(TransactionStatus.COMMITTED))
            return true;
        return false;
    }

    public boolean deleteUser(String uid){
        UserInfoEntity userInfoEntity=getUserInfoByUid(uid);
        if(userInfoEntity==null)
            return false;
        UserAuthInfoEntity authInfoEntity=new UserAuthInfoEntity(userInfoEntity.getName(),null);

        session.beginTransaction();
        session.delete(userInfoEntity);
        session.delete(authInfoEntity);
        session.getTransaction().commit();
        if(session.getTransaction().getStatus().equals(TransactionStatus.COMMITTED))
            return true;
        return false;
    }

    private boolean checkUserInfoLegal(UserInfoEntity userInfoEntity){
        if(userInfoEntity.getId()==null || userInfoEntity.getId().length()!=36)
            return false;

        if(userInfoEntity.getName()==null || userInfoEntity.getName().equals(""))
            return false;

        return true;
    }

}
