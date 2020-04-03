package com.itheima;

import com.itheima.dao.IRoleDao;
import com.itheima.dao.IUserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MybatisTest {
    private InputStream inputStream;
    private SqlSession sqlSession;
    private IUserDao userDao;
    private IRoleDao roleDao;

    @Before
    public void init() throws IOException {
        inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sessionFactory.openSession();
        //参数true表示开启自动提交(一般不设置)
        //sqlSession = sessionFactory.openSession(true);
        userDao = sqlSession.getMapper(IUserDao.class);
        roleDao = sqlSession.getMapper(IRoleDao.class);
    }

    @After
    public void destory() throws IOException {
        //插入操作要提交事务
        sqlSession.commit();
        sqlSession.close();
        inputStream.close();
    }

    @Test
    public void findAll() {
        System.out.println(userDao.findAll());
    }

    @Test
    public void findRoleAll() {
        System.out.println(roleDao.findAll());
    }
}
