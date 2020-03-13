package com.chenxi.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mr.Chen
 * @Description: 封装页面的元素，包括QuestionDTO和分页内容
 * @Date:Created in 18:38 2020/3/10
 */
@Data
public class PaginationDTO {
    /**
     * 是否展示首页
    */
    private Boolean showFirstPage;
    /**
     * 是否展示上一页
     */
    private Boolean showPrevious;
    /**
     * 是否展示下一页
     */
    private Boolean showNext;
    /**
     * 是否展示最后一页
     */
    private Boolean showEndPage;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 页码
     */
    private Integer page;
    private List<QuestionDTO> questions;
    private List<Integer> pages;

    /**
     * @param totalCount 问题条数
     * @return 返回第一个返回记录行的偏移量，初始量为0
     */
    public Integer getPagination(Integer totalCount, Integer page, Integer pageSize) {
        //获取总页数
        if (totalCount % pageSize == 0) {
            totalPage = totalCount / pageSize;
        } else {
            totalPage = totalCount / pageSize + 1;
        }
        //页码越界处理
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        Integer offset = pageSize * (page - 1);
        int part = 3;
        //添加页码，展示7个
        pages = new ArrayList<>();
        pages.add(page);
        for (int i = 1; i <= part; i++) {
            if (page - i > 0) {
                //每次都添加进集合的首部
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                //在集合的尾部追加数据
                pages.add(page + i);
            }
        }
        this.page = page;
        showPrevious = (page != 1);
        showNext = (!page.equals(totalPage));
        showFirstPage = !pages.contains(1);
        showEndPage = !pages.contains(totalPage);
        return offset;
    }
}
