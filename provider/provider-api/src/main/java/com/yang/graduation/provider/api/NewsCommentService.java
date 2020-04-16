package com.yang.graduation.provider.api;

import com.yang.graduation.commons.domain.dto.CommentDto;
import com.yang.graduation.commons.domain.dto.FrontCommentsDto;
import com.yang.graduation.commons.domain.NewsComment;

import java.util.List;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/2 21:36
 */
public interface NewsCommentService {
    /**
     * 通过新闻id查找所有评论
     * @param newsId 新闻ID
     * @return {@link List<NewsComment>}
     */
    List<NewsComment> getCommentsByNewsId(String newsId);

    /**
     * 根据评论id更新评论状态
     * @param newsComment {@link NewsComment}
     * @return 1 0
     */
    int updateComment(NewsComment newsComment);

    /**
     * 前台通过新闻id得到评论
     * @param newsId
     * @return
     */
    List<FrontCommentsDto> frontComments(String newsId);

    /**
     * 创建评论
     * @param commentDto
     * @return
     */
    int createComment(CommentDto commentDto);

    /**
     * 通过newsId找到评论数
     * @param newsId
     * @return
     */
    int getCommentsCountByNewsId(String newsId);
}
