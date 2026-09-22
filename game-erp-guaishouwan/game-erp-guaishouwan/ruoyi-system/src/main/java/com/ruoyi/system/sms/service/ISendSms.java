package com.ruoyi.system.sms.service;

import java.util.Map;

/**
 * @author zyy
 * @date 2023-05-08
 */
public interface ISendSms {
    Map<String, Object> sendSms(String phone, String type) throws Exception;

    Map<String, Object> sendLianLuSms(String phone, String type) throws Exception;

    Map<String, Object> sendSmsForNotice(String phone, String type) throws Exception;
}
