package com.ruoyi.web.controller.model.controller;

import java.util.List;

import com.ruoyi.system.model.domain.AddPrice;
import com.ruoyi.system.model.service.IAddPriceService;
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
 * 批量加价Controller
 *
 * @author ruoyi
 * @date 2022-07-17
 */
@RestController
@RequestMapping("/account/price")
public class AddPriceController extends BaseController
{
    @Autowired
    private IAddPriceService addPriceService;

    /**
     * 查询批量加价列表
     */
    @PreAuthorize("@ss.hasPermi('account:price:list')")
    @GetMapping("/list")
    public TableDataInfo list(AddPrice addPrice)
    {
        startPage();
        List<AddPrice> list = addPriceService.selectAddPriceList(addPrice);
        return getDataTable(list);
    }

    /**
     * 导出批量加价列表
     */
    @PreAuthorize("@ss.hasPermi('account:price:export')")
    @Log(title = "批量加价", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AddPrice addPrice)
    {
        List<AddPrice> list = addPriceService.selectAddPriceList(addPrice);
        ExcelUtil<AddPrice> util = new ExcelUtil<AddPrice>(AddPrice.class);
        return util.exportExcel(list, "批量加价数据");
    }

    /**
     * 获取批量加价详细信息
     */
    @PreAuthorize("@ss.hasPermi('account:price:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(addPriceService.selectAddPriceById(id));
    }

    /**
     * 新增批量加价
     */
    @PreAuthorize("@ss.hasPermi('account:price:add')")
    @Log(title = "批量加价", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AddPrice addPrice)
    {
        return toAjax(addPriceService.insertAddPrice(addPrice));
    }

    /**
     * 修改批量加价
     */
    @PreAuthorize("@ss.hasPermi('account:price:edit')")
    @Log(title = "批量加价", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AddPrice addPrice)
    {
        return toAjax(addPriceService.updateAddPrice(addPrice));
    }

    /**
     * 删除批量加价
     */
    @PreAuthorize("@ss.hasPermi('account:price:remove')")
    @Log(title = "批量加价", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(addPriceService.deleteAddPriceByIds(ids));
    }
}
