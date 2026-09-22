//package com.ruoyi.system.pay.controller;
//
//
//import com.alibaba.fastjson.JSONObject;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.ruoyi.common.core.controller.BaseController;
//import com.ruoyi.common.core.domain.entity.SysUser;
//import com.ruoyi.common.utils.DateUtils;
//import com.ruoyi.common.utils.StringUtils;
//import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoMapper;
//import com.ruoyi.system.V2.submitAccount.mapper.OrderRecMapper;
//import com.ruoyi.system.V2.submitAccount.service.IOrderRecService;
//import com.ruoyi.system.mapper.SysUserMapper;
//import com.ruoyi.system.model.domain.GoodsInfo;
//import com.ruoyi.system.model.domain.OrderRec;
//import com.ruoyi.system.pay.domain.PayRecord;
//import com.ruoyi.system.pay.service.IPayRecordService;
//import com.ruoyi.system.push.service.IPushService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections4.CollectionUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author zyy
// * @date 2023-09-12 16:12:33
// */
//@Slf4j
//@RestController
//@RequestMapping("/pay")
//public class PayNotifyControllerBak extends BaseController {
//
//    @Autowired
//    private IPayRecordService payRecordService;
//    @Autowired
//    private IOrderRecService orderRecService;
//    @Autowired
//    private OrderRecMapper orderRecMapper;
//    @Autowired
//    private IPushService pushService;
//    @Autowired
//    private SysUserMapper sysUserMapper;
//    @Autowired
//    private GoodsInfoMapper goodsInfoMapper;
//
//    @PostMapping("/notifyBak")
//    public String notify(@RequestParam Map<String, String> params) {
//        String result = "failure";
//        Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
//        while (iter.hasNext()) {
//            Map.Entry<String, String> entry = iter.next();
//            System.out.println(entry.getKey() + "\t" + entry.getValue());
//        }
//        try {
//            Date nowDate = DateUtils.getNowDate();
//            //1.验证out_trade_no 是否为商家系统中创建的订单号
//            String outTradeNo = params.get("out_trade_no");
//            //2.判断 total_amount 是否确实为该订单的实际金额
//            String totalAmount = params.get("total_amount");
//            //3.校验通知中的 seller_id是否为 out_trade_no 这笔单据的对应的操作方
//            String sellerId = params.get("seller_id");
//            //4.验证 app_id 是否为该商家本身
//            String appId = params.get("app_id");
//
//            String merchantId = params.get("yd_merchant_id");
//            String userId = params.get("yd_user_id");
//
//            LambdaQueryWrapper<PayRecord> lambdaQueryWrapper = new LambdaQueryWrapper();
//            lambdaQueryWrapper.eq(PayRecord::getCenterNo, outTradeNo);
//            lambdaQueryWrapper.eq(PayRecord::getPayMerchantId, merchantId);
////            lambdaQueryWrapper.eq(PayRecord::getPayTotalMoney, totalAmount);
//            lambdaQueryWrapper.eq(PayRecord::getUserId, userId);
//            PayRecord payRecord = payRecordService.getOne(lambdaQueryWrapper);
//            if (StringUtils.isNull(payRecord)) {
//                log.error("未查询到订单");
//                return result;
//            }
//
//            payRecord.setOrderState(2);
//            //交易订单创建时间
//            String gmtCreate = params.get("gmt_create");
//            //交易付款时间
//            String gmtPayment = params.get("gmt_payment");
//            //回调时间
//            String notifyTime = params.get("notify_time");
//            //回调id
//            String notifyId = params.get("notify_id");
//            //买家id
//            String buyerId = params.get("buyer_id");
//            //交易流水号
//            String tradeNo = params.get("trade_no");
//
//            if (StringUtils.isNotEmpty(gmtCreate)) {
//                payRecord.setPayCreateTime(DateUtils.parseDate(gmtCreate));
//            }
//            if (StringUtils.isNotEmpty(gmtPayment)) {
//                nowDate = DateUtils.parseDate(gmtPayment);
//            }
//            payRecord.setPayEndTime(nowDate);
//            if (StringUtils.isNotEmpty(notifyTime)) {
//                payRecord.setPayNotifyTime(DateUtils.parseDate(notifyTime));
//            }
//            payRecord.setPayNotifyId(notifyId);
//            payRecord.setBuyerId(buyerId);
//            payRecord.setPayNo(tradeNo);
//
//
//            payRecordService.updateById(payRecord);
//            OrderRec orderRec = orderRecService.selectOrderRecById(payRecord.getBusinessId());
//            if (StringUtils.isNotNull(orderRec)) {
//                orderRec.setOderstatus("4");
//                orderRec.setIfPay("1");
////                orderRec.setFinishTime(payRecord.getPayEndTime());
//                QueryWrapper<PayRecord> queryWrapper = new QueryWrapper<>();
//                queryWrapper.select("sum(pay_total_money) as payTotalMoney");
//                queryWrapper.eq("order_state" , 2);
//                queryWrapper.eq("business_id" , orderRec.getId());
//                List<PayRecord> list = payRecordService.list(queryWrapper);
//                if (CollectionUtils.isNotEmpty(list)) {
//                    PayRecord payRecord1 = list.get(0);
//                    if (StringUtils.isNotNull(payRecord1.getPayTotalMoney())) {
//                        orderRec.setPaidAmount(BigDecimal.valueOf(payRecord1.getPayTotalMoney()));
//                    }
//                }
//                orderRecMapper.updateOrderRec(orderRec);
//                push(orderRec, totalAmount);
//            }
//            result = "success";
//            log.info("回调正常走完了");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    void push(OrderRec orderRec, String totalAmount) {
//        if (StringUtils.isNotEmpty(orderRec.getAccountid())) {
//            GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(Long.valueOf(orderRec.getAccountid()));
//            buyer(orderRec);
//            supplyer(goodsInfo);
//        }
//    }
//
//    /**
//     * 买家付款成功 提醒买家
//     *
//     * @param orderRec
//     */
//    void buyer(OrderRec orderRec) {
//        SysUser sysUser = sysUserMapper.selectUserById(orderRec.getSalechanelid());
//        if (StringUtils.isNotNull(sysUser)) {
//            String[] getuiAlias = new String[]{sysUser.getUserName()};
//            String title = "【订单消息】";
//            String conten = "【您下单编号为" + orderRec.getAccountid() + "的商品付款成功，点击查看详情】";
//            JSONObject payload = new JSONObject();
//            payload.put("type" , "1000");
//            Integer pushType = 2;
//            pushService.push(getuiAlias, title, conten, payload, pushType);
//        }
//    }
//
//    /**
//     * 买家付款成功 通知卖家
//     *
//     * @param goodsInfo
//     */
//    void supplyer(GoodsInfo goodsInfo) {
//        SysUser sysUser = sysUserMapper.selectUserById(goodsInfo.getCreateId());
//        if (StringUtils.isNotNull(sysUser)) {
//            String[] getuiAlias = new String[]{sysUser.getUserName()};
//            String title = "【订单消息】";
//            String conten = "【您寄售编号为" + goodsInfo.getId() + "的商品已有人购买，点击查看详情】";
//            JSONObject payload = new JSONObject();
//            payload.put("type" , "2002");
//            Integer pushType = 2;
//            pushService.push(getuiAlias, title, conten, payload, pushType);
//        }
//    }
//
//}
//
