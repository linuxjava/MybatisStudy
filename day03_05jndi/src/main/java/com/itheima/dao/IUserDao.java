package com.itheima.dao;

import com.itheima.domain.User;

import java.util.List;

public interface IUserDao {

    /**
     * 查询指定用户，同时包含所有账号信息
     *
     * @return
     */
    List<User> findAll();
}
