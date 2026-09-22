package com.ruoyi.web.controller.V2.recycle.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.model.domain.Rateof;
import com.ruoyi.system.V2.recycle.service.IRateofService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 折扣率Controller
 * 
 * @author ruoyi
 * @date 2022-12-11
 */
@RestController
@RequestMapping("/account/rateof")
public class RateofController extends BaseController
{
    @Autowired
    private IRateofService rateofService;

    /**
     * 查询折扣率列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Rateof rateof)
    {
        startPage();
        List<Rateof> list = rateofService.selectRateofList(rateof);
        return getDataTable(list);
    }

    /**
     * 导出折扣率列表
     */
    @Log(title = "折扣率", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Rateof rateof)
    {
        List<Rateof> list = rateofService.selectRateofList(rateof);
        ExcelUtil<Rateof> util = new ExcelUtil<Rateof>(Rateof.class);
        return util.exportExcel(list, "折扣率数据");
    }

    /**
     * 获取折扣率详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(rateofService.selectRateofById(id));
    }

    /**
     * 新增折扣率
     */
    @Log(title = "折扣率", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Rateof rateof)
    {
        return toAjax(rateofService.insertRateof(rateof));
    }

    /**
     * 修改折扣率
     */
    @Log(title = "折扣率", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Rateof rateof)
    {
        return toAjax(rateofService.updateRateof(rateof));
    }

    /**
     * 删除折扣率
     */
    @Log(title = "折扣率", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(rateofService.deleteRateofByIds(ids));
    }
}
