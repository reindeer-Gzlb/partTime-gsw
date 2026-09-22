package com.ruoyi.web.controller.V2.web.conntroller;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.PhoneLoginBody;
import com.ruoyi.common.core.domain.model.RegisterBody;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * web 我的收藏 功能
 *
 * @author zyy
 * @date 2023-05-03
 */
@RestController
@RequestMapping("/h5")
public class H5LoginController extends BaseController {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private SysLoginService loginService;
    @Autowired
    private SysUserMapper userMapper;


    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping(value = "/login")
    public AjaxResult loginByTelephone(@RequestBody PhoneLoginBody loginBody) {
//        if (loginBody != null) {
//            if (Objects.isNull(loginBody.getPhone())) {
//                throw new GlobalException("手机号不能为空");
//            }
//            if (Objects.isNull(loginBody.getDxyzm())) {
//                throw new GlobalException("短信验证码不能为空");
//            }
//            if (Objects.isNull(loginBody.getYzmRankey())) {
//                throw new GlobalException("验证码无效");
//            }
//            boolean flag = checkYZM(loginBody.getDxyzm(), loginBody.getYzmRankey());
//            if (!flag) {
//                throw new GlobalException("验证码无效");
//            }
//        } else {
//            throw new GlobalException("提交数据有误");
//        }
        // 生成令牌
        String token = loginService.loginByPhone(loginBody, "h5");
        AjaxResult ajax = AjaxResult.success();
        ajax.put(Constants.TOKEN, token);
        redisCache.deleteObject(loginBody.getYzmRankey());
        return ajax;
    }

    //根据短信验证码修改密码
    @ResponseBody
    @PostMapping("/SmsforPwd")
    public AjaxResult SmsforPwd(@RequestBody RegisterBody user) {
        SysUser user1 = getLoginUser().getUser();
        if (!StringUtils.equals(user1.getPhonenumber(), user.getPhonenumber())) {
            throw new GlobalException("输入的手机号不是本账号的手机号");
        }
        if (user != null) {
            if (Objects.isNull(user.getPassword())) {
                throw new GlobalException("密码不能为空");
            }
            boolean flag = redisCache.checkYZM(user.getDxyzm(), user.getYzmRankey(), user.getPhonenumber());
            if (!flag) {
                throw new GlobalException("验证码无效");
            }
        } else {
            throw new GlobalException("提交数据有误");
        }
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (Objects.isNull(info)) {
            throw new GlobalException("手机号不存在");
        }
        SysUser sysUser = new SysUser();
        sysUser.setUserId(info.getUserId());
        sysUser.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        int count = userMapper.updateUser(sysUser);
        return AjaxResult.success(count);
    }
}


