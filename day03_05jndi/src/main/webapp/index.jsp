<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<%@ page import="java.io.InputStream" %>
<%@ page import="com.itheima.dao.IUserDao" %>
<%@ page import="org.apache.ibatis.session.SqlSession" %>
<%@ page import="org.apache.ibatis.io.Resources" %>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory" %>
<%@ page import="org.apache.ibatis.session.SqlSessionFactoryBuilder" %>
<html>
<body>
122222
    <%
        InputStream inputStream;
        SqlSession sqlSession;
        IUserDao userDao;

        inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sessionFactory.openSession();
        userDao = sqlSession.getMapper(IUserDao.class);

        System.out.println(userDao.findAll());

        sqlSession.commit();
        sqlSession.close();
        inputStream.close();
    %>
</body>
</html>
