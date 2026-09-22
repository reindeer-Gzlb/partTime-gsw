package com.ruoyi.web.controller.model.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.model.domain.AccountInfo;
import com.ruoyi.system.model.domain.RecoveryAccountInfo;
import com.ruoyi.system.model.service.IAccountInfoService;
import com.ruoyi.system.model.service.IRecoveryAccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


/**
 * 回收客服
 */
@RestController
@RequestMapping("/system/recovery")
public class RecoveryAccountInfoController extends BaseController {

    @Autowired
    private IAccountInfoService accountInfoService;

    @Autowired
    private IRecoveryAccountInfoService recoveryAccountInfoService;


    /**
     * 查询回收游戏账号列表
     */
    @GetMapping("/list")
    public TableDataInfo list(RecoveryAccountInfo recoveryAccountInfo)
    {
        startPage();
        List<RecoveryAccountInfo> list = recoveryAccountInfoService.selectRecoveryAccountInfoList(recoveryAccountInfo);
        return getDataTable(list);
    }

    @Log(title = "移动端回收客服新增游戏账号", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody RecoveryAccountInfo recoveryAccountInfo)
    {
        RecoveryAccountInfo info = recoveryAccountInfoService.selectRecoveryAccountInfoById(recoveryAccountInfo.getExtractAccount());
        if (null != info){
            throw new ServiceException("玩家售卖的账号已存在于系统中，请联系管理员操作!");
        }
        //数据提交之后标记为账号资源池状态
        recoveryAccountInfo.setRecoveryState(4);
        //同时审核标识为:未进行包赔审核
        recoveryAccountInfo.setAuditFlag(1);
        recoveryAccountInfoService.insertRecoveryAccountInfo(recoveryAccountInfo);
        Long id = recoveryAccountInfo.getId();
        return AjaxResult.success(id);
    }


    @Log(title = "编辑游戏账号", businessType = BusinessType.UPDATE)
    @PutMapping("/recoveryEditInfo")
    public AjaxResult recoveryInfo(@RequestBody RecoveryAccountInfo accountInfo){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        accountInfo.setUpdateId(loginUser.getUserId());
        accountInfo.setUpdateBy(loginUser.getUsername());
        accountInfo.setUpdateTime(new Date());
        return toAjax(recoveryAccountInfoService.updateRecoveryAccountInfo(accountInfo));
    }


    @Log(title = "编辑游戏账号", businessType = BusinessType.UPDATE)
    @PutMapping("/recycledInfo")
    public AjaxResult recycledInfo(@RequestBody RecoveryAccountInfo accountInfo){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        accountInfo.setUpdateId(loginUser.getUserId());
        accountInfo.setUpdateBy(loginUser.getUsername());
        accountInfo.setUpdateTime(new Date());
        return toAjax(recoveryAccountInfoService.updateRecoveryAccountInfo(accountInfo));
    }


    @Log(title ="问题账号重新回收", businessType = BusinessType.UPDATE)
    @PutMapping("/norecoveredInfo")
    public AjaxResult norecoveredInfo(@RequestBody RecoveryAccountInfo accountInfo){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        accountInfo.setUpdateId(loginUser.getUserId());
        accountInfo.setUpdateBy(loginUser.getUsername());
        accountInfo.setUpdateTime(new Date());
        return toAjax(accountInfoService.norecoveredInfo(accountInfo));
    }


    /**
     * 导出回收游戏账号列表
     */
    @Log(title = "回收游戏账号", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(RecoveryAccountInfo recoveryAccountInfo)
    {
        List<RecoveryAccountInfo> list = recoveryAccountInfoService.selectRecoveryAccountInfoList(recoveryAccountInfo);
        ExcelUtil<RecoveryAccountInfo> util = new ExcelUtil<RecoveryAccountInfo>(RecoveryAccountInfo.class);
        return util.exportExcel(list, "回收游戏账号数据");
    }

    /**
     * 获取回收游戏账号详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(recoveryAccountInfoService.selectRecoveryAccountInfoById(id));
    }
}
