package com.chenxi.community.exception;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 21:09 2020/3/15
 */
public enum MyErrorCode implements IMyErrorCode {

    /**
     * 问题找不到错误信息
     */
    QUESTION_NOT_FOUND("你找的问题不见了，请换一个试试");

    private String message;

    MyErrorCode(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
