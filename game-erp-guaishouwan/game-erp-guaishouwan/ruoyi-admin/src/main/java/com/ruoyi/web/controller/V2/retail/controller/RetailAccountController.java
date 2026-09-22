package com.ruoyi.web.controller.V2.retail.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.V2.goodInfo.service.IGoodsInfoService;
import com.ruoyi.system.V2.retail.service.IRetailAccountService;
import com.ruoyi.system.model.domain.GameInfo;
import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.model.domain.RetailAddPrice;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueReq;
import com.ruoyi.system.service.ISysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 零售号Controller
 *
 * @author ruoyi
 * @date 2022-05-27
 */
@RestController
@RequestMapping("/account/account")
public class RetailAccountController extends BaseController {
    @Autowired
    private IRetailAccountService retailAccountService;

    @Autowired
    private IGoodsInfoService goodsInfoService;


    @Autowired
    private ISysUserService userService;

    /**
     * 查询零售号列表
     */
    @GetMapping("/list")
    public TableDataInfo list(GoodsInfoAndAttrValueReq goodsInfo) {
        Long userId = SecurityUtils.getUserId();
        List<RetailAddPrice> param = retailAccountService.param(goodsInfo, userId);
        if (CollectionUtils.isEmpty(param)) {
            return getDataTable(new ArrayList<>());
        }
        PageDomain pageDomain = TableSupport.buildPageRequest();
        List<GoodsInfo> goodsInfos = goodsInfoService.selectByResList(goodsInfo, pageDomain);
        List<SysUser> supplyCustomer = userService.getUserByRoleCode("supplyCustomer");
        goodsInfoService.bacthAccRetail(goodsInfos, supplyCustomer, userId);
        for (GoodsInfo info : goodsInfos) {
            if (Objects.isNull(info.getNewPrice())) {
                info.setPrice(info.getPrice().setScale(0, BigDecimal.ROUND_UP));
            } else {
                info.setPrice(info.getNewPrice().setScale(0, BigDecimal.ROUND_UP));
            }

            if (Objects.equals(info.getCreateId(), userId)) {
                info.setIfself(1);
            } else {
                info.setIfself(2);
            }
        }
        retailAccountService.bacthAcc(goodsInfos, param);
        for (GoodsInfo info : goodsInfos) {
            info.setGameName("");
            info.setSupplyId(null);
        }
        return getDataTable(goodsInfos);
    }

    /**
     * 获查询分销选择的游戏
     */
    @GetMapping(value = "/getGameType")
    public AjaxResult getGameType() {
        List<GameInfo> result = retailAccountService.getGameType(null, null);
        return AjaxResult.success(result);
    }

    /**
     * 获取零售号详细信息
     */
    @PreAuthorize("@ss.hasPermi('account:account:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(retailAccountService.selectRetailAccountById(id));
    }

    /**
     * 批量加价
     */
    @Log(title = "批量加价")
    @GetMapping("bacthAddPrice")
    public AjaxResult bacthAddPrice(@RequestParam Map<String, Object> params) {
        retailAccountService.bacthAddPrice(params);
        return AjaxResult.success();
    }

    /**
     * 批量加价
     */
    @Log(title = "批量加价回显")
    @GetMapping("bacthAddPriceDetails")
    public AjaxResult bacthAddPriceDetails(@RequestParam Map<String, Object> params) {
        Map<String, Object> result = retailAccountService.bacthAddPriceDetails(params);
        return AjaxResult.success(result);
    }

    /**
     * 删除零售号
     */
    @Log(title = "零售号", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(retailAccountService.deleteRetailAccountByIds(ids));
    }
}
