package com.yang.graduation.commons.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

/**
 * admin
 * @author liyang
 */
@Data
public class Admin implements Serializable {
    private String id;

    @Length(min = 2, max = 10, message = "admin的名字应该在2-10之间!")
    private String name;

    @Length(min = 6, max = 16, message = "密码长度在6-16之间!")
    private String password;

    private Date registTime;

    private Date lastLoginTime;

    private String token;

    private String email;

    private String adminIcon;


    private static final long serialVersionUID = 1L;
}