package com.ruoyi.web.controller.V2.financial.controller;

import java.util.List;

import com.ruoyi.system.model.domain.BillType;
import com.ruoyi.system.V2.financial.service.IBillTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 账单类型Controller
 * 
 * @author ruoyi
 * @date 2023-02-24
 */
@RestController
@RequestMapping("/account/billType")
public class BillTypeController extends BaseController
{
    @Autowired
    private IBillTypeService billTypeService;

    /**
     * 查询账单类型列表
     */
    @GetMapping("/list")
    public TableDataInfo list(BillType billType)
    {
        startPage();
        List<BillType> list = billTypeService.selectBillTypeList(billType);
        return getDataTable(list);
    }
    /**
     * 查询账单类型列表
     */
    @GetMapping("/listCW")
    public TableDataInfo listCW(BillType billType)
    {
        startPage();
        List<BillType> list = billTypeService.selectBillTypeListByCW(billType);
        return getDataTable(list);
    }

    /**
     * 查询账单类型列表H5用
     */
    @GetMapping("/h5List")
    public TableDataInfo h5List(BillType billType)
    {
        startPage();
        List<BillType> list = billTypeService.selectH5BillTypeList(billType);
        return getDataTable(list);
    }


    /**
     * 导出账单类型列表
     */
    @Log(title = "账单类型", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BillType billType)
    {
        List<BillType> list = billTypeService.selectBillTypeList(billType);
        ExcelUtil<BillType> util = new ExcelUtil<BillType>(BillType.class);
        return util.exportExcel(list, "账单类型数据");
    }

    /**
     * 获取账单类型详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(billTypeService.selectBillTypeById(id));
    }

    /**
     * 新增账单类型
     */
    @Log(title = "账单类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BillType billType)
    {
        return toAjax(billTypeService.insertBillType(billType));
    }

    /**
     * 修改账单类型
     */
    @Log(title = "账单类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BillType billType)
    {
        return toAjax(billTypeService.updateBillType(billType));
    }

    /**
     * 删除账单类型
     */
    @Log(title = "账单类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(billTypeService.deleteBillTypeByIds(ids));
    }
}
