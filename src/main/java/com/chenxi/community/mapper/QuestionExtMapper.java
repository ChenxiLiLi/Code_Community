package com.chenxi.community.mapper;

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
     * @return 返回更新结果 1为更新成功
     */
    int incView(Question question);

}