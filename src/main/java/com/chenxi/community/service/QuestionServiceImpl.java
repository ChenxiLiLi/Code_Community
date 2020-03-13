package com.chenxi.community.service;

import com.chenxi.community.dto.PaginationDTO;
import com.chenxi.community.dto.QuestionDTO;
import com.chenxi.community.mapper.QuestionMapper;
import com.chenxi.community.mapper.UserMapper;
import com.chenxi.community.model.Question;
import com.chenxi.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public PaginationDTO getPaginationDTOList(String accountId, Integer page, Integer pageSize) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount;         //问题条数
        Integer offset;             //limit查询限制条件
        List<Question> questions;   //Question集合
        if ("0".equals(accountId)) {
            //查询全部问题
            totalCount = questionMapper.count();
            offset = paginationDTO.getPagination(totalCount, page, pageSize);
            questions = questionMapper.list(offset, pageSize);
        } else {
            //查询当前用户发布的问题
            totalCount = questionMapper.userCount(accountId);
            offset = paginationDTO.getPagination(totalCount, page, pageSize);
            questions = questionMapper.userList(accountId, offset, pageSize);
        }
        //QuestionDTO封装Question和User
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findByAccountId(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    @Override
    public QuestionDTO getQuestionById(Integer id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.getQuestionById(id);
        User user = userMapper.findByAccountId(question.getCreator());
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

}
