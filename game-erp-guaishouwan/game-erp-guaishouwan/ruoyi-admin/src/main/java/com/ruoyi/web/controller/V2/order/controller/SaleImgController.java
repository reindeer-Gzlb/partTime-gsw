package com.ruoyi.web.controller.V2.order.controller;

import java.util.List;

import com.ruoyi.system.model.domain.SaleImg;
import com.ruoyi.system.V2.order.service.ISaleImgService;
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
 * 供货商提交的图片
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/account/img")
public class SaleImgController extends BaseController
{
    @Autowired
    private ISaleImgService saleImgService;

    /**
     * 查询备注图片列表
     */
    @PreAuthorize("@ss.hasPermi('account:img:list')")
    @GetMapping("/list")
    public TableDataInfo list(SaleImg saleImg)
    {
        startPage();
        List<SaleImg> list = saleImgService.selectSaleImgList(saleImg);
        return getDataTable(list);
    }

    /**
     * 导出备注图片列表
     */
    @PreAuthorize("@ss.hasPermi('account:img:export')")
    @Log(title = "备注图片", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SaleImg saleImg)
    {
        List<SaleImg> list = saleImgService.selectSaleImgList(saleImg);
        ExcelUtil<SaleImg> util = new ExcelUtil<SaleImg>(SaleImg.class);
        return util.exportExcel(list, "备注图片数据");
    }

    /**
     * 获取备注图片详细信息
     */
    @PreAuthorize("@ss.hasPermi('account:img:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(saleImgService.selectSaleImgById(id));
    }

    /**
     * 新增备注图片
     */
    @PreAuthorize("@ss.hasPermi('account:img:add')")
    @Log(title = "备注图片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SaleImg saleImg)
    {
        return toAjax(saleImgService.insertSaleImg(saleImg));
    }

    /**
     * 修改备注图片
     */
    @PreAuthorize("@ss.hasPermi('account:img:edit')")
    @Log(title = "备注图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SaleImg saleImg)
    {
        return toAjax(saleImgService.updateSaleImg(saleImg));
    }

    /**
     * 删除备注图片
     */
    @PreAuthorize("@ss.hasPermi('account:img:remove')")
    @Log(title = "备注图片", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(saleImgService.deleteSaleImgByIds(ids));
    }
}
