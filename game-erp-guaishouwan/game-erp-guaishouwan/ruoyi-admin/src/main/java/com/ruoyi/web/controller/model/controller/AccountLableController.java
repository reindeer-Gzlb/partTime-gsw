package com.ruoyi.web.controller.model.controller;

import java.util.List;
//import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.model.domain.AccountLable;
import com.ruoyi.system.model.service.IAccountLableService;
//import netscape.javascript.JSObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 账号标签Controller
 *
 * @author ruoyi
 * @date 2022-07-28
 */
@RestController
@RequestMapping("/account/lable")
public class AccountLableController extends BaseController
{
    @Autowired
    private IAccountLableService accountLableService;

    /**
     * 查询账号标签列表
     */
    @PreAuthorize("@ss.hasPermi('account:lable:list')")
    @GetMapping("/list")
    public TableDataInfo list(AccountLable accountLable)
    {
        startPage();
        List<AccountLable> list = accountLableService.selectAccountLableList(accountLable);
        return getDataTable(list);
    }

    /**
     * 导出账号标签列表
     */
    @PreAuthorize("@ss.hasPermi('account:lable:export')")
    @Log(title = "账号标签", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AccountLable accountLable)
    {
        List<AccountLable> list = accountLableService.selectAccountLableList(accountLable);
        ExcelUtil<AccountLable> util = new ExcelUtil<AccountLable>(AccountLable.class);
        return util.exportExcel(list, "账号标签数据");
    }

    /**
     * 获取账号标签详细信息
     */
    @PreAuthorize("@ss.hasPermi('account:lable:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(accountLableService.selectAccountLableById(id));
    }

    /**
     * 新增账号标签
     */
    @Log(title = "账号标签", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody JSONObject jsonObject)
    {
        Long userId = SecurityUtils.getUserId();
        return toAjax(accountLableService.insertAccountLable(jsonObject,userId));
    }
    /**
     * 新增账号标签
     */
    @GetMapping("/oneKeyLable")
    public AjaxResult oneKeyLable()
    {
        Long userId = SecurityUtils.getUserId();
        return toAjax(accountLableService.oneKeyLable(userId));
    }

    /**
     * 修改账号标签
     */
    @PreAuthorize("@ss.hasPermi('account:lable:edit')")
    @Log(title = "账号标签", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AccountLable accountLable)
    {
        return toAjax(accountLableService.updateAccountLable(accountLable));
    }

    /**
     * 删除账号标签
     */
    @PreAuthorize("@ss.hasPermi('account:lable:remove')")
    @Log(title = "账号标签", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(accountLableService.deleteAccountLableByIds(ids));
    }
}
