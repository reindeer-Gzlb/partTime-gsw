package com.ruoyi.system.sms.service.impl;

import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.sms.vo.AliYunSmsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AliyunSms {

    @Autowired
    private AliYunSmsConfig aliYunSmsConfig;

    public AjaxResult sendSms(Map<String, String> map) throws Exception {
        // 工程代码泄露可能会导致AccessKey泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun
        // .com/document_detail/378657.html
        com.aliyun.dysmsapi20170525.Client client = createClient(aliYunSmsConfig.getAccessKeyId(),
                aliYunSmsConfig.getAccessKeySecret());
        com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest =
                new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                        .setPhoneNumbers(map.get("phoneNumbers"))  //接收短信的手机号码
                        .setSignName(map.get("signName"))
                        .setTemplateCode(map.get("templateCode"))
                        .setTemplateParam(map.get("templateParam"));
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        String fanhui = "";
        try {
            // 复制代码运行请自行打印 API 的返回值
            SendSmsResponse Response = client.sendSmsWithOptions(sendSmsRequest, runtime);
            Map<String, Object> result = new HashMap<>();
            result.put("code", Response.getStatusCode());
            result.put("resheads", Response.getHeaders());
            result.put("msg", Response.getBody());
            return AjaxResult.success(result);
        } catch (TeaException error) {
            // 如有需要，请打印 error
            fanhui = com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            fanhui = com.aliyun.teautil.Common.assertAsString(error.message);
        }
        return AjaxResult.error(fanhui);
    }

    public com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }
}
