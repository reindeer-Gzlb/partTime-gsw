package com.ruoyi.system.V2.order.service;

import com.ruoyi.system.model.domain.AccountRemake;

import java.util.List;

/**
 * 销售订单备注Service接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface IAccountRemakeService 
{
    /**
     * 查询销售订单备注
     * 
     * @param id 销售订单备注主键
     * @return 销售订单备注
     */
    public AccountRemake selectAccountRemakeById(Long id);

    /**
     * 查询销售订单备注列表
     * 
     * @param accountRemake 销售订单备注
     * @return 销售订单备注集合
     */
    public List<AccountRemake> selectAccountRemakeList(AccountRemake accountRemake);

    /**
     * 新增销售订单备注
     * 
     * @param accountRemake 销售订单备注
     * @return 结果
     */
    public int insertAccountRemake(AccountRemake accountRemake);

    /**
     * 修改销售订单备注
     * 
     * @param accountRemake 销售订单备注
     * @return 结果
     */
    public int updateAccountRemake(AccountRemake accountRemake);

    /**
     * 批量删除销售订单备注
     * 
     * @param ids 需要删除的销售订单备注主键集合
     * @return 结果
     */
    public int deleteAccountRemakeByIds(Long[] ids);

    /**
     * 删除销售订单备注信息
     * 
     * @param id 销售订单备注主键
     * @return 结果
     */
    public int deleteAccountRemakeById(Long id);
}
