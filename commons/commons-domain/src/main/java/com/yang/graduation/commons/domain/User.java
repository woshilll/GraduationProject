package com.yang.graduation.commons.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user
 * @author woshilll
 */
@Data
public class User implements Serializable {
    /**
     * id
     */
    private String id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户名 唯一
     */
    private String name;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String userIcon;

    /**
     * 自我介绍
     */
    private String talk;

    private String token;

    /**
     * 注册时间
     */
    private Date registTime;

    /**
     * 上次登录时间
     */
    private Date lastLoginTime;

    /**
     * 0 正常 1 禁言 2 封禁
     */
    private Integer banned;

    private static final long serialVersionUID = 1L;
}