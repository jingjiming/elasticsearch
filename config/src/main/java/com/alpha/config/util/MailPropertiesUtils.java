package com.alpha.config.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by jiming.jing on 2020/4/29.
 */
public class MailPropertiesUtils {

    private static Properties props = new Properties();

    static {
        loadProps();
    }

    /**
     * 加载Properties文件，可采用InputStream和InputStreamReader两种方式
     */
    synchronized public static void loadProps() {
        try (InputStream inputStream = MailPropertiesUtils.class.getClassLoader().getResourceAsStream("mail.properties");
             InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8")) {
            props.load(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }
}
