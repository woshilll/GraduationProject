package com.yang.graduation.message;

import com.google.common.collect.Maps;
import com.yang.graduation.message.producer.api.PhoneMessageProducerService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/21 17:58
 */
@RestController
@RequestMapping("/message")
public class PhoneMessageController {
    @Resource
    private PhoneMessageProducerService phoneMessageProducerService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @PostMapping("/send/phone/code/{phone}")
    public Boolean sendPhoneCode(@PathVariable String phone) {
        String codeStr = redisTemplate.boundValueOps(phone).get();
        if (codeStr != null) {
            return false;
        }
        HashMap<String, String> map = Maps.newHashMap();
        map.put("phone", phone);
        Random random = new Random();
        int code = random.nextInt(1000000);
        if (code < 100000) {
            code += 100000;
        }
        map.put("code", code + "");
        redisTemplate.boundValueOps(phone).set(code + "", 5, TimeUnit.MINUTES);
        return phoneMessageProducerService.sendPhoneMessage(map);
    }
}
