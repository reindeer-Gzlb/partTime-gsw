package com.ruoyi.web.controller.V2.gameInfo.controller;

import java.util.List;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.model.domain.GameInfo;
import com.ruoyi.system.V2.gameInfo.service.IGameInfoService;
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
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * gameinfoController
 * 
 * @author ruoyi
 * @date 2023-04-08
 */
@RestController
@RequestMapping("/game/info")
public class GameInfoController extends BaseController
{
    @Autowired
    private IGameInfoService gameInfoService;

    /**
     * 查询gameinfo列表
     */
    @GetMapping("/list")
    public TableDataInfo list(GameInfo gameInfo)
    {
        List<GameInfo> list = gameInfoService.selectGameInfoList(gameInfo);
        return getDataTable(list);
    }

    /**
     * 导出gameinfo列表
     */
    @Log(title = "gameinfo", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GameInfo gameInfo)
    {
        List<GameInfo> list = gameInfoService.selectGameInfoList(gameInfo);
        ExcelUtil<GameInfo> util = new ExcelUtil<GameInfo>(GameInfo.class);
        return util.exportExcel(list, "gameinfo数据");
    }

    /**
     * 获取gameinfo详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(gameInfoService.selectGameInfoById(id));
    }

    /**
     * 新增gameinfo
     */
    @Log(title = "gameinfo", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GameInfo gameInfo)
    {
        return toAjax(gameInfoService.insertGameInfo(gameInfo));
    }

    /**
     * 修改gameinfo
     */
    @Log(title = "gameinfo", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GameInfo gameInfo)
    {
        return toAjax(gameInfoService.updateGameInfo(gameInfo));
    }

    /**
     * 删除gameinfo
     */
    @Log(title = "gameinfo", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(gameInfoService.deleteGameInfoByIds(ids));
    }
}
