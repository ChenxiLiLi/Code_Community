package com.chenxi.community.exception;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 20:56 2020/3/15
 */
public class MyException extends RuntimeException {

    private String message;

    public MyException(IMyErrorCode errorCode){
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
