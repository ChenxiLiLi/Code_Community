package com.chenxi.community.controller;

import com.chenxi.community.dto.CommentDTO;
import com.chenxi.community.dto.QuestionDTO;
import com.chenxi.community.enums.CommentTypeEnum;
import com.chenxi.community.service.CommentService;
import com.chenxi.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author: Mr.Chen
 * @Description: 处理问题更新控制器
 * @Date:Created in 20:49 2020/3/13
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){
        //获取问题展示DTO对象
        QuestionDTO questionDTO = questionService.getQuestionById(id);
        //获取评论列表DTO对象
        List<CommentDTO> commentList = commentService.getListByTargetId(id, CommentTypeEnum.QUESTION);
        //一次点击之后阅读数+1
        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", commentList);
        return "question";
    }
}
