package com.chenxi.community.controller;

import com.chenxi.community.dto.CommentDTO;
import com.chenxi.community.mapper.CommentMapper;
import com.chenxi.community.model.Comment;
import com.chenxi.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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
    public Object post(@RequestBody CommentDTO commentDTO, HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            throw new Customize
        }else {
            Comment comment = new Comment();
            comment.setCommentator(user.getAccountId());
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
}
