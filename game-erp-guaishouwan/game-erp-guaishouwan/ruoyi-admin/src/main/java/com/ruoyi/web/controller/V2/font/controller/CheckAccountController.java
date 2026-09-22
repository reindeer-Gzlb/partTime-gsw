package com.ruoyi.web.controller.V2.font.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.model.domain.CheckAccount;
import com.ruoyi.system.V2.font.service.ICheckAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 账号校验Controller
 * 
 * @author ruoyi
 * @date 2022-10-06
 */
@RestController
@RequestMapping("/check/account")
public class CheckAccountController extends BaseController
{
    @Autowired
    private ICheckAccountService checkAccountService;

    /**
     * 查询账号校验列表
     */
    @GetMapping("/list")
    public TableDataInfo list(CheckAccount checkAccount)
    {
        startPage();
        try {
            Long userId = SecurityUtils.getUserId();
            if (!Objects.equals(userId, 1L)) {
                checkAccount.setCreateId(userId);
            }
        } catch (Exception e) {
        }
        List<CheckAccount> list = checkAccountService.selectCheckAccountList(checkAccount);
        return getDataTable(list);
    }

    /**
     * 导出账号校验列表
     */
    @Log(title = "账号校验", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CheckAccount checkAccount)
    {
        List<CheckAccount> list = checkAccountService.selectCheckAccountList(checkAccount);
        ExcelUtil<CheckAccount> util = new ExcelUtil<CheckAccount>(CheckAccount.class);
        return util.exportExcel(list, "账号校验数据");
    }

    /**
     * 获取账号校验详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(checkAccountService.selectCheckAccountById(id));
    }

    /**
     * 新增账号校验
     */
    @Log(title = "账号校验", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CheckAccount checkAccount)
    {
        return toAjax(checkAccountService.insertCheckAccount(checkAccount));
    }

    /**
     * 修改账号校验
     */
    @Log(title = "账号校验", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CheckAccount checkAccount)
    {
        return toAjax(checkAccountService.updateCheckAccount(checkAccount));
    }

    /**
     * 删除账号校验
     */
    @Log(title = "账号校验", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(checkAccountService.deleteCheckAccountByIds(ids));
    }
}
