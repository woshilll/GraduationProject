package com.yang.graduation.message;

import com.google.common.collect.Maps;
import com.yang.graduation.message.producer.api.MailMessageProducerService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/23 16:25
 */
@RestController
@RequestMapping("/message")
public class MailMessageController {
    @Resource
    private MailMessageProducerService mailMessageProducerService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @PostMapping("/send/mail/code/{mail}/{user}")
    public Boolean sendPhoneCode(@PathVariable String mail, @PathVariable String user) {
        String mailCode =  redisTemplate.boundValueOps(mail).get();
        if (mailCode != null) {
            return false;
        }
        HashMap<String, String> map = Maps.newHashMap();
        map.put("mail", mail);
        Random random = new Random();
        int code = random.nextInt(1000000);
        if (code < 100000) {
            code += 100000;
        }
        map.put("code", code + "");
        map.put("user", user);
        redisTemplate.boundValueOps(mail).set(code + "", 5, TimeUnit.MINUTES);
        return mailMessageProducerService.sendMailMessage(map);
    }
}
