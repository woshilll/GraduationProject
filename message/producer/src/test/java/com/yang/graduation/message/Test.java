package com.yang.graduation.message;

import com.google.common.collect.Maps;
import com.yang.graduation.message.producer.api.PhoneMessageProducerService;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Random;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/21 12:17
 */
@SpringBootTest(classes = ProducerApplication.class)
@RunWith(SpringRunner.class)
public class Test {
    @Resource
    private PhoneMessageProducerService phoneMessageProducerService;

    @org.junit.Test
    public void sendPhoneMessage() {
        Map<String, String> map = Maps.newHashMap();
        map.put("phone", "15238376457");
        Random random = new Random(100000);
        int i = random.nextInt();
        if (i < 100000) {
            i += 100000;
        }
        map.put("code", i + "");
        boolean res = phoneMessageProducerService.sendPhoneMessage(map);
        Assert.assertTrue(res);
    }
}
