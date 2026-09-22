package com.ruoyi.web.controller.V2.inter.controller;

import java.util.List;

import com.ruoyi.system.model.domain.InterRec;
import com.ruoyi.system.V2.inter.service.IInterRecService;
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
 * 接口记录Controller
 *
 * @author ruoyi
 * @date 2022-06-23
 */
@RestController
@RequestMapping("/inter/rec")
public class InterRecController extends BaseController
{
    @Autowired
    private IInterRecService interRecService;

    /**
     * 查询接口记录列表
     */
    @PreAuthorize("@ss.hasPermi('account:rec:list')")
    @GetMapping("/list")
    public TableDataInfo list(InterRec interRec)
    {
        startPage();
        List<InterRec> list = interRecService.selectInterRecList(interRec);
        return getDataTable(list);
    }

    /**
     * 导出接口记录列表
     */
    @PreAuthorize("@ss.hasPermi('account:rec:export')")
    @Log(title = "接口记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(InterRec interRec)
    {
        List<InterRec> list = interRecService.selectInterRecList(interRec);
        ExcelUtil<InterRec> util = new ExcelUtil<InterRec>(InterRec.class);
        return util.exportExcel(list, "接口记录数据");
    }

    /**
     * 获取接口记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('account:rec:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(interRecService.selectInterRecById(id));
    }

    /**
     * 新增接口记录
     */
    @PreAuthorize("@ss.hasPermi('account:rec:add')")
    @Log(title = "接口记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody InterRec interRec)
    {
        return toAjax(interRecService.insertInterRec(interRec));
    }

    /**
     * 修改接口记录
     */
    @PreAuthorize("@ss.hasPermi('account:rec:edit')")
    @Log(title = "接口记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InterRec interRec)
    {
        return toAjax(interRecService.updateInterRec(interRec));
    }

    /**
     * 删除接口记录
     */
    @PreAuthorize("@ss.hasPermi('account:rec:remove')")
    @Log(title = "接口记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(interRecService.deleteInterRecByIds(ids));
    }
}
