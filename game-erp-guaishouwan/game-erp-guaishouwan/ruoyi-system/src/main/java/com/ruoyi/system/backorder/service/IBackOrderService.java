package com.ruoyi.system.backorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.backorder.domain.BackOrder;
import com.ruoyi.system.model.domain.Backlist;
import com.ruoyi.system.model.domain.MsgGroupInfo;
import com.ruoyi.system.pay.domain.PayRecord;

/**
 * 回收订单表(back_order)表服务接口
 *
 * @author zyy
 * @date 2023-12-02 13:17:26
 */
public interface IBackOrderService extends IService<BackOrder> {
    //创建订单
    BackOrder createOrder(Backlist backlist);

    //一键拉群
    BackOrder pullGroup(BackOrder backOrder);

    //取消群组
    BackOrder cancelGroup(BackOrder backOrder);

    //恢复群组
    BackOrder recoveryGroup(BackOrder backOrder);

    //获取订单的群组hxid
    MsgGroupInfo getToGroup(BackOrder backOrder);

    //立即支付
    PayRecord payment(BackOrder backOrder, PayRecord payRecordReq);

    //支付成功回调
    BackOrder sucessOrder(PayRecord payRecord);

    //查询未支付的订单
    BackOrder queryOrderById(BackOrder backOrder);

    //修改回收价
    BackOrder changeAmount(BackOrder backOrder);

    //完成订单
    BackOrder finishOrder(BackOrder backOrder);

    //取消订单
    BackOrder cancelOrder(BackOrder backOrder);

}

