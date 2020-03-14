package com.chenxi.community.mapper;

import com.chenxi.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: Mr.Chen
 * @Description: Question SQL类
 * @Date:Created in 19:28 2020/3/7
 */
@Mapper
public interface QuestionMapper {
    /**
     * 往数据库中插入一条问题记录
     * @param question 问题对象
     */
    @Insert("INSERT INTO QUESTION (title,description,gmt_create,gmt_modified,creator,tag) VALUES (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    /**
     * LIMIT 子句可以指定SELECT语句返回指定的记录数，此处返回所有问题条数
     * @param offset 指定第一个返回记录行的偏移量，初始量为0
     * @param pageSize 指定返回记录行的最大数目
     * @return 返回所有问题的集合
     */
    @Select("SELECT * FROM QUESTION LIMIT #{offset},#{pageSize}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "pageSize") Integer pageSize);

    /**
     * LIMIT 子句可以指定SELECT语句返回指定的记录数，此处返回指定用户发布的问题条数
     * @param accountId Github用户的唯一标识
     * @param offset 指定第一个返回记录行的偏移量，初始量为0
     * @param pageSize 指定返回记录行的最大数目
     * @return 返回指定用户的问题集合
     */
    @Select("SELECT * FROM QUESTION WHERE CREATOR = #{accountId} LIMIT #{offset},#{pageSize}")
    List<Question> userList(@Param(value = "accountId") String accountId, @Param(value = "offset") Integer offset, @Param(value = "pageSize") Integer pageSize);

    /**
     * 用来返回所有用户发布的问题的总条数，count(1)或count(*)都可以，sql做了优化，基本没有差别
     * @return 返回问题的总条数
     */
    @Select("SELECT COUNT(1) FROM QUESTION")
    Integer count();

    /**
     * 用来返回当前用户发布的问题总数
     * @param accountId Github用户的唯一标识
     * @return 返回当前用户发布的问题总数
     */
    @Select("SELECT COUNT(1) FROM QUESTION WHERE CREATOR = #{accountId}")
    Integer userCount(@Param(value = "accountId") String accountId);

    /**
     * 通过id查询Question对象
     * @param id Question的唯一标识
     * @return Question对象
     */
    @Select("SELECT * FROM QUESTION WHERE id = #{id}")
    Question getQuestionById(@Param(value = "id") Integer id);

    /**
     * 更新数据库中的问题记录
     * @param question 最新的Question对象
     */
    @Update("UPDATE QUESTION SET title = #{title}, description = #{description}, tag = #{tag}, gmt_modified = #{gmtModified} WHERE id = #{id}")
    void update(Question question);
}
