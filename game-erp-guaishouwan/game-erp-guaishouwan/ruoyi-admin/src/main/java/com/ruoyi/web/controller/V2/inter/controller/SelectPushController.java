package com.ruoyi.web.controller.V2.inter.controller;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.model.domain.SalePartner;
import com.ruoyi.system.model.domain.SelectPush;
import com.ruoyi.system.V2.inter.service.ISelectPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 推送管理池Controller
 * 
 * @author ruoyi
 * @date 2022-10-08
 */
@RestController
@RequestMapping("/account/push")
public class SelectPushController extends BaseController
{
    @Autowired
    private ISelectPushService selectPushService;

    /**
     * 查询【请填写功能名称】列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SelectPush selectPush)
    {
        startPage();
        selectPush.setState("0");
        List<SelectPush> list = selectPushService.selectSelectPushList(selectPush);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SelectPush selectPush)
    {
        List<SelectPush> list = selectPushService.selectSelectPushList(selectPush);
        ExcelUtil<SelectPush> util = new ExcelUtil<SelectPush>(SelectPush.class);
        return util.exportExcel(list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(selectPushService.selectSelectPushById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PostMapping
    public AjaxResult add(@RequestBody SelectPush selectPush)
    {
        return toAjax(selectPushService.insertSelectPush(selectPush));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PutMapping
    public AjaxResult edit(@RequestBody SelectPush selectPush)
    {
        return toAjax(selectPushService.updateSelectPush(selectPush));
    }


    @GetMapping("/getAllSupliar")
    public AjaxResult getAllSupliar(@RequestParam String config)
    {
        Map<String,Object> resultMap =  selectPushService.getAllSupliar(config);
        return AjaxResult.success(resultMap);
    }


    @PostMapping("/updateAupliar")
    public AjaxResult addAupliar(@RequestBody SelectPush selectPush)
    {
        selectPushService.addAupliar(selectPush);
        return AjaxResult.success();
    }


    //获取该供货商分配的接口渠道
    @GetMapping("/selectAllConfigBySup")
    public AjaxResult selectAllConfigBySup(@RequestParam Long createId)
    {
        List<SalePartner> salePartnerList = selectPushService.selectAllConfigBySup(createId);
        return AjaxResult.success(salePartnerList);
    }
    //编辑配置
    @PostMapping("/editConfigBySup")
    public AjaxResult editConfigBySup(@RequestBody SelectPush selectPush)
    {
         selectPushService.editConfigBySup(selectPush);
        return AjaxResult.success();
    }

    /**
     */
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(selectPushService.deleteSelectPushByIds(ids));
    }
}
