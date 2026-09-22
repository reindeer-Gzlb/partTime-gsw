package com.ruoyi.web.controller.model.controller;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.model.domain.FieldConfig;
import com.ruoyi.system.service.IFieldConfigService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 游戏类型字段配置Controller
 *
 * @author ruoyi
 * @date 2022-07-03
 */
@RestController
@RequestMapping("/account/config")
public class FieldConfigController extends BaseController
{
    @Autowired
    private IFieldConfigService fieldConfigService;

    /**
     * 查询游戏类型字段配置列表
     */
    @GetMapping("/list")
    public TableDataInfo list(FieldConfig fieldConfig)
    {
        List<FieldConfig> list = fieldConfigService.selectFieldConfigList(fieldConfig);
        return getDataTable(list);
    }


    @GetMapping("/listType")
    public List<FieldConfig> listType(@RequestParam("type") String type)
    {
        return fieldConfigService.selectFieldConfigByType(type);
    }

    @GetMapping("/labelType")
    public List<FieldConfig> labelType(@RequestParam("type") String type)
    {
        return fieldConfigService.selectFieldConfigByLbelType(type);
    }

    /**
     * 导出游戏类型字段配置列表
     */
    @Log(title = "游戏类型字段配置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FieldConfig fieldConfig)
    {
        List<FieldConfig> list = fieldConfigService.selectFieldConfigList(fieldConfig);
        ExcelUtil<FieldConfig> util = new ExcelUtil<FieldConfig>(FieldConfig.class);
        return util.exportExcel(list, "游戏类型字段配置数据");
    }

    /**
     * 获取游戏类型字段配置详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(fieldConfigService.selectFieldConfigById(id));
    }

    /**
     * 新增游戏类型字段配置
     */
    @Log(title = "游戏类型字段配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FieldConfig fieldConfig)
    {
        return toAjax(fieldConfigService.insertFieldConfig(fieldConfig));
    }
    @Log(title = "游戏类型字段配置", businessType = BusinessType.INSERT)
    @PutMapping
    public AjaxResult edit(@RequestBody FieldConfig fieldConfig)
    {
        return toAjax(fieldConfigService.updateFieldConfig(fieldConfig));
    }


    @Log(title = "游戏类型字段配置", businessType = BusinessType.INSERT)
    @PostMapping("/addJson")
    public AjaxResult addJson(@RequestBody JSONObject params)
    {
        return toAjax(fieldConfigService.addJson(params));
    }

    /**
     * 修改游戏类型字段配置
     */
    @Log(title = "游戏类型字段配置", businessType = BusinessType.UPDATE)
    @PostMapping("/editJson")
    public AjaxResult editJson(@RequestBody JSONObject params)
    {
        return toAjax(fieldConfigService.updateJson(params));
    }

    /**
     * 删除游戏类型字段配置
     */
    @Log(title = "游戏类型字段配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(fieldConfigService.deleteFieldConfigByIds(ids));
    }
}
