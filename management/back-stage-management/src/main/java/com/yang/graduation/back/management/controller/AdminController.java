package com.yang.graduation.back.management.controller;

import com.yang.graduation.commons.domain.Admin;
import com.yang.graduation.commons.domain.AdminLogs;
import com.yang.graduation.commons.domain.PageInfo;
import com.yang.graduation.dto.ResponseResult;
import com.yang.graduation.dto.param.IconParam;
import com.yang.graduation.provider.api.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

    /**
     * 更新个人信息
     * @param admin {@link Admin}
     * @return {@link ResponseResult}
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody Admin admin) {
        int res = adminService.updateById(admin);
        if (res > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "更新成功!");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.UPDATE_FAIL, "更新失败!");
    }
    /**
     * 更新个人信息
     * @param admin {@link Admin}
     * @return {@link ResponseResult}
     */
    @PostMapping("/info/update")
    public ResponseResult<Void> updateInfo(@RequestBody Admin admin) {
        int res = adminService.updateById(admin);
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
        int res = adminService.modifyIcon(iconParam.getPath(), iconParam.getName());
        if (res > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "更新头像成功!");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.UPLOAD_FAIL, "更新头像失败!");
    }
    /**
     * 更新用户头像
     * @param iconParam {@link IconParam}
     * @return {@link ResponseResult}
     */
    @PostMapping("/info/modify/icon")
    public ResponseResult<Void> infoModifyIcon(@RequestBody IconParam iconParam) {
        int res = adminService.modifyIcon(iconParam.getPath(), iconParam.getName());
        if (res > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "更新头像成功!");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.UPLOAD_FAIL, "更新头像失败!");
    }

    /**
     * 模糊查询所有的用户
     * @param map page/limit/name/email
     * @return
     */
    @PostMapping("/select/all")
    public ResponseResult<PageInfo<Admin>> selectAll(@RequestBody Map<String, Object> map) {
        PageInfo<Admin> adminList = adminService.getAdminList(map);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "查询成功", adminList);
    }

    /**
     * 通过主键删除用户
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}/{name}")
    public ResponseResult<Void> delete(@PathVariable String id, @PathVariable String name) {
        int res = adminService.deleteById(id);
        if (res > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "删除用户成功!");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.DELETE_FAIL, "删除用户失败!");
    }

    /**
     * 验证密码的正确性,用于修改密码时
     * @param name
     * @param password
     * @return
     */
    @GetMapping("/validate/password")
    public ResponseResult<Void> validatePwd(String name, String password) {
        Admin admin = adminService.getAdmin(name);
        if (bCryptPasswordEncoder.matches(password, admin.getPassword())) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "密码正确");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.VALIDATE_PWD, "密码错误!!!");
    }

    /**
     * 更新密码
     * @param name 当前用户
     * @param newPwd 新密码
     * @return {@link ResponseResult}
     */
    @PostMapping("/update/pwd")
    public ResponseResult<Void> updatePwd(String name, String newPwd){
        int res = adminService.updatePwd(name, newPwd);
        if (res > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "更新密码成功!");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.UPDATE_FAIL, "更新密码失败!");
    }

    /**
     * 得到所有的登录日志
     * @return {@link ResponseResult<List<AdminLogs>>}
     */
    @GetMapping("/getAdminLogs")
    public ResponseResult<List<AdminLogs>> getAdminLogs() {
        List<AdminLogs> adminLogs = adminService.getAdminLogs();
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "成功!", adminLogs.stream().limit(20).collect(Collectors.toList()));
    }

    /**
     * 新增管理员
     * @param name
     * @return
     */
    @PostMapping("/insert/{name}")
    public ResponseResult<Void> insert(@PathVariable String name) {
        if (StringUtils.isNotBlank(name)) {
            Admin admin = adminService.getAdmin(name);
            if (admin == null) {
                admin = new Admin();
                admin.setNickName(name);
                admin.setName(name);
                admin.setPassword("123456");
                int res = adminService.regAdmin(admin);
                if (res > 0) {
                    return new ResponseResult<>(ResponseResult.CodeStatus.OK, "新增管理员成功,初始密码为123456!");
                }
            }
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.INSERT_FAIL, "新增管理员失败!");
    }
}
