package com.ruoyi.web.controller.V2.tool.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.model.domain.Black;
import com.ruoyi.system.model.domain.HBlack;
import com.ruoyi.system.V2.tool.service.IBlackApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author lz
 * @version 1.0
 * @date 2022/6/27 14:43
 */
@RestController
@RequestMapping("/black")
public class BlackController extends BaseController {

    @Autowired
    private IBlackApi blackApi;

    @ResponseBody
    @GetMapping("/gameType")
    public AjaxResult GameType()
    {
        List<Map> maps = blackApi.allGameType();
        return AjaxResult.success(maps);
    }
    @ResponseBody
    @GetMapping("/blackList")
    public TableDataInfo blackList(@RequestParam Map<String,Object> params)
    {
        startPage();
        List<HBlack> blacks = blackApi.allAcount(params);
        return getDataTable(blacks);
    }

    //查看交易备注
    @PostMapping(value = "/addBlack")
    public AjaxResult addBlack(@RequestBody Black black) {
        return AjaxResult.success(blackApi.addBlack(black));
    }
}
