package com.yang.graduation.commons.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * news_comment
 * @author woshilll
 */
@Data
@Table(name = "news_comment")
public class NewsComment implements Serializable {
    /**
     * 评论id
     */
    @Id
    private String id;

    /**
     * 新闻id
     */
    @NotNull(message = "新闻id是必须的!")
    @Column(name = "news_id")
    private String newsId;

    /**
     * 评论用户id
     */
    @NotNull(message = "用户id是必须的!")
    @Column(name = "comment_user_id")
    private String commentUserId;

    /**
     * 评论内容
     */
    @NotNull(message = "评论内容是必须的!")
    @Length(min = 1, max = 255, message = "评论长度需在1-255之间!")
    private String details;

    /**
     * 评论时间
     */
    @Column(name = "comment_date")
    private Date commentDate;

    /**
     * 0 正常 1 非法评论
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}