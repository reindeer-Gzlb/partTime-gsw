package com.ruoyi.web.controller.V2.tool.controller;

import java.util.List;

import com.ruoyi.system.model.domain.TelUnbind;
import com.ruoyi.system.V2.tool.service.ITelUnbindService;
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
 * 检测任务池Controller
 * 
 * @author ruoyi
 * @date 2023-04-19
 */
@RestController
@RequestMapping("/account/unbind")
public class TelUnbindController extends BaseController
{
    @Autowired
    private ITelUnbindService telUnbindService;

    /**
     * 查询检测任务池列表
     */
    @GetMapping("/list")
    public TableDataInfo list(TelUnbind telUnbind)
    {
        startPage();
        List<TelUnbind> list = telUnbindService.selectTelUnbindList(telUnbind);
        return getDataTable(list);
    }

    /**
     * 导出检测任务池列表
     */
    @Log(title = "检测任务池", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TelUnbind telUnbind)
    {
        List<TelUnbind> list = telUnbindService.selectTelUnbindList(telUnbind);
        ExcelUtil<TelUnbind> util = new ExcelUtil<TelUnbind>(TelUnbind.class);
        return util.exportExcel(list, "检测任务池数据");
    }

    /**
     * 获取检测任务池详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(telUnbindService.selectTelUnbindById(id));
    }

    /**
     * 新增检测任务池
     */
    @Log(title = "检测任务池", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TelUnbind telUnbind)
    {
        return toAjax(telUnbindService.insertTelUnbind(telUnbind));
    }

    /**
     * 修改检测任务池
     */
    @Log(title = "检测任务池", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TelUnbind telUnbind)
    {
        return toAjax(telUnbindService.updateTelUnbind(telUnbind));
    }

    /**
     * 删除检测任务池
     */
    @Log(title = "检测任务池", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(telUnbindService.deleteTelUnbindByIds(ids));
    }
}
