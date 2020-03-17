package com.chenxi.community.service;

import com.chenxi.community.model.Comment;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 2:30 2020/3/18
 */
public interface CommentService {
    /**
     * 插入一条评论记录
     * @param comment 评论对象
     */
    void insert(Comment comment);
}
