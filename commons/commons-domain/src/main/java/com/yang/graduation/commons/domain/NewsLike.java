package com.yang.graduation.commons.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * news_like
 * @author woshilll
 */
@Data
@Table(name = "news_like")
public class NewsLike implements Serializable {
    /**
     * 点赞id
     */
    @Id
    private Integer id;

    /**
     * 新闻id
     */
    @NotNull(message = "新闻id是必须的!")
    private String newsId;

    /**
     * 用户id
     */
    @NotNull(message = "用户id是必须的!")
    private String userId;

    /**
     * 点赞时间
     */
    private Date likeTime;

    private static final long serialVersionUID = 1L;
}