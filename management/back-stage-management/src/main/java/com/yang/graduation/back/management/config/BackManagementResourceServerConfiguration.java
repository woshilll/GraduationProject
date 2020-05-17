package com.yang.graduation.back.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/1 11:34
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class BackManagementResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/back/admin/update/**", "/back/admin/modify/**", "/back/admin/delete/**", "/back/admin/insert/**",
                        "/back/user/delete/**", "/back/news/delete/**").hasAuthority("ROOT")
                .antMatchers("/**").hasAnyAuthority("USER", "ROOT");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //配置资源id 对应oauth2里的AuthorizationServerConfiguration
        resources.resourceId("backend-resources");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
