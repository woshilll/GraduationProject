package com.yang.graduation.commons.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/2 12:28
 */
@Data
public class PageInfo<T> implements Serializable {
    private int draw;
    private int start;
    private int length;
    private int recordsTotal;
    private int recordsFiltered;
    private List<T> data;
    private String error;
}
