package com.yang.graduation.provider.api;

import com.yang.graduation.commons.domain.NewsCategory;

import java.util.List;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/2 21:36
 */
public interface NewsCategoryService {
    /**
     * 得到所有的新闻分类
     * @return {@link List}
     */
    List<NewsCategory> getAll();

    /**
     * 得到一个
     * @param id
     * @return
     */
    NewsCategory getOne(Integer id);
}
