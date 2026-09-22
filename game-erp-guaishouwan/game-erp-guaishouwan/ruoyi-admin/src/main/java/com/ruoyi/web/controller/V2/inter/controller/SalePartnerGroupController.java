package com.ruoyi.web.controller.V2.inter.controller;

import java.util.List;

import com.ruoyi.system.V2.inter.service.ISalePartnerGroupService;
import com.ruoyi.system.model.domain.SalePartnerGroup;
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
 * 接口配置Controller
 * 
 * @author ruoyi
 * @date 2023-05-06
 */
@RestController
@RequestMapping("/sale/group")
public class SalePartnerGroupController extends BaseController
{
    @Autowired
    private ISalePartnerGroupService salePartnerGroupService;

    /**
     * 查询接口配置列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SalePartnerGroup salePartnerGroup)
    {
        List<SalePartnerGroup> list = salePartnerGroupService.selectSalePartnerGroupList(salePartnerGroup);
        return getDataTable(list);
    }

    /**
     * 导出接口配置列表
     */
    @Log(title = "接口配置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SalePartnerGroup salePartnerGroup)
    {
        List<SalePartnerGroup> list = salePartnerGroupService.selectSalePartnerGroupList(salePartnerGroup);
        ExcelUtil<SalePartnerGroup> util = new ExcelUtil<SalePartnerGroup>(SalePartnerGroup.class);
        return util.exportExcel(list, "接口配置数据");
    }

    /**
     * 获取接口配置详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(salePartnerGroupService.selectSalePartnerGroupById(id));
    }

    /**
     * 新增接口配置
     */
    @Log(title = "接口配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SalePartnerGroup salePartnerGroup)
    {
        return toAjax(salePartnerGroupService.insertSalePartnerGroup(salePartnerGroup));
    }

    /**
     * 修改接口配置
     */
    @Log(title = "接口配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SalePartnerGroup salePartnerGroup)
    {
        return toAjax(salePartnerGroupService.updateSalePartnerGroup(salePartnerGroup));
    }

    /**
     * 删除接口配置
     */
    @PreAuthorize("@ss.hasPermi('account:group:remove')")
    @Log(title = "接口配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(salePartnerGroupService.deleteSalePartnerGroupByIds(ids));
    }
}
