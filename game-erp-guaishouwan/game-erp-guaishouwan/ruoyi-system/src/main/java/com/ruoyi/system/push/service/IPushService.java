package com.ruoyi.system.push.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author zyy
 * @date 2023-10-02
 */
public interface IPushService {

    void push(String[] getuiAlias, String title, String content, JSONObject payload, Integer pushType);
    
    void pushIM(Map<String, Object> pushInfoMap);
}
