package com.alpha.common.util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright:
 * Author: jiming.jing
 * Date: 2017年10月31日
 * Description:
 */
public class IpAddrUtils {

    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR",
            "X-Real-IP"
    };

    /**
     * 获取客户端ip地址(可以穿透代理)
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        for (String header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    /**
     * 获取外网IP
     *
     * @return
     */
    public static String getV4IP() {
        String chinaz = "http://ip.chinaz.com/getip.aspx";
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(chinaz);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String read = "";
            while ((read = bufferedReader.readLine()) != null) {
                sb.append(read + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
        Matcher m = p.matcher(sb.toString());
        if (m.find()) {
            String ip = m.group(1);
            return ip;
            // System.out.println(ipstr);
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        /*InetAddress inetAddress;
        try {
            // 实例化InetAddress对象，返回本地主机
            inetAddress = InetAddress.getLocalHost();
            // 获取本地主机名
            String hostName = inetAddress.getHostName();
            // IP地址的完全限定域名
            String canonicalHostName = inetAddress.getCanonicalHostName();
            // 本地主机的原始IP地址
            byte[] address = inetAddress.getAddress();
            int a = 0;
            if (address[3] < 0) {
                a = address[3] + 256;
            }
            // 获取本地主机的IP地址
            String hostAddress = inetAddress.getHostAddress();
            boolean reachable = inetAddress.isReachable(2000);
            System.out.println(inetAddress.toString());
            System.out.println("主机名为：" + hostName);
            System.out.println("此IP地址的完全限定域名：" + canonicalHostName);
            System.out.println("原始IP地址为：" + address[0] + "." + address[1] + "." + address[2] + "." + a);
            System.out.println("IP地址为：" + hostAddress);
            System.out.println("是否能到达此IP地址：" + reachable);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }*/
    }
}
