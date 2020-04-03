package com.chenxi.community.mapper;

import com.chenxi.community.dto.QuestionQueryDTO;
import com.chenxi.community.model.Question;
import com.chenxi.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author Mr.Chen
 */
public interface QuestionExtMapper {

    /**
     * 更新阅读数
     * @param question 问题对象
     */
    void incViewCount(Question question);

    /**
     * 更新评论数
     * @param question 问题对象
     */
    void incCommentCount(Question question);

    /**
     * 展示相关问题
     * @param question 问题对象
     * @return 与当前问题包含的Tag相关联的问题的集合
     */
    List<Question> selectRelatedQuestion(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}