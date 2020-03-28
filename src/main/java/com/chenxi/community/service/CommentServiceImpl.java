package com.chenxi.community.service;

import com.chenxi.community.dto.CommentDTO;
import com.chenxi.community.enums.CommentTypeEnum;
import com.chenxi.community.enums.NotificationsStatusEnum;
import com.chenxi.community.enums.NotificationsTypeEnum;
import com.chenxi.community.exception.MyErrorCode;
import com.chenxi.community.exception.MyException;
import com.chenxi.community.mapper.*;
import com.chenxi.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 2:32 2020/3/18
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private NotificationsMapper notificationsMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(Comment comment, User commentator) {
        //插入评论记录之前需要判断parentId是否存在，即当前问题是否存在，可能在评论之前被删除了
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new MyException(MyErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        //评论类型为空或者不存在
        if (comment.getType() == null || !CommentTypeEnum.isExits(comment.getType())) {
            throw new MyException(MyErrorCode.TYPE_PARAM_WRONG);
        }
        //根据评论类型做相应的处理
        if (comment.getType().equals(CommentTypeEnum.COMMENT.getType())) {
            //子评论
            //1、查看当前评论是否存在
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new MyException(MyErrorCode.COMMENT_NOT_FOUND);
            }
            //查询当前问题对象
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if (question == null) {
                throw new MyException(MyErrorCode.QUESTION_NOT_FOUND);
            }
            //2、存在则插入一条评论记录
            commentMapper.insert(comment);
            //3、更新子评论数
            dbComment.setCommentCount(1);
            commentExtMapper.incCommentCount(dbComment);
            //4、更新一条评论类型的通知
            createNotify(comment, dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationsTypeEnum.REPLY_COMMENT, question.getId());
        } else {
            //评论问题
            //1、判断当前停留页面的问题是否存在
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new MyException(MyErrorCode.QUESTION_NOT_FOUND);
            }
            //2、更新Question的commentCount
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
            //3、更新一条回复类型的通知
            createNotify(comment, question.getCreator(), commentator.getName(), question.getTitle(), NotificationsTypeEnum.REPLY_QUESTION, question.getId());
        }
    }

    /**
     * 创建通知
     * @param comment 当前的评论或回复对象
     * @param receiver 接收通知的对象
     * @param notifyName 操作人用户名
     * @param outerTitle 问题标题
     * @param notificationsTypeEnum 通知类型
     * @param outerid 所属问题的id 用来进行跳转
     */
    private void createNotify(Comment comment, String receiver, String notifyName, String outerTitle, NotificationsTypeEnum notificationsTypeEnum, Long outerid) {
        Notifications notifications = new Notifications();
        notifications.setNotifier(comment.getCommentator());
        notifications.setNotifierName(notifyName);
        notifications.setOuterid(outerid);
        notifications.setReceiver(receiver);
        notifications.setOuterTitle(outerTitle);
        notifications.setType(notificationsTypeEnum.getType());
        notifications.setStatus(NotificationsStatusEnum.UNREAD.getStatus());
        notifications.setGmtCreate(System.currentTimeMillis());
        notificationsMapper.insert(notifications);
    }

    @Override
    public List<CommentDTO> getListByTargetId(Long id, CommentTypeEnum typeEnum) {
        //查找当前问题的所有评论/当前评论的所有回复
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(typeEnum.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if (comments.size() == 0) {
            return new ArrayList<>();
        }

        //评论非空，获取去重的评论人
        Set<String> commentators = comments.stream().map(Comment::getCommentator).collect(Collectors.toSet());
        List<String> userId = new ArrayList<>(commentators);

        //获取评论人并转换为map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdIn(userId);
        List<User> users = userMapper.selectByExample(userExample);
        //Map: key-value == AccountId-User，通过哈希表来降低复杂度
        Map<String, User> userMap = users.stream().collect(Collectors.toMap(User::getAccountId, user -> user));

        //封装到CommentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
