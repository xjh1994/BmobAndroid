package com.xjh1994.bmob.util;

/**
 * Created by XJH on 16/3/9.
 */
public class StringUtil {

    public static boolean tooShort(String string, int min) {
        return string.length() < min ? true : false;
    }

    public static boolean tooLong(String string, int max) {
        return string.length() > max ? true : false;
    }
}
