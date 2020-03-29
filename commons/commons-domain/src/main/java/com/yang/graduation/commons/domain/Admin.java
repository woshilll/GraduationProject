package com.yang.graduation.commons.domain;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Date;

/**
 * admin
 * @author liyang
 */
@Data
public class Admin implements Serializable {
    private String id;

    private String name;

    private String password;

    private Date registTime;

    private Date lastLoginTime;

    private String token;

    private String email;

    private String adminIcon;


    private static final long serialVersionUID = 1L;
}