package com.ruoyi.framework.web.service;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.PhoneLoginBody;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.security.sms.SmsCodeAuthenticationToken;
import com.ruoyi.system.domain.SysUserLoginToken;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserLoginTokenService;
import com.ruoyi.system.service.ISysUserService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private ISysUserLoginTokenService sysUserLoginTokenService;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid, String loginType) {

        if (StringUtils.isNotEmpty(loginType) && StringUtils.equals(loginType, "h5")) {

        } else {
            boolean captchaOnOff = configService.selectCaptchaOnOff();
            // 验证码开关
            if (captchaOnOff) {
                validateCaptcha(username, code, uuid);
            }
        }

        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
                        MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
                        e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS,
                MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());

        String token = tokenService.createToken(loginUser);
        SysUserLoginToken sysUserLoginToken = new SysUserLoginToken();
        sysUserLoginToken.setUserId(loginUser.getUserId());
        sysUserLoginToken.setToken(loginUser.getToken());
        sysUserLoginTokenService.save(sysUserLoginToken);
        // 生成token
        return token;
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
                    MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
                    MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }

    /**
     * h5登录验证
     *
     * @param loginBody 手机号
     * @return 结果
     */
    public String loginByPhone(PhoneLoginBody loginBody, String type) {
        if (loginBody != null) {
            boolean flag = redisCache.checkYZM(loginBody.getDxyzm(), loginBody.getYzmRankey(), loginBody.getPhone());
            if (!flag) {
                throw new GlobalException("验证码错误");
            }
        } else {
            throw new GlobalException("提交数据有误");
        }
        String phone = loginBody.getPhone();
        if (StringUtils.isNotEmpty(type)) {
            phone = phone + "==" + type;
        }
//        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
//        String captcha = redisCache.getCacheObject(verifyKey);
//        redisCache.deleteObject(verifyKey);
      /*  if (captcha == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(user.getUserName(), Constants.LOGIN_FAIL,
            MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(user.getUserName(), Constants.LOGIN_FAIL,
            MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }*/
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new SmsCodeAuthenticationToken(phone));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(phone, Constants.LOGIN_FAIL,
                        MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(phone, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(phone, Constants.LOGIN_SUCCESS, MessageUtils.message(
                "user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token

        //检测是否注册环信
        SysUser sysUser = userService.selectUserById(loginUser.getUserId());
        if (StringUtils.isEmpty(sysUser.getHxuuid())) {
            //注册环信
            Map<String, Object> params = new HashMap<>();
            params.put("username", sysUser.getUserName());
            params.put("pwd", sysUser.getUserName());
            SysUser sysUser1 = userService.selectUserByUserName(MapUtils.getString(params, "username"));
            //修改
            JSONObject uptParams = new JSONObject();
            uptParams.put("nickname", sysUser1.getNickName());
            uptParams.put("avatar", sysUser1.getAvatar());
            uptParams.put("phone", sysUser1.getPhonenumber());
            uptParams.put("username", sysUser1.getHxusername());
        }
        String token = tokenService.createToken(loginUser);
        SysUserLoginToken sysUserLoginToken = new SysUserLoginToken();
        sysUserLoginToken.setUserId(loginUser.getUserId());
        sysUserLoginToken.setToken(loginUser.getToken());
        sysUserLoginTokenService.save(sysUserLoginToken);
        // 生成token
        return token;
    }

    public String oneKeylogin(Map<String, Object> params) {

        /**
         * {
         *     "data": {
         *         "code": 0,
         *         "success": true,
         *         "phoneNumber": "19837010956"
         *     },
         *     "statusCode": 200,
         *     "header": {
         *         "request-id": "ac1cc3041695453453624161923",
         *         "Date": "Sat, 23 Sep 2023 07:17:35 GMT",
         *         "Connection": "keep-alive",
         *         "Set-Cookie": "acw_tc=2760820116954534536183908e39a147cf3ca4147cc995a1f069e51ac90d7e;path=/;
         *         HttpOnly;Max-Age=1800",
         *         "Content-Type": "application/json",
         *         "Vary": "Accept-Encoding",
         *         "content-disposition": "attachment",
         *         "Transfer-Encoding": "chunked"
         *     },
         *     "errMsg": "request:ok",
         *     "cookies": [
         *         "acw_tc=2760820116954534536183908e39a147cf3ca4147cc995a1f069e51ac90d7e;path=/;HttpOnly;Max-Age=1800"
         *     ]
         * }
         */

        //获取as
        //获取openid
        //调接口登录
        //返回手机号
        //判断手机号是否注册
        //注册兵登录
        String access_token = MapUtils.getString(params, "access_token");
        String openid = MapUtils.getString(params, "openid");
        System.out.println("一键登录进来了");
        if (1 == 1) {
            throw new GlobalException("暂未申请一键登录参数");
        }
        String url = "https://uni.feihengdaishou.com/getTel?access_token=" + access_token + "&openid=" + openid;

        String body = HttpRequest.get(url).execute().body();
        System.out.println("一键登录返回body：" + body);
        JSONObject jsonObject = JSONObject.parseObject(body);
//        JSONObject data = jsonObject.getJSONObject("data");
        String code = jsonObject.getString("code");
        if (Objects.equals("0", code)) {
            String phoneNumber = jsonObject.getString("phoneNumber");

            SysUser sysUser = userService.selectUserByUserName(phoneNumber);
            if (Objects.isNull(sysUser)) {
                //开始注册
                sysUser = new SysUser();
                sysUser.setUserName(phoneNumber);
                sysUser.setPhonenumber(phoneNumber);
                sysUser.setNickName("用户" + DateUtils.randomTen());
                sysUser.setPassword(SecurityUtils.encryptPassword("123456"));
                sysUser.setCreateTime(new Date());
                userService.registerUser(sysUser);
            }

            //开始登录
            Authentication authentication = null;
            try {
                // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
                authentication = authenticationManager
                        .authenticate(new SmsCodeAuthenticationToken(phoneNumber));
            } catch (Exception e) {
                if (e instanceof BadCredentialsException) {
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(phoneNumber, Constants.LOGIN_FAIL,
                            MessageUtils.message("user.password.not.match")));
                    throw new UserPasswordNotMatchException();
                } else {
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(phoneNumber, Constants.LOGIN_FAIL,
                            e.getMessage()));
                    throw new ServiceException(e.getMessage());
                }
            }
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(phoneNumber, Constants.LOGIN_SUCCESS,
                    MessageUtils.message(
                            "user.login.success")));
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            String token = tokenService.createToken(loginUser);
            SysUserLoginToken sysUserLoginToken = new SysUserLoginToken();
            sysUserLoginToken.setUserId(loginUser.getUserId());
            sysUserLoginToken.setToken(loginUser.getToken());
            sysUserLoginTokenService.save(sysUserLoginToken);
            // 生成token
            return token;
        } else {
            throw new GlobalException("登录失败");
        }

    }
}
