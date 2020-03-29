package com.yang.graduation.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author 李洋
 * @date 2020/3/23 21:34
 */
@SpringBootApplication
@MapperScan(basePackages = "com.yang.graduation.commons.mapper")
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
