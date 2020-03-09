package com.chenxi.community.mapper;

import com.chenxi.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 19:28 2020/3/7
 */
@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO QUESTION (title,description,gmt_create,gmt_modified,creator,tag) VALUES (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("SELECT * FROM QUESTION")
    List<Question> list();
}
