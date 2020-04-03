package com.itheima;

import com.itheima.dao.IAccountDao;
import com.itheima.dao.IUserDao;
import com.itheima.domain.Account;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class AccountDaoTest {
    private SqlSessionFactory sessionFactory;
    private InputStream inputStream;
    private SqlSession sqlSession;
    private IAccountDao accountDao;

    @Before
    public void init() throws IOException {
        inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sessionFactory.openSession();
        //参数true表示开启自动提交(一般不设置)
        //sqlSession = sessionFactory.openSession(true);
        accountDao = sqlSession.getMapper(IAccountDao.class);
    }

    @After
    public void destory() throws IOException {
        //插入操作要提交事务
        sqlSession.commit();
        sqlSession.close();
        inputStream.close();
    }

    @Test
    public void findByUid() {
        System.out.println(accountDao.findByUid(46));
    }
}
