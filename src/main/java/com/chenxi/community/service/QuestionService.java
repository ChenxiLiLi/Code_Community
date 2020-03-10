package com.chenxi.community.service;

import com.chenxi.community.dto.PaginationDTO;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 15:04 2020/3/9
 */
public interface QuestionService {
    /**
     * @param page 当前页码
     * @param pageSize 总页数
     * @return 元素的集合
     */
    PaginationDTO getPaginationDTOList(Integer page, Integer pageSize);
}
