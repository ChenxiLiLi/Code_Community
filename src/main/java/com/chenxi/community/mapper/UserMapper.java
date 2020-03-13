package com.chenxi.community.mapper;

import com.chenxi.community.model.User;
import org.apache.ibatis.annotations.*;

/**
 * @Author: Mr.Chen
 * @Description: 用户的Mapper映射
 * @Date:Created in 23:39 2020/3/5
 */
@Mapper
public interface UserMapper {
    /**
     * 往数据库中插入一条用户记录
     * @param user 用户对象
     */
    @Insert("INSERT INTO USER (account_id,name,token,gmt_create,gmt_modified,avatar_url) VALUES (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insertUser(User user);

    /**
     * 根据token匹配数据库，查询用户
     * @param token 访问令牌
     * @return 返回用户
     */
    @Select("SELECT * FROM USER WHERE token = #{token}")
    User findByToken(@Param("token") String token);

    /**
     * 通过Creator匹配数据库，查询用户
     * @param creator Question与User的关联
     * @return 返回用户
     */
    @Select("SELECT * FROM USER WHERE account_id = #{creator}")
    User findByAccountId(@Param("creator") String creator);

    /**
     * 通过accountID来获取用户对象
     * @param accountId Github用户的唯一标识
     * @return 返回用户
     */
    @Select("SELECT * FROM USER WHERE account_id = #{accountId}")
    User getUserByAccountId(@Param("accountId") String accountId);

    /**
     * 更新数据库中的用户信息
     * @param dbUser 指代数据库中的用户
     */
    @Update("UPDATE USER SET name = #{name}, account_id = #{accountId}, token = #{token}, gmt_modified = #{gmtModified}, avatar_url=#{avatarUrl} WHERE id = #{id}")
    void updateUser(User dbUser);
}
