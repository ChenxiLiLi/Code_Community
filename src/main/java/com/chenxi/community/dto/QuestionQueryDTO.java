package com.chenxi.community.dto;

import lombok.Data;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 18:24 2020/4/2
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer pageSize;
}
