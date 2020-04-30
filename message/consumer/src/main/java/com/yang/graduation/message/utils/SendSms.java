package com.yang.graduation.message.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.yang.graduation.commons.secret.AliSecret;
import org.springframework.stereotype.Component;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/22 20:17
 */
@Component
public class SendSms {
    private static final String TEMPLATE_CODE = "SMS_188631099";
    private static final String TEMPLATE_PARAM = "{\"code\":\"[value]\"}";

    public CommonResponse sendSms(String phone, String smsCode) {
        DefaultProfile profile = DefaultProfile.getProfile(AliSecret.REGION_ID.getValue(), AliSecret.ACCESS_KEY_ID.getValue(), AliSecret.ACCESS_KEY_SECRET.getValue());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", AliSecret.REGION_ID.getValue());
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "时事新闻学习系统");
        request.putQueryParameter("TemplateCode", TEMPLATE_CODE);
        request.putQueryParameter("TemplateParam", TEMPLATE_PARAM.replace("[value]", smsCode));

        try {
            return client.getCommonResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }
}
