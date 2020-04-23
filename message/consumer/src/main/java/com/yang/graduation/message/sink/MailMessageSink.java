package com.yang.graduation.message.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/23 16:40
 */
public interface MailMessageSink {
    /**
     * 邮箱消费者
     * @return
     */
    @Input("mail-message")
    SubscribableChannel mailMessage();
}
