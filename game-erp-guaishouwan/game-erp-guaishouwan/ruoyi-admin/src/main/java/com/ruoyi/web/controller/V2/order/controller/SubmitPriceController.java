package com.ruoyi.web.controller.V2.order.controller;

import java.util.List;

import com.ruoyi.system.model.domain.SubmitPrice;
import com.ruoyi.system.V2.order.service.ISubmitPriceService;
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
 * 客户端下单Controller
 * 
 * @author ruoyi
 * @date 2023-03-22
 */
@RestController
@RequestMapping("/account/submit/price")
public class SubmitPriceController extends BaseController
{
    @Autowired
    private ISubmitPriceService submitPriceService;

    /**
     * 查询客户端下单列表
     */
    @PreAuthorize("@ss.hasPermi('account:price:list')")
    @GetMapping("/list")
    public TableDataInfo list(SubmitPrice submitPrice)
    {
        startPage();
        List<SubmitPrice> list = submitPriceService.selectSubmitPriceList(submitPrice);
        return getDataTable(list);
    }

    /**
     * 导出客户端下单列表
     */
    @PreAuthorize("@ss.hasPermi('account:price:export')")
    @Log(title = "客户端下单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SubmitPrice submitPrice)
    {
        List<SubmitPrice> list = submitPriceService.selectSubmitPriceList(submitPrice);
        ExcelUtil<SubmitPrice> util = new ExcelUtil<SubmitPrice>(SubmitPrice.class);
        return util.exportExcel(list, "客户端下单数据");
    }

    /**
     * 获取客户端下单详细信息
     */
    @PreAuthorize("@ss.hasPermi('account:price:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(submitPriceService.selectSubmitPriceById(id));
    }

    /**
     * 新增客户端下单
     */
    @PreAuthorize("@ss.hasPermi('account:price:add')")
    @Log(title = "客户端下单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SubmitPrice submitPrice)
    {
        return toAjax(submitPriceService.insertSubmitPrice(submitPrice));
    }

    /**
     * 修改客户端下单
     */
    @PreAuthorize("@ss.hasPermi('account:price:edit')")
    @Log(title = "客户端下单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SubmitPrice submitPrice)
    {
        return toAjax(submitPriceService.updateSubmitPrice(submitPrice));
    }

    /**
     * 删除客户端下单
     */
    @PreAuthorize("@ss.hasPermi('account:price:remove')")
    @Log(title = "客户端下单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(submitPriceService.deleteSubmitPriceByIds(ids));
    }
}
