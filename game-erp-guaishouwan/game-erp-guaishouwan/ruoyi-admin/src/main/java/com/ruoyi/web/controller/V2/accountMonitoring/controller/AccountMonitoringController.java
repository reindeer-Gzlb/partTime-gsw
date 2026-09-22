package com.ruoyi.web.controller.V2.accountMonitoring.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.accountMonitoring.service.IAccountMonitoringService;
import com.ruoyi.system.model.domain.AccountMonitoring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/account/monitoring")
public class AccountMonitoringController extends BaseController {
    @Autowired
    IAccountMonitoringService accountMonitoringService;

    @GetMapping("/list")
    public TableDataInfo list(AccountMonitoring accountMonitoring) {
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNull(loginUser)) {
            throw new GlobalException("未获取到用户");
        }
        SysUser currentUser = loginUser.getUser();
        Long createId = null;
        // 如果是超级管理员，则不过滤数据
        if (StringUtils.isNotNull(currentUser) && !currentUser.isAdmin()) {
            createId = currentUser.getUserId();
        }
        LambdaQueryWrapper<AccountMonitoring> queryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotEmpty(accountMonitoring.getAccountNumber())) {
            queryWrapper.like(AccountMonitoring::getAccountNumber, accountMonitoring.getAccountNumber());
        }
        if (StringUtils.isNotEmpty(accountMonitoring.getAccountPhone())) {
            queryWrapper.like(AccountMonitoring::getAccountPhone, accountMonitoring.getAccountPhone());
        }
        if (StringUtils.isNotEmpty(accountMonitoring.getEnabled())) {
            queryWrapper.eq(AccountMonitoring::getEnabled, accountMonitoring.getEnabled());
        }
        if (StringUtils.isNotNull(createId)) {
            queryWrapper.eq(AccountMonitoring::getCreateId, createId);
        }
        queryWrapper.eq(AccountMonitoring::getDel, 1);
        queryWrapper.orderByDesc(AccountMonitoring::getCreateTime);
        startPage();
        List<AccountMonitoring> list = accountMonitoringService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     *
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(accountMonitoringService.getById(id));
    }

    /**
     * 新增
     */
    @Log(title = "accountMonitoring", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AccountMonitoring accountMonitoring) {
        return toAjax(accountMonitoringService.saveAndGetCheckId(accountMonitoring));
    }

    /**
     * 上架
     */
    @Log(title = "accountMonitoring", businessType = BusinessType.UPDATE)
    @GetMapping(value = "/up/{id}")
    public AjaxResult up(@PathVariable Long id) {
        return toAjax(accountMonitoringService.accountMonitoringUpOrDown(checkAccountMonitoringById(id), "1"));
    }

    /**
     * 下架
     */
    @Log(title = "accountMonitoring", businessType = BusinessType.UPDATE)
    @GetMapping(value = "/down/{id}")
    public AjaxResult down(@PathVariable Long id) {
        return toAjax(accountMonitoringService.accountMonitoringUpOrDown(checkAccountMonitoringById(id), "0"));
    }

    /**
     * 删除
     */
    @Log(title = "accountMonitoring", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        return toAjax(accountMonitoringService.accountMonitoringDelete(checkAccountMonitoringById(id)));
    }

    /**
     * 手动查询任务
     */
    @Log(title = "accountMonitoring", businessType = BusinessType.DELETE)
    @GetMapping("/query/{id}")
    public AjaxResult accountMonitoringQuery(@PathVariable Long id) {
        return AjaxResult.success(accountMonitoringService.accountMonitoringQuery(checkAccountMonitoringById(id)));
    }

    private AccountMonitoring checkAccountMonitoringById(Long id) {
        if (Objects.isNull(id)) {
            throw new GlobalException("id不能为空");
        }
        AccountMonitoring accountMonitoring = accountMonitoringService.getById(id);
        if (Objects.isNull(accountMonitoring)) {
            throw new GlobalException("未找到此数据");
        }
        if (StringUtils.isEmpty(accountMonitoring.getCheckId())) {
            throw new GlobalException("任务id为空");
        }
        return accountMonitoring;
    }

//    /**
//     * 删除
//     */
//    @Log(title = "accountMonitoring", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids) {
//        return toAjax(accountMonitoringService.removeByIds(Arrays.asList(ids)));
//    }

//    /**
//     *修改
//     */
//    @Log(title = "accountMonitoring", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody Bargain bargain) {
//        return toAjax(accountMonitoringService.updateById(bargain));
//    }
}
