package com.yang.graduation.provider.api;

import com.yang.graduation.commons.domain.NewsLike;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/2 21:37
 */
public interface NewsLikeService {
    /**
     * 通过新闻id得到点赞数
     * @param newsId
     * @return
     */
    int getLikeCountsByNewsId(String newsId);

    /**
     * 当前用户当前新闻是否点赞
     * @param id
     * @param newsId
     * @return
     */
    NewsLike isLike(String id, String newsId);

    /**
     * 点赞
     * @param id
     * @param newsId
     * @return
     */
    int remove(String id, String newsId);

    /**
     * 取消赞
     * @param id
     * @param newsId
     */
    int create(String id, String newsId);
}
