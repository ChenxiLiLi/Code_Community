package com.chenxi.community.controller;

import com.chenxi.community.dto.QuestionDTO;
import com.chenxi.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: Mr.Chen
 * @Description: 问题控制器
 * @Date:Created in 20:49 2020/3/13
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model){
        QuestionDTO questionDTO = questionService.getQuestionById(id);
        model.addAttribute("question", questionDTO);
        return "question";
    }
}
