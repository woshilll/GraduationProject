package com.yang.graduation.commons.mapper;


import com.yang.graduation.commons.domain.NewsLike;
import com.yang.graduation.commons.tk.mybatis.MyMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 新闻点赞mapper
 * @author yangge666
 */

public interface NewsLikeMapper extends MyMapper<NewsLike> {
    /**
     * 通过新闻作者的id得到已发布的新闻所有点赞数
     * @param id
     * @return
     */
    int getLikeCountByNewsAuthorId(@Param("id") String id);
}