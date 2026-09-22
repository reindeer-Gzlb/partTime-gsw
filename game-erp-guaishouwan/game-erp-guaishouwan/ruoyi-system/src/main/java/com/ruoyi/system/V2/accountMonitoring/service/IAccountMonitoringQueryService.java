package com.ruoyi.system.V2.accountMonitoring.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.model.domain.AccountMonitoringQuery;

/**
 * @author zyy
 * @date 2023-05-05
 */
public interface IAccountMonitoringQueryService extends IService<AccountMonitoringQuery> {
    void saveAccountMonitoringQueryAndMonitoring(String backStr);
}
