package com.ruoyi.web.controller.V2.hsgame;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.V2.hsgame.service.IHsGameService;
import com.ruoyi.system.model.domain.HsGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 回收游戏配置Controller
 * 
 * @author ruoyi
 * @date 2023-07-29
 */
@RestController
@RequestMapping("/account/game")
public class HsGameController extends BaseController
{
    @Autowired
    private IHsGameService hsGameService;

    /**
     * 查询回收游戏配置列表
     */
    @GetMapping("/list")
    public TableDataInfo list(HsGame hsGame)
    {
        startPage();
        List<HsGame> list = hsGameService.selectHsGameList(hsGame);
        return getDataTable(list);
    }

    /**
     * 导出回收游戏配置列表
     */
    @Log(title = "回收游戏配置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(HsGame hsGame)
    {
        List<HsGame> list = hsGameService.selectHsGameList(hsGame);
        ExcelUtil<HsGame> util = new ExcelUtil<HsGame>(HsGame.class);
        return util.exportExcel(list, "回收游戏配置数据");
    }

    /**
     * 获取回收游戏配置详细信息
     */
    @GetMapping
    public AjaxResult getInfo(Long createId)
    {
        HsGame hsGame = new HsGame();
        hsGame.setCreateId(createId);
        List<HsGame> hsGames = hsGameService.selectHsGameList(hsGame);
        return AjaxResult.success(hsGames);
    }

    /**
     * 新增回收游戏配置
     */
    @Log(title = "回收游戏配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HsGame hsGame)
    {
        return toAjax(hsGameService.insertHsGame(hsGame));
    }

    /**
     * 修改回收游戏配置
     */
    @Log(title = "回收游戏配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HsGame hsGame)
    {
        return toAjax(hsGameService.updateHsGame(hsGame));
    }

    /**
     * 删除回收游戏配置
     */
    @Log(title = "回收游戏配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hsGameService.deleteHsGameByIds(ids));
    }
}
