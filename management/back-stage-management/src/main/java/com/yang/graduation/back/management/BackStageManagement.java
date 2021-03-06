package com.yang.graduation.back.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/3/29 00:03
 */
@SpringBootApplication
@EnableDiscoveryClient
public class BackStageManagement {
    public static void main(String[] args) {
        SpringApplication.run(BackStageManagement.class, args);
    }
}
