package com.ruoyi.web.controller.V2.font.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.V2.goodInfo.service.IGoodsInfoService;
import com.ruoyi.system.V2.inter.service.IApiTaskPoolService;
import com.ruoyi.system.V2.submitAccount.service.IOrderRecService;
import com.ruoyi.system.model.domain.GoodsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 游戏属性Controller
 *
 * @author ruoyi
 * @date 2023-02-22
 */
@RestController
@RequestMapping("/indexData/count")
public class IndexDataCountController extends BaseController {

    //    @Autowired
//    private IAccountInfoService accountInfoService;
    @Autowired
    private IApiTaskPoolService apiTaskPoolService;
    @Autowired
    private IOrderRecService orderRecService;

    @Autowired
    private IGoodsInfoService goodsInfoService;

    /**
     * 供货商出号统计
     */
    @GetMapping("/subOrder")
    public AjaxResult subOrder() {
        List<Map<String, Object>> result = orderRecService.subOrder();
        Collections.sort(result, (o1, o2) -> Integer.parseInt(o2.get("cot").toString()) - Integer.parseInt(o1.get(
                "cot").toString()));

        return AjaxResult.success(result);
    }

    /**
     * 销售卖号统计
     */
    @GetMapping("/sellOrder")
    public AjaxResult sellOrder() {
        List<Map<String, Object>> result = orderRecService.sellOrder();
        Collections.sort(result, (o1, o2) -> Integer.parseInt(o2.get("cot").toString()) - Integer.parseInt(o1.get(
                "cot").toString()));
        return AjaxResult.success(result);
    }


    /**
     * 账号统计
     */
    @GetMapping("/account")
    public AjaxResult account(@RequestParam Map<String, Object> params) {
        return AjaxResult.success();
    }

    /**
     * 问题账号统计
     */
    @GetMapping("/erraccount")
    public AjaxResult erraccount(@RequestParam Map<String, Object> params) {
        return AjaxResult.success();
    }


    /**
     * 十天不上新用户
     */
    @GetMapping("/notShangxin")
    public AjaxResult notShangxin(@RequestParam Map<String, Object> params) {
        return AjaxResult.success();
    }


    /**
     * 供货商账号统计
     */
    @GetMapping("/userGameCount")
    public AjaxResult userGameCount() {

        List<Map<String, Object>> result = goodsInfoService.userGameCount();
        Collections.sort(result, (o1, o2) -> Integer.parseInt(o2.get("cot").toString()) - Integer.parseInt(o1.get(
                "cot").toString()));

        return AjaxResult.success(result);
    }

    /**
     * 当前任务数
     */
    @GetMapping("/taskCount")
    public AjaxResult taskCount() {

        List<Map<String, Object>> result = apiTaskPoolService.taskCount();
        Collections.sort(result, (o1, o2) -> Integer.parseInt(o2.get("cot").toString()) - Integer.parseInt(o1.get(
                "cot").toString()));

        return AjaxResult.success(result);
    }

    /**
     * 游戏统计
     */
    @GetMapping("/GameTypeCount")
    public AjaxResult GameTypeCount() {
        List<Map<String, Object>> result = goodsInfoService.countForSupply(new GoodsInfo());
        Collections.sort(result, (o1, o2) -> Integer.parseInt(o2.get("cot").toString()) - Integer.parseInt(o1.get(
                "cot").toString()));

        return AjaxResult.success(result);
    }

}
