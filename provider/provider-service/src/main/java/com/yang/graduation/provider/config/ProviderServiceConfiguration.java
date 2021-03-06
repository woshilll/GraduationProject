package com.yang.graduation.provider.config;

import com.yang.graduation.provider.util.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author 李洋
 * @date 2020/3/28 23:12
 */
@Configuration
public class ProviderServiceConfiguration {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(0, 0);
    }
}
