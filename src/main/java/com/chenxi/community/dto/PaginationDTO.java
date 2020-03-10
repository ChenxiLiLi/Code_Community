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
    private boolean showFirstPage;                      //首页
    private boolean showPrevious;                       //上一页
    private boolean showNext;                           //下一页
    private boolean showEndPage;                        //最后一页
    private Integer totalPage;                          //总页数
    private Integer page;                               //当前页数
    private List<QuestionDTO> questions;                //Question列表
    private List<Integer> pages = new ArrayList<>();    //用来展示的页码

    /**
     * @param totalCount 问题条数
     * @return 返回当前页码数 - 1
     */
    public Integer setPagination(Integer totalCount, Integer page, Integer pageSize) {
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
        //计算数据库查询时需要的Limit的左侧条件，用来返回
        Integer offset = pageSize * (page - 1);
        //添加页码，展示7个
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);  //每次都添加进集合的首部
            }
            if (page + i <= totalPage) {
                pages.add(page + i);                     //在集合的尾部追加数据
            }
        }
        this.page = page;
        //展示上一页按钮
        showPrevious = (page != 1);
        //展示下一页按钮
        showNext = (!page.equals(totalPage));
        //展示首页按钮
        showFirstPage = !pages.contains(1);
        //展示最后一页按钮
        showEndPage = !pages.contains(totalPage);

        return offset;
    }
}
