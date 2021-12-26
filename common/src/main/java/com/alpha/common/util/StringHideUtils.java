package com.alpha.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 名字、身份证、手机号隐藏显示
 */
public class StringHideUtils {

    /**
     * 定义所有常量
     */
    public static final String EMPTY = "";
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;

    /**
     * @param str
     * @param len
     * @Description 字符串向左截取
     */
    public static String left(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < ZERO) {
            return EMPTY;
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(ZERO, len);

    }

    /**
     * @param str
     * @param len
     * @return java.lang.String
     * @Description 字符串向右截取
     */
    public static String right(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < ZERO) {
            return EMPTY;
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(str.length() - len);

    }

    /**
     * @param str
     * @return java.lang.String
     * @Description 根据不同名字的长度返回不同的显示数据
     */
    public static String checkNameLength(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == ONE) {
            return str;
        } else if (str.length() == TWO) {
            return "*" + StringHideUtils.right(str, ONE);
        } else if (str.length() == THREE) {
            return StringHideUtils.left(str, ONE) + "*" + StringHideUtils.right(str, ONE);
        } else if (str.length() == FOUR) {
            return StringHideUtils.left(str, ONE) + "**" + StringHideUtils.right(str, ONE);
        }
        return str;
    }

    public static String replaceNameX(String str) {
        String reg = ".{1}";
        StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        int i = 0;
        while (m.find()) {
            i++;
            if (i == 1)
                continue;
            m.appendReplacement(sb, "*");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        System.out.println("名字: " + StringHideUtils.checkNameLength("海"));
        System.out.println("名字: " + StringHideUtils.checkNameLength("贼王"));
        System.out.println("名字: " + StringHideUtils.checkNameLength("海贼王"));
        System.out.println("名字: " + StringHideUtils.checkNameLength("大海贼王"));
        System.out.println("手机号: " + StringHideUtils.left("15838883888", THREE) + "*****" + StringHideUtils.right("15838883888", FOUR));
        System.out.println("信用卡号16位: " + StringHideUtils.left("1234567891011121", FOUR) + "*****" + StringHideUtils.right("1234567891011121", FOUR));
        System.out.println("银行卡号19位: " + StringHideUtils.left("1234567891011121314", FOUR) + "*****" + StringHideUtils.right("1234567891011121314", FOUR));
    }
}
