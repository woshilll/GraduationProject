package com.yang.graduation.front.service;

import com.google.common.collect.Lists;
import com.yang.graduation.provider.api.UserService;
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
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/10 18:29
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    public static final String BANNED = "this user is banned, so you can not login in";

    @Reference(version = "1.0.0")
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        com.yang.graduation.commons.domain.User user = userService.validateUser(s);
        // 默认所有用户拥有USER权限
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");
        grantedAuthorities.add(grantedAuthority);
        //用户存在
        if (user != null) {
            if (user.getBanned() == 2) {
                //说明账号禁用!
                return new User(BANNED, user.getPassword(), Lists.newArrayList(new SimpleGrantedAuthority("BANNED")));
            }
            return new User(user.getName(), user.getPassword(), grantedAuthorities);
        }
        //用户不存在
        return null;
    }
}
