package com.ruoyi.web.controller.V2.order.controller;

import java.util.List;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.model.domain.AccountOrder;
import com.ruoyi.system.V2.order.service.IAccountOrderService;
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
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 销售订单Controller
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/account/order")
public class AccountOrderController extends BaseController
{
    @Autowired
    private IAccountOrderService accountOrderService;

    /**
     * 查询销售订单列表
     */
    @GetMapping("/list")
    public TableDataInfo list(AccountOrder accountOrder)
    {
        startPage();
        List<AccountOrder> list = accountOrderService.selectAccountOrderList(accountOrder);
        return getDataTable(list);
    }

    /**
     * 导出销售订单列表
     */
    @Log(title = "销售订单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AccountOrder accountOrder)
    {
        List<AccountOrder> list = accountOrderService.selectAccountOrderList(accountOrder);
        ExcelUtil<AccountOrder> util = new ExcelUtil<AccountOrder>(AccountOrder.class);
        return util.exportExcel(list, "销售订单数据");
    }

    /**
     * 获取销售订单详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(accountOrderService.selectAccountOrderById(id));
    }

    /**
     * 获取销售订单详细信息
     */
    @GetMapping(value = "account/{id}")
    public AjaxResult getInfoByAccount(@PathVariable("id") Long id)
    {
        return AjaxResult.success(accountOrderService.getInfoByAccount(id));
    }

    /**
     * 新增销售订单
     */
    @Log(title = "销售订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AccountOrder accountOrder)
    {
        return toAjax(accountOrderService.insertAccountOrder(accountOrder));
    }

    /**
     * 修改销售订单
     */
    @Log(title = "销售订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AccountOrder accountOrder)
    {
        return toAjax(accountOrderService.updateAccountOrder(accountOrder));
    }

    /**
     * 删除销售订单
     */
    @Log(title = "销售订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(accountOrderService.deleteAccountOrderByIds(ids));
    }
}
