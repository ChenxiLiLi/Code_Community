package com.chenxi.community.controller;

import com.chenxi.community.dto.PaginationDTO;
import com.chenxi.community.model.User;
import com.chenxi.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Mr.Chen
 * @Description: 个人中心控制器
 * @Date:Created in 13:55 2020/3/11
 */
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                          Model model) {
        //判断用户是否登录，没有则返回首页
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        if ("questions".equals(action)) {
            //展示个人问题（需要封装问题对象）
            PaginationDTO paginationDTO = questionService.getPaginationDTOList(user.getAccountId(), page, pageSize);
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName","我的提问");
            model.addAttribute("pagination2", paginationDTO);
        }else if("personInfo".equals(action)){
            //展示个人资料
            model.addAttribute("section", "personInfo");
            model.addAttribute("sectionName","个人资料");
        }
        return "profile";
    }
}
