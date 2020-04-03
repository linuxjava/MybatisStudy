package com.itheima.dao;

import com.itheima.domain.User;

import java.util.List;

public interface IUserDao {
    User findUserById(Integer uid);

    List<User> findAll();
}
