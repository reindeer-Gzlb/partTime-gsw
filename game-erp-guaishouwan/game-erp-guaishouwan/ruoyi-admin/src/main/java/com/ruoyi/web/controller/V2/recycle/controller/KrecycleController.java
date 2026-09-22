package com.ruoyi.web.controller.V2.recycle.controller;

import java.util.List;

import com.ruoyi.system.model.domain.Krecycle;
import com.ruoyi.system.V2.recycle.service.IKrecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户端实际订单
 * 
 * @author ruoyi
 * @date 2023-03-28
 */
@RestController
@RequestMapping("/account/krecycle")
public class KrecycleController extends BaseController
{
    @Autowired
    private IKrecycleService krecycleService;

    /**
     */
    @GetMapping("/list")
    public TableDataInfo list(Krecycle krecycle)
    {
        startPage();
        List<Krecycle> list = krecycleService.selectKrecycleList(krecycle);
        return getDataTable(list);
    }

    /**
     */
    @GetMapping("/export")
    public AjaxResult export(Krecycle krecycle)
    {
        List<Krecycle> list = krecycleService.selectKrecycleList(krecycle);
        ExcelUtil<Krecycle> util = new ExcelUtil<Krecycle>(Krecycle.class);
        return util.exportExcel(list, "【请填写功能名称】数据");
    }

    /**
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(krecycleService.selectKrecycleById(id));
    }

    /**
     */
    @PostMapping
    public AjaxResult add(@RequestBody Krecycle krecycle)
    {
        return toAjax(krecycleService.insertKrecycle(krecycle));
    }

    /**
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Krecycle krecycle)
    {
        return toAjax(krecycleService.updateKrecycle(krecycle));
    }

    /**
     */
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(krecycleService.deleteKrecycleByIds(ids));
    }


    /**
     */
    @GetMapping(value = "/getOrderByHxId/{hxId}")
    public AjaxResult getOrderByHxId(@PathVariable("hxId") String id)
    {
        return AjaxResult.success();
    }
}
