package com.chenxi.community.service;

import com.chenxi.community.dto.PaginationDTO;
import com.chenxi.community.dto.QuestionDTO;
import com.chenxi.community.model.Question;

import java.util.List;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 15:04 2020/3/9
 */
public interface QuestionService {
    /**
     * 该方法用来获取展示到前端的页面元素
     * @param accountId Github用户的唯一标识
     * @param page 当前页码
     * @param pageSize 总页数
     * @return 页面元素的集合
     */
    PaginationDTO getPaginationDTOList(String accountId, Integer page, Integer pageSize);

    /**
     * 通过Question的ID来查询Question对象
     * @param id Question的唯一标识
     * @return QuestionDTO对象
     */
    QuestionDTO getQuestionById(Long id);

    /**
     * 根据Question的情况判断是否更新Question内容
     * @param question 问题对象
     */
    void createOrUpdate(Question question);

    /**
     * 更新阅读数
     * @param id Question的唯一标识
     */
    void incView(Long id);

    /**
     * 展示相关问题
     * @param questionDTO 问题对象
     * @return 与当前问题包含的Tag相关联的问题的集合
     */
    List<QuestionDTO> getRelatedQuestion(QuestionDTO questionDTO);
}
