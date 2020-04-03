package com.chenxi.community.service;

import com.chenxi.community.dto.PaginationDTO;
import com.chenxi.community.dto.QuestionDTO;
import com.chenxi.community.dto.QuestionQueryDTO;
import com.chenxi.community.exception.MyErrorCode;
import com.chenxi.community.exception.MyException;
import com.chenxi.community.mapper.QuestionExtMapper;
import com.chenxi.community.mapper.QuestionMapper;
import com.chenxi.community.mapper.UserMapper;
import com.chenxi.community.model.Question;
import com.chenxi.community.model.QuestionExample;
import com.chenxi.community.model.User;
import com.chenxi.community.model.UserExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: Mr.Chen
 * @Description: 对Question的逻辑处理
 * @Date:Created in 14:49 2020/3/9
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Override
    public PaginationDTO<QuestionDTO> getPaginationDTOList(String search, Integer page, Integer pageSize, Boolean isUser) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        int totalCount;             //问题条数
        Integer offset;             //limit查询限制条件
        List<Question> questions;   //Question集合

        if (isUser) {
            //用户中心, 传进来的search为User的accountId
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                    .andCreatorEqualTo(search);
            totalCount = (int) questionMapper.countByExample(questionExample);
            offset = paginationDTO.getPagination(totalCount, page, pageSize);
            questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, pageSize));
        } else {
            //非用户中心,传进来的search为用户搜索内容,为空时展示所有的问题
            if (StringUtils.isNotBlank(search)) {
                //拆分search，应用正则表达式
                String[] tags = StringUtils.split(search, " ");
                search = Arrays.stream(tags).collect(Collectors.joining("|"));
            }
            //根据search内容匹配查询问题
            QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
            questionQueryDTO.setSearch(search);
            totalCount = questionExtMapper.countBySearch(questionQueryDTO);
            offset = paginationDTO.getPagination(totalCount, page, pageSize);
            questionQueryDTO.setPage(offset);
            questionQueryDTO.setPageSize(pageSize);
            questions = questionExtMapper.selectBySearch(questionQueryDTO);
        }

        //QuestionDTO封装Question和User
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTOList.add(creatorWithAccountId(question, questionDTO));
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    @Override
    public QuestionDTO getQuestionById(Long id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new MyException(MyErrorCode.QUESTION_NOT_FOUND);
        }
        return creatorWithAccountId(question, questionDTO);
    }

    @Override
    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            //创建问题记录
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        } else {
            //更新问题内容
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            System.out.println(updateQuestion.getGmtModified());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                    .andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, questionExample);
            if (updated != 1) {
                throw new MyException(MyErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    @Override
    public void incView(Long id) {
        //增加阅读数
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incViewCount(question);
    }

    @Override
    public List<QuestionDTO> getRelatedQuestion(QuestionDTO questionDTO) {
        //获取当前问题的所有标签
        String[] tags = StringUtils.split(questionDTO.getTag(), ",");
        //将标签用|隔开 e.g: "TagA|TagB|TagC"，应用正则表达式
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        //设置Question对象
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(regexpTag);
        //查找相关的问题
        List<Question> questions = questionExtMapper.selectRelatedQuestion(question);
        //将问题转换成QuestionDTO集合返回
        List<QuestionDTO> QuestionDTOs = questions.stream().map(p -> {
            QuestionDTO questionDTO1 = new QuestionDTO();
            BeanUtils.copyProperties(p, questionDTO1);
            return questionDTO1;
        }).collect(Collectors.toList());
        return QuestionDTOs;
    }

    /**
     * 通过关联creator和accountId查找数据库获取QuestionDTO对象
     */
    private QuestionDTO creatorWithAccountId(Question question, QuestionDTO questionDTO) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(question.getCreator());
        List<User> users = userMapper.selectByExample(userExample);
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(users.get(0));
        return questionDTO;
    }
}
