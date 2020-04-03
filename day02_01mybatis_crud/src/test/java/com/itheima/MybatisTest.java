package com.itheima;

import com.itheima.dao.IUser1Dao;
import com.itheima.dao.IUserDao;
import com.itheima.domain.QueryVo;
import com.itheima.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.TRANSACTION_MODE;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class MybatisTest {
    private InputStream inputStream;
    private SqlSession sqlSession;
    private IUserDao userDao;
    private IUser1Dao user1Dao;

    @Before
    public void init() throws IOException {
        inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sessionFactory.openSession();
        //参数true表示开启自动提交(一般不设置)
                              //sqlSession = sessionFactory.openSession(true);
        userDao = sqlSession.getMapper(IUserDao.class);
        user1Dao = sqlSession.getMapper(IUser1Dao.class);
    }

    @After
    public void destory() throws IOException {
        //插入操作要提交事务
        sqlSession.commit();
        sqlSession.close();
        inputStream.close();
    }

    @Test
    public void findAll() throws IOException {
        System.out.println(userDao.findAll());
    }

    @Test
    public void saveUser(){
        User user = new User();
        user.setUsername("mybatis last insert id");
        user.setAddress("北京");
        user.setSex("男");
        user.setBrithday(new Date());
        userDao.saveUser(user);

        System.out.println(user);
    }

    @Test
    public void updateUser(){
        User user = new User();
        user.setId(49);
        user.setUsername("mybatis update");
        user.setAddress("北京");
        user.setSex("女");
        user.setBrithday(new Date());
        userDao.updateUser(user);
    }

    @Test
    public void deleteUser(){
        userDao.deleteUser(49);
    }

    @Test
    public void findById(){
        System.out.println(userDao.findById(50));
    }

    @Test
    public void findByName(){
        System.out.println(userDao.findByName("%王%"));
    }

    @Test
    public void findByName1(){
        System.out.println(userDao.findByName1("王"));
    }

    @Test
    public void findByVo(){
        QueryVo queryVo = new QueryVo();
        User user = new User();
        user.setUsername("%王%");
        queryVo.setUser(user);
        System.out.println(userDao.findByVo(queryVo));
    }

    @Test
    public void count(){
        System.out.println(userDao.count());
    }

    @Test
    public void findAll1() throws IOException {
        System.out.println(user1Dao.findAll());
    }
}
