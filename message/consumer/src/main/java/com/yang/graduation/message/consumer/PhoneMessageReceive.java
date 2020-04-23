package com.yang.graduation.message.consumer;

import com.aliyuncs.CommonResponse;
import com.yang.graduation.commons.utils.MapperUtil;
import com.yang.graduation.message.utils.SendSms;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/22 11:49
 */
@Service
public class PhoneMessageReceive {
    @Resource
    private SendSms sendSms;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @StreamListener("phone-message")
    public void receivePhoneMessage(String mapJson) throws Exception{
        Map<String, Object> map = MapperUtil.json2map(mapJson);
        String phone = String.valueOf(map.get("phone"));
        String code = String.valueOf(map.get("code"));
        CommonResponse commonResponse = sendSms.sendSms(phone, code);
        if (commonResponse.getHttpStatus() != HttpStatus.OK.value()) {
            redisTemplate.delete(phone);
        }
    }
}
