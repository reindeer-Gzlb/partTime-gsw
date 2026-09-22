package com.ruoyi.web.controller.model.controller;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.model.domain.ClassSkin;
import com.ruoyi.system.model.domain.Skin;
import com.ruoyi.system.service.ISkinService;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 *
 * @author ruoyi
 * @date 2022-07-03
 */
@RestController
@RequestMapping("/account/skin")
public class SkinController extends BaseController
{
    @Autowired
    private ISkinService skinService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('account:skin:list')")
    @GetMapping("/list")
    public TableDataInfo list(Skin skin)
    {
        startPage();
        List<Skin> list = skinService.selectSkinList(skin);
        return getDataTable(list);
    }

    @GetMapping("/allList")
    public AjaxResult  allList(Skin skin)
    {
        List<Skin> list = skinService.selectSkinList(skin);
        return AjaxResult.success(list);
    }
    /**
     * 查查询所有皮肤分类
     */
    @GetMapping("/classTypeList")
    public AjaxResult classTypeList(ClassSkin classSkin)
    {
        List<Map<String,Object>> list = skinService.selectSkinTypeList(classSkin);
        return AjaxResult.success(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('account:skin:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Skin skin)
    {
        List<Skin> list = skinService.selectSkinList(skin);
        ExcelUtil<Skin> util = new ExcelUtil<Skin>(Skin.class);
        return util.exportExcel(list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('account:skin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(skinService.selectSkinById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('account:skin:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Skin skin)
    {
        return toAjax(skinService.insertSkin(skin));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('account:skin:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Skin skin)
    {
        return toAjax(skinService.updateSkin(skin));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('account:skin:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(skinService.deleteSkinByIds(ids));
    }
}
