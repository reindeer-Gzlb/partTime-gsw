package com.ruoyi.system.V2.submitAccount.mapper;

import com.ruoyi.system.model.domain.OrderRec;
import com.ruoyi.system.model.dto.OrderRecRes;

import java.util.List;
import java.util.Map;

/**
 * 销售订单记录Mapper接口
 *
 * @author ruoyi
 * @date 2022-10-28
 */
public interface OrderRecMapper {
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
     * 删除销售订单记录
     *
     * @param id 销售订单记录主键
     * @return 结果
     */
    public int deleteOrderRecById(Long id);

    /**
     * 批量删除销售订单记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderRecByIds(Long[] ids);

    Map<String, Object> sumPrice(OrderRec orderRec);

    List<Map<String, Object>> createList();

    List<OrderRecRes> myOrderRecList(OrderRec orderRec);

    OrderRecRes myOrderRecOne(Long id);

    List<OrderRec> selectTimeOutOrder(OrderRec orderRec);

    Map<String, Object> selectOrderStatus(Long id);

    OrderRecRes orderRecLimitOne(OrderRec orderRec);
}
