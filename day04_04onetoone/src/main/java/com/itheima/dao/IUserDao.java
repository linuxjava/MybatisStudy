package com.itheima.dao;

import com.itheima.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IUserDao {
    @Select("select * FROM user WHERE id=#{uid}")
    User findOne(Integer id);
}
