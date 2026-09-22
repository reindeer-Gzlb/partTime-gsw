package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.SysUserLoginToken;

/**
 * 用户 业务层
 *
 * @author ruoyi
 */
public interface ISysUserLoginTokenService extends IService<SysUserLoginToken> {

    void delByUserId(Long userId);
}
