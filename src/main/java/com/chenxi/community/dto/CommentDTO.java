package com.chenxi.community.dto;

import com.chenxi.community.model.User;
import lombok.Data;

/**
 * @Author: Mr.Chen
 * @Description: 评论列表对象
 * @Date:Created in 6:58 2020/3/20
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private String commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private User user;
}
