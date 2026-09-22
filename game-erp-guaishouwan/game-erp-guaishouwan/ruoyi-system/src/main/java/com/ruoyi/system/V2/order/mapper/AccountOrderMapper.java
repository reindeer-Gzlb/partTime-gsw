package com.ruoyi.system.V2.order.mapper;

import com.ruoyi.system.model.domain.AccountOrder;

import java.util.List;
import java.util.Map;

/**
 * 销售订单Mapper接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface AccountOrderMapper 
{
    /**
     * 查询销售订单
     * 
     * @param id 销售订单主键
     * @return 销售订单
     */
    public AccountOrder selectAccountOrderById(Long id);
    public AccountOrder selectAccountOrderByAccountId(Long id);

    /**
     * 查询销售订单列表
     * 
     * @param accountOrder 销售订单
     * @return 销售订单集合
     */
    public List<AccountOrder> selectAccountOrderList(AccountOrder accountOrder);

    /**
     * 新增销售订单
     * 
     * @param accountOrder 销售订单
     * @return 结果
     */
    public int insertAccountOrder(AccountOrder accountOrder);

    /**
     * 修改销售订单
     * 
     * @param accountOrder 销售订单
     * @return 结果
     */
    public int updateAccountOrder(AccountOrder accountOrder);

    /**
     * 删除销售订单
     * 
     * @param id 销售订单主键
     * @return 结果
     */
    public int deleteAccountOrderById(Long id);

    /**
     * 批量删除销售订单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAccountOrderByIds(Long[] ids);

    List<Map<String, Object>> subOrder();

    List<Map<String, Object>> sellOrder();
}
