package com.yang.graduation.commons.secret;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/30 10:05
 */
public enum AliSecret {
    /**
     * ali oss endpoint oss存储切入点
     */
    OSS_ENDPOINT("oss-cn-beijing.aliyuncs.com"),
    /**
     * ali oss bucket name oss仓库名
     */
    OSS_BUCKET_NAME("graduation-woshilll"),
    /**
     * key 秘钥key
     */
    ACCESS_KEY_ID("LTAI4FeyQ6Bw8sng76EcxHqa"),
    /**
     * secret 秘钥
     */
    ACCESS_KEY_SECRET(""),
    /**
     * region id 地区
     */
    REGION_ID("cn-hangzhou");

    private final String value;

    AliSecret(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }

}
