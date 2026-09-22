package com.ruoyi.web.controller.V2.tool.controller;

import java.util.List;

import com.ruoyi.system.model.domain.ErrorInfo;
import com.ruoyi.system.V2.tool.service.IErrorInfoService;
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
 * 错误信息Controller
 * 
 * @author ruoyi
 * @date 2023-04-18
 */
@RestController
@RequestMapping("/account/err/info")
public class ErrorInfoController extends BaseController
{
    @Autowired
    private IErrorInfoService errorInfoService;

    /**
     * 查询错误信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErrorInfo errorInfo)
    {
        startPage();
        List<ErrorInfo> list = errorInfoService.selectErrorInfoList(errorInfo);
        return getDataTable(list);
    }

    /**
     * 导出错误信息列表
     */
    @Log(title = "错误信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ErrorInfo errorInfo)
    {
        List<ErrorInfo> list = errorInfoService.selectErrorInfoList(errorInfo);
        ExcelUtil<ErrorInfo> util = new ExcelUtil<ErrorInfo>(ErrorInfo.class);
        return util.exportExcel(list, "错误信息数据");
    }

    /**
     * 获取错误信息详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(errorInfoService.selectErrorInfoById(id));
    }

    /**
     * 新增错误信息
     */
    @Log(title = "错误信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErrorInfo errorInfo)
    {
        return toAjax(errorInfoService.insertErrorInfo(errorInfo));
    }

    /**
     * 修改错误信息
     */
    @Log(title = "错误信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErrorInfo errorInfo)
    {
        return toAjax(errorInfoService.updateErrorInfo(errorInfo));
    }

    /**
     * 删除错误信息
     */
    @Log(title = "错误信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(errorInfoService.deleteErrorInfoByIds(ids));
    }
}
