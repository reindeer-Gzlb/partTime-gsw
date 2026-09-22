package com.ruoyi.system.V2.accountMonitoring.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.V2.accountMonitoring.mapper.AccountMonitoringQueryMapper;
import com.ruoyi.system.V2.accountMonitoring.mapper.MonitoringErrMapper;
import com.ruoyi.system.V2.accountMonitoring.service.IAccountMonitoringQueryService;
import com.ruoyi.system.V2.accountMonitoring.service.IAccountMonitoringService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.AccountMonitoring;
import com.ruoyi.system.model.domain.AccountMonitoringQuery;
import com.ruoyi.system.model.domain.MonitoringErr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author zyy
 * @date 2023-05-05
 */
@Service
public class AccountMonitoringQueryServiceImpl extends ServiceImpl<AccountMonitoringQueryMapper,
        AccountMonitoringQuery> implements IAccountMonitoringQueryService {
    @Autowired
    private IAccountMonitoringService accountMonitoringService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    MonitoringErrMapper monitoringErrMapper;

    @Override
    @Transactional
    public void saveAccountMonitoringQueryAndMonitoring(String backStr) {
        JSONObject jsonObject = JSONObject.parseObject(backStr);
        String qq = jsonObject.getString("qq");
        String checkId = jsonObject.getString("checkId");
        String telephone = jsonObject.getString("telephone");
        String status = jsonObject.getString("status");
        String queryResult = jsonObject.getString("queryResult");

        LambdaQueryWrapper<AccountMonitoring> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(AccountMonitoring::getCheckId, checkId);
        queryWrapper.eq(AccountMonitoring::getDel, "1");
        AccountMonitoring accountMonitoring = accountMonitoringService.getOne(queryWrapper);
        if (Objects.isNull(accountMonitoring)) {
            MonitoringErr monitoringErr = new MonitoringErr();
            monitoringErr.setErrText("进入了账号监测回调,但是未找到监测记录,请管理员检查,checkId：" + checkId);
            monitoringErrMapper.insert(monitoringErr);
            return;
        }
        accountMonitoringService.checkUserNumsAndDown(accountMonitoring);
        AccountMonitoringQuery accountMonitoringQuery = new AccountMonitoringQuery();
        accountMonitoringQuery.setQq(qq);
        accountMonitoringQuery.setTelephone(telephone);
        accountMonitoringQuery.setStatus(status);
        accountMonitoringQuery.setQueryId(Integer.valueOf(checkId));
        accountMonitoringQuery.setBindTel(queryResult);
        accountMonitoringQuery.setAccountMonitoringId(accountMonitoring.getId());
        save(accountMonitoringQuery);
        accountMonitoring.setStatus(status);
        accountMonitoring.setBindTel(queryResult);
        accountMonitoringService.updateById(accountMonitoring);
    }
}
