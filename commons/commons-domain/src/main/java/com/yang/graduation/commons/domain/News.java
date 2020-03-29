package com.yang.graduation.commons.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * news
 * @author woshilll
 */
@Data
public class News implements Serializable {
    /**
     * 新闻id
     */
    private String id;

    /**
     * 新闻标题
     */
    private String title;

    /**
     * 新闻摘要
     */
    private String content;

    /**
     * 新闻内容
     */
    private String contentHtml;

    /**
     * 图片展示
     */
    private String image;

    /**
     * 上传时间
     */
    private Date postTime;

    /**
     * 点赞数量
     */
    private Integer likeCount;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 分类
     */
    private Integer categoryId;

    /**
     * 上传用户id
     */
    private String userId;

    /**
     * 0 待审核 1 审核通过 2 审核失败
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}