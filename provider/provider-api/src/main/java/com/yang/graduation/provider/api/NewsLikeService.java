package com.yang.graduation.provider.api;

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
}
