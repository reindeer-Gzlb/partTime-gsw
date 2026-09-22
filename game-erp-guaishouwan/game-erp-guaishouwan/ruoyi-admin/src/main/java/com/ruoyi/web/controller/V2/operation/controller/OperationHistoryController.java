package com.ruoyi.web.controller.V2.operation.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.V2.operation.service.IOperationHistoryService;
import com.ruoyi.system.model.domain.OperationHistory;
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
import com.ruoyi.common.core.page.TableDataInfo;

import java.util.List;

/**
 * 操作历史记录Controller
 *
 * @author ruoyi
 * @date 2022-05-16
 */
@RestController
@RequestMapping("/account/history")
public class OperationHistoryController extends BaseController
{
    @Autowired
    private IOperationHistoryService operationHistoryService;

    /**
     * 查询操作历史记录列表
     */
    @PreAuthorize("@ss.hasPermi('account:history:list')")
    @GetMapping("/list")
    public TableDataInfo list(OperationHistory operationHistory)
    {
        startPage();
        List<OperationHistory> list = operationHistoryService.selectOperationHistoryList(operationHistory);
        return getDataTable(list);
    }

    /**
     * 导出操作历史记录列表
     */
    @PreAuthorize("@ss.hasPermi('account:history:export')")
    @Log(title = "操作历史记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(OperationHistory operationHistory)
    {
        List<OperationHistory> list = operationHistoryService.selectOperationHistoryList(operationHistory);
        ExcelUtil<OperationHistory> util = new ExcelUtil<OperationHistory>(OperationHistory.class);
        return util.exportExcel(list, "操作历史记录数据");
    }

    /**
     * 获取操作历史记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('account:history:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(operationHistoryService.selectOperationHistoryById(id));
    }

    /**
     * 新增操作历史记录
     */
    @PreAuthorize("@ss.hasPermi('account:history:add')")
    @Log(title = "操作历史记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OperationHistory operationHistory)
    {
        return toAjax(operationHistoryService.insertOperationHistory(operationHistory));
    }

    /**
     * 修改操作历史记录
     */
    @PreAuthorize("@ss.hasPermi('account:history:edit')")
    @Log(title = "操作历史记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OperationHistory operationHistory)
    {
        return toAjax(operationHistoryService.updateOperationHistory(operationHistory));
    }

    /**
     * 删除操作历史记录
     */
    @PreAuthorize("@ss.hasPermi('account:history:remove')")
    @Log(title = "操作历史记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(operationHistoryService.deleteOperationHistoryByIds(ids));
    }


    @Log(title = "操作历史状态", businessType = BusinessType.OTHER)
    @GetMapping("/selectOperationStatus")
    public AjaxResult selectOperationStatus(){
        return AjaxResult.success(operationHistoryService.selectOperationStatus());
    }
}
