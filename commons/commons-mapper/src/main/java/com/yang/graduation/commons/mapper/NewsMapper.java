package com.yang.graduation.commons.mapper;


import com.yang.graduation.commons.domain.News;
import com.yang.graduation.commons.domain.NewsParam;
import com.yang.graduation.commons.tk.mybatis.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * 新闻mapper
 * @author yangge666
 */

public interface NewsMapper extends MyMapper<News> {

    /**
     * 查询数量
     * @param map 查询字段 title status 时间区间 start end
     * @return
     */
    int count(Map<String, Object> map);

    /**
     * 分页 + 模糊查询
     * @param map 条件
     * 必须具备 page/起始位置 limit/每页长度 查询字段 title status 时间区间 start end
     * @return {@link List}
     */
    List<NewsParam> page(Map<String, Object> map);
}