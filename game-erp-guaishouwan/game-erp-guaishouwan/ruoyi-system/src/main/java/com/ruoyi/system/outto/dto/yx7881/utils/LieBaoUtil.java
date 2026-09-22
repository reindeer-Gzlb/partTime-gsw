package com.ruoyi.system.outto.dto.yx7881.utils;

import java.util.HashMap;
import java.util.Map;

public class LieBaoUtil {

    /**
     * 获取带有签名的header头
     *
     * @param x_service_app
     * @param secret_key
     * @param paramStr
     * @return
     */
    public static Map<String, String> getHearder(String x_service_app, String secret_key, String paramStr) {

        String x_service_timestamp = String.valueOf(System.currentTimeMillis());

        StringBuilder authToken = new StringBuilder(128);
        authToken.append(x_service_app).append('\n')
                .append(secret_key).append('\n')
                .append(x_service_timestamp).append('\n')
                .append(SecurityUtil.encryptMD5(paramStr));

        String token = SecurityUtil.encryptMD5(
                authToken.toString()
        );
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("x-service-app", x_service_app);
        headers.put("x-service-token", token);
        headers.put("x-service-timestamp", x_service_timestamp);

        return headers;

    }
}
