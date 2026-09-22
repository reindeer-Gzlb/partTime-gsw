package com.ruoyi.web.controller.V2.font.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.V2.font.service.IIndexService;
import com.ruoyi.system.model.domain.GoodsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author lz
 * @version 1.0
 * @date 2022/9/26 16:03
 */
@RestController
@RequestMapping("/indexData")
public class IndexDataController {


    @Autowired
    private IIndexService indexService;

    /**
     * 全部 未售 已售 自售
     */
    @GetMapping(value = "/salesCount")
    public AjaxResult salesCount(GoodsInfo goodsInfo) {
        Map<String, Object> sales = indexService.salesCount(goodsInfo);
        return AjaxResult.success(sales);
    }

    /**
     * 当月营业额  本周营业额  当天营业额  本年营业额
     */
    @GetMapping(value = "/salesSum")
    public AjaxResult salesSum() {
        Map<String, Object> sales = indexService.salesSum();
        return AjaxResult.success(sales);
    }


    /**
     * 获取最近一年
     */
    @GetMapping(value = "/gameTypeSale")
    public AjaxResult gameTypeSale(@RequestParam Map<String, Object> params) {
        Map<String, Object> stringObjectMap = indexService.gameTypeSale(params);
        return AjaxResult.success(stringObjectMap);
    }

    /**
     * 不同游戏销量占比
     */
    @GetMapping(value = "/gameTypeFor")
    public AjaxResult gameTypeFor() {
        Map<String, Object> gameTypeFor = indexService.gameTypeFor();
        return AjaxResult.success(gameTypeFor);
    }

    /**
     * 获取最近七天数据
     */
    @GetMapping(value = "/sevenDaysCount")
    public AjaxResult sevenDaysCount() {
        Map<String, Object> gameTypeFor = indexService.sevenDaysCount();
        return AjaxResult.success(gameTypeFor);
    }

    /**
     * 获取最近一年
     */
    @GetMapping(value = "/yearCount")
    public AjaxResult yearCount() {
        Map<String, Object> stringObjectMap = indexService.yearCount();
        return AjaxResult.success(stringObjectMap);
    }

    @GetMapping(value = "/gameTypeGon")
    public AjaxResult gameTypeGon() {
        Map<String, Object> sales = indexService.gameTypeGon();
        return AjaxResult.success(sales);
    }


}
