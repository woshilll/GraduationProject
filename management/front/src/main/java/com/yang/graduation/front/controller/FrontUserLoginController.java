package com.yang.graduation.front.controller;

import com.google.common.collect.Maps;
import com.yang.graduation.commons.utils.MapperUtil;
import com.yang.graduation.commons.utils.OkHttpClientUtil;
import com.yang.graduation.dto.ResponseResult;
import com.yang.graduation.front.dto.LoginParam;
import com.yang.graduation.front.service.UserDetailsServiceImpl;
import com.yang.graduation.provider.api.UserService;
import okhttp3.Response;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 登录以及注销
 * @author woshilll
 * @version v1.0.0
 * @date 2020/3/31 22:22
 */
@RestController
public class FrontUserLoginController {
    private static final String URL_OAUTH_TOKEN = "http://localhost:9300/oauth/token";

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
    private UserService userService;
    /**
     * 用户登录
     *
     * @param loginParam {@link LoginParam}
     * @return {@link ResponseResult}
     */
    @PostMapping(value = "/front/login")
    public ResponseResult<Map<String, Object>> login(@RequestBody LoginParam loginParam, HttpServletRequest request) {
        Map<String, Object> result = Maps.newHashMap();
        //验证密码是否正确
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginParam.getUsername());
        //错误
        if (userDetails == null || !passwordEncoder.matches(loginParam.getPassword(), userDetails.getPassword())) {
            return new ResponseResult<>(ResponseResult.CodeStatus.LOGIN_FAIL, "账号或密码错误!", null);
        }
        //账号被封禁
        if (UserDetailsServiceImpl.BANNED.equals(userDetails.getUsername())) {
            return new ResponseResult<>(ResponseResult.CodeStatus.COUNT_BANNED, "该账号已被封禁!请联系伟大洋洋管理员!", null);
        }
        //请求oauth/token得到token
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
        result.put("name", userDetails.getUsername());
        //设置登录时间
        userService.updateLoginTime(userDetails.getUsername());
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "登录成功!", result);
    }

    /**
     * 用户注销
     *
     * @return {@link ResponseResult}
     */
    @PostMapping("/front/logout")
    public ResponseResult<Void> logout(@RequestBody String  access_token) {
        //删除token 注销
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(access_token);
        tokenStore.removeAccessToken(oAuth2AccessToken);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "用户已注销");
    }
}
