package com.chenxi.community.controller;

import com.chenxi.community.dto.NotificationsDTO;
import com.chenxi.community.dto.PaginationDTO;
import com.chenxi.community.enums.NotificationsTypeEnum;
import com.chenxi.community.model.User;
import com.chenxi.community.service.NotificationsService;
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
 * @Description: 消息通知控制器
 * @Date:Created in 23:17 2020/3/26
 */
@Controller
public class NotificationsController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationsService notificationsService;

    @GetMapping("/notifications/action/{action}")
    public String notification(@PathVariable(name = "action") String action,
                               HttpServletRequest request,
                               @RequestParam(name = "page", defaultValue = "1") Integer page,
                               @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                               Model model) {
        //用户未登录不会出现通知标签，但可能直接通过url访问，所以提前判空
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        //用来返回展示信息的对象
        PaginationDTO<NotificationsDTO> paginationDTO = null;
        if ("index".equals(action)) {
            //展示评论相关信息
            paginationDTO = notificationsService.getPaginationDTO(user.getAccountId(), page, pageSize, NotificationsTypeEnum.REPLY_COMMENT);
            model.addAttribute("section", "index");
            model.addAttribute("sectionName", "评论");
        } else if ("reply".equals(action)) {
            //展示回复相关信息
            //用户已经登录，通过accountId查询出用户的所有的回复
            paginationDTO = notificationsService.getPaginationDTO(user.getAccountId(), page, pageSize, NotificationsTypeEnum.REPLY_QUESTION);
            model.addAttribute("section", "reply");
            model.addAttribute("sectionName", "回复");
        } else if ("like".equals(action)) {
            //展示点赞相关信息
            paginationDTO = notificationsService.getPaginationDTO(user.getAccountId(), page, pageSize, NotificationsTypeEnum.LIKE_COMMENT);
            model.addAttribute("section", "like");
            model.addAttribute("sectionName", "点赞");
        }
        Long unReadComment = notificationsService.unreadCount(user.getAccountId(), NotificationsTypeEnum.REPLY_COMMENT);
        Long unReadReply = notificationsService.unreadCount(user.getAccountId(), NotificationsTypeEnum.REPLY_QUESTION);
        Long unReadLike = notificationsService.unreadCount(user.getAccountId(), NotificationsTypeEnum.LIKE_COMMENT);
        model.addAttribute("unReadComment", unReadComment);
        model.addAttribute("unReadReply", unReadReply);
        model.addAttribute("unReadLike", unReadLike);
        model.addAttribute("pagination", paginationDTO);
        return "notifications";
    }

    @GetMapping("/notifications/detail/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           HttpServletRequest request,
                           Model model){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        //读取通知
        NotificationsDTO notificationsDTO = notificationsService.read(id, user);
        //跳转到相应的question页面
        if (NotificationsTypeEnum.REPLY_COMMENT.getType().equals(notificationsDTO.getType())
            || NotificationsTypeEnum.REPLY_QUESTION.getType().equals(notificationsDTO.getType())
            || NotificationsTypeEnum.LIKE_COMMENT.getType().equals(notificationsDTO.getType())) {
            return "redirect:/question/" + notificationsDTO.getOuterid();
        } else {
            return "redirect:/";
        }
    }
}
