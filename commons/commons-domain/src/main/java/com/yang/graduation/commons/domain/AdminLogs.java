package com.yang.graduation.commons.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/7 18:54
 */
@Data
@Table(name = "admin_logs")
public class AdminLogs implements Serializable {
    /**
     * 日志id
     */
    @Id
    private Integer id;
    /**
     * 登录ip
     */
    private String ip;
    /**
     * 通过的浏览器
     */
    private String browser;
    /**
     * admin id
     */
    private String adminId;
    /**
     * 登录用户名
     */
    private String name;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 登录地
     */
    private String city;
}
