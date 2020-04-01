package com.yang.graduation.oauth2.controller;

import com.google.common.collect.Maps;
import com.yang.graduation.commons.domain.Admin;
import com.yang.graduation.commons.utils.MapperUtil;
import com.yang.graduation.commons.utils.OkHttpClientUtil;
import com.yang.graduation.dto.ResponseResult;
import com.yang.graduation.oauth2.dto.LoginInfo;
import com.yang.graduation.oauth2.dto.LoginParam;

import com.yang.graduation.provider.api.AdminService;
import okhttp3.Response;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/3/31 22:22
 */
@RestController
public class UserLoginController {
    private static final String URL_OAUTH_TOKEN = "http://localhost:9100/oauth/token";

    @Value("${graduation.oauth2.grant_type}")
    public String grantType;
    @Value("${graduation.oauth2.client_id}")
    public String clientId;
    @Value("${graduation.oauth2.client_secret}")
    public String clientSecret;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private BCryptPasswordEncoder passwordEncoder;
    @Resource
    private TokenStore tokenStore;

    @Reference(version = "1.0.0")
    private AdminService adminService;

    /**
     * 用户登录
     *
     * @param loginParam {@link LoginParam}
     * @return {@link ResponseResult}
     */
    @PostMapping(value = "/user/login")
    public ResponseResult<Map<String, Object>> login(@RequestBody LoginParam loginParam) {
        Map<String, Object> result = Maps.newHashMap();
        //验证密码是否正确
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginParam.getUsername());
        //错误
        if (userDetails == null || !passwordEncoder.matches(loginParam.getPassword(), userDetails.getPassword())) {
            return new ResponseResult<>(ResponseResult.CodeStatus.LOGIN_FAIL, "账号或密码错误!", null);
        }
        Map<String, String> params = Maps.newHashMap();
        params.put("username", loginParam.getUsername());
        params.put("password", loginParam.getPassword());
        params.put("grant_type", grantType);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        try {
            Response response = OkHttpClientUtil.getInstance().postData(URL_OAUTH_TOKEN, params);
            assert response.body() != null;
            String jsonString = response.body().string();
            Map<String, Object> jsonMap = MapperUtil.json2map(jsonString);
            String token = String.valueOf(jsonMap.get("access_token"));
            result.put("token", token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "登录成功!", result);
    }


    /**
     * 获取用户信息
     *
     * @param request {@link HttpServletRequest}
     * @return {@link ResponseResult}
     */
    @GetMapping("/user/info")
    public ResponseResult<LoginInfo> info(HttpServletRequest request) {
        //获取token
        String accessToken = request.getParameter("access_token");
        //获取认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin admin = adminService.getAdmin(authentication.getName());
        //封装并返回结果
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setName(admin.getName());
        loginInfo.setAvatar(admin.getAdminIcon());
        loginInfo.setToken(accessToken);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取用户信息", loginInfo);
    }

    /**
     * 用户注销
     *
     * @param request {@link HttpServletRequest}
     * @return {@link ResponseResult}
     */
    @PostMapping("/user/logout")
    public ResponseResult<Void> logout(HttpServletRequest request) {
        //获取token
        String accessToken = request.getParameter("access_token");
        //删除token 注销
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(accessToken);
        tokenStore.removeAccessToken(oAuth2AccessToken);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "用户已注销");
    }
}
