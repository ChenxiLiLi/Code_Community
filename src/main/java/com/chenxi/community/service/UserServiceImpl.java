package com.chenxi.community.service;

import com.chenxi.community.mapper.UserMapper;
import com.chenxi.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 13:19 2020/3/13
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void createOrUpdate(User user) {
        User dbUser = userMapper.getUserByAccountId(user.getAccountId());
        if (dbUser == null) {
            //为新用户，create
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insertUser(user);
        } else {
            //为老用户，update
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setToken(user.getToken());
            dbUser.setName(user.getName());
            userMapper.updateUser(dbUser);
        }
    }
}
