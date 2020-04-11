package com.yang.graduation.oauth2.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录参数
 * @author woshilll
 * @version v1.0.0
 * @date 2020/3/31 22:22
 */
@Data
public class LoginParam implements Serializable {
    private String username;
    private String password;
}
