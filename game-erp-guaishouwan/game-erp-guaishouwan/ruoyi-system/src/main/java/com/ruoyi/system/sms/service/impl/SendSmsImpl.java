package com.ruoyi.system.sms.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.send.config.YunLuConfig;
import com.ruoyi.system.send.service.SendMsgService;
import com.ruoyi.system.sms.service.ISendSms;
import com.ruoyi.system.sms.vo.AliYunSmsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zyy
 * @date 2023-05-08
 */
@Service
public class SendSmsImpl implements ISendSms {

    @Autowired
    private AliyunSms aliyunSms;
    @Resource
    private RedisCache redisCache;
    @Autowired
    private AliYunSmsConfig aliYunSmsConfig;
    @Autowired
    private YunLuConfig yunLuConfig;
    @Autowired
    private SendMsgService sendMsgService;

    @Override
    public Map<String, Object> sendSms(String phone, String type) throws Exception {
        String yzmRankey = IdUtils.fastUUID();
        if (StringUtils.isEmpty(type)) {
            type = "dh";
        }
        String dhkey = type + phone;
        if (Objects.nonNull(redisCache.getCacheObject(dhkey))) {
            long now = new Date().getTime();
            long his = redisCache.getCacheObject(dhkey);
            if ((now - his) / 1000 < 60) {
                throw new GlobalException("发送短信频繁,请稍后再试!");
            }
        }
        Map<String, String> map = new HashMap<>();
        if (StringUtils.equals(type, "huishou")) {
            map = aliYunSmsConfig.getBackTemplate();
        } else if (StringUtils.equals(type, "phoneLogin")) {
            map = aliYunSmsConfig.getPhoneLoginTemplate();
        } else if (StringUtils.equals(type, "public")) {
            map = aliYunSmsConfig.getPublicYZMTemplate();
        } else if (StringUtils.equals(type, "mima")) {
            map = aliYunSmsConfig.getPasswordYZMTemplate();
        } else {
            throw new GlobalException("未设置指定短信模板");
        }
        String dxyzm = getRanString(); //生成6位随机数
        map.put("phoneNumbers", phone);
        map.put("templateParam", "{code:" + dxyzm + "}");
        AjaxResult ajaxResult = aliyunSms.sendSms(map);
        Map<String, Object> result = new HashMap<>();
        if (Objects.equals(200, ajaxResult.get("code"))) {
            String realKey = yzmRankey + phone;
            redisCache.setCacheObject(realKey, dxyzm, 5, TimeUnit.MINUTES);
            redisCache.setCacheObject(dhkey, DateUtils.getNowDate().getTime(), 2, TimeUnit.MINUTES);
            result.put("code", 200);
            result.put("yzmRankey", yzmRankey);
//            result.put("dxyzm", dxyzm);
        } else {
            result.put("code", 500);
        }
        return result;
    }

    @Override
    public Map<String, Object> sendSmsForNotice(String phone, String type) throws Exception {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.equals(type, "huishou")) {
            map = aliYunSmsConfig.getPublishSuccessTemplate();
        } else if (StringUtils.equals(type, "baojia")) {
            map = aliYunSmsConfig.getQuotationSuccessTemplate();
        } else {
            throw new GlobalException("未设置指定短信模板");
        }
        map.put("phoneNumbers", phone);
        map.put("templateParam", "");
        AjaxResult ajaxResult = aliyunSms.sendSms(map);
        return ajaxResult;
    }

    public String getRanString() {
        Random random = new Random();
        //把随机生成的数字转成字符串
        String str = String.valueOf(random.nextInt(9));
        for (int i = 0; i < 5; i++) {
            str += random.nextInt(9);
        }
        if (str.startsWith("0")) {
            str = str.replaceFirst("0", "1");
        }
        return str;
    }

    @Override
    public Map<String, Object> sendLianLuSms(String phone, String type) throws Exception {
        String yzmRankey = IdUtils.fastUUID();
        if (StringUtils.isEmpty(type)) {
            type = "dh";
        }
        String dhkey = type + phone;
        if (Objects.nonNull(redisCache.getCacheObject(dhkey))) {
            long now = new Date().getTime();
            long his = redisCache.getCacheObject(dhkey);
            if ((now - his) / 1000 < 60) {
                throw new GlobalException("发送短信频繁,请稍后再试!");
            }
        }

        String typeString = "";
        if (StringUtils.equals(type, "phoneLogin")) {
            typeString = "登录";
        } else if (StringUtils.equals(type, "mima")) {
            typeString = "修改密码";
        } else {
            throw new GlobalException("未设置指定短信模板,请联系系统管理员");
        }
        String dxyzm = getRanString(); //生成6位随机数

        sendMsgService.sendCode(typeString, dxyzm, phone);

        Map<String, Object> result = new HashMap<>();
        String realKey = yzmRankey + phone;
        redisCache.setCacheObject(realKey, dxyzm, 5, TimeUnit.MINUTES);
        redisCache.setCacheObject(dhkey, DateUtils.getNowDate().getTime(), 2, TimeUnit.MINUTES);
        result.put("code", 200);
        result.put("yzmRankey", yzmRankey);
        return result;
    }
}
