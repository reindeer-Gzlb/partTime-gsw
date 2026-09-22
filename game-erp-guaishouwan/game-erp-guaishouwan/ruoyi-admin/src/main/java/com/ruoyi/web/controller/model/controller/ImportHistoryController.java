package com.ruoyi.web.controller.model.controller;

import java.util.List;

import com.ruoyi.system.model.domain.ImportHistory;
import com.ruoyi.system.model.service.IImportHistoryService;
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
 * 导入记录
 *
 * @author ruoyi
 * @date 2022-05-26
 */
@RestController
@RequestMapping("/account/importHistory")
public class ImportHistoryController extends BaseController
{
    @Autowired
    private IImportHistoryService importHistoryService;

    /**
     * 查询联调记录列表
     */
    @PreAuthorize("@ss.hasPermi('account:history:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImportHistory importHistory)
    {
        startPage();
        List<ImportHistory> list = importHistoryService.selectImportHistoryList(importHistory);
        return getDataTable(list);
    }

    /**
     * 导出联调记录列表
     */
    @PreAuthorize("@ss.hasPermi('account:history:export')")
    @Log(title = "联调记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ImportHistory importHistory)
    {
        List<ImportHistory> list = importHistoryService.selectImportHistoryList(importHistory);
        ExcelUtil<ImportHistory> util = new ExcelUtil<ImportHistory>(ImportHistory.class);
        return util.exportExcel(list, "联调记录数据");
    }

    /**
     * 获取联调记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('account:history:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(importHistoryService.selectImportHistoryById(id));
    }

    /**
     * 新增联调记录
     */
    @PreAuthorize("@ss.hasPermi('account:history:add')")
    @Log(title = "联调记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImportHistory importHistory)
    {
        return toAjax(importHistoryService.insertImportHistory(importHistory));
    }

    /**
     * 修改联调记录
     */
    @PreAuthorize("@ss.hasPermi('account:history:edit')")
    @Log(title = "联调记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImportHistory importHistory)
    {
        return toAjax(importHistoryService.updateImportHistory(importHistory));
    }

    /**
     * 删除联调记录
     */
    @PreAuthorize("@ss.hasPermi('account:history:remove')")
    @Log(title = "联调记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(importHistoryService.deleteImportHistoryByIds(ids));
    }
}
