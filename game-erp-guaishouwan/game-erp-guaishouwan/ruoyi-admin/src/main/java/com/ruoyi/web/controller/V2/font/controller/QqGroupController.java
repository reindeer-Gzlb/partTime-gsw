package com.ruoyi.web.controller.V2.font.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.model.domain.QqGroup;
import com.ruoyi.system.V2.font.service.IQqGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 【请填写功能名称】Controller
 *
 * @author ruoyi
 * @date 2022-10-04
 */
@RestController
@RequestMapping("/account/group")
public class QqGroupController extends BaseController {
    @Autowired
    private IQqGroupService qqGroupService;

    /**
     * 查询【请填写功能名称】列表
     */
    @GetMapping("/list")
    public TableDataInfo list(QqGroup qqGroup) {
        startPage();

        Long userId = SecurityUtils.getUserId();
        if (!Objects.equals(userId, 1L)) {
            qqGroup.setCreateId(userId);
            qqGroup.setTypeResource(2);
        }

        List<QqGroup> list = qqGroupService.selectQqGroupList(qqGroup);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(QqGroup qqGroup) {
        List<QqGroup> list = qqGroupService.selectQqGroupList(qqGroup);
        ExcelUtil<QqGroup> util = new ExcelUtil<QqGroup>(QqGroup.class);
        return util.exportExcel(list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(qqGroupService.selectQqGroupById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PostMapping
    public AjaxResult add(@RequestBody QqGroup qqGroup) {
        return toAjax(qqGroupService.insertQqGroup(qqGroup));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PutMapping
    public AjaxResult edit(@RequestBody QqGroup qqGroup) {
        return toAjax(qqGroupService.updateQqGroup(qqGroup));
    }

    /**
     * 删除【请填写功能名称】
     */
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(qqGroupService.deleteQqGroupByIds(ids));
    }
}
