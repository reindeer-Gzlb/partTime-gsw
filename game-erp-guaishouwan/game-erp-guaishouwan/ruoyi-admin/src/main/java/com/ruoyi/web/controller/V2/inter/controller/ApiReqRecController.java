package com.ruoyi.web.controller.V2.inter.controller;

import java.util.List;

import com.ruoyi.system.model.domain.ApiReqRec;
import com.ruoyi.system.V2.inter.service.IApiReqRecService;
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
 * api请求记录Controller
 * 
 * @author ruoyi
 * @date 2022-10-27
 */
@RestController
@RequestMapping("/account/req/rec")
public class ApiReqRecController extends BaseController
{
    @Autowired
    private IApiReqRecService apiReqRecService;

    /**
     * 查询api请求记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ApiReqRec apiReqRec)
    {
        startPage();
        List<ApiReqRec> list = apiReqRecService.selectApiReqRecList(apiReqRec);
        return getDataTable(list);
    }

    /**
     * 导出api请求记录列表
     */
    @Log(title = "api请求记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ApiReqRec apiReqRec)
    {
        List<ApiReqRec> list = apiReqRecService.selectApiReqRecList(apiReqRec);
        ExcelUtil<ApiReqRec> util = new ExcelUtil<ApiReqRec>(ApiReqRec.class);
        return util.exportExcel(list, "api请求记录数据");
    }

    /**
     * 获取api请求记录详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(apiReqRecService.selectApiReqRecById(id));
    }

    /**
     * 新增api请求记录
     */
    @Log(title = "api请求记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ApiReqRec apiReqRec)
    {
        return toAjax(apiReqRecService.insertApiReqRec(apiReqRec));
    }

    /**
     * 修改api请求记录
     */
    @Log(title = "api请求记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ApiReqRec apiReqRec)
    {
        return toAjax(apiReqRecService.updateApiReqRec(apiReqRec));
    }

    /**
     * 删除api请求记录
     */
    @Log(title = "api请求记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(apiReqRecService.deleteApiReqRecByIds(ids));
    }
}
