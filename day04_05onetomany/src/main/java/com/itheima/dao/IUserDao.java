package com.itheima.dao;

import com.itheima.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

//开启二级缓存
@CacheNamespace(blocking = true)
public interface IUserDao {
    /**
     * 一对多延迟加载
     * @param id
     * @return
     */
    @Select("select * FROM user WHERE id=#{uid}")
    @Results(id = "userMap", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "address", column = "address"),
            @Result(property = "accounts", column = "id", many = @Many(select = "com.itheima.dao.IAccountDao.findByUid", fetchType = FetchType.LAZY))
    })
    User findOne(Integer id);

}
