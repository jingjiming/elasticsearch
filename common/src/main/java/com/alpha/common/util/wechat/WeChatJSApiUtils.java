package com.alpha.common.util.wechat;

import com.alibaba.fastjson.JSONObject;
import com.alpha.common.http.HttpClient;
import com.alpha.common.util.StringUtils;
import com.alpha.config.util.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jiming.jing on 2020/4/16.
 */
public class WeChatJSApiUtils {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /*@Value("${wechat.appId}")
    private String appId;
    @Value("${wechat.appSecret}")
    private String appSecret;*/

    //微信参数
    private String accessToken;
    private String jsApiTicket;
    //获取参数的时刻
    private Long getTiketTime = 0L;
    private Long getTokenTime = 0L;
    //参数的有效时间,单位是秒(s)
    private Long tokenExpireTime = 0L;
    private Long ticketExpireTime = 0L;

    /*@Autowired
    JedisUtil jedisUtil;*/
    /**
     * 获取微信参数 只针对js sdk
     */
    public Map<String, String> getWeChatParams(String url) {
        //当前时间
        long now = System.currentTimeMillis();

        //判断accessToken是否已经存在或者token是否过期
        if (StringUtils.isBlank(accessToken) || (now - getTokenTime > tokenExpireTime * 1000)) {
            JSONObject tokenInfo = getAccessToken();
            if (tokenInfo != null) {
                accessToken = tokenInfo.getString("access_token");
                tokenExpireTime = tokenInfo.getLongValue("expires_in");
                //获取token的时间
                getTokenTime = System.currentTimeMillis();
            } else {
                log.error("----->failure of getting tokenInfo,please do some check~");
            }
        }

        //判断jsApiTicket是否已经存在或者是否过期
        if (StringUtils.isBlank(jsApiTicket) || (now - getTiketTime > ticketExpireTime * 1000)) {
            JSONObject ticketInfo = getJsApiTicket();
            if (ticketInfo != null) {
                jsApiTicket = ticketInfo.getString("ticket");
                ticketExpireTime = ticketInfo.getLongValue("expires_in");
                getTiketTime = System.currentTimeMillis();
            } else {
                log.error("----->failure of getting tokenInfo,please do some check~");
            }
        }

        //生成微信权限验证的参数
        Map<String, String> wechatParam = makeWXTicket(jsApiTicket, url);
        return wechatParam;
    }

    /**
     * 获取accessToken jssdk
     */
    private JSONObject getAccessToken() {
        String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        String requestUrl = accessTokenUrl.replace("APPID", PropertiesUtils.getProperty("wechat.appId"))
                .replace("APPSECRET", PropertiesUtils.getProperty("wechat.appSecret"));
        String result = HttpClient.doGet(requestUrl);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }

    /**
     * 获取ticket jssdk
     * @return
     */
    private JSONObject getJsApiTicket() {
        String apiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
        String requestUrl = apiTicketUrl.replace("ACCESS_TOKEN", accessToken);
        String result = HttpClient.doGet(requestUrl);
        return JSONObject.parseObject(result);
    }

    /**
     * 生成微信权限验证的参数
     * @param jsApiTicket
     * @param url
     * @return
     */
    public Map<String, String> makeWXTicket(String jsApiTicket, String url) {
        Map<String, String> ret = new HashMap<>();
        String nonceStr = createNonceStr();
        String timestamp = createTimestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsApiTicket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        log.info("string1----->" + string1);
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
            log.info("signature----->" + signature);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsApiTicket);
        ret.put("nonceStr", nonceStr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appid", PropertiesUtils.getProperty("wechat.appId"));

        return ret;
    }

    //字节数组转换为十六进制字符串
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    //生成随机字符串
    private static String createNonceStr() {
        return UUID.randomUUID().toString();
    }

    //生成时间戳
    private static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

}
