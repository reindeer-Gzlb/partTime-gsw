package com.ruoyi.web.controller.V2.phone.controller;

import java.util.List;
import java.util.Objects;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.model.domain.TelManage;
import com.ruoyi.system.V2.phone.service.ITelManageService;
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
 * 手机管理Controller
 *
 * @author ruoyi
 * @date 2023-04-05
 */
@RestController
@RequestMapping("/account/tel/manage")
public class TelManageController extends BaseController {
    @Autowired
    private ITelManageService telManageService;

    /**
     * 查询手机管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(TelManage telManage) {
        Long userId = SecurityUtils.getUserId();
        startPage();
        if (!Objects.equals(userId, 1l)) {
            telManage.setCreateId(userId);
        }
        List<TelManage> list = telManageService.selectTelManageList(telManage);
        return getDataTable(list);
    }
    /**
     * 查询手机管理列表
     */
    @GetMapping("/allList")
    public AjaxResult allList(TelManage telManage) {
        List<TelManage> list = telManageService.allList(telManage);
        return AjaxResult.success(list);
    }

    /**
     * 导出手机管理列表
     */
    @Log(title = "手机管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TelManage telManage) {
        List<TelManage> list = telManageService.selectTelManageList(telManage);
        ExcelUtil<TelManage> util = new ExcelUtil<TelManage>(TelManage.class);
        return util.exportExcel(list, "手机管理数据");
    }

    /**
     * 获取手机管理详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        String result = telManageService.countGame(id);
        return AjaxResult.success(result);
    }

    /**
     * 新增手机管理
     */
    @Log(title = "手机管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TelManage telManage) {
        return toAjax(telManageService.insertTelManage(telManage));
    }

    /**
     * 修改手机管理
     */
    @Log(title = "手机管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TelManage telManage) {
        return toAjax(telManageService.updateTelManage(telManage));
    }

    /**
     * 删除手机管理
     */
    @Log(title = "手机管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(telManageService.deleteTelManageByIds(ids));
    }
}
