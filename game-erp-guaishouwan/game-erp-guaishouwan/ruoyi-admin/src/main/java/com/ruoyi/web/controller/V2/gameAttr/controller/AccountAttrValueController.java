package com.ruoyi.web.controller.V2.gameAttr.controller;


import java.util.*;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.model.domain.AccountAttrValue;
import com.ruoyi.system.V2.gameAttr.service.IAccountAttrValueService;
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
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 账号属性表Controller
 * 
 * @author ruoyi
 * @date 2023-04-04
 */
@RestController
@RequestMapping("/goods/accountattrvalue")
public class AccountAttrValueController extends BaseController
{
    @Autowired
    private IAccountAttrValueService accountAttrValueService;

    /**
     * 查询账号属性表列表
     */
    @PreAuthorize("@ss.hasPermi('goods:accountattrvalue:list')")
    @GetMapping("/list")
    public TableDataInfo list(AccountAttrValue accountAttrValue)
    {
        startPage();
        List<AccountAttrValue> list = accountAttrValueService.selectAccountAttrValueList(accountAttrValue);
        return getDataTable(list);
    }

    /**
     * 导出账号属性表列表
     */
    @PreAuthorize("@ss.hasPermi('goods:accountattrvalue:export')")
    @Log(title = "账号属性表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AccountAttrValue accountAttrValue)
    {
        List<AccountAttrValue> list = accountAttrValueService.selectAccountAttrValueList(accountAttrValue);
        ExcelUtil<AccountAttrValue> util = new ExcelUtil<AccountAttrValue>(AccountAttrValue.class);
        return util.exportExcel(list, "账号属性表数据");
    }

    /**
     * 获取账号属性表详细信息
     */
    @PreAuthorize("@ss.hasPermi('goods:accountattrvalue:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(accountAttrValueService.selectAccountAttrValueById(id));
    }

    /**
     * 新增账号属性表
     */
    @PreAuthorize("@ss.hasPermi('goods:accountattrvalue:add')")
    @Log(title = "账号属性表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AccountAttrValue accountAttrValue)
    {
        return toAjax(accountAttrValueService.insertAccountAttrValue(accountAttrValue));
    }

    /**
     * 修改账号属性表
     */
    @PreAuthorize("@ss.hasPermi('goods:accountattrvalue:edit')")
    @Log(title = "账号属性表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AccountAttrValue accountAttrValue)
    {
        return toAjax(accountAttrValueService.updateAccountAttrValue(accountAttrValue));
    }

    /**
     * 删除账号属性表
     */
    @PreAuthorize("@ss.hasPermi('goods:accountattrvalue:remove')")
    @Log(title = "账号属性表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(accountAttrValueService.deleteAccountAttrValueByIds(ids));
    }

}
