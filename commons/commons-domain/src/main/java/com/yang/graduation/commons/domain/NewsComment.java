package com.yang.graduation.commons.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * news_comment
 * @author woshilll
 */
@Data
public class NewsComment implements Serializable {
    /**
     * 评论id
     */
    private String id;

    /**
     * 新闻id
     */
    private String newsId;

    /**
     * 评论用户id
     */
    private String commentUserId;

    /**
     * 评论内容
     */
    private String details;

    /**
     * 评论时间
     */
    private Date commentDate;

    /**
     * 0 正常 1 非法平路
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}