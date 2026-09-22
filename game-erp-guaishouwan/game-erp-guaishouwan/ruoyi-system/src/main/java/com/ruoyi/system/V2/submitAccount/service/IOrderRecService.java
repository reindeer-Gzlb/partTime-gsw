package com.ruoyi.system.V2.submitAccount.service;

import com.ruoyi.system.model.domain.Finance;
import com.ruoyi.system.model.domain.OrderRec;

import java.util.List;
import java.util.Map;

/**
 * 销售订单记录Service接口
 *
 * @author ruoyi
 * @date 2022-10-28
 */
public interface IOrderRecService {
    /**
     * 查询销售订单记录
     *
     * @param id 销售订单记录主键
     * @return 销售订单记录
     */
    public OrderRec selectOrderRecById(Long id);

    /**
     * 查询销售订单记录列表
     *
     * @param orderRec 销售订单记录
     * @return 销售订单记录集合
     */
    public List<OrderRec> selectOrderRecList(OrderRec orderRec);

    /**
     * 新增销售订单记录
     *
     * @param orderRec 销售订单记录
     * @return 结果
     */
    public int insertOrderRec(OrderRec orderRec);

    /**
     * 修改销售订单记录
     *
     * @param orderRec 销售订单记录
     * @return 结果
     */
    public int updateOrderRec(OrderRec orderRec);

    /**
     * 批量删除销售订单记录
     *
     * @param ids 需要删除的销售订单记录主键集合
     * @return 结果
     */
    public int deleteOrderRecByIds(Long[] ids);

    /**
     * 删除销售订单记录信息
     *
     * @param id 销售订单记录主键
     * @return 结果
     */
    public int deleteOrderRecById(Long id);

    Map<String, Object> sumPrice(OrderRec orderRec);

    List<Map<String, Object>> createList();

    int insertOrderRecTwo(OrderRec orderRec);

    int selfSale(Finance finance);

    int selfSaleFQ(Finance finance);

    List<Map<String, Object>> subOrder();

    List<Map<String, Object>> sellOrder();


    /**
     * 移动支付成功后，销售订单生成。
     *
     * @param orderRec
     * @return 结果
     */
    public int payInsertOrderRec(OrderRec orderRec);

    /**
     * 账号提号
     * @param orderRec
     * @return
     */
    public int tihao(OrderRec orderRec);

    /**
     * 完成订单
     * @param orderRec
     * @return
     */
    public int finishOrder(OrderRec orderRec);

    /**
     * 取消订单
     * @param orderRec
     * @return
     */
    public int myOrderCancel(OrderRec orderRec);

    /**
     * 售后
     * @param orderRec
     * @return
     */
    public int afterSale(OrderRec orderRec);

    OrderRec createOrder(OrderRec orderRecReq);
}
