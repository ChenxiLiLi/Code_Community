package com.chenxi.community.dto;

import com.chenxi.community.exception.MyErrorCode;
import com.chenxi.community.exception.MyException;
import lombok.Data;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: Mr.Chen
 * @Description: 用来返回给前端展示提示信息
 * @Date:Created in 2:13 2020/3/18
 */
@Data
public class ResultDTO {
    private Integer code;
    private String message;

    public static ResultDTO errorOf(Integer code, String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO okOf() {
        return errorOf(200, "成功");
    }

    public static ResultDTO errorOf(MyErrorCode notLogin) {
        return errorOf(notLogin.getCode(), notLogin.getMessage());
    }

    public static ResultDTO errorOf(MyException e) {
        return errorOf(e.getCode(), e.getMessage());
    }
}
