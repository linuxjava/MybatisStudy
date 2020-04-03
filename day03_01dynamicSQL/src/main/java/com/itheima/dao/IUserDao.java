package com.itheima.dao;

import com.itheima.domain.QueryVo;
import com.itheima.domain.User;

import java.util.List;

public interface IUserDao {
    List<User> findUserByCondition(User condition);

    List<User> findUserInIds(QueryVo qvo);
}
