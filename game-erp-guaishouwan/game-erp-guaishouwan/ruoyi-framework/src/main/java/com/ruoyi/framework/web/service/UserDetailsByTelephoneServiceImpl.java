package com.ruoyi.framework.web.service;

/**
 * @author zyy
 * @date 2023-05-10
 */

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * 用户验证处理
 *
 * @author gmk
 */
@Service("userDetailsByTelephoneServiceImpl")
public class UserDetailsByTelephoneServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsByTelephoneServiceImpl.class);

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        String realPhone = "";
        String type = "";
        if (StringUtils.contains(phone, "==")) {
            String[] arr = phone.split("==");
            realPhone = arr[0];
            type = arr[1];
        } else {
            realPhone = phone;
        }
        SysUser user = userService.selectUserByPhonenumber(realPhone);
        if (StringUtils.isNull(user)) {
            if (StringUtils.equals(type, "h5")) {
                user = new SysUser();
                user.setUserName(realPhone);
                user.setNickName("用户" + DateUtils.randomTen());
                user.setPhonenumber(realPhone);
                user.setPassword(SecurityUtils.encryptPassword("123456"));
                user.setUserNewType(2);
                user.setStatus("0");
                userService.insertUser(user);
            } else {
                log.info("登录用户：{} 不存在.", realPhone);
                throw new ServiceException("登录用户：" + realPhone + " 不存在");
            }
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", realPhone);
            throw new ServiceException("对不起，您的账号：" + realPhone + " 已被删除");
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", realPhone);
            throw new ServiceException("对不起，您的账号：" + realPhone + " 已停用");
        }

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user) {
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
    }
}
