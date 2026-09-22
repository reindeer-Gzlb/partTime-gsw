package com.ruoyi.system.steam;

import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class UrlParamParser {
    public static Map<String, String> parseParams(String url) {
        Map<String, String> params = new HashMap<>();
        try {
            URI uri = new URI(url);
            String query = uri.getQuery();
            if (query == null) return params;

            // 处理URL编码
            String decodedQuery = URLDecoder.decode(query, "UTF-8");

            String[] pairs = decodedQuery.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                String key = idx > 0 ? pair.substring(0, idx) : pair;
                String value = idx > 0 && pair.length() > idx + 1
                        ? pair.substring(idx + 1) : "";
                params.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }
}