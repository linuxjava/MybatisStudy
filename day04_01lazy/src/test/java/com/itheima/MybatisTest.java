package com.itheima;

import com.itheima.dao.IAccountDao;
import com.itheima.dao.IUserDao;
import com.itheima.domain.Account;
import com.itheima.domain.User;
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

public class MybatisTest {
    private InputStream inputStream;
    private SqlSession sqlSession;
    private IUserDao userDao;
    private IAccountDao accountDao;

    @Before
    public void init() throws IOException {
        inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sessionFactory.openSession();
        //参数true表示开启自动提交(一般不设置)
        //sqlSession = sessionFactory.openSession(true);
        userDao = sqlSession.getMapper(IUserDao.class);
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
    public void findUserById() {
        System.out.println(userDao.findUserById(50));
    }

    @Test
    public void findAllAccount() {
        System.out.println("-------------------------");
        List<Account> accountList = accountDao.findAll();
        for (Account account : accountList){
            System.out.println(account.toString1());
            //注意不能调用toString，调用默认toString会调用触发user的查询(暂不清楚为什么)
            //System.out.println(account.toString());
        }
    }

    @Test
    public void findAccountByUid() {
        System.out.println(accountDao.findAccountByUid(46));
    }

    @Test
    public void findAllUser(){
        List<User> userList = userDao.findAll();
        for (User user : userList){
            System.out.println(user.toString1());
        }
    }
}
