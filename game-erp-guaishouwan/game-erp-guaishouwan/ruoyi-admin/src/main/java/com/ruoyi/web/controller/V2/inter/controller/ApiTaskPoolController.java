package com.ruoyi.web.controller.V2.inter.controller;

import java.util.List;

import com.ruoyi.system.model.domain.ApiTaskPool;
import com.ruoyi.system.V2.inter.service.IApiTaskPoolService;
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
 * 【请填写功能名称】Controller
 *
 * @author ruoyi
 * @date 2022-09-28
 */
@RestController
@RequestMapping("/task/pool")
public class ApiTaskPoolController extends BaseController
{
    @Autowired
    private IApiTaskPoolService apiTaskPoolService;

    /**
     * 查询【请填写功能名称】列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ApiTaskPool apiTaskPool)
    {
        startPage();
        List<ApiTaskPool> list = apiTaskPoolService.selectApiTaskPoolList(apiTaskPool);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ApiTaskPool apiTaskPool)
    {
        List<ApiTaskPool> list = apiTaskPoolService.selectApiTaskPoolList(apiTaskPool);
        ExcelUtil<ApiTaskPool> util = new ExcelUtil<ApiTaskPool>(ApiTaskPool.class);
        return util.exportExcel(list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(apiTaskPoolService.selectApiTaskPoolById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ApiTaskPool apiTaskPool)
    {
        return toAjax(apiTaskPoolService.insertApiTaskPool(apiTaskPool));
    }

    /**
     * 修改【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ApiTaskPool apiTaskPool)
    {
        return toAjax(apiTaskPoolService.updateApiTaskPool(apiTaskPool));
    }

    /**
     * 删除【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(apiTaskPoolService.deleteApiTaskPoolByIds(ids));
    }
}
