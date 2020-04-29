package com.yang.graduation.commons.domain.analyze;

import lombok.Data;

import java.io.Serializable;

/**
 * 分类下点赞数和评论数分析
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/8 16:58
 */
@Data
public class CommentAndLikeParam implements Serializable {
    private Integer categoryId;
    /**
     * 分类名
     */
    private String categoryName;
    /**
     * 评论数
     */
    private Integer commentCount;
    /**
     * 点赞数
     */
    private Integer likeCount;
}
