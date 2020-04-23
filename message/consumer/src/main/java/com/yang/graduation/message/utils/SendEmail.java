package com.yang.graduation.message.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.stereotype.Component;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/23 16:43
 */
@Component
public class SendEmail {

    private static final String REGION_ID = "cn-hangzhou";
    private static final String ACCESS_KEY_ID = "LTAI4FeyQ6Bw8sng76EcxHqa";
    private static final String SECRET = "mmWLUwiOxbMVhwynQ0f5gypkKwdQF5";
    public SingleSendMailResponse sendMail(String mail, String code, String user) {
        IClientProfile profile = DefaultProfile.getProfile(REGION_ID, ACCESS_KEY_ID, SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            request.setAccountName("woshilll@woshilll.top");
            request.setFromAlias("woshilll");
            request.setAddressType(1);
            request.setTagName("bindMail");
            request.setReplyToAddress(true);
            request.setToAddress(mail);
            request.setSubject("[时事新闻平台] 绑定邮箱通知");
            String htmlStr = "<p data-spm-anchor-id=\"5176.2020520150.112.i13.79b87528QGCSDF\">尊敬的" + user + "您好，感谢您使用时事新闻平台。您的绑定验证码为：</p>\n" +
                    "<p data-spm-anchor-id=\"5176.2020520150.112.i12.79b87528QGCSDF\"><span style=\"font-size: 72px; background-color: #fbeeb8;\" data-spm-anchor-id=\"5176.2020520150.113.i1.79b87528QGCSDF\">" + code + "</span></p>\n" +
                    "<p data-spm-anchor-id=\"5176.2020520150.112.i12.79b87528QGCSDF\"><span data-spm-anchor-id=\"5176.2020520150.112.i19.79b87528QGCSDF\">如果您并未申请<a href=\"../../index\" target=\"_self\">时事新闻平台</a>绑定邮箱服务，可能其他用户输入了您的邮箱地址。请注意保护账户的隐私安全。</span></p>\n" +
                    "<p data-spm-anchor-id=\"5176.2020520150.112.i12.79b87528QGCSDF\"><span data-spm-anchor-id=\"5176.2020520150.112.i19.79b87528QGCSDF\">感谢您的使用！</span></p>";
            request.setHtmlBody(htmlStr);
            request.setMethod(MethodType.POST);
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            //捕获错误异常码
            e.printStackTrace();
            return null;
        }
    }
}
