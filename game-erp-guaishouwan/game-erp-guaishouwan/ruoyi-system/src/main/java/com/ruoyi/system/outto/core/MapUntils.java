package com.ruoyi.system.outto.core;

import com.ruoyi.common.utils.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lz
 * @version 1.0
 * @date 2022/6/6 15:51
 */
public class MapUntils {


    public static String getCover(Map<String,Object> params){


        System.out.println("通过Map.keySet遍历key和value：");

        StringBuffer resut = new StringBuffer();

        for (String key : params.keySet()) {

            System.out.println("key= "+ key + " and value= " + params.get(key));

            if (Objects.nonNull(params.get(key)) && StringUtils.isNotEmpty(String.valueOf(params.get(key)))){
                resut.append(key+"="+params.get(key)+"&");
            }

        }
        return resut.toString();
    }

    public static String unicodeToString(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        if (!StringUtils.isNull(str)){
            Matcher matcher = pattern.matcher(str);
            char ch;
            while (matcher.find()) {
                ch = (char) Integer.parseInt(matcher.group(2), 16);
                str = str.replace(matcher.group(1), ch+"" );
            }
        }
        return str;
    }

}
