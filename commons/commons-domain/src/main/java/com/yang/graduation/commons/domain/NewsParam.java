package com.yang.graduation.commons.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/3 22:39
 */
@Data
public class NewsParam implements Serializable {
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
     * 分类 id
     */
    private Integer categoryId;

    /**
     * 分类名
     */
    private String categoryName;
    /**
     * 上传用户id
     */
    private String userId;
    /**
     * 上传作者名
     */
    private String authorName;

    /**
     * 0 待审核 1 审核通过 2 审核失败
     */
    private Integer status;

    /**
     * 审核人
     */
    private String audit;
    /**
     * 0 没有删除 1 删除
     */
    private Integer isDelete;

    private String nickName;
    private Integer authorStatus;
    private String talk;
    private String userIcon;

}
