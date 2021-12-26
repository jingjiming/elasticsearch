package com.alpha.common.util;

/**
 * Created by jiming.jing on 2018/2/12.
 */
public class XssDecode {

    public static String decode(String s) {

        if ((s == null) || ("".equals(s))) {
            return s;
        }

        StringBuilder sb = new StringBuilder(s.length() + 16);

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            switch (c) {
                case '＞':
                    sb.append('>');
                    break;
                case '＜':
                    sb.append('<');
                    break;
                case '‘':
                    sb.append('\'');
                    break;
                case '“':
                    sb.append('"');
                    break;
                case '＆':
                    sb.append('&');
                    break;
                case '＼':
                    sb.append('\\');
                    break;
                case '＃':
                    sb.append('#');
                    break;
                case '（':
                    sb.append('(');
                    break;
                case '）':
                    sb.append(')');
                    break;
                case '％':
                    sb.append('%');
                    break;
                case '＊':
                    sb.append('*');
                    break;
                case '＝':
                    sb.append('=');
                    break;
                default:
                    sb.append(c);
            }
        }

        return sb.toString();
    }
}
