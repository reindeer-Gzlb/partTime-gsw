package com.ruoyi.web.controller.V2.gameAttr.controller;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.model.domain.GameAttribute;
import com.ruoyi.system.V2.gameAttr.service.IGameAttributeService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 游戏配置Controller
 * 
 * @author ruoyi
 * @date 2023-04-08
 */
@RestController
@RequestMapping("/game/attribute")
public class GameAttributeController extends BaseController
{
    @Autowired
    private IGameAttributeService gameAttributeService;

    /**
     * 查询游戏配置列表
     */
    @GetMapping("/list")
    public TableDataInfo list(GameAttribute gameAttribute)
    {
        List<GameAttribute> list = gameAttributeService.selectGameAttributeList(gameAttribute);
        return getDataTable(list);
    }

    /**
     * 查询游戏配置列表
     */
    @GetMapping("/keylist")
    public TableDataInfo keylist(GameAttribute gameAttribute)
    {
        List<GameAttribute> list = gameAttributeService.keySelectGameAttributeList(gameAttribute);
        return getDataTable(list);
    }

    /**
     * 导出游戏配置列表
     */
    @Log(title = "游戏配置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GameAttribute gameAttribute)
    {
        List<GameAttribute> list = gameAttributeService.selectGameAttributeList(gameAttribute);
        ExcelUtil<GameAttribute> util = new ExcelUtil<GameAttribute>(GameAttribute.class);
        return util.exportExcel(list, "游戏配置数据");
    }

    /**
     * 获取游戏配置详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(gameAttributeService.selectGameAttributeById(id));
    }

    /**
     * 新增游戏配置
     */
    @Log(title = "游戏配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GameAttribute gameAttribute)
    {
        return toAjax(gameAttributeService.insertGameAttribute(gameAttribute));
    }

    /**
     * 修改游戏配置
     */
    @Log(title = "游戏配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GameAttribute gameAttribute)
    {
        return toAjax(gameAttributeService.updateGameAttribute(gameAttribute));
    }

    /**
     * 删除游戏配置
     */
    @Log(title = "游戏配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(gameAttributeService.deleteGameAttributeByIds(ids));
    }



    /**
     * 获取所有属性
     */
    @PostMapping("allAttr")
    public AjaxResult allAttr(@RequestBody Map<String,Object> params)
    {
        String ids = MapUtils.getString(params, "ids");
        List<GameAttribute> list = gameAttributeService.allAttr(ids);
        return AjaxResult.success(list);
    }


    /**
     * 根据游戏获取所有分类树
     */
    @GetMapping("/listAllTree")
    public AjaxResult listAllTree(GameAttribute gameAttribute)
    {
        List<GameAttribute> list = gameAttributeService.listAllTree(gameAttribute);
        return AjaxResult.success(list);
    }
}
