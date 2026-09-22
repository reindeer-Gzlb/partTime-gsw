package com.ruoyi.system.V2.accountMonitoring.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.system.V2.accountMonitoring.mapper.AccountMonitoringMapper;
import com.ruoyi.system.V2.accountMonitoring.service.IAccountMonitoringQueryService;
import com.ruoyi.system.V2.accountMonitoring.service.IAccountMonitoringService;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.AccountMonitoring;
import com.ruoyi.system.model.domain.AccountMonitoringQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zyy
 * @date 2023-05-29
 */
@Service
public class AccountMonitoringServiceImpl extends ServiceImpl<AccountMonitoringMapper, AccountMonitoring> implements IAccountMonitoringService {

    @Autowired
    IAccountMonitoringQueryService accountMonitoringQueryService;
    @Autowired
    private SysDictDataMapper sysDictDataMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    Integer userId = null;
    String pwd = "";
    String baseUrl = "";

    @Override
    public boolean saveAndGetCheckId(AccountMonitoring accountMonitoring) {
        Long userId = checkUserNums();  // 检查操作用户 自动获取次数
        initializationParams();// 初始化参数
        List<AccountMonitoring> list =
                list(new LambdaQueryWrapper<AccountMonitoring>().eq(AccountMonitoring::getAccountNumber,
                        accountMonitoring.getAccountNumber()).eq(AccountMonitoring::getDel, 1));
        if (CollectionUtils.isNotEmpty(list)) {
            throw new GlobalException("该账号已存在,不能重复添加");
        }
        Map<String, Object> params = getAddParams(accountMonitoring);
        String response = HttpUtils.doPostForm(baseUrl + "account/add", params, null);
        JSONObject resultJson = JSONObject.parseObject(response);
        String msg = resultJson.getString("msg");
        if ("1".equals(resultJson.getString("code")) && StringUtils.equals(msg, "ok")) {
            JSONObject data = resultJson.getJSONObject("data");
            String checkId = data.getString("checkId");
            if (StringUtils.isNotEmpty(checkId)) {
                accountMonitoring.setCheckId(checkId);
                accountMonitoring.setCreateId(userId);
                accountMonitoring.setEnabled("1");
                save(accountMonitoring);
            }
        } else {
            throw new GlobalException(response);
        }
        return true;
    }

    @Override
    public boolean accountMonitoringUpOrDown(AccountMonitoring accountMonitoring, String type) {
        Long userId = null;
        if (StringUtils.equals("1", type)) {
            userId = checkUserNums();
        }
        if (StringUtils.isNull(userId)) {
            userId = SecurityUtils.getUserId();
        }
        initializationParams();
        Map<String, Object> params = getUpOrDownParams(accountMonitoring, type);
        String response = HttpUtils.doPostForm(baseUrl + "account/switch", params, null);
        JSONObject resultJson = JSONObject.parseObject(response);
        String msg = resultJson.getString("msg");
        if ("1".equals(resultJson.getString("code")) && StringUtils.equals(msg, "ok")) {
            accountMonitoring.setEnabled(type);
            updateById(accountMonitoring);
        } else {
            throw new GlobalException(response);
        }
        return true;
    }

    @Override
    public boolean accountMonitoringDelete(AccountMonitoring accountMonitoring) {
        initializationParams();
        Map<String, Object> params = getDeleteParams(accountMonitoring);
        String response = HttpUtils.doPostForm(baseUrl + "account/del", params, null);
        JSONObject resultJson = JSONObject.parseObject(response);
        String msg = resultJson.getString("msg");
        if ("1".equals(resultJson.getString("code")) && StringUtils.equals(msg, "ok")) {
//            LambdaQueryWrapper<AccountMonitoringQuery> queryWrapper = new LambdaQueryWrapper();
//            queryWrapper.eq(AccountMonitoringQuery::getAccountMonitoringId, accountMonitoring.getId());
//            accountMonitoringQueryService.remove(queryWrapper);
//            removeById(accountMonitoring);
            accountMonitoring.setDel(2);
            accountMonitoring.setUpdateId(SecurityUtils.getUserId());
            updateById(accountMonitoring);
        } else {
            throw new GlobalException(response);
        }
        return true;
    }

    @Override
    @Transactional
    public AccountMonitoringQuery accountMonitoringQuery(AccountMonitoring accountMonitoring) {
        initializationParams();
        Map<String, Object> params = getQueryParams(accountMonitoring);
        String response = HttpUtils.doPostForm(baseUrl + "account/query", params, null);
        JSONObject resultJson = JSONObject.parseObject(response);
        String msg = resultJson.getString("msg");
        if ("1".equals(resultJson.getString("code")) && StringUtils.equals(msg, "ok")) {
            String data = resultJson.getString("data");
            AccountMonitoringQuery accountMonitoringQuery = JSON.parseObject(data, AccountMonitoringQuery.class);
            accountMonitoringQuery.setAccountMonitoringId(accountMonitoring.getId());
            accountMonitoringQuery.setCreateId(SecurityUtils.getUserId());
            accountMonitoringQuery.setCode(resultJson.getInteger("code"));
            accountMonitoringQuery.setMsg(msg);
            accountMonitoringQuery.setTime(resultJson.getString("time"));
            accountMonitoringQueryService.save(accountMonitoringQuery);
            accountMonitoring.setStatus(accountMonitoringQuery.getStatus());
            accountMonitoring.setBindTel(accountMonitoringQuery.getBindTel());
            accountMonitoring.setUpdateId(SecurityUtils.getUserId());
            updateById(accountMonitoring);
            return accountMonitoringQuery;
        } else {
            throw new GlobalException(response);
        }
    }


    Map<String, Object> getAddParams(AccountMonitoring accountMonitoring) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("pwd", pwd);
        params.put("account_number", accountMonitoring.getAccountNumber());
        params.put("account_phone", accountMonitoring.getAccountPhone());
        params.put("check_interval", accountMonitoring.getCheckInterval());
        params.put("interval_type", accountMonitoring.getIntervalType());
        params.put("task_type", accountMonitoring.getTaskType());
        params.put("auto_switch_type", accountMonitoring.getAutoSwitchType());
        params.put("remark", accountMonitoring.getRemark());
        return params;
    }

    Map<String, Object> getUpOrDownParams(AccountMonitoring accountMonitoring, String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("pwd", pwd);
        params.put("checkId", accountMonitoring.getCheckId());
        if (StringUtils.equals(type, "1")) {
            params.put("enabled", 1);
        } else {
            params.put("enabled", 0);
        }
        return params;
    }

    Map<String, Object> getDeleteParams(AccountMonitoring accountMonitoring) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("pwd", pwd);
        params.put("checkId", accountMonitoring.getCheckId());
        return params;
    }

    Map<String, Object> getQueryParams(AccountMonitoring accountMonitoring) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("pwd", pwd);
        params.put("checkId", accountMonitoring.getCheckId());
        return params;
    }

    void initializationParams() {
        if (Objects.isNull(userId) || StringUtils.isEmpty(pwd) || StringUtils.isEmpty(baseUrl)) {
            List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("AccountMonitoring");
            for (SysDictData sysDictData : sysDictDataList) {
                if (sysDictData.getDictLabel().equals("userId")) {
                    userId = Integer.valueOf(sysDictData.getDictValue());
                }
                if (sysDictData.getDictLabel().equals("pwd")) {
                    pwd = sysDictData.getDictValue();
                }
                if (sysDictData.getDictLabel().equals("baseUrl")) {
                    baseUrl = sysDictData.getDictValue();
                }
            }
        }
        if (Objects.isNull(userId) || StringUtils.isEmpty(pwd) || StringUtils.isEmpty(baseUrl)) {
            throw new GlobalException("未配置账号监测的字典信息");
        }
    }

    Long checkUserNums() {
        Long userId = SecurityUtils.getUserId();
        SysUser sysUser = sysUserMapper.selectUserById(userId);
        if (StringUtils.isNull(sysUser)) {
            throw new GlobalException("未找到当前用户");
        }
        if (StringUtils.isNull(sysUser.getCampNumber()) || sysUser.getCampNumber() <= 0) {
            throw new GlobalException("由于系统运营成本较大，自动获取，监控找回接入的是第三方服务，0.3元/次 此功能支持定制 充值微信：【yudu3518】");
        }
        return sysUser.getUserId();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void checkUserNumsAndDown(AccountMonitoring accountMonitoring) {
        Long userId = accountMonitoring.getCreateId();
        if (StringUtils.isNotNull(userId)) {
            SysUser sysUser = sysUserMapper.selectUserById(userId);
            if (StringUtils.isNotNull(sysUser)) {
                if (StringUtils.isNotNull(sysUser.getCampNumber()) && sysUser.getCampNumber() > 0) {
                    Integer integer = sysUser.getCampNumber();
                    int i = integer.intValue() - 1;
                    sysUser.setCampNumber(i);
                    sysUserMapper.upCampNumber(sysUser.getCampNumber(), sysUser.getUserId());
                    System.out.println("i=" + i);
                    if (i > 0) {
                        return;
                    }
                }
            }
        }
        accountMonitoringUpOrDownHD(accountMonitoring, "0");
    }

    public void accountMonitoringUpOrDownHD(AccountMonitoring accountMonitoring, String type) {
        initializationParams();
        Map<String, Object> params = getUpOrDownParams(accountMonitoring, type);
        String response = HttpUtils.doPostForm(baseUrl + "account/switch", params, null);
        JSONObject resultJson = JSONObject.parseObject(response);
        String msg = resultJson.getString("msg");
        if ("1".equals(resultJson.getString("code")) && StringUtils.equals(msg, "ok")) {
            accountMonitoring.setEnabled(type);
            updateById(accountMonitoring);
        }
    }

}
