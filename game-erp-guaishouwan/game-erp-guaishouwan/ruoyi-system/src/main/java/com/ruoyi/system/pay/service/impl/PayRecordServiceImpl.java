package com.ruoyi.system.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.BigDecimalUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.goodInfo.service.IGoodsInfoService;
import com.ruoyi.system.V2.submitAccount.mapper.OrderRecMapper;
import com.ruoyi.system.V2.submitAccount.service.IOrderRecService;
import com.ruoyi.system.feign.PayFeignService;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.model.domain.OrderRec;
import com.ruoyi.system.pay.domain.PayRecord;
import com.ruoyi.system.pay.mapper.PayRecordMapper;
import com.ruoyi.system.pay.service.IPayRecordService;
import com.ruoyi.system.pay.vo.UnifyPayReq;
import com.ruoyi.system.pay.vo.UnifyQueryOrderVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 移动支付记录表(pay_record)表服务实现类
 *
 * @author zyy
 * @date 2023-09-15 16:23:58
 */
@Service("payRecordService")
public class PayRecordServiceImpl extends ServiceImpl<PayRecordMapper, PayRecord> implements IPayRecordService {

    @Autowired
    SysDictDataMapper sysDictDataMapper;
    @Autowired
    IOrderRecService orderRecService;
    @Autowired
    IGoodsInfoService goodsInfoService;
    @Autowired
    OrderRecMapper orderRecMapper;

    @Autowired
    PayFeignService payFeignService;

    @Override
    @Transactional
    public PayRecord savePayRecord(PayRecord payRecord) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        if (StringUtils.isNull(payRecord.getGoodsId())) {
            throw new GlobalException("商品编号不能为空");
        }
        GoodsInfo goodsInfo = goodsInfoService.selectGoodsInfoById(payRecord.getGoodsId());
        if (StringUtils.isNull(goodsInfo)) {
            throw new GlobalException("未找到此商品");
        }
        if (goodsInfo.getSaleState() != 1 && goodsInfo.getSaleState() != 2) {
            throw new GlobalException("此商品状态异常");
        }
        if (StringUtils.isNotNull(goodsInfo.getOrderRecId())) {
            OrderRec orderRec = orderRecMapper.selectOrderRecById(goodsInfo.getOrderRecId());
            if (StringUtils.isNotNull(orderRec)) {
                if (StringUtils.equals(orderRec.getOderstatus(), "1")
                        || StringUtils.equals(orderRec.getOderstatus(), "4")) {
                    throw new GlobalException("已出售");
                }
                if (StringUtils.equals(orderRec.getOderstatus(), "3")) {
                    if (user.getUserId().equals(orderRec.getSalechanelid())) {
                        if (StringUtils.isNull(orderRec.getPayRecordId())) {
//                            String no = payRecord.getNo();
//                            Double totalAmount = payRecord.getPayTotalMoney();
//                            Double dj = BigDecimalUtils.mul(totalAmount, 0.1);
//                            if (dj < 50) {
//                                dj = 50d;
//                            }
//                            if (payRecord.getAmountType() == 1) {
//                                totalAmount = dj;
//                                no = no + "-dj";
//                            } else if (payRecord.getAmountType() == 2) {
//                                totalAmount = BigDecimalUtils.sub(totalAmount, dj);
//                                no = no + "-wk";
//                            }
                            payRecord.setNo(orderRec.getNo());
                            payRecord.setUserId(user.getUserId());
                            payRecord.setUserName(user.getNickName());
                            payRecord.setCreateId(user.getUserId());
                            payRecord.setOrderState(1);
                            payRecord.setBusinessId(orderRec.getId());
                            save(payRecord);
                            orderRec.setPayRecordId(payRecord.getId());
                            orderRecMapper.updateOrderRec(orderRec);
                        }
                        throw new GlobalException("待支付");
                    } else {
                        throw new GlobalException("被下单");
                    }
                }
            }
        }
        payRecord.setUserId(user.getUserId());
        payRecord.setUserName(user.getNickName());
        payRecord.setCreateId(user.getUserId());
        payRecord.setOrderState(1);
        save(payRecord);
        OrderRec orderRec = new OrderRec();
        orderRec.setSalechanelid(user.getUserId());
        orderRec.setSalePrice(String.valueOf(payRecord.getPayTotalMoney()));
        orderRec.setSumNum(goodsInfo.getThirdId());
        orderRec.setGames(goodsInfo.getGameName());
        orderRec.setGameid(goodsInfo.getGameId());
        orderRec.setAccountid(String.valueOf(goodsInfo.getId()));
        orderRec.setAccount(goodsInfo.getAccountNumber());
        if (StringUtils.isNotNull(goodsInfo.getPrice())) {
            orderRec.setSuplyprice(goodsInfo.getPrice().toPlainString());
        }
        orderRec.setSupllyId(goodsInfo.getCreateId());
        orderRec.setOderstatus("3");
        orderRec.setSumNum(goodsInfo.getThirdId());

        orderRec.setPayRecordId(payRecord.getId());
        orderRec.setGoodsAmount(payRecord.getGoodsAmount());
        orderRec.setGuaranteeType(payRecord.getGuaranteeType());
        orderRec.setGuaranteeAmount(payRecord.getGuaranteeAmount());
        SysDictData sysDictDataReq = new SysDictData();
        sysDictDataReq.setDictType("payCenter");
        sysDictDataReq.setDictLabel("timeOut");
        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataList(sysDictDataReq);
        int timeOut = 15;
        if (CollectionUtils.isNotEmpty(sysDictDataList) && sysDictDataList.size() == 1) {
            String dictValue = sysDictDataList.get(0).getDictValue();
            try {
                timeOut = Integer.parseInt(dictValue);
            } catch (Exception e) {
                log.error("timeOut转换失败");
            }
        }
        orderRec.setCloseTime(DateUtils.addMinutes(DateUtils.getNowDate(), timeOut));
        orderRecService.tihao(orderRec);
        payRecord.setBusinessId(orderRec.getId());
        payRecord.setNo(orderRec.getNo());
        updateById(payRecord);
        return payRecord;
    }

    public synchronized String generateNo(String prefix) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(DateUtils.dateTimeNow("yyyyMMddHHmmssSSS"));
        sb.append(RandomStringUtils.randomNumeric(4));
        return sb.toString();
    }

    @Override
    public PayRecord queryOrder(PayRecord payRecordReq) {
        PayRecord payRecord = getById(payRecordReq.getId());
        if (StringUtils.isNotNull(payRecord)) {
            UnifyQueryOrderVo unifyQueryOrderVo = new UnifyQueryOrderVo();
            unifyQueryOrderVo.setCenterNo(payRecord.getCenterNo());
            try {
                String res = payFeignService.queryOrder(unifyQueryOrderVo);
                JSONObject jsonObject = JSONObject.parseObject(res);
                String code = jsonObject.getString("code");
                if (StringUtils.equals(code, "200")) {
                    //交易支付成功
                    String data = jsonObject.getString("data");
                    JSONObject dataJson = JSONObject.parseObject(data);
                    String orderState = dataJson.getString("orderState");
                    String payTotalMoney = dataJson.getString("payTotalMoney");
                    if (StringUtils.isNotEmpty(orderState)) {
                        if (StringUtils.equals(orderState, "2")) {
                            payRecord.setOrderState(Integer.valueOf(orderState));
                            payRecord.setBuyerId(dataJson.getString("buyerId"));
                            payRecord.setPayNo(dataJson.getString("payNo"));
                            String payEndTime = dataJson.getString("payEndTime");
                            if (StringUtils.isNotEmpty(payEndTime)) {
                                payRecord.setPayEndTime(DateUtils.parseDate(payEndTime));
                            }
                            updateById(payRecord);
                        }
                        if (StringUtils.equals(orderState, "4")) {
                            payRecord.setOrderState(Integer.valueOf(orderState));
                            updateById(payRecord);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("去支付中心请求查询订单接口返回进入catch" + e.getMessage());
            }
        }
        return payRecord;
    }

    @Override
    public OrderRec queryOrderByOrderId(OrderRec orderRec) {
        if (StringUtils.isNull(orderRec.getPayRecordId())) {
            throw new GlobalException("未找到此订单的支付记录id");
        }
        PayRecord payRecord = getById(orderRec.getPayRecordId());
        if (StringUtils.isNull(payRecord)) {
            throw new GlobalException("未找到此订单的支付记录信息");
        }
        UnifyQueryOrderVo unifyQueryOrderVo = new UnifyQueryOrderVo();
//        unifyQueryOrderVo.setNo(payRecord.getNo());
//        unifyQueryOrderVo.setPayMerchantId(String.valueOf(payRecord.getPayMerchantId()));
//        unifyQueryOrderVo.setUserId(String.valueOf(payRecord.getUserId()));
        unifyQueryOrderVo.setCenterNo(payRecord.getCenterNo());

        String res = payFeignService.queryOrder(unifyQueryOrderVo);
        JSONObject jsonObject = JSONObject.parseObject(res);
        String code = jsonObject.getString("code");
        if (StringUtils.equals(code, "200")) {
            //交易支付成功
            String data = jsonObject.getString("data");
            JSONObject dataJson = JSONObject.parseObject(data);
            String orderState = dataJson.getString("orderState");
            String payTotalMoney = dataJson.getString("payTotalMoney");
            if (StringUtils.isNotEmpty(orderState) && StringUtils.equals(orderState, "2")) {
                if (payRecord.getOrderState() != 2 || !orderRec.getPaidAmount().toPlainString().equals(orderRec.getSalePrice())) {
                    payRecord.setOrderState(Integer.valueOf(orderState));
                    payRecord.setBuyerId(dataJson.getString("buyerId"));
                    payRecord.setPayNo(dataJson.getString("payNo"));
                    String payEndTime = dataJson.getString("payEndTime");
                    if (StringUtils.isNotEmpty(payEndTime)) {
                        payRecord.setPayEndTime(DateUtils.parseDate(payEndTime));
                    }

                    updateById(payRecord);
                    orderRec.setOderstatus("4");
                    orderRec.setIfPay("1");
                    orderRec.setPayState(2);
                    QueryWrapper<PayRecord> queryWrapper = new QueryWrapper<>();
                    queryWrapper.select("sum(pay_total_money) as payTotalMoney");
                    queryWrapper.eq("order_state", 2);
                    queryWrapper.eq("business_id", orderRec.getId());
                    List<PayRecord> list = list(queryWrapper);
                    if (CollectionUtils.isNotEmpty(list)) {
                        PayRecord payRecord1 = list.get(0);
                        if (StringUtils.isNotNull(payRecord1.getPayTotalMoney())) {
                            orderRec.setPaidAmount(BigDecimal.valueOf(payRecord1.getPayTotalMoney()));
                        }
                    }
//                    if (StringUtils.isNotEmpty(payTotalMoney)) {
//                        if (StringUtils.isNull(orderRec.getPaidAmount())) {
//                            orderRec.setPaidAmount(new BigDecimal(payTotalMoney));
//                        } else {
//                            orderRec.setPaidAmount(orderRec.getPaidAmount().add(new BigDecimal(payTotalMoney)));
//                        }
//                    }
                    orderRecMapper.updateOrderRec(orderRec);
                }
            }
        }
        return orderRec;
    }

    @Override
    public PayRecord payment(PayRecord payRecord) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        LambdaQueryWrapper<PayRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayRecord::getUserId, user.getUserId());
        queryWrapper.eq(PayRecord::getPayWay, payRecord.getPayWay());
        queryWrapper.eq(PayRecord::getPayScene, payRecord.getPayScene());
        queryWrapper.eq(PayRecord::getNo, payRecord.getNo());
        queryWrapper.eq(PayRecord::getOrderState, 1);
        List<PayRecord> list = list(queryWrapper);
        PayRecord rePayRecord = null;
        for (PayRecord record : list) {
            int compareResult = Double.compare(record.getPayTotalMoney(),
                    payRecord.getPayTotalMoney());
            if (compareResult == 0
                    && record.getPayWay() == payRecord.getPayWay()
                    && record.getPayScene() == payRecord.getPayScene()) {
                rePayRecord = new PayRecord();
                rePayRecord = record;
                break;
            } else {
                payRecord.setId(record.getId());
            }
        }
        if (StringUtils.isNotNull(rePayRecord)) {
            return rePayRecord;
        }
        //去字典拿支付中心参数
        Map<String, String> payCenterInfo = getPayCenterInfo();
        String notifyUrl = "";
        String merchantId = "";
        if (StringUtils.isNotEmpty(payRecord.getNotifyUrl())) {
            notifyUrl = payRecord.getNotifyUrl();
        } else {
            notifyUrl = payCenterInfo.get("notifyUrl");
        }
        merchantId = payCenterInfo.get("merchantId");

        payRecord.setUserId(user.getUserId());
        payRecord.setUserName(user.getNickName());
        payRecord.setCreateId(user.getUserId());
        payRecord.setOrderState(1);

        String no = payRecord.getNo();
        Double totalAmount = payRecord.getPayTotalMoney();
        String goodsTitle = payRecord.getGoodsTitle();
        if (StringUtils.isEmpty(goodsTitle)) {
            goodsTitle = "购买商品";
        }

        UnifyPayReq unifyPayReq = new UnifyPayReq();
        unifyPayReq.setPayMerchantId(merchantId);
        unifyPayReq.setNo(no);
        unifyPayReq.setNotifyUrl(notifyUrl);
        unifyPayReq.setReturnUrl(payRecord.getReturnUrl());
        unifyPayReq.setPayScene(String.valueOf(payRecord.getPayScene()));
        unifyPayReq.setPayWay(String.valueOf(payRecord.getPayWay()));
        unifyPayReq.setTitle(goodsTitle);
        unifyPayReq.setTotalAmount(totalAmount);
        unifyPayReq.setUserId(String.valueOf(payRecord.getUserId()));
        unifyPayReq.setQrPayMode(payRecord.getQrPayMode());
        unifyPayReq.setQrcodeWidth(payRecord.getQrcodeWidth());
        String res = payFeignService.unify(unifyPayReq);
        if (StringUtils.isNull(res)) {
            throw new GlobalException("调起支付失败,请联系管理员");
        }
//        log.info(res);
        payRecord.setPayMerchantId(Long.valueOf(merchantId));
        payRecord.setNotifyUrl(notifyUrl);
        payRecord.setPayTotalMoney(totalAmount);
        payRecord.setGoodsTitle(goodsTitle);
        JSONObject jsonObject = JSONObject.parseObject(res);
        String code = jsonObject.getString("code");
        if (StringUtils.equals(code, "200")) {
            String data = jsonObject.getString("data");
            JSONObject dataJson = JSONObject.parseObject(data);
            String formStr = dataJson.getString("formStr");
            String centerNo = dataJson.getString("centerNo");
            payRecord.setCenterNo(centerNo);
            payRecord.setFormStr(formStr);
        }
        saveOrUpdate(payRecord);
        return payRecord;
    }

    /**
     * 下单并发起支付（支持定金 / 尾款分笔场景）
     * <p>
     * 与 {@link #payment(PayRecord)} 的区别在于：本方法以业务订单 {@link OrderRec} 作为入口，
     * 由业务订单的总价决定本次实际支付金额，并按 {@code payRecord.amountType} 区分三种金额模式：
     * <ul>
     *   <li>{@code amountType == 1}：支付定金。定金 = 商品金额 × 10%，不足 50 元按 50 元保底；
     *   支付订单号追加后缀 {@code "-dj"}，并把支付中心的绝对超时时间对齐到业务订单的关单时间，
     *   保证"订单到期未付则定金也一并作废"；</li>
     *   <li>{@code amountType == 2}：支付尾款。金额 = 销售价 - 已付金额（即剩余未付部分）；
     *   支付订单号追加后缀 {@code "-wk"}；</li>
     *   <li>{@code amountType} 其它值：按全额支付，支付订单号沿用业务订单的原单号。</li>
     * </ul>
     * 同时会对「同一用户 + 同一支付方式 + 同一支付场景 + 同一支付订单号 + 未支付」的支付记录做去重，
     * 若已存在金额/方式/场景完全一致的未支付记录则直接复用，避免在支付中心重复下单；
     * 若同一支付订单号下已存在其它金额的未支付记录，则把它的主键借给本次 payRecord，
     * 后续走"更新"而非"新增"。
     * <p>
     * 最终会调用支付中心的统一下单接口，把支付中心返回的 {@code centerNo}（支付中心单号）
     * 和 {@code formStr}（唤起支付的表单串）写入本地支付记录；并把本次支付记录 id 反写到业务订单上，
     * 便于后续通过订单反查支付记录。
     *
     * @param orderRec  业务订单，提供订单号、总价、商品金额、已付金额、关单时间、商品 id 等关键信息；不可为空
     * @param payRecord 支付请求参数（含 {@code payWay} 支付方式、{@code payScene} 支付场景、
     *                  {@code amountType} 金额类型、{@code notifyUrl}/{@code returnUrl}/{@code goodsTitle} 等可选项），
     *                  本方法会对其做填充并落库
     * @return 已落库的支付记录（含支付中心单号 {@code centerNo} 和唤起支付的表单串 {@code formStr}）
     */
    @Override
    @Transactional
    public PayRecord paymentTwo(OrderRec orderRec, PayRecord payRecord) {
        // 1) 取当前登录用户，作为支付记录的归属人 / 创建人
        SysUser user = SecurityUtils.getLoginUser().getUser();

        // 2) 计算本次实际支付金额与支付订单号
        //    no           = 业务订单号（按金额类型可能追加 "-dj" 定金 / "-wk" 尾款 后缀，使同一订单可以分多笔支付）
        //    totalAmount  = 销售价（默认全额）
        //    goodsAmount  = 商品金额（仅用于计算定金）
        String no = orderRec.getNo();
        Double totalAmount = Double.valueOf(String.valueOf(orderRec.getSalePrice()));
        Double goodsAmount = Double.valueOf(String.valueOf(orderRec.getGoodsAmount()));

        // 定金 = 商品金额 × 10%，不足 50 元按 50 元保底
        Double dj = BigDecimalUtils.mul(goodsAmount, 0.1);
        if (dj < 50) {
            dj = 50d;
        }

        // 根据 amountType 决定本次是「付定金」还是「付尾款」，并相应调整金额与订单号后缀
        if (payRecord.getAmountType() == 1) {
            // 定金：金额 = 定金（dj），订单号追加 "-dj"
            totalAmount = dj;
            no = no + "-dj";
        } else if (payRecord.getAmountType() == 2) {
            // 尾款：金额 = 销售价 - 已付金额（通常已付部分就是前面已支付的定金）
            totalAmount = BigDecimalUtils.sub(totalAmount, orderRec.getPaidAmount().doubleValue());
            no = no + "-wk";
        }

        // 3) 去重：同一用户 + 同一支付方式 + 同一支付场景 + 同一支付订单号 + 未支付 只能保留一条记录
        //    - 金额/方式/场景完全一致：直接复用，避免在支付中心重复下单
        //    - 同一订单号下金额不一致：把已有记录的 id 借给本次 payRecord，后续 saveOrUpdate 走"更新"
        //    - 没有任何记录：保持新对象，最终新增一条
        LambdaQueryWrapper<PayRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayRecord::getUserId, user.getUserId());
        queryWrapper.eq(PayRecord::getPayWay, payRecord.getPayWay());
        queryWrapper.eq(PayRecord::getPayScene, payRecord.getPayScene());
        queryWrapper.eq(PayRecord::getNo, no);
        queryWrapper.eq(PayRecord::getOrderState, 1);
        List<PayRecord> list = list(queryWrapper);
        PayRecord rePayRecord = null;
        for (PayRecord record : list) {
            int compareResult = Double.compare(record.getPayTotalMoney(),
                    totalAmount);
            if (compareResult == 0
                    && record.getPayWay() == payRecord.getPayWay()
                    && record.getPayScene() == payRecord.getPayScene()) {
                // 金额/方式/场景完全一致：直接复用这条记录返回（支付中心也不需要再调一次）
                rePayRecord = new PayRecord();
                rePayRecord = record;
                break;
            } else {
                // 同号不同额：把已有记录的 id 借给当前 payRecord，后续落库走"更新"
                payRecord.setId(record.getId());
            }
        }
        if (StringUtils.isNotNull(rePayRecord)) {
            return rePayRecord;
        }

        // 4) 从字典 payCenter 中取支付中心配置（notifyUrl / merchantId）
        //    请求中若自带 notifyUrl 则优先使用请求值（用于特殊渠道自定义回调）
        Map<String, String> payCenterInfo = getPayCenterInfo();
        String notifyUrl = "";
        String merchantId = "";
        if (StringUtils.isNotEmpty(payRecord.getNotifyUrl())) {
            notifyUrl = payRecord.getNotifyUrl();
        } else {
            notifyUrl = payCenterInfo.get("notifyUrl");
        }
        merchantId = payCenterInfo.get("merchantId");

        // 5) 填充支付记录基础字段
        payRecord.setUserId(user.getUserId());
        payRecord.setUserName(user.getNickName());
        payRecord.setCreateId(user.getUserId());
        payRecord.setOrderState(1);                  // 1 = 待支付
        payRecord.setBusinessId(orderRec.getId());   // 关联到本次业务订单

        // 商品标题若调用方未传，给一个默认值，避免支付中心因 title 为空而校验失败
        String goodsTitle = payRecord.getGoodsTitle();
        if (StringUtils.isEmpty(goodsTitle)) {
            goodsTitle = "购买商品";
        }

        // 6) 组装支付中心"统一下单"请求
        UnifyPayReq unifyPayReq = new UnifyPayReq();
        unifyPayReq.setPayMerchantId(merchantId);
        unifyPayReq.setNo(no);
        unifyPayReq.setNotifyUrl(notifyUrl);
        unifyPayReq.setReturnUrl(payRecord.getReturnUrl());
        unifyPayReq.setPayScene(String.valueOf(payRecord.getPayScene()));
        unifyPayReq.setPayWay(String.valueOf(payRecord.getPayWay()));
        unifyPayReq.setTitle(goodsTitle);
        unifyPayReq.setTotalAmount(totalAmount);
        unifyPayReq.setUserId(String.valueOf(payRecord.getUserId()));
        // 定金场景：把支付中心的"绝对超时时间 (csTime)"对齐到业务订单的关单时间，
        // 避免出现"业务订单已关单、但定金支付仍可成功"的不一致情况
        if (payRecord.getAmountType() == 1) {
            unifyPayReq.setCsTime(orderRec.getCloseTime());
        }

        // 7) 调支付中心"统一下单"接口
        String res = payFeignService.unify(unifyPayReq);
        if (StringUtils.isNull(res)) {
            throw new GlobalException("调起支付失败,请联系管理员");
        }
        // 8) 把支付中心返回的关键信息回填到本地支付记录
        payRecord.setPayMerchantId(Long.valueOf(merchantId));
        payRecord.setNotifyUrl(notifyUrl);
        payRecord.setNo(no);
        payRecord.setPayTotalMoney(totalAmount);
        payRecord.setGoodsTitle(goodsTitle);
        // 若调用方没指定 goodsId，则默认用业务订单上的商品 id（accountid 即商品 id）
        if (StringUtils.isNull(payRecord.getGoodsId())) {
            payRecord.setGoodsId(Long.valueOf(orderRec.getAccountid()));
        }

        // 9) 解析支付中心响应：拿到 centerNo（支付中心单号，后续查询 / 关闭订单都用它）
        //    和 formStr（唤起支付的表单串，前端用它来拉起支付收银台）
        JSONObject jsonObject = JSONObject.parseObject(res);
        String code = jsonObject.getString("code");
        if (StringUtils.equals(code, "200")) {
            String data = jsonObject.getString("data");
            JSONObject dataJson = JSONObject.parseObject(data);
            String formStr = dataJson.getString("formStr");
            String centerNo = dataJson.getString("centerNo");
            payRecord.setCenterNo(centerNo);
            payRecord.setFormStr(formStr);
        }

        // 10) 落库：若前面已经把已有记录的 id 借给 payRecord，则这里走"更新"；否则新增一条
        saveOrUpdate(payRecord);

        // 11) 把本次支付记录 id 反写到业务订单，便于后续通过订单反查支付记录 / 触发业务回调
        orderRec.setPayRecordId(payRecord.getId());
        orderRecMapper.updateOrderRec(orderRec);

        return payRecord;
    }

    @Override
    public PayRecord closeOrder(PayRecord payRecord) {
        boolean closeFlag = false;
        if (StringUtils.isNotNull(payRecord)) {
            UnifyQueryOrderVo unifyQueryOrderVo = new UnifyQueryOrderVo();
            unifyQueryOrderVo.setCenterNo(payRecord.getCenterNo());
            try {
                String res = payFeignService.closeOrder(unifyQueryOrderVo);
                JSONObject jsonObject = JSONObject.parseObject(res);
                String code = jsonObject.getString("code");
                if (StringUtils.equals(code, "200")) {
                    //交易支付成功
                    String data = jsonObject.getString("data");
                    JSONObject dataJson = JSONObject.parseObject(data);
                    String orderState = dataJson.getString("orderState");
                    if (StringUtils.isNotEmpty(orderState) && StringUtils.equals(orderState, "4")) {
                        closeFlag = true;
                    }
                }
            } catch (Exception e) {
                log.error("去支付中心请求关闭订单接口返回进入catch" + e.getMessage());
            }
        }
        //无论成功与否 都要改变支付订单状态
        payRecord.setOrderState(3);
        updateById(payRecord);
        return payRecord;
    }

    @Async
    @Override
    public void closeOtherOrders(PayRecord payRecord) {
        LambdaQueryWrapper<PayRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayRecord::getUserId, payRecord.getUserId());
        queryWrapper.eq(PayRecord::getNo, payRecord.getNo());
        queryWrapper.eq(PayRecord::getOrderState, 1);
        List<PayRecord> list = list(queryWrapper);
        for (PayRecord record : list) {
            closeOrder(record);
        }
    }


    Map<String, String> getPayCenterInfo() {
        String url = "";
        String merchantId = "";
        String notifyUrl = "";
        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("payCenter");
        if (CollectionUtils.isNotEmpty(sysDictDataList)) {
            for (SysDictData dictData : sysDictDataList) {
                if ("url".equals(dictData.getDictLabel())) {
                    url = dictData.getDictValue();
                } else if ("merchantId".equals(dictData.getDictLabel())) {
                    merchantId = dictData.getDictValue();
                } else if ("notifyUrl".equals(dictData.getDictLabel())) {
                    notifyUrl = dictData.getDictValue();
                }
            }
        } else {
            throw new GlobalException("未配置支付中心参数");
        }
        if (StringUtils.isEmpty(url)) {
//            throw new GlobalException("支付中心参数`url`未配置");
        }
        if (StringUtils.isEmpty(merchantId)) {
            throw new GlobalException("支付中心参数`merchantId`未配置");
        }
        if (StringUtils.isEmpty(notifyUrl)) {
            throw new GlobalException("支付中心参数`notifyUrl`未配置");
        }
        Map<String, String> payCenterInfo = new HashMap();
        payCenterInfo.put("url", url);
        payCenterInfo.put("merchantId", merchantId);
        payCenterInfo.put("notifyUrl", notifyUrl);
        return payCenterInfo;
    }

}

