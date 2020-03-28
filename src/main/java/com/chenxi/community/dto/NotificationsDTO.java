package com.chenxi.community.dto;

import lombok.Data;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 4:01 2020/3/27
 */
@Data
public class NotificationsDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private String notifier;
    private String notifierName;
    private Long outerid;
    private String outerTitle;
    private Integer type;
    private String typeName;
}
