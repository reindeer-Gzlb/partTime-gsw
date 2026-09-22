package com.ruoyi.system.V2.order.mapper;

import com.ruoyi.system.model.domain.SubmitPrice;

import java.util.List;

/**
 * 客户端下单Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-22
 */
public interface SubmitPriceMapper 
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
     * 删除客户端下单
     * 
     * @param id 客户端下单主键
     * @return 结果
     */
    public int deleteSubmitPriceById(Long id);

    /**
     * 批量删除客户端下单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSubmitPriceByIds(Long[] ids);
}
