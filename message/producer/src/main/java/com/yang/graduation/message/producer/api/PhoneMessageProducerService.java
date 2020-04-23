package com.yang.graduation.message.producer.api;

import java.util.Map;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/21 12:24
 */
public interface PhoneMessageProducerService {
    /**
     * 发送短信验证码
     * @param map 必有 phone 手机号 code 验证码
     * @return
     */
    boolean sendPhoneMessage(Map<String, String> map);
}
