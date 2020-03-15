package com.chenxi.community.advice;

import com.chenxi.community.exception.MyException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: Mr.Chen
 * @Description: 异常处理器
 * @Date:Created in 16:19 2020/3/15
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model) {
        if (e instanceof MyException){
            model.addAttribute("message", e.getMessage());
        }else{
            model.addAttribute("message", "服务跑去度假了~~~");
        }
        return new ModelAndView("error");
    }
}
