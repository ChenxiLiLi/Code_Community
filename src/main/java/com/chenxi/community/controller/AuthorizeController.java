package com.chenxi.community.controller;

import com.chenxi.community.dto.AccessTokenDTO;
import com.chenxi.community.dto.GithubUser;
import com.chenxi.community.model.User;
import com.chenxi.community.provider.GithubProvider;
import com.chenxi.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author: Mr.Chen
 * @Description: 处理用户登录控制器
 * @Date:Created in 18:31 2020/3/2
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Autowired
    private UserService userService;

    @RequestMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClientId(clientId);
        accessTokenDTO.setClientSecret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirectUri(redirectUri);
        accessTokenDTO.setState(state);
        //获取访问令牌
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //通过访问令牌查找用户
        GithubUser githubUser = githubProvider.getUser(accessToken);
        //查找得到对象则往数据库中插入一条用户记录，然后将token写入Cookie
        if(githubUser != null && githubUser.getId() != null){
            //获取User
            User user = new User();
            //设置User的属性
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);
            //将自己的cookie写入数据库，用来辨别用户的身份
            response.addCookie(new Cookie("token", token));
        }
        //查找不到对象时的处理
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        //从session中移除user
        request.getSession().removeAttribute("user");
        //删除token
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        //返回首页
        return "redirect:/";
    }

}
