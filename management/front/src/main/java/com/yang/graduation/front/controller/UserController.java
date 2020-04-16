package com.yang.graduation.front.controller;

import com.yang.graduation.commons.domain.User;
import com.yang.graduation.commons.domain.dto.UserHoverDto;
import com.yang.graduation.dto.ResponseResult;
import com.yang.graduation.front.dto.LoginParam;
import com.yang.graduation.provider.api.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/14 22:10
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Reference(version = "1.0.0")
    private UserService userService;

    /**
     * 得到hover需要的用户信息
     * @param name
     * @return
     */
    @GetMapping("/userHover/{name}")
    public ResponseResult<UserHoverDto> userHover(@PathVariable("name") String name) {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success", userService.getUserHoverDto(name));
    }

    /**
     * 注册验证用户是否存在
     * @param username
     * @return
     */
    @PostMapping("/validateName/{username}")
    public ResponseResult<Void> validateName(@PathVariable String username) {
        User user = userService.getUser(username);
        if (user == null) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "fail");
    }

    /**
     * 注册
     * @param loginParam
     * @return
     */
    @PostMapping("/reg")
    public ResponseResult<Void> reg(@RequestBody LoginParam loginParam) {
        User user = new User();
        user.setName(loginParam.getUsername());
        user.setPassword(loginParam.getPassword());
        int res = userService.regUser(user);
        if (res > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.INSERT_FAIL, "fail");
    }

}
