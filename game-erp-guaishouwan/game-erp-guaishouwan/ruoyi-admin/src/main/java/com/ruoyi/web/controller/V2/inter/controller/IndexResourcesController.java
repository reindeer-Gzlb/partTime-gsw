package com.ruoyi.web.controller.V2.inter.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.V2.inter.service.IIndexResourcesService;
import com.ruoyi.system.model.domain.IndexResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Objects;

@RestController
@RequestMapping("/index/resources")
public class IndexResourcesController extends BaseController {
    @Autowired
    IIndexResourcesService indexResourcesService;

    @GetMapping("/list")
    public TableDataInfo list(IndexResources indexResources) {
        LambdaQueryWrapper<IndexResources> queryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(indexResources.getSystemType())) {
            queryWrapper.eq(IndexResources::getSystemType, indexResources.getSystemType());
        }
        if (Objects.nonNull(indexResources.getType())) {
            queryWrapper.eq(IndexResources::getType, indexResources.getType());
        }
        Long userId = SecurityUtils.getUserId();
        if (!Objects.equals(userId,1L)){
            queryWrapper.eq(IndexResources::getCreateId,userId );
            queryWrapper.eq(IndexResources::getTypeResource,2);
        }


        queryWrapper.orderBy(true, true, IndexResources::getSort);
        queryWrapper.orderBy(true, false, IndexResources::getId);
        return getDataTable(indexResourcesService.list(queryWrapper));
    }

    /**
     * 获取indexResources详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(indexResourcesService.getById(id));
    }

    /**
     * 新增indexResources
     */
    @Log(title = "indexResources", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IndexResources indexResources) {
        Long userId = getLoginUser().getUserId();
        indexResources.setCreateId(userId);
        if (!Objects.equals(userId,1L)){
            indexResources.setTypeResource(2);
        }

        return toAjax(indexResourcesService.save(indexResources));
    }

    /**
     * 修改indexResources
     */
    @Log(title = "indexResources", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IndexResources indexResources) {
        indexResources.setUpdateId(getLoginUser().getUserId());
        return toAjax(indexResourcesService.updateById(indexResources));
    }

    /**
     * 删除indexResources
     */
    @Log(title = "indexResources", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(indexResourcesService.removeByIds(Arrays.asList(ids)));
    }

    /**
     * 获取最新indexResources最新一条
     */
    @GetMapping(value = "/lastOne")
    public AjaxResult lastOne(IndexResources indexResources) {
        LambdaQueryWrapper<IndexResources> queryWrapper = new LambdaQueryWrapper<>();
//        if (Objects.nonNull(IndexResources.getAppType())) {
//            queryWrapper.eq(AppVersion::getAppType, appVersion.getAppType());
//        }
        queryWrapper.orderByDesc(IndexResources::getCreateTime).last("limit 1");
        return AjaxResult.success(indexResourcesService.getOne(queryWrapper));
    }
}
