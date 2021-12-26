package com.alpha.common.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by jiming.jing on 2018/1/3.
 */
public class SystemMessage {

    public static final InheritableThreadLocal<Locale> language = new InheritableThreadLocal<>();

    private static final String BUNDLE_NAME = "messages";
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    private SystemMessage() {}

    private static Logger logger = LoggerFactory.getLogger(SystemMessage.class);

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        }catch (MissingResourceException e) {
            logger.error(e.getMessage());
            return '!' + key + '!';
        }
    }

    /**
     * 支持国际化
     * @param key 键值
     * @param properties 资源文件名[messages] 不带国际化后缀cn/en 如在language下以language.messages访问
     * @return
     * @throws Exception
     */
    public static String getString(String key, String properties) throws Exception {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(properties, SystemMessage.language.get());
        return resourceBundle.getString(key);
    }
}
