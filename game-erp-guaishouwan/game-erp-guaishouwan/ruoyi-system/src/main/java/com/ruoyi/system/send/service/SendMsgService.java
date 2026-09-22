package com.ruoyi.system.send.service;


import com.alibaba.fastjson.JSONObject;

/**
 * 发送消息
 */
public interface SendMsgService {

    /**
     * @param type
     * @param code
     * @param tel
     */
    public void sendCode(String type, String code, String tel);

    /**
     * @param accountId
     * @param tel
     */
    public void sendOnCount(String accountId, String tel);

    /**
     * params
     */
    public void sendLinkCode(JSONObject params);


    /**
     * 发送短信
     */
    public void sendByCode(String code, String[] param, String tel);


}
