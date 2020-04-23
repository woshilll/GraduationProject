package com.yang.graduation.message.producer;

import com.yang.graduation.message.api.PhoneMessageSource;
import com.yang.graduation.message.producer.api.PhoneMessageProducerService;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/21 12:10
 */
@Service
public class PhoneMessageProducerImpl implements PhoneMessageProducerService {
    @Resource
    private PhoneMessageSource source;

    /**
     * 发送短信验证码
     * @param map 必有 phone 手机号 code 验证码
     * @return
     */
    @Override
    public boolean sendPhoneMessage(Map<String, String> map) {
        return source.phoneMessage().send(MessageBuilder.withPayload(map).build());
    }
}
