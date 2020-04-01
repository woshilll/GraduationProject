package com.yang.graduation.commons.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * news_category
 * @author woshilll
 */
@Data
@Table(name = "news_category")
public class NewsCategory implements Serializable {
    /**
     * 分类id
     */
    @Id
    private Integer id;

    /**
     * 分类名
     */
    @NotNull(message = "分类名不能为空!")
    @Length(min = 1, max = 10, message = "分类名最多有十位!")
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