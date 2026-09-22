package com.ruoyi.system.logo.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.logo.domain.SysShopLogo;
import com.ruoyi.system.logo.service.ISysShopLogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * logoController
 *
 * @author ruoyi
 * @date 2024-08-09
 */
@RestController
@RequestMapping("/sys/logo")
public class SysShopLogoController extends BaseController {
    @Autowired
    private ISysShopLogoService sysShopLogoService;

    /**
     * 查询logo列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysShopLogo sysShopLogo) {
        startPage();
        Long userId = SecurityUtils.getUserId();
        if (!Objects.equals(userId ,1L)){
            sysShopLogo.setCreateId(userId);
        }
        List<SysShopLogo> list = sysShopLogoService.selectSysShopLogoList(sysShopLogo);
        return getDataTable(list);
    }

    /**
     * 导出logo列表
     */
    @Log(title = "logo", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysShopLogo sysShopLogo) {
        List<SysShopLogo> list = sysShopLogoService.selectSysShopLogoList(sysShopLogo);
        ExcelUtil<SysShopLogo> util = new ExcelUtil<SysShopLogo>(SysShopLogo.class);
        return util.exportExcel(list, "logo数据");
    }

    /**
     * 获取logo详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysShopLogoService.selectSysShopLogoById(id));
    }

    /**
     * 新增logo
     */
    @Log(title = "logo", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysShopLogo sysShopLogo) {
        return toAjax(sysShopLogoService.insertSysShopLogo(sysShopLogo));
    }

    /**
     * 修改logo
     */
    @Log(title = "logo", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysShopLogo sysShopLogo) {
        return toAjax(sysShopLogoService.updateSysShopLogo(sysShopLogo));
    }

    /**
     * 删除logo
     */
    @Log(title = "logo", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysShopLogoService.deleteSysShopLogoByIds(ids));
    }
}
