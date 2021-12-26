package com.alpha.common.util;

import java.io.InputStream;
import java.util.List;

/**
 * Created by JIMING on 2018/9/22.
 */
public class ServerWhiteListUtils {

    private static List<String> serverWhiteList = null;

    static {
        // 读取白名单列表
        try (InputStream is = ServerWhiteListUtils.class.getClassLoader().getResourceAsStream("config/server.json")) {
            serverWhiteList = JSONUtils.getInstance().readValue(is, List.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // 判断当前host是否在白名单内
    public static boolean isWhite(String host) {
        if (serverWhiteList == null || serverWhiteList.size() == 0) {
            return true;
        }
        for (String str : serverWhiteList) {
            if (str != null && str.equals(host)) {
                return true;
            }
        }
        return false;
    }
}
