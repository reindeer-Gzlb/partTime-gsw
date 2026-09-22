package com.ruoyi.system.send.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.sendMsg.*;

import java.util.HashMap;
import java.util.Map;

public class SmsSend {
    private String SignName;
    private String[] PhoneNumberSet;
    private String SessionContext;
    private String SessionContextSet;
    private String[][] ContextParamSet;
    private String TemplateId;
    private String[] TemplateParamSet;
    private JSONObject[] RCSTemplateParamSet;
    // 语音参数
    private String TemplateName;
    private String TemplateType;
    private String TemplateContent;

    // 公共参数
    private String TaskTime;
    private String Tag;
    private String TaskId;
    private int PageNo = 0;
    private int PageSize = 0;

    public String getSignName() {
        return this.SignName;
    }

    public void setSignName(String SignName) {
        this.SignName = SignName;
    }

    public String getTemplateId() {
        return this.TemplateId;
    }

    public void setTemplateId(String TemplateId) {
        this.TemplateId = TemplateId;
    }

    public String[] getPhoneNumberSet() {
        return this.PhoneNumberSet;
    }

    public void setPhoneNumberSet(String[] PhoneNumberSet) {
        this.PhoneNumberSet = PhoneNumberSet;
    }

    public String getSessionContext() {
        return this.SessionContext;
    }

    public void setSessionContext(String SessionContext) {
        this.SessionContext = SessionContext;
    }

    public String getSessionContextSet() {
        return this.SessionContextSet;
    }

    public void setSessionContextSet(String SessionContextSet) {
        this.SessionContextSet = SessionContextSet;
    }

    public String[][] getContextParamSet() {
        return this.ContextParamSet;
    }

    public void setContextParamSet(String[][] ContextParamSet) {
        this.ContextParamSet = ContextParamSet;
    }

    public String[] getTemplateParamSet() {
        return this.TemplateParamSet;
    }

    public void setTemplateParamSet(String[] TemplateParamSet) {
        this.TemplateParamSet = TemplateParamSet;
    }

    public JSONObject[] getRCSTemplateParamSet() {
        return this.RCSTemplateParamSet;
    }

    public void setRCSTemplateParamSet(JSONObject[] RCSTemplateParamSet) {
        this.RCSTemplateParamSet = RCSTemplateParamSet;
    }

    public String getTemplateName() {
        return this.TemplateName;
    }

    public void setTemplateName(String TemplateName) {
        this.TemplateName = TemplateName;
    }

    public String getTemplateType() {
        return this.TemplateType;
    }

    public void setTemplateType(String TemplateType) {
        this.TemplateType = TemplateType;
    }

    public String getTemplateContent() {
        return this.TemplateContent;
    }

    public void setTemplateContent(String TemplateContent) {
        this.TemplateContent = TemplateContent;
    }

    public String getTaskTime() {
        return this.TaskTime;
    }

    public void setTaskTime(String TaskTime) {
        this.TaskTime = TaskTime;
    }

    public String getTag() {
        return this.Tag;
    }

    public void setTag(String Tag) {
        this.Tag = Tag;
    }

    public String getTaskId() {
        return this.TaskId;
    }

    public void setTaskId(String TaskId) {
        this.TaskId = TaskId;
    }

    public int getPageSize() {
        return this.PageSize;
    }

    public void setPageSize(int PageSize) {
        this.PageSize = PageSize;
    }

    public int getPageNo() {
        return this.PageNo;
    }

    public void setPageNo(int PageNo) {
        this.PageNo = PageNo;
    }

    private static final String TYPE1 = "1";
    private static final String TYPE2 = "2";
    private static final String TYPE3 = "3";
    // 模板短信接口(api2607 4.2.4)要求 Version 取值 1.2.0
    private static final String VERSION_V120 = "1.2.0";

    private static final String prefix = "sms/trade/";

    public SmsSend() {
    }

    /**
     * 国内短信普通接口
     */
    public JSONObject NormalSend(Credential credential, SmsSend source) throws Exception {
        String url = Constants.DOMAIN_API.concat(prefix).concat("normal/send");

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("MchId", credential.getMchId());
        requestData.put("AppId", credential.getAppId());
        requestData.put("Version", Constants.Version);
        requestData.put("SignType", Constants.MD5);
        requestData.put("TimeStamp", String.valueOf(SystemClock.now()));
        requestData.put("SignName", source.getSignName());
        requestData.put("PhoneNumberSet", source.getPhoneNumberSet());
        requestData.put("SessionContext", source.getSessionContext());
        requestData.put("Type", TYPE1);

        if (source.getTaskTime() != null) {
            requestData.put("TaskTime", source.getTaskTime());
        }
        if (source.getTag() != null) {
            requestData.put("Tag", source.getTag());
        }

        requestData.put("Signature", Encoder.generateSignature(requestData, credential.getAppKey(), Constants.MD5));
        return Request.requestOnce(url, JSON.toJSONString(requestData), credential.isHttp(), 10000, 10000);
    }

    /**
     * 国内短信个性接口
     */
    public JSONObject PersonalSend(Credential credential, SmsSend source) throws Exception {
        String url = Constants.DOMAIN_API.concat(prefix).concat("personal/send");

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("MchId", credential.getMchId());
        requestData.put("AppId", credential.getAppId());
        requestData.put("Version", Constants.Version);
        requestData.put("SignType", Constants.MD5);
        requestData.put("TimeStamp", String.valueOf(SystemClock.now()));
        requestData.put("ContextParamSet", source.getContextParamSet());
        requestData.put("Type", TYPE2);

        if (source.getTemplateId() != null) {
            requestData.put("TemplateId", source.getTemplateId());
        } else {
            requestData.put("SignName", source.getSignName());
            requestData.put("SessionContextSet", source.getSessionContextSet());
        }

        if (source.getTaskTime() != null) {
            requestData.put("TaskTime", source.getTaskTime());
        }
        if (source.getTag() != null) {
            requestData.put("Tag", source.getTag());
        }

        requestData.put("Signature", Encoder.generateSignature(requestData, credential.getAppKey(), Constants.MD5));
        return Request.requestOnce(url, JSON.toJSONString(requestData), credential.isHttp(), 10000, 10000);
    }

    /**
     * 国内短信模板接口（api2607 4.2.4：Version=1.2.0，请求体不携带SignName，签名串为
     * AppId=xxx&MchId=xxx&SignType=MD5&TemplateId=xxx&TimeStamp=xxx&Type=3&Version=1.2.0&key=xxx）
     *
     * AppId=1001xxx&MchId=1xxx9&SignName=【联麓】&SignType=MD5&Tag=123&TimeStamp=1694159054&Type=1&Version=1.1.0&key=6fxxxx50
     */
    public JSONObject TemplateSend(Credential credential, SmsSend source) throws Exception {
        String url = Constants.DOMAIN_API.concat(prefix).concat("template/send");

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("MchId", credential.getMchId());
        requestData.put("AppId", credential.getAppId());
        requestData.put("Version", "1.2.0");
        requestData.put("SignType", Constants.MD5);
        requestData.put("TimeStamp", String.valueOf(SystemClock.now()));
        requestData.put("TemplateId", source.getTemplateId());
        requestData.put("PhoneNumberSet", source.getPhoneNumberSet());
        requestData.put("TemplateParamSet", source.getTemplateParamSet());
        System.out.println("SSSS : "  + source);
        System.out.println("SSSS1111 : "  + source.getPhoneNumberSet());
        System.out.println("SSSS2222222 : "  + source.getTemplateParamSet());

        requestData.put("Type", TYPE3);
//        requestData.put("SignName", "【联麓】");
        // 打印requestData
        System.out.println("普通信息：" + requestData);
        System.out.println("TemplateParamSet：" + source.getTemplateParamSet());

        if (source.getTaskTime() != null) {
            requestData.put("TaskTime", source.getTaskTime());
        }
        if (source.getTag() != null) {
            requestData.put("Tag", source.getTag());
        }

        requestData.put("Signature", Encoder.generateSignature(requestData, credential.getAppKey(), Constants.MD5));
        System.out.println("requestData" + requestData);
        return Request.requestOnce(url, JSON.toJSONString(requestData), credential.isHttp(), 10000, 10000);
    }

    /**
     * 短信回执
     */
    public JSONObject Report(Credential credential, SmsSend source) throws Exception {
        String url = Constants.DOMAIN_API.concat(prefix).concat("report");

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("MchId", credential.getMchId());
        requestData.put("AppId", credential.getAppId());
        requestData.put("Version", Constants.Version);
        requestData.put("SignType", Constants.MD5);
        requestData.put("TimeStamp", String.valueOf(SystemClock.now()));
        requestData.put("TaskId", source.getTaskId());

        if (source.getPageSize() != 0) {
            requestData.put("pageSize", source.getPageSize());
        }
        if (source.getPageNo() != 0) {
            requestData.put("pageNo", source.getPageNo());
        }
        requestData.put("Signature", Encoder.generateSignature(requestData, credential.getAppKey(), Constants.MD5));
        return Request.requestOnce(url, JSON.toJSONString(requestData), credential.isHttp(), 10000, 10000);
    }

    /**
     * 短信回复
     */
    public JSONObject Reply(Credential credential, SmsSend source) throws Exception {
        String url = Constants.DOMAIN_API.concat(prefix).concat("reply");

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("MchId", credential.getMchId());
        requestData.put("AppId", credential.getAppId());
        requestData.put("Version", Constants.Version);
        requestData.put("SignType", Constants.MD5);
        requestData.put("TimeStamp", String.valueOf(SystemClock.now()));
        requestData.put("TaskId", source.getTaskId());

        if (source.getPageSize() != 0) {
            requestData.put("pageSize", source.getPageSize());
        }
        if (source.getPageNo() != 0) {
            requestData.put("pageNo", source.getPageNo());
        }

        requestData.put("Signature", Encoder.generateSignature(requestData, credential.getAppKey(), Constants.MD5));
        return Request.requestOnce(url, JSON.toJSONString(requestData), credential.isHttp(), 10000, 10000);
    }

    /**
     * 彩信普通发送
     */
    public JSONObject RCSNormalSend(Credential credential, SmsSend source) throws Exception {
        String url = Constants.DOMAIN_API.concat("/rcs/normal/send");

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("MchId", credential.getMchId());
        requestData.put("AppId", credential.getAppId());
        requestData.put("Version", Constants.Version);
        requestData.put("SignType", Constants.MD5);
        requestData.put("TimeStamp", String.valueOf(SystemClock.now()));
        requestData.put("TemplateId", source.getTemplateId());
        requestData.put("PhoneNumberSet", source.getPhoneNumberSet());

        if (source.getTaskTime() != null) {
            requestData.put("TaskTime", source.getTaskTime());
        }
        if (source.getTag() != null) {
            requestData.put("Tag", source.getTag());
        }

        requestData.put("Signature", Encoder.generateSignature(requestData, credential.getAppKey(), Constants.MD5));
        return Request.requestOnce(url, JSON.toJSONString(requestData), credential.isHttp(), 10000, 10000);
    }

    /**
     * 彩信个性发送
     */
    public JSONObject RCSPersonalSend(Credential credential, SmsSend source) throws Exception {
        String url = Constants.DOMAIN_API.concat("rcs/personal/send");

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("MchId", credential.getMchId());
        requestData.put("AppId", credential.getAppId());
        requestData.put("Version", Constants.Version);
        requestData.put("SignType", Constants.MD5);
        requestData.put("TimeStamp", String.valueOf(SystemClock.now()));
        requestData.put("TemplateId", source.getTemplateId());
        requestData.put("TemplateParamSet", source.getRCSTemplateParamSet());

        if (source.getTaskTime() != null) {
            requestData.put("TaskTime", source.getTaskTime());
        }
        if (source.getTag() != null) {
            requestData.put("Tag", source.getTag());
        }

        requestData.put("Signature", Encoder.generateSignature(requestData, credential.getAppKey(), Constants.MD5));
        return Request.requestOnce(url, JSON.toJSONString(requestData), credential.isHttp(), 10000, 10000);
    }
}
