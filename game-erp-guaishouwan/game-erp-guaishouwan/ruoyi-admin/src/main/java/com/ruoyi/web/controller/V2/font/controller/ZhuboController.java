package com.ruoyi.web.controller.V2.font.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.model.domain.Zhubo;
import com.ruoyi.system.V2.font.service.IZhuboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 【请填写功能名称】Controller
 * 
 * @author ruoyi
 * @date 2023-03-09
 */
@RestController
@RequestMapping("/account/zhubo")
public class ZhuboController extends BaseController
{
    @Autowired
    private IZhuboService zhuboService;

    /**
     * 查询【请填写功能名称】列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Zhubo zhubo)
    {
        startPage();
        List<Zhubo> list = zhuboService.selectZhuboList(zhubo);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @GetMapping("/export")
    public AjaxResult export(Zhubo zhubo)
    {
        List<Zhubo> list = zhuboService.selectZhuboList(zhubo);
        ExcelUtil<Zhubo> util = new ExcelUtil<Zhubo>(Zhubo.class);
        return util.exportExcel(list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(zhuboService.selectZhuboById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PostMapping
    public AjaxResult add(@RequestBody Zhubo zhubo)
    {
        return toAjax(zhuboService.insertZhubo(zhubo));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Zhubo zhubo)
    {
        return toAjax(zhuboService.updateZhubo(zhubo));
    }

    /**
     * 删除【请填写功能名称】
     */
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(zhuboService.deleteZhuboByIds(ids));
    }
}
