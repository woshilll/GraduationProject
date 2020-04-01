package com.yang.graduation.oauth2.dto;

import lombok.Data;

/**
 * 用户info dto
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/1 10:23
 */
@Data
public class LoginInfo {
    private String name;
    private String avatar;
    private String token;
}
