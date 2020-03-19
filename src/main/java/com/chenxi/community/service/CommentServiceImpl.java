package com.chenxi.community.service;

import com.chenxi.community.enums.CommentTypeEnum;
import com.chenxi.community.exception.MyErrorCode;
import com.chenxi.community.exception.MyException;
import com.chenxi.community.mapper.CommentMapper;
import com.chenxi.community.mapper.QuestionExtMapper;
import com.chenxi.community.mapper.QuestionMapper;
import com.chenxi.community.model.Comment;
import com.chenxi.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(Comment comment) {
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
            //回复评论
            //1、查看当前评论是否存在
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new MyException(MyErrorCode.COMMENT_NOT_FOUND);
            }
            //2、存在则插入一条评论记录
            commentMapper.insert(comment);
        } else {
            //回复问题
            //1、判断当前停留页面的问题是否存在
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new MyException(MyErrorCode.QUESTION_NOT_FOUND);
            }
            //2、更新Question的commentCount
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
        }
    }
}
