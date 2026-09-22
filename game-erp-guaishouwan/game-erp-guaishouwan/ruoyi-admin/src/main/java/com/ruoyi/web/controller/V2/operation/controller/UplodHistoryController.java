package com.ruoyi.web.controller.V2.operation.controller;

import java.util.List;

import com.ruoyi.system.model.domain.UplodHistory;
import com.ruoyi.system.V2.operation.service.IUplodHistoryService;
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
 * 导入记录Controller
 *
 * @author ruoyi
 * @date 2022-05-16
 */
@RestController
@RequestMapping("/account/uplodHistory")
public class UplodHistoryController extends BaseController
{
    @Autowired
    private IUplodHistoryService uplodHistoryService;

    /**
     * 查询导入记录列表
     */
    @PreAuthorize("@ss.hasPermi('account:history:list')")
    @GetMapping("/list")
    public TableDataInfo list(UplodHistory uplodHistory)
    {
        startPage();
        List<UplodHistory> list = uplodHistoryService.selectUplodHistoryList(uplodHistory);
        return getDataTable(list);
    }

    /**
     * 导出导入记录列表
     */
    @PreAuthorize("@ss.hasPermi('account:history:export')")
    @Log(title = "导入记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(UplodHistory uplodHistory)
    {
        List<UplodHistory> list = uplodHistoryService.selectUplodHistoryList(uplodHistory);
        ExcelUtil<UplodHistory> util = new ExcelUtil<UplodHistory>(UplodHistory.class);
        return util.exportExcel(list, "导入记录数据");
    }

    /**
     * 获取导入记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('account:history:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(uplodHistoryService.selectUplodHistoryById(id));
    }

    /**
     * 新增导入记录
     */
    @PreAuthorize("@ss.hasPermi('account:history:add')")
    @Log(title = "导入记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UplodHistory uplodHistory)
    {
        return toAjax(uplodHistoryService.insertUplodHistory(uplodHistory));
    }

    /**
     * 修改导入记录
     */
    @PreAuthorize("@ss.hasPermi('account:history:edit')")
    @Log(title = "导入记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UplodHistory uplodHistory)
    {
        return toAjax(uplodHistoryService.updateUplodHistory(uplodHistory));
    }

    /**
     * 删除导入记录
     */
    @PreAuthorize("@ss.hasPermi('account:history:remove')")
    @Log(title = "导入记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(uplodHistoryService.deleteUplodHistoryByIds(ids));
    }
}
