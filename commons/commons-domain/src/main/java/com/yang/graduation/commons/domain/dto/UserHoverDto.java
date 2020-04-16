package com.yang.graduation.commons.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/14 21:42
 */
@Data
public class UserHoverDto implements Serializable {
    private String id;
    private String nickName;
    private String talk;
    private String icon;
    private Integer getLikeCount;
    private Integer getCommentCount;
    private Integer status;
}
