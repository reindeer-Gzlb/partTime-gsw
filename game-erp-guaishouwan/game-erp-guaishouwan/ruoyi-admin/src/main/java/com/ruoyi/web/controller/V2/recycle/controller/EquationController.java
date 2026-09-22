package com.ruoyi.web.controller.V2.recycle.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.model.domain.Equation;
import com.ruoyi.system.V2.recycle.service.IEquationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公式配置Controller
 * 
 * @author ruoyi
 * @date 2022-12-11
 */
@RestController
@RequestMapping("/account/equation")
public class EquationController extends BaseController
{
    @Autowired
    private IEquationService equationService;

    /**
     * 查询公式配置列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Equation equation)
    {
        startPage();
        List<Equation> list = equationService.selectEquationList(equation);
        return getDataTable(list);
    }

    /**
     * 导出公式配置列表
     */
    @Log(title = "公式配置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Equation equation)
    {
        List<Equation> list = equationService.selectEquationList(equation);
        ExcelUtil<Equation> util = new ExcelUtil<Equation>(Equation.class);
        return util.exportExcel(list, "公式配置数据");
    }

    /**
     * 获取公式配置详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(equationService.selectEquationById(id));
    }

    /**
     * 新增公式配置
     */
    @Log(title = "公式配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Equation equation)
    {
        return toAjax(equationService.insertEquation(equation));
    }

    /**
     * 修改公式配置
     */
    @Log(title = "公式配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Equation equation)
    {
        return toAjax(equationService.updateEquation(equation));
    }

    /**
     * 删除公式配置
     */
    @Log(title = "公式配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(equationService.deleteEquationByIds(ids));
    }
}
