package com.yang.graduation.message.controller;

import com.google.common.collect.Maps;
import com.yang.graduation.commons.utils.MyPattern;
import com.yang.graduation.message.producer.api.MailMessageProducerService;
import com.yang.graduation.message.producer.api.PhoneMessageProducerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 忘记密码消息
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/24 16:56
 */
@RestController
@RequestMapping("/message/forget")
public class ForgetMessageController {
    @Resource
    private PhoneMessageProducerService phoneMessageProducerService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private MailMessageProducerService mailMessageProducerService;
    @PostMapping("/send/phone/code/{phone}")
    public Boolean sendPhoneCode(@PathVariable String phone) {
        if (StringUtils.isNotBlank(phone) && phone.matches(MyPattern.PHONE)) {
            String codeStr = redisTemplate.boundValueOps("forget_" + phone).get();
            if (codeStr != null) {
                return false;
            }
            Map<String, String> map = Maps.newHashMap();
            map.put("phone", phone);
            Random random = new Random();
            int code = random.nextInt(1000000);
            if (code < 100000) {
                code += 100000;
            }
            map.put("code", code + "");
            redisTemplate.boundValueOps("forget_" + phone).set(code + "", 5, TimeUnit.MINUTES);
            return phoneMessageProducerService.sendPhoneMessage(map);
        }
        return false;
    }
    @PostMapping("/send/mail/code/{mail}")
    public Boolean sendMailCode(@PathVariable String mail) {
        if (StringUtils.isNotBlank(mail) && mail.matches(MyPattern.EMAIL)) {
            String mailCode = redisTemplate.boundValueOps("forget_" + mail).get();
            if (mailCode != null) {
                return false;
            }
            Map<String, String> map = Maps.newHashMap();
            map.put("mail", mail);
            Random random = new Random();
            int code = random.nextInt(1000000);
            if (code < 100000) {
                code += 100000;
            }
            map.put("code", code + "");
            map.put("user", mail);
            redisTemplate.boundValueOps("forget_" + mail).set(code + "", 5, TimeUnit.MINUTES);
            return mailMessageProducerService.sendMailMessage(map);
        }
        return false;
    }
}
