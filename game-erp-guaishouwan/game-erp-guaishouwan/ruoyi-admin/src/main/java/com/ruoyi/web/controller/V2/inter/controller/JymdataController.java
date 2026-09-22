package com.ruoyi.web.controller.V2.inter.controller;

import java.util.List;

import com.ruoyi.system.model.domain.Jymdata;
import com.ruoyi.system.V2.inter.service.IJymdataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 * 
 * @author ruoyi
 * @date 2023-03-27
 */
@RestController
@RequestMapping("/account/jymdata")
public class JymdataController extends BaseController
{
    @Autowired
    private IJymdataService jymdataService;

    /**
     * 查询【请填写功能名称】列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Jymdata jymdata)
    {
        startPage();
        List<Jymdata> list = jymdataService.selectJymdataList(jymdata);
        return getDataTable(list);
    }


    /**
     * 获取【请填写功能名称】详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(jymdataService.selectJymdataById(id));
    }

    @GetMapping(value = "/allChannel")
    public AjaxResult allChannel()
    {
        List<String> result =  jymdataService.allChannel();
        return AjaxResult.success(result);
    }
}
