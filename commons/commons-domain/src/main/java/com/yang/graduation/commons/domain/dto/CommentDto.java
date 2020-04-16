package com.yang.graduation.commons.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/15 13:31
 */
@Data
public class CommentDto implements Serializable {
    private String name;
    private String userDetails;
    private String newsId;
}
