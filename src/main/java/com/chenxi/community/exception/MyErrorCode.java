package com.chenxi.community.exception;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 21:09 2020/3/15
 */
public enum MyErrorCode implements IMyErrorCode {

    /**
     * 选择的问题不存在数据库中
     */
    QUESTION_NOT_FOUND(2001, "你找的问题不见了，请换一个试试"),

    /**
     * 评论时问题已经被删除或回复时该层评论已经被删除
     */
    TARGET_PARAM_NOT_FOUND(2002, "没有选中任何问题或评论进行回复"),
    NOT_LOGIN(2003, "请先登录再进行操作"),
    SYS_ERROR(2004, "服务器跑去度假了，请稍后再进行尝试"),
    TYPE_PARAM_WRONG(2005, "评论类型不存在"),
    COMMENT_NOT_FOUND(2006, "回复的评论不存在") ;

    private String message;
    private Integer code;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    MyErrorCode(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
