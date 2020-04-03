package com.itheima.dao;

import com.itheima.domain.User;
import com.itheima.domain.User1;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IUserDao {
    @Select("select * from user")
    List<User> findAll();

    @Insert("INSERT into user(username, address, sex, birthday)VALUES(#{username}, #{address}, #{sex}, #{birthday})")
    @Options(useGeneratedKeys = true, keyColumn="id")
    void saveUser(User user);

    @Update("UPDATE user set username=#{username},address=#{address},sex=#{sex},birthday=#{birthday} WHERE id=#{id}")
    void update(User user);

    @Delete("DELETE FROM user WHERE id=#{uid}")
    void delete(Integer id);

    @Select("select * FROM user WHERE id=#{uid}")
    User findOne(Integer id);

    @Select("select count(*) from user")
    Integer count();

    @Select("select * from user where username like #{username}")
    List<User> findUserByName(String name);


    /**
     * 实体类属性与数据库列映射
     * @return
     */
    @Select("select * from user")
    @Results(id = "userMap", value = {
            @Result(id = true, property = "userId", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "userBirthday", column = "birthday"),
            @Result(property = "userSex", column = "sex"),
            @Result(property = "userAddress", column = "address")
    })
    List<User1> findAll1();


    @Select("select * FROM user WHERE id=#{uid}")
    @ResultMap(value = {"userMap"})
    User1 findOne1(Integer id);
}
