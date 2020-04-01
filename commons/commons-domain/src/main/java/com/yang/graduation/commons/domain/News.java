package com.yang.graduation.commons.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * news
 * @author woshilll
 */
@Data
@Table(name = "news")
public class News implements Serializable {
    /**
     * 新闻id
     */
    @Id
    private String id;

    /**
     * 新闻标题
     */
    @NotNull(message = "新闻标题不能为空!")
    @Length(min = 4, max = 255, message = "新闻的标题至少是4位!")
    private String title;

    /**
     * 新闻摘要
     */
    @NotNull(message = "新闻摘要不能为空!")
    @Length(min = 4, message = "新闻的摘要至少是4位!")
    private String content;

    /**
     * 新闻内容
     */
    @NotNull(message = "新闻内容要不能为空!")
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
    @NotNull(message = "上传用户不能为空!")
    private String userId;

    /**
     * 0 待审核 1 审核通过 2 审核失败
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}