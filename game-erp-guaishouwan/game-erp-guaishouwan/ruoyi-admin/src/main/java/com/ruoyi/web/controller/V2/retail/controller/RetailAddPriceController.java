package com.ruoyi.web.controller.V2.retail.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.model.domain.RetailAddPrice;
import com.ruoyi.system.V2.retail.service.IRetailAddPriceService;
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
 * 分销加价Controller
 *
 * @author lz
 * @date 2022-09-01
 */
@RestController
@RequestMapping("/account/rprice")
public class RetailAddPriceController extends BaseController
{
    @Autowired
    private IRetailAddPriceService retailAddPriceService;


    /**
     * 查询分销加价列表
     */
    @GetMapping("/list")
    public TableDataInfo list(RetailAddPrice retailAddPrice)
    {
        startPage();
        List<RetailAddPrice> list = retailAddPriceService.selectRetailAddPriceList(retailAddPrice);
        return getDataTable(list);
    }


    /**
     * 查询分销加价回显
     */
    @GetMapping("/show")
    public AjaxResult show()
    {
        JSONObject result = retailAddPriceService.show();
        return AjaxResult.success(result);
    }

    /**
     * 导出分销加价列表
     */
    @Log(title = "分销加价", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RetailAddPrice retailAddPrice)
    {
        List<RetailAddPrice> list = retailAddPriceService.selectRetailAddPriceList(retailAddPrice);
        ExcelUtil<RetailAddPrice> util = new ExcelUtil<RetailAddPrice>(RetailAddPrice.class);
        return util.exportExcel(list, "分销加价数据");
    }
    /**
     * 未增加的供货商
     */
    @Log(title = "未增加的供货商", businessType = BusinessType.EXPORT)
    @GetMapping("/noAddRetailSuppply")
    public AjaxResult noAddRetailSuppply()
    {
        List<String> list = retailAddPriceService.noAddRetailSuppply();
        return AjaxResult.success(list);
    }

    /**
     * 获取分销加价详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(retailAddPriceService.selectRetailAddPriceById(id));
    }

    /**
     * 新增分销加价
     */
    @Log(title = "分销加价", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RetailAddPrice retailAddPrice)
    {
        return toAjax(retailAddPriceService.insertRetailAddPrice(retailAddPrice));
    }

    /**
     * 修改分销加价
     */
    @Log(title = "分销加价", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RetailAddPrice retailAddPrice)
    {
        return toAjax(retailAddPriceService.updateRetailAddPrice(retailAddPrice));
    }

    /**
     * 删除分销加价
     */
    @Log(title = "分销加价", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(retailAddPriceService.deleteRetailAddPriceByIds(ids));
    }
    /**
     * 删除分销加价
     */
    @Log(title = "折扣排行", businessType = BusinessType.DELETE)
	@GetMapping("/disrank")
    public AjaxResult disrank()
    {
        List<Map<String,Object>> result = retailAddPriceService.disrank();
        return AjaxResult.success(result);
    }
}
