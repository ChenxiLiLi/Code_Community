package com.chenxi.community.mapper;

import com.chenxi.community.model.Comment;

/**
 * @author Mr.Chen
 */
public interface CommentExtMapper {

    /**
     * 更新评论数
     * @param comment 当前评论对象
     */
    void incCommentCount(Comment comment);

}