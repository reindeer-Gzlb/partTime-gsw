package com.ruoyi.web.controller.model.controller;

import java.util.List;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.model.domain.UserKey;
import com.ruoyi.system.model.service.IUserKeyService;
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
 * 密钥Controller
 *
 * @author ruoyi
 * @date 2022-05-26
 */
@RestController
@RequestMapping("/account/key")
public class UserKeyController extends BaseController
{
    @Autowired
    private IUserKeyService userKeyService;

    /**
     * 查询密钥列表
     */
    @PreAuthorize("@ss.hasPermi('account:key:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserKey userKey)
    {
        startPage();
        List<UserKey> list = userKeyService.selectUserKeyList(userKey);
        return getDataTable(list);
    }


    /**
     * 导出密钥列表
     */
    @PreAuthorize("@ss.hasPermi('account:key:export')")
    @Log(title = "密钥", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(UserKey userKey)
    {
        List<UserKey> list = userKeyService.selectUserKeyList(userKey);
        ExcelUtil<UserKey> util = new ExcelUtil<UserKey>(UserKey.class);
        return util.exportExcel(list, "密钥数据");
    }

    /**
     * 获取密钥详细信息
     */
    @PreAuthorize("@ss.hasPermi('account:key:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(userKeyService.selectUserKeyById(id));
    }

    /**
     * 新增密钥
     */
    @PreAuthorize("@ss.hasPermi('account:key:add')")
    @Log(title = "密钥", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserKey userKey)
    {
        return toAjax(userKeyService.insertUserKey(userKey));
    }

    /**
     * 修改密钥
     */
    @PreAuthorize("@ss.hasPermi('account:key:edit')")
    @Log(title = "密钥", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserKey userKey)
    {
        return toAjax(userKeyService.updateUserKey(userKey));
    }

    /**
     * 删除密钥
     */
    @PreAuthorize("@ss.hasPermi('account:key:remove')")
    @Log(title = "密钥", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userKeyService.deleteUserKeyByIds(ids));
    }
}
