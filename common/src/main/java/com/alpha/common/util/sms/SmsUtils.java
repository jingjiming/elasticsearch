package com.alpha.common.util.sms;

import com.alpha.config.util.PropertiesUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信服务工具类
 * Created by jiming.jing on 2019/12/25.
 */
public class SmsUtils {

    public static RestTemplate restTemplate = new RestTemplate();

    private static final String SEND_URL = PropertiesUtils.getProperty("sms.sendURL");
    private static final String CLIENT_ID = PropertiesUtils.getProperty("sms.clientId");
    private static final String PASSWORD = PropertiesUtils.getProperty("sms.password");
    private static final String BUSINESS_CODE = PropertiesUtils.getProperty("sms.businessCode");


    public static SmsBaseResponse sendSmsByGet(String mobile, String content) {
        Map<String, Object> stringObjectMap = getCommonParams();
        stringObjectMap.put("mobile", mobile);
        stringObjectMap.put("content", content);
        SmsBaseResponse smsBaseResponse =
                restTemplate.getForObject(SEND_URL, SmsBaseResponse.class, stringObjectMap);
        return smsBaseResponse;
    }


    private static Map<String, Object> getCommonParams() {
        Map<String, Object> map = new HashMap<>();
        map.put("clientId", CLIENT_ID);
        map.put("password", PASSWORD);
        map.put("businessCode", BUSINESS_CODE);
        return map;
    }

}
