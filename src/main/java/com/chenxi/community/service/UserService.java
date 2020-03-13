package com.chenxi.community.service;

import com.chenxi.community.model.User;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 13:19 2020/3/13
 */
public interface UserService {

    /**
     * 通过传进来的用户对象来验证是否需要更新用户信息
     * @param user 登录的用户对象
     */
    void createOrUpdate(User user);
}
