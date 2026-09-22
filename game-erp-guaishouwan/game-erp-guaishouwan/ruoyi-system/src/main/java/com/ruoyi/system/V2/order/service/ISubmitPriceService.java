package com.ruoyi.system.V2.order.service;

import com.ruoyi.system.model.domain.SubmitPrice;

import java.util.List;

/**
 * 客户端下单Service接口
 * 
 * @author ruoyi
 * @date 2023-03-22
 */
public interface ISubmitPriceService 
{
    /**
     * 查询客户端下单
     * 
     * @param id 客户端下单主键
     * @return 客户端下单
     */
    public SubmitPrice selectSubmitPriceById(Long id);

    /**
     * 查询客户端下单列表
     * 
     * @param submitPrice 客户端下单
     * @return 客户端下单集合
     */
    public List<SubmitPrice> selectSubmitPriceList(SubmitPrice submitPrice);

    /**
     * 新增客户端下单
     * 
     * @param submitPrice 客户端下单
     * @return 结果
     */
    public int insertSubmitPrice(SubmitPrice submitPrice);

    /**
     * 修改客户端下单
     * 
     * @param submitPrice 客户端下单
     * @return 结果
     */
    public int updateSubmitPrice(SubmitPrice submitPrice);

    /**
     * 批量删除客户端下单
     * 
     * @param ids 需要删除的客户端下单主键集合
     * @return 结果
     */
    public int deleteSubmitPriceByIds(Long[] ids);

    /**
     * 删除客户端下单信息
     * 
     * @param id 客户端下单主键
     * @return 结果
     */
    public int deleteSubmitPriceById(Long id);
}
