package com.ruoyi.web.controller.V2.recycle.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.model.domain.Attribute;
import com.ruoyi.system.V2.recycle.service.IAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 游戏配置Controller
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
@RestController
@RequestMapping("/account/attribute")
public class AttributeController extends BaseController
{
    @Autowired
    private IAttributeService attributeService;

    /**
     * 查询游戏配置列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Attribute attribute)
    {
        List<Attribute> list = attributeService.selectAttributeList(attribute);
        return getDataTable(list);
    }
    /**
     * 查询游戏配置列表
     */
    @GetMapping("/allList")
    public AjaxResult allList(Attribute attribute)
    {
        List<Attribute> list = attributeService.selectAttributeList(attribute);
        return AjaxResult.success(list);
    }
    /**
     * 获取分类树
     */
    @GetMapping("/listTree")
    public AjaxResult listTree(Attribute attribute)
    {
        List<Attribute> list = attributeService.listTree(attribute);
        return AjaxResult.success(list);
    }
    /**
     * 获取分类树
     */
    @GetMapping("/selectEquation")
    public AjaxResult selectEquation(Attribute attribute)
    {
        List<Attribute> list = attributeService.selectEquation(attribute);
        return AjaxResult.success(list);
    }

    /**
     * 导出游戏配置列表
     */
    @Log(title = "游戏配置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Attribute attribute)
    {
        List<Attribute> list = attributeService.selectAttributeList(attribute);
        ExcelUtil<Attribute> util = new ExcelUtil<Attribute>(Attribute.class);
        return util.exportExcel(list, "游戏配置数据");
    }

    /**
     * 获取游戏配置详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(attributeService.selectAttributeById(id));
    }

    /**
     * 新增游戏配置
     */
    @Log(title = "游戏配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Attribute attribute)
    {
        return toAjax(attributeService.insertAttribute(attribute));
    }

    /**
     * 修改游戏配置
     */
    @Log(title = "游戏配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Attribute attribute)
    {
        return toAjax(attributeService.updateAttribute(attribute));
    }

    /**
     * 删除游戏配置
     */
    @Log(title = "游戏配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(attributeService.deleteAttributeByIds(ids));
    }
}
