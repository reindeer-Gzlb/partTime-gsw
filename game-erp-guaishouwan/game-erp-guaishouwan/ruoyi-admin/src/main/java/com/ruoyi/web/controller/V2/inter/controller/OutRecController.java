package com.ruoyi.web.controller.V2.inter.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.model.domain.OutRec;
import com.ruoyi.system.V2.inter.service.IOutRecService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 接口推送记录
 *
 * @author ruoyi
 * @date 2022-06-07
 */
@RestController
@RequestMapping("/account/rec")
public class OutRecController extends BaseController
{
    @Autowired
    private IOutRecService outRecService;

    /**
     * 清空推送记录
     */
    @GetMapping("/delAllOut")
    public AjaxResult delAllOut(@RequestParam Map<String,Object> params){
        String saleCode = MapUtils.getString(params,"saleCode");
        if (StringUtils.isEmpty(saleCode)){
            throw new GlobalException("参数不全");
        }
        try{
            outRecService.delAllOut(saleCode);
        }catch (Exception e){
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success("操作成功");
    }

    /**
     * 查询推送记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(OutRec outRec)
    {
        startPage();
        List<OutRec> list = outRecService.selectOutRecList(outRec);
        return getDataTable(list);
    }

    /**
     * 导出推送记录列表
     */
    @PreAuthorize("@ss.hasPermi('account:rec:export')")
    @Log(title = "推送记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(OutRec outRec)
    {
        List<OutRec> list = outRecService.selectOutRecList(outRec);
        ExcelUtil<OutRec> util = new ExcelUtil<OutRec>(OutRec.class);
        return util.exportExcel(list, "推送记录数据");
    }

    /**
     * 获取推送记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('account:rec:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(outRecService.selectOutRecById(id));
    }

    /**
     * 新增推送记录
     */
    @PreAuthorize("@ss.hasPermi('account:rec:add')")
    @Log(title = "推送记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OutRec outRec)
    {
        return toAjax(outRecService.insertOutRec(outRec));
    }

    /**
     * 修改推送记录
     */
    @PreAuthorize("@ss.hasPermi('account:rec:edit')")
    @Log(title = "推送记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OutRec outRec)
    {
        return toAjax(outRecService.updateOutRec(outRec));
    }

    /**
     * 删除推送记录
     */
    @Log(title = "推送记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{saleIds}")
    public AjaxResult remove(@PathVariable String[] saleIds)
    {
        return toAjax(outRecService.deleteOutRecByIds(saleIds));
    }
}
