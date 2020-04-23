package com.yang.graduation.message;

import com.yang.graduation.message.sink.MailMessageSink;
import com.yang.graduation.message.sink.PhoneMessageSink;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/22 11:40
 */
@SpringBootApplication
@EnableBinding(value = {PhoneMessageSink.class, MailMessageSink.class})
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
