package com.chenxi.community.mapper;

import com.chenxi.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Mr.Chen
 * @Description: 用户的Mapper映射
 * @Date:Created in 23:39 2020/3/5
 */
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO USER (account_id,name,token,gmt_create,gmt_modified) VALUES (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insertUser(User user);
}
