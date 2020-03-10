package com.chenxi.community.controller;

import com.chenxi.community.dto.PaginationDTO;
import com.chenxi.community.mapper.UserMapper;
import com.chenxi.community.model.User;
import com.chenxi.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        //通过请求对象获取cookie
        Cookie[] cookies = request.getCookies();
        //对cookie判空
        if (cookies == null || cookies.length == 0) {
            System.out.println("不存在cookie");
        } else {
            //查找自定义的cookie-别名token
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    //通过token识别user
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        //展示话题列表
        PaginationDTO paginationDTO = questionService.getPaginationDTOList(page, pageSize);
        model.addAttribute("paginations", paginationDTO);
        return "index";
    }
}
