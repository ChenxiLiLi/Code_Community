package com.chenxi.community.dto;

import com.chenxi.community.model.User;
import lombok.Data;

/**
 * @Author: Mr.Chen
 * @Description: 添加User，用来关联User与Question
 * @Date:Created in 19:28 2020/3/7
 */
@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private String creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
