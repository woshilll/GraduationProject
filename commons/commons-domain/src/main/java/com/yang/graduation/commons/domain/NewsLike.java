package com.yang.graduation.commons.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * news_like
 * @author woshilll
 */
@Data
public class NewsLike implements Serializable {
    /**
     * 点赞id
     */
    private Integer id;

    /**
     * 新闻id
     */
    private String newsId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 点赞时间
     */
    private Date likeTime;

    private static final long serialVersionUID = 1L;
}