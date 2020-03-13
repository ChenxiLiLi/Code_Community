package com.chenxi.community.model;

import lombok.Data;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 19:28 2020/3/7
 */
@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private String creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
}
