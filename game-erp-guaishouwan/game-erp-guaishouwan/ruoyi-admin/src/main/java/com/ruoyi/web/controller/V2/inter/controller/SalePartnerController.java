package com.ruoyi.web.controller.V2.inter.controller;

import java.util.List;

import com.ruoyi.system.model.domain.SalePartner;
import com.ruoyi.system.V2.inter.service.ISalePartnerService;
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
 * 销售伙伴Controller
 *
 * @author ruoyi
 * @date 2022-06-07
 */
@RestController
@RequestMapping("/partner/partner")
public class SalePartnerController extends BaseController
{
    @Autowired
    private ISalePartnerService salePartnerService;

    /**
     * 查询销售伙伴列表
     */
    @PreAuthorize("@ss.hasPermi('partner:partner:list')")
    @GetMapping("/list")
    public TableDataInfo list(SalePartner salePartner)
    {
        startPage();
        List<SalePartner> list = salePartnerService.selectSalePartnerList(salePartner);
        return getDataTable(list);
    }
    /**
     * 查询销售伙伴列表
     */
    @GetMapping("/allList")
    public TableDataInfo allList(SalePartner salePartner)
    {
        List<SalePartner> list = salePartnerService.selectSalePartnerList(salePartner);
        return getDataTable(list);
    }

    /**
     * 导出销售伙伴列表
     */
    @PreAuthorize("@ss.hasPermi('partner:partner:export')")
    @Log(title = "销售伙伴", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SalePartner salePartner)
    {
        List<SalePartner> list = salePartnerService.selectSalePartnerList(salePartner);
        ExcelUtil<SalePartner> util = new ExcelUtil<SalePartner>(SalePartner.class);
        return util.exportExcel(list, "销售伙伴数据");
    }

    /**
     * 获取销售伙伴详细信息
     */
    @PreAuthorize("@ss.hasPermi('partner:partner:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(salePartnerService.selectSalePartnerById(id));
    }

    /**
     * 新增销售伙伴
     */
    @PreAuthorize("@ss.hasPermi('partner:partner:add')")
    @Log(title = "销售伙伴", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SalePartner salePartner)
    {
        return toAjax(salePartnerService.insertSalePartner(salePartner));
    }

    /**
     * 修改销售伙伴
     */
    @PreAuthorize("@ss.hasPermi('partner:partner:edit')")
    @Log(title = "销售伙伴", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SalePartner salePartner)
    {
        return toAjax(salePartnerService.updateSalePartner(salePartner));
    }

    /**
     * 删除销售伙伴
     */
    @PreAuthorize("@ss.hasPermi('partner:partner:remove')")
    @Log(title = "销售伙伴", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(salePartnerService.deleteSalePartnerByIds(ids));
    }
}
