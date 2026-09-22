package com.ruoyi.web.controller.V2.financial.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.system.model.domain.BillType;
import com.ruoyi.system.model.domain.Finance;
import com.ruoyi.system.model.exel.FinanceExcel;
import com.ruoyi.system.V2.financial.mapper.BillTypeMapper;
import com.ruoyi.system.V2.financial.service.IFinanceService;
import com.ruoyi.system.service.impl.SysUserServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.openxml4j.util.ZipSecureFile;
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
 * 财务报表Controller
 *
 * @author ruoyi
 * @date 2023-02-24
 */
@RestController
@RequestMapping("/account/finance")
public class FinanceController extends BaseController {
    @Autowired
    private IFinanceService financeService;
    @Autowired
    private SysUserServiceImpl sysUserService;
    @Resource
    private BillTypeMapper billTypeMapper;

    /**
     * 查询财务报表列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Finance finance) {
        startPage();
        List<Finance> list = financeService.selectFinanceList(finance);
        return getDataTable(list);
    }

    /**
     * 查询财务报表列表
     */
    @GetMapping("/myList")
    public TableDataInfo myList(Finance finance) {
        startPage();
        finance.setCreateId(SecurityUtils.getUserId());
        List<Finance> list = financeService.selectFinanceList(finance);
        return getDataTable(list);
    }

    /**
     * 查询财务报表列表
     */
    @GetMapping("/data")
    public AjaxResult data(Finance finance) {
        return AjaxResult.success(financeService.data(finance));
    }

    /**
     * 查询财务报表列表
     */
    @GetMapping("/statisticsData")
    public AjaxResult statisticsData(Finance finance) {
        Long userId = SecurityUtils.getUserId();
        String username = SecurityUtils.getUsername();
        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("local_user_account_config");
        if (CollectionUtils.isNotEmpty(sysDictDataList)) {
            for (SysDictData sysDictData : sysDictDataList) {
                if (StringUtils.isNotEmpty(sysDictData.getDictValue())) {
                    if (StringUtils.equals(sysDictData.getDictValue(), username)) {
                        userId = 106L;
                    }
                }
            }
        }
        if (Objects.equals(userId,1L)){
            userId = null;
        }
        JSONObject result = new JSONObject();
        finance.setCreateId(userId);
        finance.setIotype("2");
        finance.setStatus(1L);
        List<Map<String, Object>> mapList = financeService.statisticsData(finance);
        result.put("revenue", mapList);
        finance.setIotype("1");
        finance.setStatus(1L);
        List<Map<String, Object>> mapList1 = financeService.statisticsData(finance);
        result.put("pay", mapList1);
        return AjaxResult.success(result);
    }

    /**
     * 导出财务报表列表
     */
    @Log(title = "财务报表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Finance finance) {
        List<Finance> list = financeService.selectFinanceList(finance);
        List<FinanceExcel> financeExcels = new ArrayList<>();
        for (Finance finance1 : list) {
            FinanceExcel financeExcel = new FinanceExcel();
            financeExcel.setPrice(String.valueOf(finance1.getPrice()));
            financeExcel.setAtta(finance1.getRemark());
            if (StringUtils.isEmpty(finance1.getIotype())) {
                finance1.setIotype("2");
            }
            BillType billType = billTypeMapper.selectBillTypeById(finance1.getBillType());
            financeExcel.setBillType(billType.getRname());
            if (finance1.getIotype().contains("2")) {
                financeExcel.setIotype("收入");
            }
            if (finance1.getIotype().contains("1")) {
                financeExcel.setIotype("支出");
            }
            if (StringUtils.isNotNull(finance1.getStatus())) {
                if (String.valueOf(finance1.getStatus()).equals("1")) {
                    financeExcel.setStatus("待支付");
                }
                if (String.valueOf(finance1.getStatus()).equals("2")) {
                    financeExcel.setStatus("未支付");
                }
            }
            financeExcel.setField1(finance1.getField1() == null ? "" : finance1.getField1());
            financeExcel.setField2(finance1.getField2() == null ? "" : finance1.getField2());
            financeExcel.setField3(finance1.getField3() == null ? "" : finance1.getField3());
            financeExcel.setField4(finance1.getField4() == null ? "" : finance1.getField4());
            financeExcel.setField5(finance1.getField5() == null ? "" : finance1.getField5());
            financeExcel.setField6(finance1.getField6() == null ? "" : finance1.getField6());
            financeExcel.setRegisteruser(finance1.getRegisteruser());
            financeExcels.add(financeExcel);
        }
        ExcelUtil<FinanceExcel> util = new ExcelUtil<>(FinanceExcel.class);
        return util.exportExcel(financeExcels, "财务报表数据");
    }

    /**
     * 导出财务报表列表
     */
    @Log(title = "财务报表模板导出", businessType = BusinessType.EXPORT)
    @GetMapping("/exportModel")
    public AjaxResult exportModel() {
        ExcelUtil<FinanceExcel> util = new ExcelUtil<FinanceExcel>(FinanceExcel.class);
        return util.importTemplateExcel("财务中心模板导出");
    }

    @Log(title = "财务报表模板导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Finance> util = new ExcelUtil<Finance>(Finance.class);
        ZipSecureFile.setMinInflateRatio(-1.0d);
        List<Finance> financeList = util.importExcel(file.getInputStream());
        String message = financeService.importAccount(financeList);
        return AjaxResult.success(message);
    }

    //根据id获取用户姓名
    @GetMapping("/getUserName")
    public AjaxResult getUserName(Long userId) {
        SysUser sysUser = sysUserService.selectUserById(userId);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("nickName", sysUser.getNickName());
        ajax.put("userName", sysUser.getUserName());
        return ajax;
    }

    /**
     * 获取财务报表详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(financeService.selectFinanceById(id));
    }

    /**
     * 新增财务报表
     */
    @Log(title = "财务报表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Finance finance) {
        return toAjax(financeService.insertFinance(finance));
    }

    /**
     * 新增财务报表H5用
     */
    @Log(title = "新增财务报表H5用", businessType = BusinessType.INSERT)
    @PostMapping(value = "/addH5")
    public AjaxResult addH5(@RequestBody Finance finance) {
        return toAjax(financeService.insertFinanceH5(finance));
    }

    /**
     * 修改财务报表
     */
    @Log(title = "财务报表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Finance finance) {
        return toAjax(financeService.updateFinance(finance));
    }

    /**
     * 删除财务报表
     */
    @Log(title = "财务报表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids, String pwd) {
        return toAjax(financeService.deleteFinanceByIds(ids, pwd));
    }

    @Autowired
    SysDictDataMapper sysDictDataMapper;

    @GetMapping("/securityPwd")
    public AjaxResult securityPwd(String pwd) {
        String securityPwd = "qwerasdfzxcv";
        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("securityPwd");
        for (SysDictData dictData : sysDictDataList) {
            if ("securityPwd".equals(dictData.getDictLabel())) {
                securityPwd = dictData.getDictValue();
            }
        }
        if (StringUtils.isNotEmpty(securityPwd)) {
            if (StringUtils.equals(pwd, securityPwd)) {

            } else {
                throw new GlobalException("密码错误");
            }
        }
        return AjaxResult.success();
    }
}
