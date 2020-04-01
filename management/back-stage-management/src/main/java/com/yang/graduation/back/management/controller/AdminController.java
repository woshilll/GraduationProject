package com.yang.graduation.back.management.controller;

import com.yang.graduation.commons.domain.Admin;
import com.yang.graduation.dto.ResponseResult;
import com.yang.graduation.provider.api.AdminService;
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
 * @date 2020/4/1 11:39
 */
@RestController
@RequestMapping("/back/admin")
public class AdminController {

    @Reference(version = "1.0.0")
    private AdminService adminService;
    /**
     * 获取个人信息
     *
     * @param username 用户名
     * @return {@link ResponseResult}
     */
    @GetMapping(value = "info/{username}")
    public ResponseResult<Admin> info(@PathVariable String username) {
        Admin umsAdmin = adminService.getAdmin(username);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取个人信息", umsAdmin);
    }

    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody Admin admin) {
        int res = adminService.updateById(admin);
        if (res > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "更新成功!");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.UPDATE_FAIL, "更新失败!");
    }
}
