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
public class SecondCacheTest {
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
        //sqlSession.commit();
        sqlSession.close();
        inputStream.close();
    }

    @Test
    public void findUserById1() {
        User user1 = userDao.findUserById(50);

        //关闭sqlSession，此时一级缓存消失
        sqlSession.close();

        SqlSession sqlSession1 = sessionFactory.openSession();
        userDao = sqlSession1.getMapper(IUserDao.class);
        User user2 = userDao.findUserById(50);
        //1.sql只查询一下，因为使用了sessionFactory的二级缓存
        //2.结果为false，因为二级缓存中缓存的是数据不是对象
        System.out.println("测试结果：" + (user1 == user2));
        sqlSession1.close();
    }

}
