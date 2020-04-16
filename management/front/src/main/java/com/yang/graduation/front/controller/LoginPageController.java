package com.yang.graduation.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 跳转登录页
 * 不需要认证
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/15 23:38
 */
@Controller
public class LoginPageController {
    @GetMapping("/login")
    public String toLogin() {
        return "login/login";
    }
}
