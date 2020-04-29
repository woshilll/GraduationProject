package com.yang.graduation.commons.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/28 13:48
 */
@Data
public class NewsStatus implements Serializable {
    public NewsStatus(Integer status, Integer count) {
        this.status = status;
        this.count = count;
    }

    public NewsStatus() {
    }

    private Integer status;
    private Integer count;
}
