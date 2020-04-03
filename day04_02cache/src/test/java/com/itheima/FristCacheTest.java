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
public class FristCacheTest {
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

    /**
     * 测试一级缓存
     */
    @Test
    public void findUserById() {
        User user1 = userDao.findUserById(50);
        User user2 = userDao.findUserById(50);
        //1.以及缓存是被sqlSession保存的
        //2.同一个sqlSession的sql只会查询一下，所以下面结果为true
        System.out.println("测试结果：" + (user1 == user2));
    }

    /**
     * 测试一级缓存
     */
    @Test
    public void findUserById1() {
        User user1 = userDao.findUserById(50);

        //关闭sqlSession
        sqlSession.close();
        sqlSession = sessionFactory.openSession();
        userDao = sqlSession.getMapper(IUserDao.class);
        //或清空缓存
        //sqlSession.clearCache();

        //结果为false，因为缓存不存在了
        User user2 = userDao.findUserById(50);
        System.out.println("测试结果：" + (user1 == user2));
    }

    @Test
    public void update() {
        User user1 = userDao.findUserById(50);

        User updateUser = new User();
        updateUser.setId(50);
        updateUser.setUsername("update user");
        userDao.updateUser(updateUser);

        //结果为false，一级缓存是 SqlSession 范围的缓存，当调用 SqlSession 的修改，添加，删除，commit()，close()等方法时，就会清空一级缓存
        User user2 = userDao.findUserById(50);
        System.out.println("测试结果：" + (user1 == user2));
    }
}
