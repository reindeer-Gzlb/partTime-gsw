package com.ruoyi.system.recovery.controller;

import java.util.List;
import java.util.Objects;

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
import com.ruoyi.system.recovery.domain.GoodsRecoveryInfo;
import com.ruoyi.system.recovery.service.IGoodsRecoveryInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 回收管理Controller
 *
 * @author ruoyi
 * @date 2024-04-20
 */
@RestController
@RequestMapping("/recovery/info")
public class GoodsRecoveryInfoController extends BaseController {
    @Autowired
    private IGoodsRecoveryInfoService goodsRecoveryInfoService;

    /**
     * 查询回收管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(GoodsRecoveryInfo goodsRecoveryInfo) {
        startPage();
        List<GoodsRecoveryInfo> list = goodsRecoveryInfoService.selectGoodsRecoveryInfoList(goodsRecoveryInfo);
        return getDataTable(list);
    }

    /**
     * 查询回收管理列表
     */
    @GetMapping("/myList")
    public TableDataInfo myList(GoodsRecoveryInfo goodsRecoveryInfo) {
        startPage();
        goodsRecoveryInfo.setCreateId(SecurityUtils.getUserId());
        List<GoodsRecoveryInfo> list = goodsRecoveryInfoService.selectGoodsRecoveryInfoList(goodsRecoveryInfo);
        return getDataTable(list);
    }
    /**
     * 导出回收管理列表
     */
    @Log(title = "回收管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GoodsRecoveryInfo goodsRecoveryInfo) {
        List<GoodsRecoveryInfo> list = goodsRecoveryInfoService.selectGoodsRecoveryInfoList(goodsRecoveryInfo);
        ExcelUtil<GoodsRecoveryInfo> util = new ExcelUtil<GoodsRecoveryInfo>(GoodsRecoveryInfo.class);
        return util.exportExcel(list, "回收管理数据");
    }

    /**
     * 获取回收管理详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(goodsRecoveryInfoService.selectGoodsRecoveryInfoById(id));
    }

    /**
     * 新增回收管理
     */
    @Log(title = "回收管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsRecoveryInfo goodsRecoveryInfo) {
        return toAjax(goodsRecoveryInfoService.insertGoodsRecoveryInfo(goodsRecoveryInfo));
    }

    /**
     * 修改回收管理
     */
    @Log(title = "回收管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsRecoveryInfo goodsRecoveryInfo) {
        return toAjax(goodsRecoveryInfoService.updateGoodsRecoveryInfo(goodsRecoveryInfo));
    }

    /**
     * 删除回收管理
     */
    @Log(title = "回收管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids, String pwd) {
        return toAjax(goodsRecoveryInfoService.deleteGoodsRecoveryInfoByIds(ids, pwd));
    }
}
