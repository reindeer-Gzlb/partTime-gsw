package com.ruoyi.system.token.utils;

import com.aliyun.oss.common.utils.HttpUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.token.annotation.NotRepeatSubmit;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

public class ApiUtil {

    /**
     * 按参数名升续拼接参数
     *
     * @param request
     * @return
     */
    public static String concatSignString(HttpServletRequest request) {
        Map<String, String> paramterMap = new HashMap<>();
        request.getParameterMap().forEach((key, value) -> paramterMap.put(key, value[0]));
        // 按照key升续排序，然后拼接参数
        Set<String> keySet = paramterMap.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            // 或略掉的字段
            if (k.equals("sign")) {
                continue;
            }
            if (paramterMap.get(k).trim().length() > 0) {
                // 参数值为空，则不参与签名
                sb.append(k).append("=").append(paramterMap.get(k).trim()).append("&");
            }
        }
        String params = sb.toString();
        return params;
    }

    public static String concatSignString(Map<String, String> map) {
        Map<String, String> paramterMap = new HashMap<>();
        map.forEach((key, value) -> paramterMap.put(key, value));
        // 按照key升续排序，然后拼接参数
        Set<String> keySet = paramterMap.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (paramterMap.get(k).trim().length() > 0) {
                // 参数值为空，则不参与签名
                sb.append(k).append("=").append(paramterMap.get(k).trim()).append("&");
            }
        }
        return sb.toString();
    }

    public static String getCover(Map<String, Object> params) {
        StringBuffer resut = new StringBuffer();
        TreeSet<String> sortSet = new TreeSet<>(params.keySet()); //排序
        for (String key : sortSet) {
            if (Objects.nonNull(params.get(key)) && StringUtils.isNotEmpty(String.valueOf(params.get(key)))) {
                resut.append(key).append("=").append(params.get(key)).append("&");
            }
        }
        String resutparams = resut.toString();
        return resutparams;
    }

    public static String getCover2(Map<String, Object> params) {
        StringBuffer resut = new StringBuffer();
        TreeSet<String> sortSet = new TreeSet<>(params.keySet()); //排序
        for (String key : sortSet) {
            resut.append(key + "=" + params.get(key) + "&");
        }
        return resut.toString();
    }


    /**
     * 获取方法上的@NotRepeatSubmit注解
     *
     * @param handler
     * @return
     */
    public static NotRepeatSubmit getNotRepeatSubmit(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            NotRepeatSubmit annotation = method.getAnnotation(NotRepeatSubmit.class);

            return annotation;
        }

        return null;
    }
}
