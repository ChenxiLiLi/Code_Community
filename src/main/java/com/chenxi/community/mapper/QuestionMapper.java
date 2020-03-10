package com.chenxi.community.mapper;

import com.chenxi.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Mr.Chen
 * @Description: Question SQL类
 * @Date:Created in 19:28 2020/3/7
 */
@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO QUESTION (title,description,gmt_create,gmt_modified,creator,tag) VALUES (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("SELECT * FROM QUESTION LIMIT #{offset},#{pageSize}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "pageSize") Integer pageSize);

    @Select("SELECT COUNT(1) FROM QUESTION")
    Integer count();
}
