package com.chenxi.community.service;

import com.chenxi.community.dto.NotificationsDTO;
import com.chenxi.community.dto.PaginationDTO;
import com.chenxi.community.model.User;

/**
 * @Author: Mr.Chen
 * @Description: 通知接口
 * @Date:Created in 3:02 2020/3/27
 */
public interface NotificationsService {
    /**
     * @param accountId 用户唯一标识
     * @param page      页码
     * @param pageSize  页的总数
     * @return PaginationDTO
     */
    PaginationDTO getPaginationDTO(String accountId, Integer page, Integer pageSize);

    NotificationsDTO read(Long id, User user);

    Long unreadCount(String accountId);
}
