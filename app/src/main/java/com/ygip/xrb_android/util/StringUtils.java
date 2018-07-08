package com.ygip.xrb_android.util;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by XQM on 2017/7/14.
 */

public class StringUtils {
    private static final String EMAIL_CHECK = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 判断字符串是不是密码（符不符合密码要求）
     * @param str
     * @return
     */
    public static boolean isPassword(String str){
        if (TextUtils.isEmpty(str)) return false;
        String regEx = "^\\w{6,12}$";
        Pattern pattern = Pattern.compile(regEx);
        return pattern.matcher(str).matches();
    }


    /**
     * 字符串(以逗号为分割符)转List
     * @param s
     * @return
     */
    public static List<String> stringToList(String s){
        if (TextUtils.isEmpty(s)) return new ArrayList<>();
        String ss[] = s.split(",");
        return Arrays.asList(ss);
    }

    /**
     * 字符串(以任何为分割符)转List
     * @param s
     * @param separator
     * @return
     */
    public static List<String> stringToList(String s, String separator) {
        if (TextUtils.isEmpty(s)) return new ArrayList<>();
        String[] ss = s.split(separator);
        return Arrays.asList(ss);
    }

    /**
     * 判断手机号码
     * @param str
     * @return
     */
    public static boolean isPhone(String str) {
        if (TextUtils.isEmpty(str)) return false;
        Pattern pattern = Pattern.compile("[0-9]{11}");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 判断是不是邮箱
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        if (TextUtils.isEmpty(str)) return false;
        Pattern pattern = Pattern.compile(EMAIL_CHECK);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
