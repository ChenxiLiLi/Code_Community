package com.chenxi.community.service;

import com.chenxi.community.dto.NotificationsDTO;
import com.chenxi.community.dto.PaginationDTO;
import com.chenxi.community.enums.NotificationsStatusEnum;
import com.chenxi.community.enums.NotificationsTypeEnum;
import com.chenxi.community.exception.MyErrorCode;
import com.chenxi.community.exception.MyException;
import com.chenxi.community.mapper.NotificationsMapper;
import com.chenxi.community.model.Notifications;
import com.chenxi.community.model.NotificationsExample;

import com.chenxi.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 3:03 2020/3/27
 */
@Service
public class NotificationsServiceImpl implements NotificationsService {

    @Autowired
    private NotificationsMapper notificationsMapper;

    @Override
    public PaginationDTO<NotificationsDTO> getPaginationDTO(String accountId, Integer page, Integer pageSize, NotificationsTypeEnum notificationsTypeEnum) {

        PaginationDTO<NotificationsDTO> paginationDTO = new PaginationDTO<>();
        int totalCount;
        Integer offset;
        List<Notifications> notifications;
        //查找相关通知
        NotificationsExample notificationsExample = new NotificationsExample();
        notificationsExample.setOrderByClause("gmt_create desc");
        notificationsExample.createCriteria()
                .andReceiverEqualTo(accountId)
                .andTypeEqualTo(notificationsTypeEnum.getType());
        totalCount = (int) notificationsMapper.countByExample(notificationsExample);
        offset = paginationDTO.getPagination(totalCount, page, pageSize);
        notifications = notificationsMapper.selectByExampleWithRowbounds(notificationsExample, new RowBounds(offset, pageSize));

        List<NotificationsDTO> notificationsDTOS = new ArrayList<>();
        if (notifications.size() == 0) {
            return paginationDTO;
        }
        for (Notifications notification : notifications) {
            NotificationsDTO notificationsDTO = new NotificationsDTO();
            BeanUtils.copyProperties(notification, notificationsDTO);
            notificationsDTO.setTypeName(NotificationsTypeEnum.nameOfType(notification.getType()));
            notificationsDTOS.add(notificationsDTO);
        }
        paginationDTO.setData(notificationsDTOS);
        return paginationDTO;
    }

    @Override
    public NotificationsDTO read(Long id, User user) {
        //查找出该问题下的所有未读通知
        NotificationsExample notificationsExample = new NotificationsExample();
        notificationsExample.createCriteria()
                .andOuteridEqualTo(id).andStatusEqualTo(NotificationsStatusEnum.UNREAD.getStatus());
        List<Notifications> notifications = notificationsMapper.selectByExample(notificationsExample);
        if (notifications.size() == 0) {
            //找不到相应的通知
            throw new MyException(MyErrorCode.NOTIFICATION_NOT_FOUND);
        }
        //如果点击时的接收人不是你自己则报错
        Notifications notification = notifications.get(0);
        if (!Objects.equals(notification.getReceiver(), user.getAccountId())) {
            //防止手动输入url访问
            throw new MyException(MyErrorCode.READ_NOTIFICATION_FAIL);
        }

        //通知读取成功，更新所有查询出来的通知的状态
        for (Notifications n : notifications) {
            n.setStatus(NotificationsStatusEnum.READ.getStatus());
            notificationsMapper.updateByPrimaryKey(n);
        }
        //将其中一个通知返回出去，用于获取相应的questionId
        NotificationsDTO notificationsDTO = new NotificationsDTO();
        BeanUtils.copyProperties(notification, notificationsDTO);
        notificationsDTO.setTypeName(NotificationsTypeEnum.nameOfType(notification.getType()));
        return notificationsDTO;
    }

    @Override
    public Long unreadCount(String accountId, NotificationsTypeEnum notificationsTypeEnum) {
        NotificationsExample example = new NotificationsExample();
        example.createCriteria()
                .andReceiverEqualTo(accountId)
                .andTypeEqualTo(notificationsTypeEnum.getType())
                .andStatusEqualTo(NotificationsStatusEnum.UNREAD.getStatus());
        return notificationsMapper.countByExample(example);
    }
}
