package com.ruoyi.web.controller.V2.font.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.font.service.ISocialInfoService;
import com.ruoyi.system.model.domain.SocialInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/social/info")
public class SocialInfoController extends BaseController {
    @Autowired
    ISocialInfoService socialInfoService;

    @GetMapping("/list")
    public TableDataInfo list(SocialInfo socialInfo) {
        LambdaQueryWrapper<SocialInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotNull(socialInfo.getType())) {
            queryWrapper.like(SocialInfo::getType, socialInfo.getType());
        }
        if (StringUtils.isNotEmpty(socialInfo.getName())) {
            queryWrapper.like(SocialInfo::getName, socialInfo.getName());
        }
        if (StringUtils.isNotEmpty(socialInfo.getAccount())) {
            queryWrapper.like(SocialInfo::getAccount, socialInfo.getAccount());
        }
        if (StringUtils.isNotNull(socialInfo.getIsEnable())) {
            queryWrapper.eq(SocialInfo::getIsEnable, socialInfo.getIsEnable());
        }
        queryWrapper.orderByDesc(SocialInfo::getId);
        startPage();
        List<SocialInfo> list = socialInfoService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * 获取socialInfo详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(socialInfoService.getById(id));
    }

    /**
     * 新增socialInfo
     */
    @Log(title = "socialInfo", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SocialInfo socialInfo) {
        return toAjax(socialInfoService.save(socialInfo));
    }

    /**
     * 修改socialInfo
     */
    @Log(title = "socialInfo", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SocialInfo socialInfo) {
        return toAjax(socialInfoService.updateById(socialInfo));
    }

    /**
     * 删除socialInfo
     */
    @Log(title = "socialInfo", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(socialInfoService.removeByIds(Arrays.asList(ids)));
    }
}
