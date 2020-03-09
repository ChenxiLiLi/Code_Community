package com.chenxi.community.controller;

import com.chenxi.community.dto.QuestionDTO;
import com.chenxi.community.mapper.QuestionMapper;
import com.chenxi.community.mapper.UserMapper;
import com.chenxi.community.model.Question;
import com.chenxi.community.model.User;
import com.chenxi.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.jws.WebParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: Mr.Chen
 * @Description: 首页
 * @Date:Created in 14:20 2020/3/1
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;


    @GetMapping("/")
    public String index(HttpServletRequest request, Model model){
        //通过请求对象获取cookie
        Cookie[] cookies = request.getCookies();
        //对cookie判空
        if(cookies == null || cookies.length == 0){
            System.out.println("不存在cookie");
        }else{
            //查找自定义的cookie-别名token
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    //通过token识别user
                    User user = userMapper.findByToken(token);
                    if(user != null){
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        //展示话题列表
        List<QuestionDTO> questionList = questionService.getQuestionList();
        model.addAttribute("questions", questionList);
        return "index";
    }
}
