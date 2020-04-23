package com.yang.graduation.message.api;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/23 16:19
 */
public interface MailMessageSource {
    /**
     * 用于验证邮箱
     * @return
     */
    @Output("mail-message")
    MessageChannel MailMessage();
}
