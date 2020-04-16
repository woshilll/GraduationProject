package com.yang.graduation.commons.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/12 16:54
 */
@Data
public class FrontCommentsDto implements Serializable {
    private String details;
    private Date commentDate;
    private String username;
    private String icon;
}
