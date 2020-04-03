package com.itheima.dao.impl;

import com.itheima.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class UserDaoImpl {
    private SqlSessionFactory factory;

    public UserDaoImpl(SqlSessionFactory factory) {
        this.factory = factory;
    }

    public List<User> findAll(){
        SqlSession sqlSession = factory.openSession();
        List<User> objects = sqlSession.selectList("com.itheima.dao.IUserDao.findAll");
        sqlSession.close();
        return objects;
    }
}
