package com.yang.graduation.commons.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * news_category
 * @author woshilll
 */
@Data
public class NewsCategory implements Serializable {
    /**
     * 分类id
     */
    private Integer id;

    /**
     * 分类名
     */
    private String name;

    /**
     * 状态 0 在线 1 下线
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date updated;

    private static final long serialVersionUID = 1L;
}