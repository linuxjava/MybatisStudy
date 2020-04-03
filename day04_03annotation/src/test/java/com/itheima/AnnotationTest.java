package com.itheima;

import com.itheima.dao.IUserDao;
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

/**
 * 		一级缓存：
 它指的是Mybatis中SqlSession对象的缓存。
 当我们执行查询之后，查询的结果会同时存入到SqlSession为我们提供一块区域中。
 该区域的结构是一个Map。当我们再次查询同样的数据，mybatis会先去sqlsession中
 查询是否有，有的话直接拿出来用。
 当SqlSession对象消失时，mybatis的一级缓存也就消失了。
 */
public class AnnotationTest {
    private SqlSessionFactory sessionFactory;
    private InputStream inputStream;
    private SqlSession sqlSession;
    private IUserDao userDao;

    @Before
    public void init() throws IOException {
        inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sessionFactory.openSession();
        //参数true表示开启自动提交(一般不设置)
        //sqlSession = sessionFactory.openSession(true);
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    @After
    public void destory() throws IOException {
        //插入操作要提交事务
        sqlSession.commit();
        sqlSession.close();
        inputStream.close();
    }

    @Test
    public void findAll(){
        System.out.println(userDao.findAll());
    }

    @Test
    public void saveUser(){
        User user = new User();
        user.setUsername("mybatis annotation");
        user.setAddress("北京市昌平区");
        userDao.saveUser(user);
        System.out.println(user);
    }

    @Test
    public void updateUser(){
        User user = new User();
        user.setId(52);
        user.setUsername("mybatis annotation update");
        user.setAddress("北京市昌平区update");
        userDao.update(user);
    }

    @Test
    public void deleteUser(){
        userDao.delete(64);
    }

    @Test
    public void findOne(){
        System.out.println(userDao.findOne(63));
    }

    @Test
    public void count(){
        System.out.println("-------" + userDao.count());
    }

    @Test
    public void findUserByName(){
        System.out.println("-------" + userDao.findUserByName("%myb%"));
    }

    @Test
    public void findAllMap(){
        System.out.println("-------" + userDao.findAll1());
    }

    @Test
    public void findOne1Map(){
        System.out.println("-------" + userDao.findOne(60));
    }
}
