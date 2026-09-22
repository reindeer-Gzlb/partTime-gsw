package com.ruoyi.system.send.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.sendMsg.Credential;
import com.ruoyi.system.send.config.YunLuConfig;
import com.ruoyi.system.send.model.SmsSend;
import com.ruoyi.system.send.service.SendMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SendMsgServiceImpl implements SendMsgService {


    @Autowired
    private YunLuConfig yunLuConfig;


    @Override
    public void sendCode(String type, String code, String tel) {

        SmsSend s = new SmsSend();
        s.setPhoneNumberSet(new String[]{tel});
        s.setSignName(yunLuConfig.getSignName());
        s.setTemplateId(yunLuConfig.getTempleteCode());
        s.setTemplateParamSet(new String[]{type, code});
//        System.out.println("1111" + JSONObject.toJSONString(s));
//        s.setSessionContext("  您正在进行短信验证码{%变量1%}操作，验证码为{%变量2%} ，该验证码5分钟内有效，请勿泄露于他人。");
        try {
            JSONObject re = s.TemplateSend(c(), s);
            System.out.println(re);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendOnCount(String accountId, String tel) {
        SmsSend s = new SmsSend();
        s.setPhoneNumberSet(new String[]{tel});
//        s.setSignName(yunLuConfig.getSignName());
//        s.setTemplateId(yunLuConfig.getTempleteOnCode());
        s.setTemplateParamSet(new String[]{accountId});
//        s.setSessionContext("您的商品已上架成功，商品编码为：{%变量1%}，请去球球通APP查看。");
        try {
            JSONObject re = s.TemplateSend(c(), s);
            System.out.println(re);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendLinkCode(JSONObject params) {

        String tel = params.getString("tel");
        String code = params.getString("code");
        String price = params.getString("price");
        String link = params.getString("link");

        SmsSend s = new SmsSend();
        s.setPhoneNumberSet(new String[]{tel});
        s.setSignName(yunLuConfig.getSignName());
        s.setTemplateId(yunLuConfig.getTempleteBaoJia());
        s.setTemplateParamSet(new String[]{code, price, link});
        try {
            JSONObject re = s.TemplateSend(c(), s);
            System.out.println(re);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendByCode(String code, String[] param, String tel) {

        SmsSend s = new SmsSend();
        s.setPhoneNumberSet(new String[]{tel});
        s.setSignName(yunLuConfig.getSignName());
        s.setTemplateId(code);
        if (Objects.nonNull(param) && param.length > 0) {
            // 数组不为null且不为空
            s.setTemplateParamSet(param);
        }
        try {
            JSONObject re = s.TemplateSend(c(), s);
            System.out.println(re);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Credential c() {
        Credential credential = new Credential();
        credential.setMchId(yunLuConfig.getMchId());
        credential.setAppId(yunLuConfig.getAppId());
        credential.setAppKey(yunLuConfig.getAppKey());
        credential.setIsHttp(true);
        return credential;
    }

}
