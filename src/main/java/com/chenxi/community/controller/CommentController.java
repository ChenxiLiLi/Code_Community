package com.chenxi.community.controller;

import com.chenxi.community.dto.CommentDTO;
import com.chenxi.community.mapper.CommentMapper;
import com.chenxi.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 2:22 2020/3/17
 */
@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO){

        Comment comment = new Comment();
        comment.setCommentator(1);
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setContent(commentDTO.getContent());
        comment.setLikeCount(0L);
        commentMapper.insert(comment);
        return null;
    }
}
