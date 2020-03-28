package com.chenxi.community.enums;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 3:22 2020/3/27
 */
public enum NotificationsStatusEnum {
    /**
     * 消息是否已读
     */
    UNREAD(0),
    READ(1);
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    NotificationsStatusEnum (Integer status) {
        this.status = status;
    }
}
