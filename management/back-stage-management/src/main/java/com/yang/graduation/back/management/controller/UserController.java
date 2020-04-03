package com.yang.graduation.back.management.controller;

import com.yang.graduation.commons.domain.PageInfo;
import com.yang.graduation.commons.domain.User;
import com.yang.graduation.dto.ResponseResult;
import com.yang.graduation.dto.param.IconParam;
import com.yang.graduation.provider.api.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/3 10:34
 */
@RestController
@RequestMapping("/back/user")
public class UserController {

    @Reference(version = "1.0.0")
    private UserService userService;
    /**
     * 获取个人信息
     *
     * @param username 用户名
     * @return {@link ResponseResult}
     */
    @GetMapping(value = "info/{username}")
    public ResponseResult<User> info(@PathVariable String username) {
        User user = userService.getUser(username);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取个人信息", user);
    }

    /**
     * 更新个人信息
     * @param user {@link User}
     * @return {@link ResponseResult}
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody User user) {
        int res = userService.updateById(user);
        if (res > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "更新成功!");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.UPDATE_FAIL, "更新失败!");
    }

    /**
     * 更新用户头像
     * @param iconParam {@link IconParam}
     * @return {@link ResponseResult}
     */
    @PostMapping("/modify/icon")
    public ResponseResult<Void> modifyIcon(@RequestBody IconParam iconParam) {
        int res = userService.modifyIcon(iconParam.getPath(), iconParam.getName());
        if (res > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "更新头像成功!");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.UPLOAD_FAIL, "更新头像失败!");
    }

    /**
     * 模糊查询 + 分页查询
     * @param map 必定包含的字段 page/起始 limit/每页大小 name/用户名 email/邮箱 nickName/昵称
     * @return {@link ResponseResult}
     */
    @PostMapping("/select/all")
    public ResponseResult<PageInfo<User>> selectAll(@RequestBody Map<String, Object> map) {
        PageInfo<User> userList = userService.getUserList(map);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "查询成功", userList);
    }

    /**
     * 删除用户
     * @param id 用户id
     * @return {@link ResponseResult}
     */
    @PostMapping("/delete/{id}")
    public ResponseResult<Void> delete(@PathVariable String id) {
        int res = userService.deleteById(id);
        if (res > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "删除用户成功!");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.DELETE_FAIL, "删除用户失败!");
    }
}
