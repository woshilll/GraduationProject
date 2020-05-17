package com.yang.graduation.commons.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * admin
 * @author liyang
 */
@Data
@Table(name = "admin")
public class Admin implements Serializable {
    @Id
    private String id;

    @NotNull(message = "admin名字不能为空!")
    @Length(min = 2, max = 10, message = "admin的名字应该在2-10之间!")
    private String name;
    @NotNull(message = "admin密码不能为空!")
    @Length(min = 6, max = 16, message = "密码长度在6-16之间!")
    private String password;

    private Date registTime;

    private Date lastLoginTime;

    private String token;

    private String email;

    private String adminIcon;
    private String nickName;
    private String node;
    private Integer status;
    private Integer root;


    private static final long serialVersionUID = 1L;
}