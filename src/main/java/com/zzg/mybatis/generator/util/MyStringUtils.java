package com.zzg.mybatis.generator.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Owen on 6/18/16.
 */
public class MyStringUtils {

    private static final String TABLE_NAME_HEADER_JUDGMENT1 = "t_";
    private static final String TABLE_NAME_HEADER_JUDGMENT2 = "tb_";
    private static final String TABLE_NAME_HEADER_JUDGMENT3 = "tbl_";
    private static final String TABLE_NAME_HEADER_JUDGMENT4 = "tc_";
    private static final String TABLE_NAME_HEADER_JUDGMENT5 = "oim_";
    /**
     *
     * convert string from slash style to camel style, such as my_course will convert to MyCourse
     *
     * @param str
     * @return
     */
    public static String dbStringToCamelStyle(String str) {
        if (str != null) {
            // 判断头部以t_或者tb_开头就舍去头部
            if (str.startsWith(TABLE_NAME_HEADER_JUDGMENT1)) {
                str = str.substring(2);
            } else if (str.startsWith(TABLE_NAME_HEADER_JUDGMENT2)) {
                str = str.substring(3);
            } else if (str.startsWith(TABLE_NAME_HEADER_JUDGMENT3)) {
                str = str.substring(4);
            } else if (str.startsWith(TABLE_NAME_HEADER_JUDGMENT4)) {
                str = str.substring(3);
            } else if (str.startsWith(TABLE_NAME_HEADER_JUDGMENT5)) {
                // 跳过
            } else {
                str = str.substring(str.indexOf("_") + 1);
            }

            if (!StringUtils.isBlank(str) && str.contains("_")) {
                str = str.toLowerCase();
                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(str.charAt(0)).toUpperCase());
                for (int i = 1; i < str.length(); i++) {
                    char c = str.charAt(i);
                    if (c != '_') {
                        sb.append(c);
                    } else {
                        if (i + 1 < str.length()) {
                            sb.append(String.valueOf(str.charAt(i + 1)).toUpperCase());
                            i++;
                        }
                    }
                }
                return sb.toString();
            } else {
                String firstChar = String.valueOf(str.charAt(0)).toUpperCase();
                String otherChars = str.substring(1);
                return firstChar + otherChars;
            }
        }
        return null;
    }

}
