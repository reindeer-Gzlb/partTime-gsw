package com.ruoyi.web.controller.V2.inter.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.inter.service.ISysMessageService;
import com.ruoyi.system.model.domain.SysMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/messages")
public class SysMessageController extends BaseController {
    @Autowired
    ISysMessageService sysMessageService;

    @GetMapping("/list")
    public TableDataInfo messageList(SysMessage sysMessage) {
//        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<SysMessage> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotNull(sysMessage.getReadState())) {
            queryWrapper.eq(SysMessage::getReadState, sysMessage.getReadState());
        }
//        queryWrapper.eq(SysMessage::getReceiveUserId, userId);
        queryWrapper.orderByDesc(SysMessage::getSendTime);
        startPage();
        return getDataTable(sysMessageService.list(queryWrapper));
    }

    /**
     * 获取messages详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysMessageService.getById(id));
    }

//    /**
//     * 新增messages
//     */
//    @Log(title = "sysMessage", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody SysMessage sysMessage) {
//        return toAjax(sysMessageService.save(sysMessage));
//    }

//    /**
//     * 修改supplyinfo
//     */
//    @Log(title = "sysMessage", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody SysMessage sysMessage) {
//        return toAjax(sysMessageService.updateById(sysMessage));
//    }

}
