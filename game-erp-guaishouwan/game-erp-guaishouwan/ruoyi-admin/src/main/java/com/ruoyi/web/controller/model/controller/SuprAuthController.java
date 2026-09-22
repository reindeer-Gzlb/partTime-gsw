package com.ruoyi.web.controller.model.controller;

import java.util.List;

import com.ruoyi.system.model.domain.SuprAuth;
import com.ruoyi.system.model.service.ISuprAuthService;
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
 * 供货商授权表Controller
 *
 * @author ruoyi
 * @date 2022-06-14
 */
@RestController
@RequestMapping("/account/auth")
public class SuprAuthController extends BaseController
{
    @Autowired
    private ISuprAuthService suprAuthService;

    /**
     * 查询供货商授权表列表
     */
    @PreAuthorize("@ss.hasPermi('account:auth:list')")
    @GetMapping("/list")
    public TableDataInfo list(SuprAuth suprAuth)
    {
        startPage();
        List<SuprAuth> list = suprAuthService.selectSuprAuthList(suprAuth);
        return getDataTable(list);
    }

    /**
     * 导出供货商授权表列表
     */
    @PreAuthorize("@ss.hasPermi('account:auth:export')")
    @Log(title = "供货商授权表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SuprAuth suprAuth)
    {
        List<SuprAuth> list = suprAuthService.selectSuprAuthList(suprAuth);
        ExcelUtil<SuprAuth> util = new ExcelUtil<SuprAuth>(SuprAuth.class);
        return util.exportExcel(list, "供货商授权表数据");
    }

    /**
     * 获取供货商授权表详细信息
     */
    @PreAuthorize("@ss.hasPermi('account:auth:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(suprAuthService.selectSuprAuthById(id));
    }
    @GetMapping(value = "/getSupeAuthByUserId")
    public AjaxResult getSupeAuthByUserId(SuprAuth suprAuth)
    {
        SuprAuth suprAuth1 = suprAuthService.getSupeAuthByUserId(suprAuth);
        return AjaxResult.success(suprAuth1);
    }



    /**
     * 新增供货商授权表
     */
    @PreAuthorize("@ss.hasPermi('account:auth:add')")
    @Log(title = "供货商授权表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SuprAuth suprAuth)
    {
        return toAjax(suprAuthService.insertSuprAuth(suprAuth));
    }

    /**
     * 修改供货商授权表
     */
    @PreAuthorize("@ss.hasPermi('account:auth:edit')")
    @Log(title = "供货商授权表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SuprAuth suprAuth)
    {
        return toAjax(suprAuthService.updateSuprAuth(suprAuth));
    }

    /**
     * 删除供货商授权表
     */
    @PreAuthorize("@ss.hasPermi('account:auth:remove')")
    @Log(title = "供货商授权表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(suprAuthService.deleteSuprAuthByIds(ids));
    }
}
