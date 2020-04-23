package com.yang.graduation.message.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/22 11:45
 */
public interface PhoneMessageSink {
    /**
     * 短信消费者
     * @return
     */
    @Input("phone-message")
    SubscribableChannel phoneMessage();
}
