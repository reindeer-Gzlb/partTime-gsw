package com.ruoyi.web.controller.system;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.domain.model.PhoneLoginBody;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid(),loginBody.getLoginType());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }


    /**
     * 登录方法
     *
     * @param params 登录信息
     * @return 结果
     */
    @PostMapping("/oneKeylogin")
    public AjaxResult oneKeylogin(@RequestBody Map<String,Object> params) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.oneKeylogin(params);
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/dxLogin")
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
        String token = loginService.loginByPhone(loginBody, "erp");
        AjaxResult ajax = AjaxResult.success();
        ajax.put(Constants.TOKEN, token);
        redisCache.deleteObject(loginBody.getYzmRankey());
        return ajax;
    }
}
