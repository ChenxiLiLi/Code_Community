package com.chenxi.community.exception;

/**
 * @Author: Mr.Chen
 * @Description: Error接口
 * @Date:Created in 21:08 2020/3/15
 */
public interface IMyErrorCode {

    /** 返回错误信息
     * @return 错误信息
     */
    String getMessage();

    /**
     * 返回提示码
     * @return 提示码
     */
    Integer getCode();
}
