package com.alpha.common.util.wechat;

import com.alibaba.fastjson.JSONObject;
import com.alpha.common.constant.RedisConstants;
import com.alpha.common.http.HttpClient;
import com.alpha.common.http.HttpParameters;
import com.alpha.common.util.StringUtils;
import com.alpha.common.util.redis.JedisUtil;
import com.alpha.config.util.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jiming.jing on 2020/4/16.
 */
@Component
public class WeChatApiUtils {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /*@Value("${wechat.appId}")
    private String appId;
    @Value("${wechat.appSecret}")
    private String appSecret;*/

    @Autowired
    JedisUtil jedisUtil;

    /**
     * 微信网页授权获取access_token（与基础接口中的access_token不同）
     * @param code
     * @return
     */
    public String getAccessToken(String code) throws Exception {
        // https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        HttpParameters[] params = new HttpParameters[] {
                new HttpParameters("appid", PropertiesUtils.getProperty("wechat.appId")),
                new HttpParameters("secret", PropertiesUtils.getProperty("wechat.appSecret")),
                new HttpParameters("code", code),
                new HttpParameters("grant_type", "authorization_code")};

        log.info("code:" + code);
        log.info("appid:" + PropertiesUtils.getProperty("wechat.appId"));
        log.info("secret:" + PropertiesUtils.getProperty("wechat.appSecret"));
        // 获取access_token
        String result = HttpClient.doGet(url, params);
        JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
        String accessToken = jsonObject.getString("access_token");
        String openId = jsonObject.getString("openid");
        log.info("errcode:" + jsonObject.get("errcode"));
        log.info("errmsg:" + jsonObject.get("errmsg"));
        log.info("opeinid:{}, --- access_token:{}", openId, accessToken);
        if (StringUtils.isNotEmpty(accessToken) && StringUtils.isNotEmpty(openId)) {
            //this.jedisUtil.set(RedisConstants.WX_ACCESS_TOKEN + ":" + openId, accessToken);
            // 获取user_info
            try {
                JSONObject userInfo = getUserInfo(openId);
                log.info("-----> wx user info:{}", userInfo.toString());
                WeChatUserInfo weChatUserInfo = new WeChatUserInfo();
                weChatUserInfo.setOpenid(userInfo.getString("openid"));
                weChatUserInfo.setNickname(userInfo.getString("nickname"));
                weChatUserInfo.setHeadimgurl(userInfo.getString("headimgurl"));
                weChatUserInfo.setSex(userInfo.getString("sex"));
                //jedisUtil.addObject(RedisConstants.WX_USER_INFO, openId, 7200, weChatUserInfo);
            } catch (Exception e) {
                log.error("获取微信授权用户信息失败! openid={}", openId, e);
            }
        }
        return openId;
    }

    /**
     * 网页授权获取授权用户信息
     * @param openId
     * @return
     */
    public JSONObject getUserInfo(String openId) throws Exception {
        // https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
        String url = "https://api.weixin.qq.com/sns/userinfo";
        //String accessToken = jedisUtil.getValue(RedisConstants.WX_ACCESS_TOKEN + ":" + openId);
        String accessToken = null;
        HttpParameters[] params = new HttpParameters[] {
                new HttpParameters("access_token", accessToken),
                new HttpParameters("openid", openId),
                new HttpParameters("lang", "zh_CN")};

        String result = HttpClient.doGet(url, params);
        JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
        return jsonObject;
    }
}
