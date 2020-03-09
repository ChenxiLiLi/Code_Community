package com.chenxi.community.model;

import lombok.Data;

/**
 * @Author: Mr.Chen
 * @Description: 使用Github登录的用户对象
 * @Date:Created in 23:40 2020/3/5
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
