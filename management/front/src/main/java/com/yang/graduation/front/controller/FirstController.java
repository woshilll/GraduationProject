package com.yang.graduation.front.controller;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

/**
 * 首页和用户中心页
 * 首页不需要认证
 * 用户中心页需要认证
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/11 12:47
 */
@Controller
public class FirstController {
    @Resource
    private TokenStore tokenStore;

    @GetMapping("/index")
    public String firstPage() {
        return "index";
    }

    @GetMapping("/user/{name}/{token}")
    public String test(@PathVariable(value = "name") String name, @PathVariable(value = "token") String token, Model model) {
        OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(token);
        if (oAuth2Authentication != null && oAuth2Authentication.getName().equals(name)) {
            model.addAttribute("name", name);
            return "profile";
        }
        return "login/toLogin";
    }
}
