package com.yang.graduation.message.consumer;

import com.aliyuncs.CommonResponse;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.yang.graduation.commons.utils.MapperUtil;
import com.yang.graduation.message.utils.SendEmail;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/23 16:42
 */
@Service
public class MailMessageReceive {
    @Resource
    private SendEmail sendEmail;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 绑定邮件消费者
     * @param mapJson
     * @throws Exception
     */
    @StreamListener("mail-message")
    public void receivePhoneMessage(String mapJson) throws Exception{
        Map<String, Object> map = MapperUtil.json2map(mapJson);
        String mail = String.valueOf(map.get("mail"));
        String code = String.valueOf(map.get("code"));
        String user = String.valueOf(map.get("user"));
        SingleSendMailResponse singleSendMailResponse = sendEmail.sendMail(mail, code, user);
        if (singleSendMailResponse == null) {
            redisTemplate.delete(mail);
        }
    }
}
