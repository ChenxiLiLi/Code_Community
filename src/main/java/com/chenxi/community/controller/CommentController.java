package com.chenxi.community.controller;

import com.chenxi.community.dto.CommentCreateDTO;
import com.chenxi.community.dto.CommentDTO;
import com.chenxi.community.dto.ResultDTO;
import com.chenxi.community.enums.CommentTypeEnum;
import com.chenxi.community.exception.MyErrorCode;
import com.chenxi.community.model.Comment;
import com.chenxi.community.model.User;
import com.chenxi.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 2:22 2020/3/17
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(MyErrorCode.NOT_LOGIN);
        }

        //判断评论内容是否为空
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(MyErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setCommentator(user.getAccountId());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        comment.setContent(commentCreateDTO.getContent());
        comment.setLikeCount(0L);
        comment.setCommentCount(0);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }

    @ResponseBody
    @GetMapping(value = "/comment/{id}")
    public ResultDTO<List<CommentDTO>> comment(@PathVariable(name = "id") Long id){
        //获取当前评论的子评论，子评论的parentId为当前评论的id，type=2
        List<CommentDTO> subCommentsDTO = commentService.getListByTargetId(id, CommentTypeEnum.COMMENT);
        //返回封装的子评论对象
        return ResultDTO.okOf(subCommentsDTO);
    }
}
