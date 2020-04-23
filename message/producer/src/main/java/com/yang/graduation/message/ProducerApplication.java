package com.yang.graduation.message;

import com.yang.graduation.message.api.MailMessageSource;
import com.yang.graduation.message.api.PhoneMessageSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/21 12:06
 */
@SpringBootApplication
@EnableBinding({PhoneMessageSource.class, MailMessageSource.class})
public class ProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }
}
