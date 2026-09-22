package com.ruoyi.system.V2.operatorTwoElements.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.operatorTwoElements.service.IOperatorTwoElementsService;
import com.ruoyi.system.model.domain.OperatorTwoElements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operatorTwoElements")
public class OperatorTwoElementsController extends BaseController {
    @Autowired
    IOperatorTwoElementsService operatorTwoElementsService;

    @GetMapping("/list")
    public TableDataInfo list(OperatorTwoElements operatorTwoElements) {
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNull(loginUser)) {
            throw new GlobalException("未获取到用户");
        }
        SysUser currentUser = loginUser.getUser();
        Long createId = null;
        // 如果是超级管理员，则不过滤数据
        if (StringUtils.isNotNull(currentUser) && !currentUser.isAdmin()) {
            createId = currentUser.getUserId();
        }
        LambdaQueryWrapper<OperatorTwoElements> queryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotEmpty(operatorTwoElements.getInputMobile())) {
            queryWrapper.like(OperatorTwoElements::getInputMobile, operatorTwoElements.getInputMobile());
        }
        if (StringUtils.isNotEmpty(operatorTwoElements.getInputName())) {
            queryWrapper.like(OperatorTwoElements::getInputName, operatorTwoElements.getInputName());
        }
        if (StringUtils.isNotNull(createId)) {
            queryWrapper.eq(OperatorTwoElements::getCreateId, createId);
        }
        queryWrapper.orderByDesc(OperatorTwoElements::getCreateTime);
        startPage();
        List<OperatorTwoElements> list = operatorTwoElementsService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     *
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(operatorTwoElementsService.getById(id));
    }

    /**
     * 新增
     */
    @Log(title = "operatorTwoElements", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody OperatorTwoElements operatorTwoElements) {
        return toAjax(operatorTwoElementsService.saveAndGetRresult(operatorTwoElements));
    }
}
