package com.ruoyi.web.controller.V2.inter.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.model.domain.SupplyInfo;
import com.ruoyi.system.V2.inter.service.ISupplyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/supply/info")
public class SupplyInfoController extends BaseController {
    @Autowired
    ISupplyInfoService supplyInfoService;

    @GetMapping("/list")
    public TableDataInfo list(SupplyInfo supplyInfo) {
        LambdaQueryWrapper<SupplyInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SupplyInfo::getIsEnble, 0);
        if (StringUtils.isNotEmpty(supplyInfo.getName())) {
            queryWrapper.like(SupplyInfo::getName, supplyInfo.getName());
        }
        List<SupplyInfo> list = supplyInfoService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 获取gameinfo详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(supplyInfoService.getById(id));
    }

    /**
     * 新增supplyinfo
     */
    @Log(title = "supplyinfo", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SupplyInfo supplyInfo) {
        return toAjax(supplyInfoService.save(supplyInfo));
    }

    /**
     * 修改supplyinfo
     */
    @Log(title = "supplyinfo", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SupplyInfo supplyInfo) {
        return toAjax(supplyInfoService.updateById(supplyInfo));
    }

    /**
     * 删除supplyinfo
     */
    @Log(title = "supplyinfo", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(supplyInfoService.removeByIds(Arrays.asList(ids)));
    }

    @GetMapping("/getForUserId")
    public AjaxResult getForUserId(Long userId) {
        return AjaxResult.success(supplyInfoService.getListForUserId(userId));
    }
}
