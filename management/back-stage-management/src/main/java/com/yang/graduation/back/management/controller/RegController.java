package com.yang.graduation.back.management.controller;

import com.yang.graduation.commons.domain.Admin;
import com.yang.graduation.dto.ResponseResult;
import com.yang.graduation.provider.api.AdminService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 管理员注册服务.
 *
 * @author woshilll
 * @version v1.0.0
 * @date 2020/3/29 00:05
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/reg")
public class RegController {

    @Reference(version = "1.0.0")
    private AdminService adminService;

    @PostMapping(value = "")
    public ResponseResult<Admin> reg(@RequestBody @Valid Admin admin, BindingResult validResult) {
        //valid验证传过来的数据是否合法
        if (validResult.hasErrors()) {
            return validResult(validResult);
        }
        String message = validateReg(admin);
        //验证通过
        if (message == null) {
            int result = adminService.regAdmin(admin);

            //注册成功
            if (result > 0) {
                admin = adminService.getAdmin(admin.getName());
                return new ResponseResult<>(HttpStatus.OK.value(), "用户注册成功!", admin);
            }
        }
        return new ResponseResult<>(HttpStatus.NOT_ACCEPTABLE.value(), message != null ? message : "用户注册失败!");
    }

    private String validateReg(Admin admin) {
        Admin getAdmin = adminService.getAdmin(admin.getName());
        if (getAdmin != null) {
            return "该用户已存在";
        }
        return null;
    }

    /**
     * 验证数据的合法性
     *
     * @param result {@link Admin}
     * @return
     */
    private ResponseResult<Admin> validResult(BindingResult result) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ObjectError allError : result.getAllErrors()) {
            stringBuilder.append(allError.getDefaultMessage());
        }
        return new ResponseResult<>(HttpStatus.NOT_ACCEPTABLE.value(), stringBuilder.toString());
    }
}
