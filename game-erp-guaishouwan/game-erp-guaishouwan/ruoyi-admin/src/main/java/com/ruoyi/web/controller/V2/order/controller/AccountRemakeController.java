package com.ruoyi.web.controller.V2.order.controller;

import java.util.List;

import com.ruoyi.system.model.domain.AccountRemake;
import com.ruoyi.system.V2.order.service.IAccountRemakeService;
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
 * 销售订单备注Controller
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/account/remake")
public class AccountRemakeController extends BaseController
{
    @Autowired
    private IAccountRemakeService accountRemakeService;

    /**
     * 查询销售订单备注列表
     */
    @PreAuthorize("@ss.hasPermi('account:remake:list')")
    @GetMapping("/list")
    public TableDataInfo list(AccountRemake accountRemake)
    {
        startPage();
        List<AccountRemake> list = accountRemakeService.selectAccountRemakeList(accountRemake);
        return getDataTable(list);
    }

    /**
     * 导出销售订单备注列表
     */
    @PreAuthorize("@ss.hasPermi('account:remake:export')")
    @Log(title = "销售订单备注", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AccountRemake accountRemake)
    {
        List<AccountRemake> list = accountRemakeService.selectAccountRemakeList(accountRemake);
        ExcelUtil<AccountRemake> util = new ExcelUtil<AccountRemake>(AccountRemake.class);
        return util.exportExcel(list, "销售订单备注数据");
    }

    /**
     * 获取销售订单备注详细信息
     */
    @PreAuthorize("@ss.hasPermi('account:remake:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(accountRemakeService.selectAccountRemakeById(id));
    }

    /**
     * 新增销售订单备注
     */
    @PreAuthorize("@ss.hasPermi('account:remake:add')")
    @Log(title = "销售订单备注", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AccountRemake accountRemake)
    {
        return toAjax(accountRemakeService.insertAccountRemake(accountRemake));
    }

    /**
     * 修改销售订单备注
     */
    @PreAuthorize("@ss.hasPermi('account:remake:edit')")
    @Log(title = "销售订单备注", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AccountRemake accountRemake)
    {
        return toAjax(accountRemakeService.updateAccountRemake(accountRemake));
    }

    /**
     * 删除销售订单备注
     */
    @PreAuthorize("@ss.hasPermi('account:remake:remove')")
    @Log(title = "销售订单备注", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(accountRemakeService.deleteAccountRemakeByIds(ids));
    }
}
