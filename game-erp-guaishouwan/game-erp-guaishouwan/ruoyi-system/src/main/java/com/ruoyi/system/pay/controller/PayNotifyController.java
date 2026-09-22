package com.ruoyi.system.pay.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoMapper;
import com.ruoyi.system.V2.submitAccount.mapper.OrderRecMapper;
import com.ruoyi.system.V2.submitAccount.service.IOrderRecService;
import com.ruoyi.system.V2.tool.service.IBacklistService;
import com.ruoyi.system.backorder.service.IBackOrderService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.model.domain.OrderRec;
import com.ruoyi.system.pay.domain.PayRecord;
import com.ruoyi.system.pay.service.IPayRecordService;
import com.ruoyi.system.pay.vo.UnifyPayRes;
import com.ruoyi.system.push.service.IPushService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author zyy
 * @date 2023-09-12 16:12:33
 */
@Slf4j
@RestController
@RequestMapping("/pay")
public class PayNotifyController extends BaseController {

    @Autowired
    private IPayRecordService payRecordService;
    @Autowired
    private IOrderRecService orderRecService;
    @Autowired
    private OrderRecMapper orderRecMapper;
    @Autowired
    private IPushService pushService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
    @Autowired
    private IBacklistService backlistService;
    @Autowired
    private IBackOrderService backOrderService;

    @PostMapping("/notify")
    public String notify(@RequestParam Map<String, String> params) {
        String result = "failure";
        try {
            UnifyPayRes unifyPayRes = JSON.parseObject(JSON.toJSONString(params), UnifyPayRes.class);
            if (StringUtils.isNull(unifyPayRes)) {
                log.error("返回值为空");
                return result;
            }
            System.out.println(unifyPayRes.toString());
            LambdaQueryWrapper<PayRecord> lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.eq(PayRecord::getCenterNo, unifyPayRes.getCenterNo());
            lambdaQueryWrapper.eq(PayRecord::getPayMerchantId, unifyPayRes.getPayMerchantId());
            lambdaQueryWrapper.eq(PayRecord::getUserId, unifyPayRes.getUserId());
            PayRecord payRecord = payRecordService.getOne(lambdaQueryWrapper);
            if (StringUtils.isNull(payRecord)) {
                log.error("未查询到订单");
                result = "success";
                return result;
            }
            payRecord.setOrderState(2);
            payRecord.setPayCreateTime(unifyPayRes.getPayCreateTime());
            payRecord.setPayEndTime(unifyPayRes.getPayEndTime());
            payRecord.setPayNotifyTime(unifyPayRes.getPayNotifyTime());
            payRecord.setPayNotifyId(unifyPayRes.getPayNotifyId());
            payRecord.setBuyerId(unifyPayRes.getBuyerId());
            payRecord.setPayNo(unifyPayRes.getPayNo());
            payRecordService.updateById(payRecord);
            payRecordService.closeOtherOrders(payRecord);
            if (payRecord.getOrderType() == 1) {
                OrderRec orderRec = orderRecService.selectOrderRecById(payRecord.getBusinessId());
                if (StringUtils.isNotNull(orderRec)) {
                    orderRec.setOderstatus("4");
                    orderRec.setIfPay("1");
                    orderRec.setPayRecordId(payRecord.getId());
                    QueryWrapper<PayRecord> queryWrapper = new QueryWrapper<>();
                    queryWrapper.select("sum(pay_total_money) as payTotalMoney");
                    queryWrapper.eq("order_state", 2);
                    queryWrapper.eq("business_id", orderRec.getId());
                    List<PayRecord> list = payRecordService.list(queryWrapper);
                    if (CollectionUtils.isNotEmpty(list)) {
                        PayRecord payRecord1 = list.get(0);
                        if (StringUtils.isNotNull(payRecord1.getPayTotalMoney())) {
                            orderRec.setPaidAmount(BigDecimal.valueOf(payRecord1.getPayTotalMoney()));
                        }
                    }
                    orderRecMapper.updateOrderRec(orderRec);
//                    push(orderRec, String.valueOf(payRecord.getPayTotalMoney()));
                }
            } else if (payRecord.getOrderType() == 6) {
                backlistService.sucessOrder(payRecord);
            } else if (payRecord.getOrderType() == 7) {
                backOrderService.sucessOrder(payRecord);
            }
            result = "success";
            log.info("回调正常走完了");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    void push(OrderRec orderRec, String totalAmount) {
        if (StringUtils.isNotEmpty(orderRec.getAccountid())) {
            GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(Long.valueOf(orderRec.getAccountid()));
            buyer(orderRec);
            supplyer(goodsInfo);
        }
    }

    /**
     * 买家付款成功 提醒买家
     *
     * @param orderRec
     */
    void buyer(OrderRec orderRec) {
        SysUser sysUser = sysUserMapper.selectUserById(orderRec.getSalechanelid());
        if (StringUtils.isNotNull(sysUser)) {
            String[] getuiAlias = new String[]{sysUser.getUserName()};
            String title = "【订单消息】";
            String conten = "【您下单编号为" + orderRec.getAccountid() + "的商品付款成功，点击查看详情】";
            JSONObject payload = new JSONObject();
            payload.put("type", "1000");
            Integer pushType = 2;
            pushService.push(getuiAlias, title, conten, payload, pushType);
        }
    }

    /**
     * 买家付款成功 通知卖家
     *
     * @param goodsInfo
     */
    void supplyer(GoodsInfo goodsInfo) {
        SysUser sysUser = sysUserMapper.selectUserById(goodsInfo.getCreateId());
        if (StringUtils.isNotNull(sysUser)) {
            String[] getuiAlias = new String[]{sysUser.getUserName()};
            String title = "【订单消息】";
            String conten = "【您寄售编号为" + goodsInfo.getId() + "的商品已有人购买，点击查看详情】";
            JSONObject payload = new JSONObject();
            payload.put("type", "2002");
            Integer pushType = 2;
            pushService.push(getuiAlias, title, conten, payload, pushType);
        }
    }

}

