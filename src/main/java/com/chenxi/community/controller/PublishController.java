package com.chenxi.community.controller;

import com.chenxi.community.dto.QuestionDTO;
import com.chenxi.community.model.Question;
import com.chenxi.community.model.User;
import com.chenxi.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Mr.Chen
 * @Description: 处理问题请求控制器
 * @Date:Created in 0:44 2020/3/7
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model){
        QuestionDTO questionDTO = questionService.getQuestionById(id);
        model.addAttribute("title",questionDTO.getTitle());
        model.addAttribute("description",questionDTO.getDescription());
        model.addAttribute("tag",questionDTO.getTag());
        model.addAttribute("id", id);
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value="title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false) Long id,
            HttpServletRequest request,
            Model model){
        //添加进model，用来前端展示
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        //判断用户的登录态，未登录不能发布问题
        if(request.getSession().getAttribute("user") == null){
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        //判断输入框的情况
        if(title == null || "".equals(title)){
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if(description == null || "".equals(description)){
            model.addAttribute("error", "问题描述不能为空");
            return "publish";
        }
        if(tag == null || "".equals(tag)){
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        //获取Question对象，判断是否存进数据库
        //获取当前用户对象，question.creator = user.accountId
        User user = (User)request.getSession().getAttribute("user");
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getAccountId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
