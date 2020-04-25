package com.yang.graduation.front.controller;

import com.yang.graduation.commons.domain.User;
import com.yang.graduation.commons.utils.MyPattern;
import com.yang.graduation.dto.ResponseResult;
import com.yang.graduation.provider.api.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/24 12:26
 */
@RestController
@RequestMapping("/forget")
public class ForgetController {
    @Reference(version = "1.0.0")
    private UserService userService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 验证手机
     * @param phone
     * @return
     */
    @GetMapping("/validate/phone/{phone}")
    public ResponseResult<Void> validatePhone(@PathVariable String phone) {
        if (StringUtils.isNotBlank(phone) && phone.matches(MyPattern.PHONE)) {
            if (userService.validatePhone(phone)) {
                //说明手机号存在
                return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success");
            } else {
                return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "手机号不存在");
            }
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "手机号有误!");

    }

    /**
     * 验证邮箱
     * @param email
     * @return
     */
    @GetMapping("/validate/email/{email}")
    public ResponseResult<Void> validateEmail(@PathVariable String email) {
        if (StringUtils.isNotBlank(email) && email.matches(MyPattern.EMAIL)) {
            if (userService.validateEmail(email)) {
                //说明邮箱存在
                return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success");
            } else {
                return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "邮箱不存在");
            }
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "邮箱有误!");

    }

    /**
     * 通过手机号更新
     * @param phone
     * @param password
     * @param code
     * @return
     */
    @PostMapping("/update/phone/{phone}/{password}/{code}")
    public ResponseResult<Void> updateByPhone(@PathVariable String phone, @PathVariable String password, @PathVariable String code, HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String remoteAddrStr = redisTemplate.boundValueOps(remoteAddr).get();
        if (remoteAddrStr != null) {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "你操作太频繁了,稍后再试");
        }
        redisTemplate.boundValueOps(remoteAddr).set("1", 1, TimeUnit.MINUTES);
        if (StringUtils.isNoneBlank(phone, password, code) && phone.matches(MyPattern.PHONE)) {
            String codeStr = redisTemplate.boundValueOps("forget_" + phone).get();
            if (StringUtils.equals(code, codeStr)) {
                //验证码正确
                User user = userService.getUserByPhone(phone);
                if (user != null) {
                    user.setPassword(passwordEncoder.encode(password));
                    int res = userService.updateById(user);
                    if (res > 0) {
                        //success
                        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success");
                    }
                    return new ResponseResult<>(ResponseResult.CodeStatus.UPDATE_FAIL, "update fail");
                }
                return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "该用户不存在");
            }
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "验证码不正确");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "信息有误");
    }

    /**
     * 通过邮箱更新
     * @param email
     * @param password
     * @param code
     * @return
     */
    @PostMapping("/update/email/{email}/{password}/{code}")
    public ResponseResult<Void> updateByEmail(@PathVariable String email, @PathVariable String password, @PathVariable String code, HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String remoteAddrStr = redisTemplate.boundValueOps(remoteAddr).get();
        if (remoteAddrStr != null) {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "你操作太频繁了,稍后再试");
        }
        redisTemplate.boundValueOps(remoteAddr).set("1", 1, TimeUnit.MINUTES);
        if (StringUtils.isNoneBlank(email, password, code) && email.matches(MyPattern.EMAIL)) {
            String codeStr = redisTemplate.boundValueOps("forget_" + email).get();
            if (StringUtils.equals(code, codeStr)) {
                //验证码正确
                User user = userService.getUserByEmail(email);
                if (user != null) {
                    user.setPassword(passwordEncoder.encode(password));
                    int res = userService.updateById(user);
                    if (res > 0) {
                        //success
                        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success");
                    }
                    return new ResponseResult<>(ResponseResult.CodeStatus.UPDATE_FAIL, "update fail");
                }
                return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "该用户不存在");
            }
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "验证码不正确");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "信息有误");
    }
}
