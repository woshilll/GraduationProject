package com.yang.graduation.oauth2.service;

import com.google.common.collect.Lists;
import com.yang.graduation.commons.domain.Admin;
import com.yang.graduation.provider.api.AdminService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义授权认证
 *
 * @author woshilll
 * @version v1.0.0
 * @date 2020/3/31 00:24
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    public static final String BANNED = "this user is banned, so you can not login in";

    @Reference(version = "1.0.0")
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Admin admin = adminService.getAdmin(s);
        // 默认所有用户拥有USER权限
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");
        grantedAuthorities.add(grantedAuthority);
        //用户存在
        if (admin != null) {
            if (admin.getStatus() != 0) {
                //说明账号禁用!
                return new User(BANNED, admin.getPassword(), Lists.newArrayList(new SimpleGrantedAuthority("BANNED")));
            }
            return new User(admin.getName(), admin.getPassword(), grantedAuthorities);
        }
        //用户不存在
        return null;
    }
}
