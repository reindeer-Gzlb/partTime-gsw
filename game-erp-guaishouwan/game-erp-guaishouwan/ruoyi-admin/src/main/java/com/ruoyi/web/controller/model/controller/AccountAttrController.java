package com.ruoyi.web.controller.model.controller;

import java.util.List;

import com.ruoyi.system.model.domain.AccountAttr;
import com.ruoyi.system.model.service.IAccountAttrService;
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
 * 游戏属性Controller
 * 
 * @author ruoyi
 * @date 2023-02-22
 */
@RestController
@RequestMapping("/account/attr")
public class AccountAttrController extends BaseController
{
    @Autowired
    private IAccountAttrService accountAttrService;

    /**
     * 查询游戏属性列表
     */
    @PreAuthorize("@ss.hasPermi('account:attr:list')")
    @GetMapping("/list")
    public TableDataInfo list(AccountAttr accountAttr)
    {
        startPage();
        List<AccountAttr> list = accountAttrService.selectAccountAttrList(accountAttr);
        return getDataTable(list);
    }

    /**
     * 导出游戏属性列表
     */
    @PreAuthorize("@ss.hasPermi('account:attr:export')")
    @Log(title = "游戏属性", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AccountAttr accountAttr)
    {
        List<AccountAttr> list = accountAttrService.selectAccountAttrList(accountAttr);
        ExcelUtil<AccountAttr> util = new ExcelUtil<AccountAttr>(AccountAttr.class);
        return util.exportExcel(list, "游戏属性数据");
    }

    /**
     * 获取游戏属性详细信息
     */
    @PreAuthorize("@ss.hasPermi('account:attr:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(accountAttrService.selectAccountAttrById(id));
    }

    /**
     * 新增游戏属性
     */
    @PreAuthorize("@ss.hasPermi('account:attr:add')")
    @Log(title = "游戏属性", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AccountAttr accountAttr)
    {
        return toAjax(accountAttrService.insertAccountAttr(accountAttr));
    }

    /**
     * 修改游戏属性
     */
    @PreAuthorize("@ss.hasPermi('account:attr:edit')")
    @Log(title = "游戏属性", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AccountAttr accountAttr)
    {
        return toAjax(accountAttrService.updateAccountAttr(accountAttr));
    }

    /**
     * 删除游戏属性
     */
    @PreAuthorize("@ss.hasPermi('account:attr:remove')")
    @Log(title = "游戏属性", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(accountAttrService.deleteAccountAttrByIds(ids));
    }

    /**
     * 查询属性配置列表
     */
    @GetMapping("/getAttrByType")
    public AjaxResult getAttrByType(AccountAttr accountAttr)
    {
        AccountAttr attr = accountAttrService.getAttrByType(accountAttr);
        return AjaxResult.success(attr);
    }
}
