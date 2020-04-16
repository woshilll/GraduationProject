package com.yang.graduation.commons.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/12 17:11
 */
@Data
public class AuthorDto implements Serializable {
    private String name;
    private String userIcon;
    private String talk;
}
