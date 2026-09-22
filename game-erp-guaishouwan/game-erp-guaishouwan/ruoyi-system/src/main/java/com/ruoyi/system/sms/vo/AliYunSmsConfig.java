package com.ruoyi.system.sms.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zyy
 * @date 2023-05-08
 */

@Data
@Component
@ConfigurationProperties(prefix = "aliyunsms")
public class AliYunSmsConfig {

    private String accessKeyId;

    private String accessKeySecret;

    /**
     * 回收页面验证码模板
     */
    private Map<String, String> backTemplate;

    /**
     * 手机号验证码登录模板
     */
    private Map<String, String> phoneLoginTemplate;

    /**
     * 短信验证码通用模板
     */
    private Map<String, String> publicYZMTemplate;

    /**
     * 通知地址  回收的时候
     */
    private Map<String, String> publishSuccessTemplate;

    /**
     * 通知地址  报价成功的时候
     */
    private Map<String, String> quotationSuccessTemplate;

    /**
     * 手机验证码修改密码模板
     */
    private Map<String, String> passwordYZMTemplate;
}
