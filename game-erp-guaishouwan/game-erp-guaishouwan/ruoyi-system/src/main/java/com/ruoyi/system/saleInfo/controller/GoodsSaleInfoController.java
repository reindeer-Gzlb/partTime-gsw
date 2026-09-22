package com.ruoyi.system.saleInfo.controller;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.system.saleInfo.domain.GoodsSaleInfo;
import com.ruoyi.system.saleInfo.service.IGoodsSaleInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 出售记录详情Controller
 *
 * @author ruoyi
 * @date 2024-04-25
 */
@RestController
@RequestMapping("/saleInfo/detail")
public class GoodsSaleInfoController extends BaseController {
    @Autowired
    private IGoodsSaleInfoService goodsSaleInfoService;

    /**
     * 查询出售记录详情列表
     */
    @GetMapping("/list")
    public TableDataInfo list(GoodsSaleInfo goodsSaleInfo) {
        startPage();
        List<GoodsSaleInfo> list = goodsSaleInfoService.selectGoodsSaleInfoList(goodsSaleInfo);
        return getDataTable(list);
    }

    /**
     * 查询出售记录详情列表
     */
    @GetMapping("/myList")
    public TableDataInfo myList(GoodsSaleInfo goodsSaleInfo) {
        startPage();
        goodsSaleInfo.setCreateId(SecurityUtils.getUserId());
        List<GoodsSaleInfo> list = goodsSaleInfoService.selectGoodsSaleInfoList(goodsSaleInfo);
        return getDataTable(list);
    }


    /**
     * 导出出售记录详情列表
     */
    @Log(title = "出售记录详情", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GoodsSaleInfo goodsSaleInfo) {
        List<GoodsSaleInfo> list = goodsSaleInfoService.selectGoodsSaleInfoList(goodsSaleInfo);
        ExcelUtil<GoodsSaleInfo> util = new ExcelUtil<GoodsSaleInfo>(GoodsSaleInfo.class);
        return util.exportExcel(list, "出售记录详情数据");
    }

    /**
     * 获取出售记录详情详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(goodsSaleInfoService.selectGoodsSaleInfoById(id));
    }

    /**
     * 新增出售记录详情
     */
    @Log(title = "出售记录详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsSaleInfo goodsSaleInfo) {
        return toAjax(goodsSaleInfoService.insertGoodsSaleInfo(goodsSaleInfo));
    }

    /**
     * 修改出售记录详情
     */
    @Log(title = "出售记录详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsSaleInfo goodsSaleInfo) {
        return toAjax(goodsSaleInfoService.updateGoodsSaleInfo(goodsSaleInfo));
    }

    /**
     * 删除出售记录详情
     */
    @Log(title = "出售记录详情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids, String pwd) {
        return toAjax(goodsSaleInfoService.deleteGoodsSaleInfoByIds(ids, pwd));
    }
}
