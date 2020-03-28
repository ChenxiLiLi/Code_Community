package com.chenxi.community.service;

import com.chenxi.community.dto.NotificationsDTO;
import com.chenxi.community.dto.PaginationDTO;
import com.chenxi.community.enums.NotificationsTypeEnum;
import com.chenxi.community.model.User;

/**
 * @Author: Mr.Chen
 * @Description: 通知接口
 * @Date:Created in 3:02 2020/3/27
 */
public interface NotificationsService {
    /**
     * 根据通知类型对通知做分页操作
     * @param accountId 用户唯一标识
     * @param page      页码
     * @param pageSize  页的总数
     * @param notificationsTypeEnum 通知类型
     * @return PaginationDTO
     */
    PaginationDTO<NotificationsDTO> getPaginationDTO(String accountId, Integer page, Integer pageSize, NotificationsTypeEnum notificationsTypeEnum);

    /**
     * 读取通知
     * @param id questionId
     * @param user 当前用户
     * @return 通知数据传输对象
     */
    NotificationsDTO read(Long id, User user);

    /**
     * 根据通知类型查找未读的通知数
     * @param accountId 用户的标识
     * @param notificationsTypeEnum 通知类型
     * @return 通知数
     */
    Long unreadCount(String accountId, NotificationsTypeEnum notificationsTypeEnum);
}
