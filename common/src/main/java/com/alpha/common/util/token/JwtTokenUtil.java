package com.alpha.common.util.token;

import com.alpha.common.util.StringUtils;
import com.alpha.config.constant.ConfigConstants;
import com.alpha.config.util.PropertiesUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtil {

    public static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    public static final String ISSUER = "alpha_user";

    /**
     * 生成token
     *
     * @param claims
     * @return
     */
    public static String createToken(Map<String, String> claims) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(PropertiesUtils.getProperty(ConfigConstants.TOKEN_SECURITY));
            JWTCreator.Builder builder = JWT.create()
                    .withIssuer(ISSUER)
                    //设置过期时间为2小时
                    .withExpiresAt(DateUtils.addHours(new Date(), 2));
            claims.forEach(builder::withClaim);
            return builder.sign(algorithm);
        } catch (Exception e) {
            logger.error("生成token失败,失败原因:{}", e);
        }
        return null;
    }

    /**
     * 验证jwt，并返回数据
     */
    public static Map<String, String> verifyToken(String token) {
        Algorithm algorithm;
        Map<String, Claim> map = new HashMap<String, Claim>();
        try {
            algorithm = Algorithm.HMAC256(PropertiesUtils.getProperty(ConfigConstants.TOKEN_SECURITY));
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
            DecodedJWT jwt = verifier.verify(token);
            map = jwt.getClaims();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> resultMap = new HashMap<>(map.size());
        map.forEach((k, v) -> resultMap.put(k, v.asString()));
        return resultMap;
    }

    /**
     * 生成登录token
     *
     * @param loginInfoEntity
     * @return
     */
    public static String createLoginToken(LoginInfoEntity loginInfoEntity) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(PropertiesUtils.getProperty(ConfigConstants.TOKEN_SECURITY));
            JWTCreator.Builder builder = JWT.create()
                    .withIssuer(ISSUER)
                    //设置过期时间为1个月
                    .withExpiresAt(DateUtils.addMonths(new Date(), 2));
            if (loginInfoEntity != null) {
                builder.withClaim(LoginInfoConstants.USER_ID, loginInfoEntity.getUserId());
                builder.withClaim(LoginInfoConstants.USER_NAME, loginInfoEntity.getUserName());
                builder.withClaim(LoginInfoConstants.IS_LOGIN, true);
            }
            return builder.sign(algorithm);
        } catch (Exception e) {
            logger.error("登陆生成token失败,失败原因:{}", e);
        }
        return null;
    }

    /**
     * 验证jwt，并返回数据
     */
    public static LoginInfoEntity verifyLoginToken(String token) {
        Algorithm algorithm;
        LoginInfoEntity loginInfoEntity = new LoginInfoEntity();
        try {
            algorithm = Algorithm.HMAC256(PropertiesUtils.getProperty(ConfigConstants.TOKEN_SECURITY));
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> map = jwt.getClaims();
            loginInfoEntity.setUserId(map.get(LoginInfoConstants.USER_ID).asString());
            if (map.get(LoginInfoConstants.USER_NAME) != null) {
                loginInfoEntity.setUserName(map.get(LoginInfoConstants.USER_NAME).asString());
            }
            loginInfoEntity.setLogin(map.get(LoginInfoConstants.IS_LOGIN).asBoolean());
            /*if (map.get(LoginInfoConstants.IS_WX) != null) {
                loginInfoEntity.setWX(map.get(LoginInfoConstants.IS_WX).asBoolean());
            }

            if (map.get(LoginInfoConstants.IS_NEW_USER) != null) {
                loginInfoEntity.setIsNewUser(map.get(LoginInfoConstants.IS_NEW_USER).asInt());
            }*/

        } catch (Exception e) {
            loginInfoEntity = null;
            logger.error("解密token失败:{}", e);
        }
        return loginInfoEntity;
    }

    /**
     * 从请求头中获取token进行校验
     *
     * @param request
     * @return
     */
    public static LoginInfoEntity validByRequestHead(HttpServletRequest request) {
        String token = request.getHeader(LoginInfoConstants.TOKEN_NAME);
        //默认设置为未登录
        if (request == null || StringUtils.isEmpty(token)) {
            return null;
        }
        return JwtTokenUtil.verifyLoginToken(token);
    }


    public static void main(String[] args) {
        LoginInfoEntity loginInfoEntity = new LoginInfoEntity();
        loginInfoEntity.setUserId("1637E67329164ED1A5AEC3D574A1B2A0");
        loginInfoEntity.setUserName("景");
        System.out.println(createLoginToken(loginInfoEntity));
    }

}
