package com.yang.graduation.commons.mapper;


import com.yang.graduation.commons.domain.NewsCategory;
import com.yang.graduation.commons.domain.analyze.CategoryNewsCountParam;
import com.yang.graduation.commons.tk.mybatis.MyMapper;

import java.util.List;

/**
 * 新闻分类mapper
 * @author yangge666
 */
public interface NewsCategoryMapper extends MyMapper<NewsCategory> {

    /**
     * 分析每个分类下新闻的数量
     * @return {@link List<CategoryNewsCountParam>}
     */
    List<CategoryNewsCountParam> categoryNewsCount();
}