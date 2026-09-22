package com.ruoyi.system.checkReal.controller;

import java.util.List;
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
import com.ruoyi.system.checkReal.domain.CheckRealInfo;
import com.ruoyi.system.checkReal.service.ICheckRealInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 实名记录Controller
 * 
 * @author ruoyi
 * @date 2024-11-15
 */
@RestController
@RequestMapping("/check/real")
public class CheckRealInfoController extends BaseController
{
    @Autowired
    private ICheckRealInfoService checkRealInfoService;

    /**
     * 查询实名记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(CheckRealInfo checkRealInfo)
    {
        startPage();
        List<CheckRealInfo> list = checkRealInfoService.selectCheckRealInfoList(checkRealInfo);
        return getDataTable(list);
    }

    /**
     * 导出实名记录列表
     */
    @Log(title = "实名记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CheckRealInfo checkRealInfo)
    {
        List<CheckRealInfo> list = checkRealInfoService.selectCheckRealInfoList(checkRealInfo);
        ExcelUtil<CheckRealInfo> util = new ExcelUtil<CheckRealInfo>(CheckRealInfo.class);
        return util.exportExcel(list, "实名记录数据");
    }

    /**
     * 获取实名记录详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(checkRealInfoService.selectCheckRealInfoById(id));
    }

    /**
     * 新增实名记录
     */
    @Log(title = "实名记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CheckRealInfo checkRealInfo)
    {
        return toAjax(checkRealInfoService.insertCheckRealInfo(checkRealInfo));
    }

    /**
     * 修改实名记录
     */
    @Log(title = "实名记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CheckRealInfo checkRealInfo)
    {
        return toAjax(checkRealInfoService.updateCheckRealInfo(checkRealInfo));
    }

    /**
     * 删除实名记录
     */
    @Log(title = "实名记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(checkRealInfoService.deleteCheckRealInfoByIds(ids));
    }
}
