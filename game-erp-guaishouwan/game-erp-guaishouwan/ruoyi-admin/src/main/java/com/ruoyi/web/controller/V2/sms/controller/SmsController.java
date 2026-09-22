package com.ruoyi.web.controller.V2.sms.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.sms.service.ISendSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 收藏
 *
 * @author ruoyi
 * @date 2023-03-09
 */
@RestController
@RequestMapping("/sms")
public class SmsController extends BaseController {

    @Resource
    private RedisCache redisCache;
    @Autowired
    private ISendSms sendSms;

    @ResponseBody
    @GetMapping("/sendYzm")
    public AjaxResult sendYzm(String phone, String type) throws Exception {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(type)) {
            throw new GlobalException("传入参数有误");
        }
        Map<String, Object> result = sendSms.sendLianLuSms(phone, type);
        return AjaxResult.success(result);
    }

}
