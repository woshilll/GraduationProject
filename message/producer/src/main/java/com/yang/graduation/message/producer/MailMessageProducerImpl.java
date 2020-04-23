package com.yang.graduation.message.producer;

import com.yang.graduation.message.api.MailMessageSource;
import com.yang.graduation.message.producer.api.MailMessageProducerService;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/23 16:22
 */
@Service
public class MailMessageProducerImpl implements MailMessageProducerService {
    @Resource
    private MailMessageSource source;
    /**
     * 发送邮件验证码
     * @param map 必有 mail 邮箱 code 验证码
     * @return
     */
    @Override
    public boolean sendMailMessage(Map<String, String> map) {
        return source.MailMessage().send(MessageBuilder.withPayload(map).build());
    }
}
