package com.yang.graduation.message.api;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/21 12:07
 */
public interface PhoneMessageSource {
    /**
     * 用于验证手机号
     * @return
     */
    @Output("phone-message")
    MessageChannel phoneMessage();
}
