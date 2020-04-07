package com.yang.graduation.commons.domain.analyze;

import lombok.Data;

import java.io.Serializable;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/7 16:08
 */
@Data
public class CategoryNewsCountParam implements Serializable {
    /**
     * 分类名
     */
    private String categoryName;
    /**
     * 当前分类下新闻总数
     */
    private Integer newsCount;
}
