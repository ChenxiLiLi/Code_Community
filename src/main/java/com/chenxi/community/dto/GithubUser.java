package com.chenxi.community.dto;

import lombok.Data;

/**
 * @Author: Mr.Chen
 * @Description: 使用Github登录的用户对象,包含name，id，bio描述，id是唯一标识
 * @Date:Created in 14:55 2020/3/3
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}
