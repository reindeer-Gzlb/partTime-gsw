package com.ruoyi.system.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.model.domain.OrderRec;
import com.ruoyi.system.pay.domain.PayRecord;

/**
 * 移动支付记录表(pay_record)表服务接口
 *
 * @author zyy
 * @date 2023-09-15 16:23:58
 */
public interface IPayRecordService extends IService<PayRecord> {

    PayRecord savePayRecord(PayRecord payRecord);

    PayRecord queryOrder(PayRecord payRecord);

    PayRecord payment(PayRecord payRecord);

    OrderRec queryOrderByOrderId(OrderRec OrderRec);

    /**
     * 下单并发起支付（支持定金 / 尾款分笔）
     * <p>
     * 以业务订单 {@link OrderRec} 作为入口，按 {@code payRecord.amountType} 决定是全额支付、付定金或付尾款，
     * 并对「同用户 + 同支付方式 + 同支付场景 + 同支付订单号 + 未支付」的记录做去重，
     * 最终调支付中心统一下单并回填 centerNo / formStr。详见实现类。
     *
     * @param orderRec  业务订单（提供订单号、总价、商品金额、已付金额、关单时间、商品 id 等）
     * @param payRecord 支付请求参数（payWay / payScene / amountType / notifyUrl / returnUrl / goodsTitle 等）
     * @return 已落库的支付记录（含支付中心单号和唤起支付的表单串）
     */
    PayRecord paymentTwo(OrderRec orderRec, PayRecord payRecord);

    PayRecord closeOrder(PayRecord payRecord);

    void closeOtherOrders(PayRecord payRecord);
}

