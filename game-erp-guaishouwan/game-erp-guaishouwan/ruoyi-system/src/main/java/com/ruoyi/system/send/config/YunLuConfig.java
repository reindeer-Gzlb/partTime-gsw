package com.ruoyi.system.send.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "yunluconfig")
public class YunLuConfig {

    private String MchId;
    private String AppId;
    private String AppKey;
//    private String TempletePingGu;
//    private String TempleteZaiXian;
//    private String TempleteZiXun;
//    private String TempleteChengJiao;
//    private String TempleteTongGuo;
//    private String TempleteBuTongGuo;
//    private String TempleteTiXing;
    private String TempleteBaoJia;
    private String TempleteCode;
    private String SignName;
    private String baseUrl;

}
