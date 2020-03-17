package com.chenxi.community.dto;

import lombok.Data;

/**
 * @Author: Mr.Chen
 * @Description: 封装评论信息
 * @Date:Created in 2:20 2020/3/17
 */
@Data
public class CommentDTO {
    private Long parentId;
    private Integer type;
    private String content;
}
