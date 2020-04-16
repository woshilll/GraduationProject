package com.yang.graduation.commons.mapper;


import com.yang.graduation.commons.domain.News;
import com.yang.graduation.commons.domain.NewsParam;
import com.yang.graduation.commons.tk.mybatis.MyMapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 得到最新的4条新闻
     * @return
     */
    List<News> least4();

    /**
     * 点赞最多4条新闻
     * @return
     */
    List<NewsParam> likeMost4();

    /**
     * 评论最多的两条
     * @return
     */
    List<NewsParam> commentMost2();

    /**
     * 随机五条新闻
     * @return
     */
    List<NewsParam> random5();

    /**
     * admin发布的4条
     * @return
     */
    List<NewsParam> adminPost4();
    /**
     * 通过分类查找到分类下所有新闻
     * @param categoryId
     * @return
     */
    List<NewsParam> getNewsByCategory(@Param("categoryId") Integer categoryId);

    /**
     * 通过标题查找新闻
     * @param title
     * @return
     */
    List<NewsParam> search(@Param("title") String title);

    /**
     * 找到最受欢迎的三个新闻
     * @param id
     * @return
     */
    List<News> getLikeMost3ByUserId(@Param("id") String id);

    /**
     * 根据用户id找到评论最多的新闻
     * @param id
     * @return
     */
    News getCommentMostByUserId(@Param("id") String id);
    /**
     * 根据用户id找到点赞最多的新闻
     * @param id
     * @return
     */
    News getLikeMostByUserId(@Param("id") String id);

    /**
     * 找一个最新的新闻
     * @param id
     * @return
     */
    News selectOneByNew(@Param("id") String id);
}