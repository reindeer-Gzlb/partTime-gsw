package com.ruoyi.system.V2.order.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.model.domain.AccountOrder;

import java.util.List;
import java.util.Map;

/**
 * 销售订单Service接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface IAccountOrderService 
{
    /**
     * 查询销售订单
     * 
     * @param id 销售订单主键
     * @return 销售订单
     */
    public AccountOrder selectAccountOrderById(Long id);

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
     * 批量删除销售订单
     * 
     * @param ids 需要删除的销售订单主键集合
     * @return 结果
     */
    public int deleteAccountOrderByIds(Long[] ids);

    /**
     * 删除销售订单信息
     * 
     * @param id 销售订单主键
     * @return 结果
     */
    public int deleteAccountOrderById(Long id);

    void submitOrder(JSONObject params);

    JSONObject superSubmit(Map<String,Object> params);

    void superSubmitRemake(JSONObject params);

    void cancelOrder(Map<String,Object> params);

    AccountOrder getInfoByAccount(Long id);
}
