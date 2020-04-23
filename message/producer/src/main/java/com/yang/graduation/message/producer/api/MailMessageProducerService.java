package com.yang.graduation.message.producer.api;

import java.util.Map;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/23 16:21
 */
public interface MailMessageProducerService {
    /**
     * 发送邮件验证码
     * @param map 必有 mail 邮箱 code 验证码
     * @return
     */
    boolean sendMailMessage(Map<String, String> map);
}
