package com.yang.graduation.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/2 00:22
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OSSFrontApplication {
    public static void main(String[] args) {
        SpringApplication.run(OSSFrontApplication.class, args);
    }
}
