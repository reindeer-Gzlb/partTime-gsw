package com.ruoyi.system.shouFromName.controller;

import java.util.List;

import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.shouFromName.domain.ShouFromName;
import com.ruoyi.system.shouFromName.service.IShouFromNameService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 收入渠道Controller
 *
 * @author ruoyi
 * @date 2024-11-22
 */
@RestController
@RequestMapping("/shouFromName")
public class ShouFromNameController extends BaseController {
    @Autowired
    private IShouFromNameService shouFromNameService;

    /**
     * 查询收入渠道列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ShouFromName shouFromName) {
        startPage();
        shouFromName.setCreateId(SecurityUtils.getUserId());
        List<ShouFromName> list = shouFromNameService.selectShouFromNameList(shouFromName);
        return getDataTable(list);
    }

    /**
     * 查询收入渠道列表
     */
    @GetMapping("/allList")
    public TableDataInfo allList(ShouFromName shouFromName) {
        List<ShouFromName> list = shouFromNameService.selectShouFromNameList(shouFromName);
        return getDataTable(list);
    }

    /**
     * 导出收入渠道列表
     */
    @GetMapping("/export")
    public AjaxResult export(ShouFromName shouFromName) {
        List<ShouFromName> list = shouFromNameService.selectShouFromNameList(shouFromName);
        ExcelUtil<ShouFromName> util = new ExcelUtil<ShouFromName>(ShouFromName.class);
        return util.exportExcel(list, "收入渠道数据");
    }

    /**
     * 获取收入渠道详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(shouFromNameService.selectShouFromNameById(id));
    }

    /**
     * 新增收入渠道
     */
    @PostMapping
    public AjaxResult add(@RequestBody ShouFromName shouFromName) {
        if (StringUtils.isEmpty(shouFromName.getName())){
            throw new GlobalException("参数不可为空");
        }
        return toAjax(shouFromNameService.insertShouFromName(shouFromName));
    }

    /**
     * 修改收入渠道
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ShouFromName shouFromName) {
        return toAjax(shouFromNameService.updateShouFromName(shouFromName));
    }

    /**
     * 删除收入渠道
     */
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(shouFromNameService.deleteShouFromNameByIds(ids));
    }
}
