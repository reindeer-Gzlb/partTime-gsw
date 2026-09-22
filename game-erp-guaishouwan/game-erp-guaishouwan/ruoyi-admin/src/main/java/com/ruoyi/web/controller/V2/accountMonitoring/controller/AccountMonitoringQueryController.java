package com.ruoyi.web.controller.V2.accountMonitoring.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.accountMonitoring.mapper.MonitoringErrMapper;
import com.ruoyi.system.V2.accountMonitoring.service.IAccountMonitoringQueryService;
import com.ruoyi.system.V2.accountMonitoring.service.IAccountMonitoringService;
import com.ruoyi.system.model.domain.AccountMonitoringQuery;
import com.ruoyi.system.model.domain.MonitoringErr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/account/monitoring/query")
public class AccountMonitoringQueryController extends BaseController {
    @Autowired
    IAccountMonitoringQueryService accountMonitoringQueryService;
    @Autowired
    IAccountMonitoringService accountMonitoringService;
    @Autowired
    MonitoringErrMapper monitoringErrMapper;

    @GetMapping("/list")
    public TableDataInfo list(Long accountMonitoringId) {
        if (Objects.isNull(accountMonitoringId)) {
            throw new GlobalException("监测任务id不能为空");
        }
        startPage();
        List<AccountMonitoringQuery> list =
                accountMonitoringQueryService.list(new LambdaQueryWrapper<AccountMonitoringQuery>()
                        .eq(AccountMonitoringQuery::getAccountMonitoringId, accountMonitoringId)
                        .orderByDesc(AccountMonitoringQuery::getCreateTime));
        return getDataTable(list);
    }

    @PostMapping("/callBack")
    public void callBack(@RequestParam Map<String, Object> map) {
        System.out.println("账号监测找回进入了回调");
        String backStr = "";
        String keyValue = "";
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (StringUtils.equals(entry.getKey(), "key")) {
                keyValue = String.valueOf(entry.getValue());
            } else {
                backStr = entry.getKey();
            }
        }

        if (StringUtils.isEmpty(backStr)) {
            MonitoringErr monitoringErr = new MonitoringErr();
            monitoringErr.setErrText("账号监测获取字符串为空,请管理员检查" + map);
            monitoringErrMapper.insert(monitoringErr);
            return;
        }
        if (!StringUtils.equals(keyValue, "147258369")) {
            MonitoringErr monitoringErr = new MonitoringErr();
            monitoringErr.setErrText("账号监测地址?后的key设置错误,请管理员设置" + map);
            monitoringErrMapper.insert(monitoringErr);
            return;
        }
        accountMonitoringQueryService.saveAccountMonitoringQueryAndMonitoring(backStr);
    }
//    /**
//     *
//     */
//    @GetMapping(value = "/{id}")
//    public AjaxResult getInfo(@PathVariable("id") Long id) {
//        return AjaxResult.success(accountMonitoringQueryService.getById(id));
//    }
//
//    /**
//     * 新增
//     */
//    @Log(title = "accountMonitoring", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody AccountMonitoringQuery accountMonitoringQuery) {
//        return toAjax(accountMonitoringQueryService.save(accountMonitoringQuery));
//    }

//    /**
//     *修改
//     */
//    @Log(title = "accountMonitoring", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody Bargain bargain) {
//        return toAjax(accountMonitoringService.updateById(bargain));
//    }

//    /**
//     * 删除BargainOrder
//     */
//    @Log(title = "accountMonitoring", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids) {
//        return toAjax(customerAuditService.removeByIds(Arrays.asList(ids)));
//    }

}
