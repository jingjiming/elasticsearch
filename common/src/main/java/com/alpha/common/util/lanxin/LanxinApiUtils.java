package com.alpha.common.util.lanxin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alpha.common.http.HttpClient;
import com.alpha.common.util.StringUtils;
import com.alpha.common.util.lanxin.beans.CardText;
import com.alpha.common.util.lanxin.beans.CardTextMessage;
import com.alpha.common.util.lanxin.beans.LanxinUserInfo;
import com.alpha.common.util.lanxin.constant.ApiRequestURL;
import com.alpha.common.util.lanxin.enums.MessageType;
import com.alpha.common.util.lanxin.enums.OrgQueryType;
import com.alpha.common.util.redis.JedisUtil;
import com.alpha.config.util.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiming.jing on 2020/4/16.
 */
@Component
public class LanxinApiUtils {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JedisUtil jedisUtil;

    /**
     * OAuth2授权接口
     * 通过authorization Code 换取 access_token
     */
    public JSONObject getToken(String code) throws Exception {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid", PropertiesUtils.getProperty("lanxin.appid"));
        paramMap.put("secret", PropertiesUtils.getProperty("lanxin.secret"));
        paramMap.put("code", code);
        paramMap.put("grant_type", "authorization_code");

        // 获取access_token
        String result = HttpClient.net(ApiRequestURL.OAUTH2_TOKEN, paramMap, HttpMethod.GET.name());

        JSONObject jsonObject = JSONObject.parseObject(result);
        String accessToken = jsonObject.getString("access_token");
        String openId = jsonObject.getString("openid");
        log.info("openid:[{}]...access_token:[{}]", openId, accessToken);
        return jsonObject;
    }

    /**
     * 用户信息
     * @param openId
     * @return
     */
    public LanxinUserInfo getUserInfo(String accessToken, String openId) throws Exception {
        LanxinUserInfo lanxinUserInfo = this.jedisUtil.getObject("lanxin:user:info", openId);
        if (lanxinUserInfo != null) {
            return lanxinUserInfo;
        }
        Map<String, String> params = new HashMap<>();
        params.put("access_token", accessToken);
        params.put("mobile", openId);
        String result = HttpClient.net(ApiRequestURL.OAUTH2_USERINFO, params, HttpMethod.GET.name());
        JSONObject jsonObject = JSONObject.parseObject(result);
        log.info("-----> user info:{}", jsonObject.toString());
        JSONArray array = jsonObject.getJSONArray("openOrgMemberList");
        if (array != null && array.size() > 0) {
            JSONObject userInfo = array.getJSONObject(0);
            LanxinUserInfo user = new LanxinUserInfo();
            user.setId(userInfo.getIntValue("id"));
            user.setName(userInfo.getString("name"));
            user.setEmail(userInfo.getString("email"));
            user.setMobile(userInfo.getString("mobile"));
            user.setPath(userInfo.getString("path"));
            user.setParentId(userInfo.getIntValue("parentId"));
            user.setOrgName(userInfo.getString("orgName"));
            user.setCompany(userInfo.getString("company"));
            jedisUtil.addObject("lanxin:user:info", openId, 7200, user);
            return user;
        } else {
            log.info("获取蓝信授权用户信息失败! openid={}", openId);
        }
        return null;
    }

    /**
     * access_token是公众号的全局唯一凭据，公众号调用各接口时，都需要使用access_token
     * @return
     * @throws Exception
     */
    public String getAccessToken(String appid, String secret) throws Exception {
        String accessToken = this.jedisUtil.get("lanxin:access_token:" + appid);
        if (StringUtils.isBlank(accessToken)) {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("grant_type", "client_credential");
            paramMap.put("appid", appid);
            paramMap.put("secret", secret);

            // 获取access_token
            String result = HttpClient.net(ApiRequestURL.TOKEN, paramMap, HttpMethod.GET.name());
            JSONObject jsonObject = JSONObject.parseObject(result);
            log.info("errcode:" + jsonObject.get("errcode"));
            if (jsonObject.getIntValue("errcode") == 0) {
                accessToken = jsonObject.getString("access_token");
                int expiresIn = jsonObject.getIntValue("expires_in");
                this.jedisUtil.setex("lanxin_access_token:" + appid, accessToken, expiresIn);
                return accessToken;
            }
        }
        return accessToken;
    }

    /**
     * 异步发送客服消息
     * @param toUsers
     * @param cardText
     * @throws Exception
     */
    @Async
    public void sendCardTextMsg(List<String> toUsers, CardText cardText) throws Exception {
        String accessToken = this.jedisUtil.get("lanxin_access_token:" + PropertiesUtils.getProperty("lanxin.appid"));
        if (StringUtils.isBlank(accessToken)) {
            accessToken = getAccessToken(PropertiesUtils.getProperty("lanxin.appid"),
                    PropertiesUtils.getProperty("lanxin.secret"));
        }
        if (accessToken != null) {
            CardTextMessage message = new CardTextMessage();
            message.setCardText(cardText);
            message.setMsgtype(MessageType.CARDTEXT.getType());
            for (String toUser : toUsers) {
                message.setTouser(toUser);
                try {
                    this.log.info("卡片消息内容：{}", JSONObject.toJSONString(message));
                    HttpClient.doJson(ApiRequestURL.MESSAGE_CUSTOM + "?access_token=" + accessToken, JSONObject.toJSONString(message));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取分支组织
     * @param structId
     * @param queryType
     * @return
     * @throws Exception
     */
    public JSONObject getOrgStruct(int structId , OrgQueryType queryType) throws Exception {
        Map<String, Integer> params = new HashMap<>();
        params.put("structId", structId);
        params.put("queryType", queryType.getValue());
        String accessToken = this.getAccessToken(PropertiesUtils.getProperty("lanxin.appid"), PropertiesUtils.getProperty("lanxin.secret"));
        if (StringUtils.isNotBlank(accessToken)) {
            String result = HttpClient.net(ApiRequestURL.ORG_STRUCT_GET + "?access_token=" + accessToken, params, HttpMethod.POST.name());
            JSONObject jsonObject = JSONObject.parseObject(result);
            return jsonObject;
        }
        return null;
    }

    /**
     * 获取组织直属成员
     * @param structId
     * @return
     * @throws Exception
     */
    public JSONObject getOrgMember(int structId) throws Exception {
        Map<String, Integer> params = new HashMap<>();
        params.put("rand", SecureRandom.getInstance("SHA1PRNG").nextInt(99999) + 10000);
        params.put("structId", structId);
        params.put("pageNo", 1);
        params.put("pageSize", 10000);
        String accessToken = this.getAccessToken(PropertiesUtils.getProperty("lanxin.appid"), PropertiesUtils.getProperty("lanxin.secret"));
        if (StringUtils.isNotBlank(accessToken)) {
            String result = HttpClient.net(ApiRequestURL.ORG_DIRECT_MEMBER_GET + "?access_token=" + accessToken, params, HttpMethod.POST.name());
            JSONObject jsonObject = JSONObject.parseObject(result);
            return jsonObject;
        }
        return null;
    }

}