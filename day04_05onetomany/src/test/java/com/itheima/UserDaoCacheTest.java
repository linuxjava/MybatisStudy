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

public class UserDaoCacheTest {
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

        inputStream.close();
    }

    /**
     * 开启二级缓存后
     * 1.sql只会查询一次
     * 2.user1和user2不是同一个对象，因为二级缓存只缓存数据，以及缓存才缓存对象
     */
    @Test
    public void findOne() {
        User user1 = userDao.findOne(46);
        System.out.println(user1.hashCode());

        sqlSession.close();

        //创建一个新的sqlSession和userDao
        sqlSession = sessionFactory.openSession();
        userDao = sqlSession.getMapper(IUserDao.class);
        User user2 = userDao.findOne(46);
        System.out.println(user2.hashCode());
    }
}
