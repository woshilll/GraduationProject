package com.yang.graduation.provider.api;

import com.yang.graduation.commons.domain.NewsParam;
import com.yang.graduation.commons.domain.PageInfo;

import java.util.Map;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/2 21:35
 */
public interface NewsService {
    /**
     * 新增新闻
     * @param newsParam {@link NewsParam}
     * @return 1 0
     */
    int insertNews(NewsParam newsParam);

    /**
     * 查询新闻总数
     * @param map 查询字段 title status 时间区间 start end
     * @return {@link Integer}
     */
    int count(Map<String, Object> map);

    /**
     * 得到新闻
     * @param map 查询字段 title status 时间区间 start end page limit
     * @return {@link PageInfo}
     */
    PageInfo<NewsParam> getNewsList(Map<String, Object> map);

    /**
     * 通过id查找新闻
     * @param id id
     * @return {@link NewsParam}
     */
    NewsParam getNewsById(String id);
}
