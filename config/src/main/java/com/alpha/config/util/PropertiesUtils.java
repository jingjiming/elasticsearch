package com.alpha.config.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Copyright:
 * Author: jiming.jing
 * Date: 2017年8月18日
 * Description:
 */
public class PropertiesUtils {

    private static Properties props = new Properties();

    static {
        loadProps();
    }

    /**
     * 加载Properties文件，可采用InputStream和InputStreamReader两种方式
     */
    synchronized public static void loadProps() {
        try (InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream("public_system.properties");
             InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8")) {

            props.load(reader);
            //props.load(new InputStreamReader(PropertiesUtils.class.getClassLoader().getResourceAsStream("index.properties"), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }

}
