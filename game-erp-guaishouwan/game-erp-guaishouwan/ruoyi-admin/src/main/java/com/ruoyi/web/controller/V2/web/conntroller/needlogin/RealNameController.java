package com.ruoyi.web.controller.V2.web.conntroller.needlogin;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.service.UploadService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * @author lz
 * @version 1.0
 * @date 2022/5/10 18:34
 */
@RestController
@RequestMapping("/realname")
public class RealNameController extends BaseController {

    @Autowired
    UploadService uploadService;
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    TokenService tokenService;

    @ResponseBody
    @PostMapping("/auth")
    public AjaxResult auth(@RequestBody Map<String, Object> map) {
        String id = MapUtils.getString(map, "id");
        String frontIdCardImg = MapUtils.getString(map, "frontIdCardImg");
        String reverseIdCardImg = MapUtils.getString(map, "reverseIdCardImg");
        String idNumber = MapUtils.getString(map, "idNumber");
        String name = MapUtils.getString(map, "name");
        if (StringUtils.isEmpty(id)) {
            throw new GlobalException("用户参数有误");
        }
//        if (StringUtils.isEmpty(frontIdCardImg) || StringUtils.isEmpty(reverseIdCardImg)) {
//            throw new GlobalException("身份证正反面照片不能为空");
//        }
        if (StringUtils.isEmpty(idNumber) || StringUtils.isEmpty(name)) {
            throw new GlobalException("姓名或身份证号不能为空");
        }
        SysUser sysUser = sysUserMapper.selectUserById(Long.valueOf(id));
        if (Objects.isNull(sysUser)) {
            throw new GlobalException("未找到此用户");
        }
//        boolean flag = uploadService.parseCode(map);
        boolean flag = uploadService.parseCodeShuMai(map);
        if (flag) {
            sysUser.setIdCard(idNumber);
            sysUser.setRealName(name);
            sysUser.setRealStatus(2);
            sysUser.setFrontIdCardImg(frontIdCardImg);
            sysUser.setReverseIdCardImg(reverseIdCardImg);
            sysUserMapper.updateUser(sysUser);
            LoginUser loginUser = getLoginUser();
            loginUser.setUser(sysUser);
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success(flag);
        } else {
            return AjaxResult.error("认证失败,请检查认证信息！");
        }
    }
}
