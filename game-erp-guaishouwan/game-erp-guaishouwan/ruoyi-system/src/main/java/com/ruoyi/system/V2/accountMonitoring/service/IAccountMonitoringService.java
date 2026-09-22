package com.ruoyi.system.V2.accountMonitoring.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.model.domain.AccountMonitoring;
import com.ruoyi.system.model.domain.AccountMonitoringQuery;

/**
 * @author zyy
 * @date 2023-05-05
 */
public interface IAccountMonitoringService extends IService<AccountMonitoring> {
    /**
     * 保存监测信息并对接破晓
     *
     * @param accountMonitoring
     * @return
     */
    boolean saveAndGetCheckId(AccountMonitoring accountMonitoring);

    /**
     * 上架或者下架 监测
     *
     * @param accountMonitoring
     * @return
     */
    boolean accountMonitoringUpOrDown(AccountMonitoring accountMonitoring, String type);

    /**
     * 删除监测信息
     *
     * @param accountMonitoring
     * @return
     */
    boolean accountMonitoringDelete(AccountMonitoring accountMonitoring);

    /**
     * 根据监测信息查询最新情况
     * @param accountMonitoring
     * @return
     */
    AccountMonitoringQuery accountMonitoringQuery(AccountMonitoring accountMonitoring);

    /**
     * 监测用户次数  并下架
     * @param accountMonitoring
     */
    void checkUserNumsAndDown(AccountMonitoring accountMonitoring);
}
