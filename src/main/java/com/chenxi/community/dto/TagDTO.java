package com.chenxi.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: Mr.Chen
 * @Description: 所有标签
 * @Date:Created in 22:50 2020/3/25
 */
@Data
public class TagDTO {
    /**
     * 标签所属种类
     */
    private String categoryName;
    /**
     * 标签集
     */
    private List<String> tags;
}
