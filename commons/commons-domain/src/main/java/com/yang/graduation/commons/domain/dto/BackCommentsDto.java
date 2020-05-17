package com.yang.graduation.commons.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/5/17 00:58
 */
@Data
public class BackCommentsDto {
    private String id;
    private String newsId;
    private String userId;
    private String newsTitle;
    private String username;
    private Integer status;
    private String details;
    private Date commentDate;
    private Integer newsStatus;
    private Integer userStatus;
}
