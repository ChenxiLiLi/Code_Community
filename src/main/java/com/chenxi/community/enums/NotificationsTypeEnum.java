package com.chenxi.community.enums;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 3:13 2020/3/27
 */
public enum NotificationsTypeEnum {
    /**
     * 通知的类型
     */
    All_NOTIFY(0, "所有的通知"),
    REPLY_QUESTION(1, "回复了您的问题"),
    REPLY_COMMENT(2, "回复了您的评论"),
    LIKE_COMMENT(3, "点赞了您的评论");
    private Integer type;
    private String name;

    public static String nameOfType(Integer type) {
        for (NotificationsTypeEnum notificationsTypeEnum : NotificationsTypeEnum.values()) {
            if(notificationsTypeEnum.getType().equals(type)) {
                return notificationsTypeEnum.getName();
            }
        }
        return "";
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationsTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
}
