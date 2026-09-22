package com.ruoyi.web.controller.V2.gameAttr.controller;

import java.util.List;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.model.domain.GameAttrTemplate;
import com.ruoyi.system.V2.gameAttr.service.IGameAttrTemplateService;
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
 * 游戏自定义属性对应模板Controller
 *
 * @author ruoyi
 * @date 2023-04-04
 */
@RestController
@RequestMapping("/goods/gameattrtemplate")
public class GameAttrTemplateController extends BaseController {
    @Autowired
    private IGameAttrTemplateService gameAttrTemplateService;

    /**
     * 查询游戏自定义属性对应模板列表
     */
    @GetMapping("/list")
    public TableDataInfo list(GameAttrTemplate gameAttrTemplate) {
        List<GameAttrTemplate> list = gameAttrTemplateService.selectGameAttrTemplateList(gameAttrTemplate);
        return getDataTable(list);
    }

    /**
     * 导出游戏自定义属性对应模板列表
     */
    @Log(title = "游戏自定义属性对应模板", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GameAttrTemplate gameAttrTemplate) {
        List<GameAttrTemplate> list = gameAttrTemplateService.selectGameAttrTemplateList(gameAttrTemplate);
        ExcelUtil<GameAttrTemplate> util = new ExcelUtil<GameAttrTemplate>(GameAttrTemplate.class);
        return util.exportExcel(list, "游戏自定义属性对应模板数据");
    }

    /**
     * 获取游戏自定义属性对应模板详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(gameAttrTemplateService.selectGameAttrTemplateById(id));
    }
    /**
     * 新增游戏自定义属性对应模板
     */
    @Log(title = "游戏自定义属性对应模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GameAttrTemplate gameAttrTemplate) {
        return toAjax(gameAttrTemplateService.insertGameAttrTemplate(gameAttrTemplate));
    }

    /**
     * 修改游戏自定义属性对应模板
     */
    @Log(title = "游戏自定义属性对应模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GameAttrTemplate gameAttrTemplate) {
        return toAjax(gameAttrTemplateService.updateGameAttrTemplate(gameAttrTemplate));
    }

    /**
     * 删除游戏自定义属性对应模板
     */
    @Log(title = "游戏自定义属性对应模板", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(gameAttrTemplateService.deleteGameAttrTemplateByIds(ids));
    }

//    @Log(title = "根据游戏id查询该游戏的自定义属性模板", businessType = BusinessType.OTHER)
//    @GetMapping("/getTemplateByGameId")
//    public TableDataInfo getTemplateByGameId(Long gameId) {
//        List<GameAttrTemplate>  gameAttrTemplates=gameAttrTemplateService.selectGameAttrTemplateByGameId(gameId);
//        return getDataTable(gameAttrTemplates);
//    }
}
