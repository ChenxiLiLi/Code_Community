package com.chenxi.community.service;

import com.chenxi.community.dto.CommentDTO;
import com.chenxi.community.model.Comment;

import java.util.List;

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

    /**
     * 返回评论列表集合
     * @param id Question的ID
     * @return 封装的列表集合
     */
    List<CommentDTO> getListByQuestionId(Long id);
}
