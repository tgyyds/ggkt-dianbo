package com.atguigu.ggkt.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${tencent.cos.fill.region}")
    private String region;

    @Value("${tencent.cos.fill.secretid}")
    private String secretid;

    @Value("${tencent.cos.fill.secretkey}")
    private String secretkey;

    @Value("${tencent.cos.fill.secretname}")
    private String secretname;

    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = region;
        ACCESS_KEY_ID = secretid;
        ACCESS_KEY_SECRET = secretkey;
        BUCKET_NAME = secretname;
    }
}
