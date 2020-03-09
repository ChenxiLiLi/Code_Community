package com.chenxi.community.service;

import com.chenxi.community.dto.QuestionDTO;

import java.util.List;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 15:04 2020/3/9
 */
public interface QuestionService {
    List<QuestionDTO> getQuestionList();
}
