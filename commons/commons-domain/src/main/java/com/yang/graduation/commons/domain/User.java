package com.yang.graduation.commons.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * user
 * @author woshilll
 */
@Data
@Table(name = "user")
public class User implements Serializable {
    /**
     * id
     */
    @Id
    private String id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户名 唯一
     */
    @NotNull(message = "用户名字不能为空!")
    @Length(min = 1, max = 20, message = "admin的名字应该在1-20之间!")
    private String name;

    /**
     * 用户密码
     */
    @NotNull(message = "用户密码不能为空!")
    @Length(min = 6, max = 16, message = "用户密码应该在6-16之间!")
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