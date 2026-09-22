package com.ruoyi.web.controller.model.controller;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.model.domain.*;
import com.ruoyi.system.model.dto.SynchronDto;
import com.ruoyi.system.model.exel.*;
import com.ruoyi.system.model.mapper.AddPriceMapper;
import com.ruoyi.system.model.service.IAccountInfoService;
import com.ruoyi.system.model.service.IRecoveryAccountInfoService;
import com.ruoyi.system.model.vo.DeleteAllVo;
import com.ruoyi.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 游戏账号Controller
 *
 * @author ruoyi
 * @date 2022-05-04
 */
@Slf4j
@RestController
@RequestMapping("/system/info")
public class AccountInfoController extends BaseController
{
    @Autowired
    private IAccountInfoService accountInfoService;


    @Autowired
    private IRecoveryAccountInfoService recoveryAccountInfoService;

    @Autowired
    private ISysUserService userService;


    /**
     * 查询游戏账号列表
     */
    @GetMapping("/list")
    public TableDataInfo list(AccountInfo accountInfo)
    {
        startPage();
        List<AccountInfo> list = accountInfoService.selectAccountInfoList(accountInfo);
        List<SysUser> supplyCustomer = userService.getUserByRoleCode("supplyCustomer");
        accountInfoService.bacthAcc(list,supplyCustomer);
        return getDataTable(list);
    }


    @GetMapping("/getImage")
    public AjaxResult getImage(AccountInfo accountInfo)
    {
        List<InfoImg> list = accountInfoService.getImage(accountInfo);
        return AjaxResult.success(list);
    }
    @GetMapping("/typeCount")
    public AjaxResult typeCount(AccountInfo accountInfo)
    {
        List<Map<String,Object>> list = accountInfoService.typeCount(accountInfo);
        return AjaxResult.success(list);
    }

    /**
     * 账号池
     * @param accountInfo
     * @return
     */
    @GetMapping("/poollist")
    public TableDataInfo poollist(AccountInfo accountInfo)
    {
        Long userId = SecurityUtils.getUserId();
        //零售客服已添加账号,账号池不需要展示
//        RetailAccount retailAccount = new RetailAccount();
//        retailAccount.setCreateId(userId);
//        List<RetailAccount> retailAccountList = retailAccountMapper.selectRetailAccountList(retailAccount);
//        List<Long> exeIds = retailAccountList.stream().map(r -> r.getAccountId()).collect(Collectors.toList());
//        accountInfo.setExeIds(exeIds);
        startPage();
        accountInfo.setSaleState(1);
        List<AccountInfo> list = accountInfoService.selectAccountInfoList(accountInfo);
        List<SysUser> supplyCustomer = userService.getUserByRoleCode("supplyCustomer");
        accountInfoService.bacthAcc(list,supplyCustomer);
        return getDataTable(list);
    }


    @GetMapping("/toBeRecycledList")
    public TableDataInfo toBeRecycledList(RecoveryAccountInfo accountInfo)
    {
        startPage();
        List<RecoveryAccountInfo> list = recoveryAccountInfoService.selectRecoveryAccountInfoList(accountInfo);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('system:recovery:list')")
    @GetMapping("/recoveryList")
    public TableDataInfo recoveryList(RecoveryAccountInfo accountInfo)
    {
        startPage();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //设置回收人
        accountInfo.setRecoveryId(loginUser.getUserId());
        List<RecoveryAccountInfo> list = recoveryAccountInfoService.selectRecoveryAccountInfoList(accountInfo);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('system:audit:list')")
    @GetMapping("/auditList")
    public TableDataInfo auditList(RecoveryAccountInfo accountInfo)
    {
        startPage();
        List<RecoveryAccountInfo> list = recoveryAccountInfoService.selectRecoveryAccountInfoList(accountInfo);
        return getDataTable(list);
    }

    /**
     * 导出游戏账号列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:export')")
    @Log(title = "游戏账号", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AccountInfo accountInfo)
    {
        List<AccountInfo> list = accountInfoService.selectAccountInfoList(accountInfo);
        if ("sale".equals(accountInfo.getOperType())){
            List<UnsoldAccountExel> unsoldAccountExels = new ArrayList<>();
            for (AccountInfo info : list) {
                UnsoldAccountExel unsoldAccountExel = new UnsoldAccountExel();
                BeanUtils.copyProperties(info,unsoldAccountExel);
                unsoldAccountExels.add(unsoldAccountExel);
            }
            ExcelUtil<UnsoldAccountExel> util = new ExcelUtil<UnsoldAccountExel>(UnsoldAccountExel.class);
            return util.exportExcel(unsoldAccountExels, "未售游戏账号数据");
        }else if (Objects.equals("cmdSale",accountInfo.getOperType())){

            List<CmdAccountExel> accountManagerExels = new ArrayList<>();
            for (AccountInfo info : list) {
                CmdAccountExel cmdAccountExel = new CmdAccountExel();
                if (Objects.isNull(info.getNewPrice())){
                    info.setNewPrice(info.getPrice());
                }
                BeanUtils.copyProperties(info,cmdAccountExel);
                accountManagerExels.add(cmdAccountExel);
            }
            ExcelUtil<CmdAccountExel> util = new ExcelUtil<>(CmdAccountExel.class);
            return util.exportExcel(accountManagerExels, "终端已售");

        }else{
            List<AccountManagerExel> accountManagerExels = new ArrayList<>();
            for (AccountInfo info : list) {
                AccountManagerExel accountManagerExel = new AccountManagerExel();
                BeanUtils.copyProperties(info,accountManagerExel);
                accountManagerExels.add(accountManagerExel);
            }
            ExcelUtil<AccountManagerExel> util = new ExcelUtil<>(AccountManagerExel.class);
            return util.exportExcel(accountManagerExels, "游戏账号数据");
        }
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<AccountInfoExel> util = new ExcelUtil<AccountInfoExel>(AccountInfoExel.class);
        return util.importTemplateExcel("王者荣耀、英雄联盟游戏账号导入模板");
    }

    @GetMapping("/importTemplateCommon")
    public AjaxResult importTemplateCommon()
    {
        ExcelUtil<AccountInfoExelCommon> util = new ExcelUtil<AccountInfoExelCommon>(AccountInfoExelCommon.class);
        return util.importTemplateExcel("通用导入模板");
    }

    @GetMapping("/importTemplateHp")
    public AjaxResult importTemplateHp()
    {
        ExcelUtil<AccountInfoExelHp> util = new ExcelUtil<AccountInfoExelHp>(AccountInfoExelHp.class);
        return util.importTemplateExcel("和平精英游戏账号导入模板");
    }


    @Log(title = "账号导入", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:info:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<AccountInfo> util = new ExcelUtil<AccountInfo>(AccountInfo.class);
        ZipSecureFile.setMinInflateRatio(-1.0d);
        List<AccountInfo> accountInfos = util.importExcel(file.getInputStream());
        for (AccountInfo accountInfo : accountInfos) {
            Integer heroes = accountInfo.getHeroes();
            Integer skins = accountInfo.getSkins();
            if (Objects.isNull(heroes)){
                heroes = accountInfo.getAttr1();
                accountInfo.setHeroes(heroes);
            }
            if (Objects.isNull(skins)){
                skins = accountInfo.getAttr2();
                accountInfo.setSkins(skins);
            }
        }
        String operName = getUsername();
        String message = accountInfoService.importAccount(accountInfos, updateSupport, operName);
        return AjaxResult.success(message);
    }

    /**
     * 获取游戏账号详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(accountInfoService.selectAccountInfoById(id));
    }

    /**
     * 新增游戏账号
     */
    @PreAuthorize("@ss.hasPermi('system:info:add')")
    @Log(title = "游戏账号", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AccountInfo accountInfo)
    {
        return toAjax(accountInfoService.insertAccountInfo(accountInfo));
    }

    /**
     * 修改游戏账号
     */
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "游戏账号", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AccountInfo accountInfo)
    {
        return toAjax(accountInfoService.updateAccountInfo(accountInfo));
    }

    /**
     * 删除游戏账号
     */
    @PreAuthorize("@ss.hasPermi('system:info:remove')")
    @Log(title = "游戏账号", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(accountInfoService.deleteAccountInfoByIds(ids));
    }

    /**
     * 上架
     */
    @Log(title = "上架", businessType = BusinessType.DELETE)
    @GetMapping("/upAccount/{id}")
    public AjaxResult upAccount(@PathVariable("id") Long id)
    {
        return toAjax(accountInfoService.upAccount(id));
    }
    /**
     * 删除游戏账号
     */
    @Log(title = "全部删除", businessType = BusinessType.DELETE)
	@GetMapping("/deleteAllAccount")
    public AjaxResult deleteAllAccount(DeleteAllVo deleteAllVo)
    {
        return toAjax(accountInfoService.deleteAllAccount(deleteAllVo));
    }
    /**
     * 审核游戏账号
     */
	@PutMapping("/{ids}")
    public AjaxResult auditInfo(@PathVariable Long[] ids, @RequestBody Map<String,Object> params)
    {
        return toAjax(accountInfoService.auditAccountInfoByIds(ids,params));
    }
    /**
     * 交易猫账号推送
     */
	@GetMapping("/jymPush")
    public AjaxResult jymPush(@RequestParam Map<String,Object> params)
    {
        accountInfoService.jymPush(params);
        return AjaxResult.success();
    }
    /**
     * 审核游戏账号
     */
	@GetMapping("byColums/{colum}/{type}")
    public AjaxResult byColums(@PathVariable("colum") String colum,
                               @PathVariable("type") String type)
    {
        return AjaxResult.success(accountInfoService.byColums(colum,type));
    }


    @PreAuthorize("@ss.hasPermi('system:recovery:audit')")
    @Log(title = "游戏账号审核包赔操作", businessType = BusinessType.UPDATE)
    @PutMapping("/auditInfo")
    public AjaxResult auditInfo(@RequestBody RecoveryAccountInfo accountInfo)
    {
        return toAjax(accountInfoService.auditInfo(accountInfo));
    }

    @PreAuthorize("@ss.hasPermi('system:recovery:add')")
    @Log(title = "操作回收游戏账号游戏账号", businessType = BusinessType.UPDATE)
    @PutMapping("/recoveryInfo")
    public AjaxResult recoveryInfo(@RequestBody RecoveryAccountInfo accountInfo){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //设置回收人
        accountInfo.setRecoveryId(loginUser.getUserId());
        accountInfo.setRecoveryName(loginUser.getUsername());
        accountInfo.setUpdateId(loginUser.getUserId());
        accountInfo.setUpdateBy(loginUser.getUsername());
        accountInfo.setUpdateTime(new Date());
        //设置回收时间
        accountInfo.setRecoveryTime(new Date());
	    return toAjax(recoveryAccountInfoService.updateRecoveryAccountInfo(accountInfo));
    }

    @Log(title = "一键更新", businessType = BusinessType.UPDATE)
    @PostMapping("/synchronousAcount")
    public AjaxResult synchronousAcount(@RequestBody SynchronDto synchronDto)
    {
        accountInfoService.synchronousAcount(synchronDto);
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('system:resources:add')")
    @Log(title = "客服点回收按钮，申请至待回收页面", businessType = BusinessType.UPDATE)
    @PutMapping("/updateToBeRecycled/{ids}")
    public AjaxResult updateToBeRecycled(@PathVariable Long[] ids)
    {
        return toAjax(accountInfoService.updateToBeRecycled(ids));
    }

    @GetMapping("/allSale")
    public AjaxResult allSale()
    {
        List<String> result = accountInfoService.allSale();
        return AjaxResult.success(result);
    }

    /**
     * 是否可擦亮
     * @return
     */
    @GetMapping("/ifPolish")
    public AjaxResult ifPolish()
    {
        boolean b = accountInfoService.ifPolish();
        return AjaxResult.success(b);
    }

//---------------------------------------------------------终端接口--------------------------------------------
    /**
     * 终端账号池
     * @param accountInfo
     * @return
     */
    @GetMapping("/cmdPoollist")
    public TableDataInfo cmdPoollist(AccountInfo accountInfo)
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        accountInfo.setPageNum(pageNum);
        accountInfo.setPageSize(pageSize);
        log.info("终端请求参数={}", JSON.toJSONString(accountInfo));
        TableDataInfo tableDataInfo = accountInfoService.cmdPoollist(accountInfo);
        log.info("终端返回参数={}", JSON.toJSONString(tableDataInfo));
        return tableDataInfo;
    }
    /**
     * 供货列表
     * @return
     */
    @GetMapping("/supplyIdList")
    public List<JSONObject> supplyIdList()
    {
        List<JSONObject> result = new ArrayList<>();
        return result;
    }

    /**
     * 新增为终端我的账号
     */
    @Log(title = "新增为终端我的账号", businessType = BusinessType.INSERT)
    @GetMapping("addCmdAccount/{ids}")
    public AjaxResult addCmdAccount(@PathVariable Long[] ids)
    {
        String msg = accountInfoService.addCmdAccount(ids);
        return AjaxResult.success(msg);
    }
    /**
     * 批量新增为终端我的账号
     */
    @Log(title = "批量新增为终端我的账号", businessType = BusinessType.INSERT)
    @GetMapping("bacthAddAccount")
    public AjaxResult bacthAddAccount(Long suplyId)
    {
        String msg = accountInfoService.bacthAddAccount(suplyId);
        return AjaxResult.success(msg);
    }
    /**
     * 批量新增为终端我的账号
     */
    @Log(title = "批量删除账号", businessType = BusinessType.INSERT)
    @GetMapping("delAccount")
    public AjaxResult delAccount(Long suplyId)
    {
        String msg = accountInfoService.delAccount(suplyId);
        return AjaxResult.success(msg);
    }




}
