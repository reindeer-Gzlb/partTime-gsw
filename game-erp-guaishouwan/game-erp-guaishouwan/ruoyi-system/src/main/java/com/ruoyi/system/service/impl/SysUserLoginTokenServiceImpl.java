package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.domain.SysUserLoginToken;
import com.ruoyi.system.mapper.SysUserLoginTokenMapper;
import com.ruoyi.system.service.ISysUserLoginTokenService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service("sysUserLoginToeknService")
public class SysUserLoginTokenServiceImpl extends ServiceImpl<SysUserLoginTokenMapper, SysUserLoginToken> implements ISysUserLoginTokenService {

    @Autowired
    private RedisCache redisCache;

    @Async
    @Override
    public void delByUserId(Long userId) {
        LambdaQueryWrapper<SysUserLoginToken> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysUserLoginToken::getUserId, userId);
        List<SysUserLoginToken> list = list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            List<String> tokenList =
                    list.stream().map(sysUserLoginToken -> Constants.LOGIN_TOKEN_KEY + sysUserLoginToken.getToken()).collect(Collectors.toList());
            redisCache.deleteObject(tokenList);
            remove(queryWrapper);
        }
    }
}

