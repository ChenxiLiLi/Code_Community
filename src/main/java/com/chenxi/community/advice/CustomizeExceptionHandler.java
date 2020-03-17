package com.chenxi.community.advice;

import com.chenxi.community.dto.ResultDTO;
import com.chenxi.community.exception.MyErrorCode;
import com.chenxi.community.exception.MyException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Mr.Chen
 * @Description: 异常处理器, 全局异常处理
 * “@ControllerAdvice”的作用：当异常抛到Controller时可以进行处理，
 * 可以对异常进行统一处理,规定返回的json格式或是跳转到一个错误页面
 * @Date:Created in 16:19 2020/3/15
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    Object errorHandle(Throwable e, Model model, HttpServletRequest request) {
        String contentType = "application/json";
        if (contentType.equals(request.getContentType())) {
            //返回JSON格式信息
            if (e instanceof MyException) {
                return ResultDTO.errorOf((MyException) e);
            } else {
                return ResultDTO.errorOf(MyErrorCode.SYS_ERROR);
            }
        } else {
            //返回错误页面
            if (e instanceof MyException) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", MyErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
