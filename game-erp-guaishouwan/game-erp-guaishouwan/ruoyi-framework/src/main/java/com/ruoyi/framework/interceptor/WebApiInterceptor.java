package com.ruoyi.framework.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.filter.RepeatedlyRequestWrapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpHelper;
import com.ruoyi.system.token.annotation.NotRepeatSubmit;
import com.ruoyi.system.token.utils.ApiUtil;
import com.ruoyi.system.token.utils.MD5Util;
import com.ruoyi.system.token.vo.TokenInfo;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class WebApiInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    RedisCache redisCache;

    /**
     * @param request
     * @param response
     * @param handler  访问的目标方法
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String timestamp = request.getHeader("timestamp");
        // 随机字符串
//        String nonce = request.getHeader("nonce");
        String sign = request.getHeader("sign");
        Assert.isTrue(!StringUtils.isEmpty(token) && !StringUtils.isEmpty(timestamp) && !StringUtils.isEmpty
                        (sign),
                "参数错误");

        // 获取超时时间
        NotRepeatSubmit notRepeatSubmit = ApiUtil.getNotRepeatSubmit(handler);
        long expireTime = notRepeatSubmit == null ? 5 * 60 * 1000 : notRepeatSubmit.value();

        // 2. 请求时间间隔

        long reqeustInterval = System.currentTimeMillis() - Long.valueOf(timestamp);
//        Assert.isTrue(reqeustInterval < expireTime, "请求超时，请重新请求");

        // 3. 校验Token是否存在
        ValueOperations<String, TokenInfo> tokenRedis = redisTemplate.opsForValue();
        TokenInfo tokenInfo = tokenRedis.get(token);
        Assert.notNull(tokenInfo, "token错误");

        // 4. 校验签名(将所有的参数加进来，防止别人篡改参数) 所有参数看参数名升续排序拼接成url

        String qingqiucanshu = "";
        if (isJson(request)) {
            RepeatedlyRequestWrapper repeatedlyRequest = (RepeatedlyRequestWrapper) request;
            String strparams = HttpHelper.getBodyString(repeatedlyRequest);
            Map<String, Object> map = JSONObject.parseObject(strparams, Map.class);
            qingqiucanshu = ApiUtil.getCover(map);
        } else {
            qingqiucanshu = ApiUtil.concatSignString(request);
        }
//        String signString = qingqiucanshu + "secret" + token + timestamp;
        // 请求参数 + Secret + token + timestamp
        String signString = qingqiucanshu + tokenInfo.getAppInfo().getAppSecret() + token + timestamp;
        String signature = MD5Util.encode(signString);
        boolean flag = signature.equals(sign);
        Assert.isTrue(flag, "签名错误");

        // 5. 拒绝重复调用(第一次访问时存储，过期时间和请求超时时间保持一致), 只有标注不允许重复提交注解的才会校验
        if (notRepeatSubmit != null) {
            if (Objects.isNull(redisCache.getCacheObject(sign))) {
                redisCache.setCacheObject(sign, 0, (int) expireTime, TimeUnit.MILLISECONDS);
            } else {
                throw new ServiceException("请勿重复提交");
            }
        }
        //6. 限制用户调用接口频率 目前写死1000毫秒
//        if (Objects.isNull(redisCache.getCacheObject(tokenInfo.getAppInfo().getAppId()))) {
//            redisCache.setCacheObject(tokenInfo.getAppInfo().getAppId(), 0, 1000, TimeUnit.MILLISECONDS);
//        } else {
//            throw new ServiceException("接口调用频繁,请稍后再试");
//        }
        return super.preHandle(request, response, handler);
    }

    private boolean isJson(HttpServletRequest request) {
        if (request.getContentType() != null) {
            return request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE) ||
                    request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE) ||
                    request.getContentType().equals(ContentType.APPLICATION_JSON.toString());
        }

        return false;
    }

}

