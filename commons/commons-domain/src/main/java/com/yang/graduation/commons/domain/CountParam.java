package com.yang.graduation.commons.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 数量参数
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/6 21:11
 */
@Data
public class CountParam implements Serializable {
    /**
     * 管理员数量
     */
    private Integer adminCount;
    /**
     * 用户数量
     */
    private Integer userCount;
    /**
     * 新闻数量
     */
    private Integer newsCount;
    /**
     * 评论数量
     */
    private Integer commentCount;
}
