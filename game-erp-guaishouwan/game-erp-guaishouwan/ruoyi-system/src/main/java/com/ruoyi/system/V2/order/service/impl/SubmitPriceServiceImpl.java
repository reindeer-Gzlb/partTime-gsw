package com.ruoyi.system.V2.order.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.model.domain.SubmitPrice;
import com.ruoyi.system.V2.order.mapper.SubmitPriceMapper;
import com.ruoyi.system.V2.order.service.ISubmitPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 客户端下单Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-22
 */
@Service
public class SubmitPriceServiceImpl implements ISubmitPriceService
{
    @Autowired
    private SubmitPriceMapper submitPriceMapper;

    /**
     * 查询客户端下单
     * 
     * @param id 客户端下单主键
     * @return 客户端下单
     */
    @Override
    public SubmitPrice selectSubmitPriceById(Long id)
    {
        return submitPriceMapper.selectSubmitPriceById(id);
    }

    /**
     * 查询客户端下单列表
     * 
     * @param submitPrice 客户端下单
     * @return 客户端下单
     */
    @Override
    public List<SubmitPrice> selectSubmitPriceList(SubmitPrice submitPrice)
    {
        return submitPriceMapper.selectSubmitPriceList(submitPrice);
    }

    /**
     * 新增客户端下单
     * 
     * @param submitPrice 客户端下单
     * @return 结果
     */
    @Override
    public int insertSubmitPrice(SubmitPrice submitPrice)
    {
        submitPrice.setCreateTime(DateUtils.getNowDate());
        return submitPriceMapper.insertSubmitPrice(submitPrice);
    }

    /**
     * 修改客户端下单
     * 
     * @param submitPrice 客户端下单
     * @return 结果
     */
    @Override
    public int updateSubmitPrice(SubmitPrice submitPrice)
    {
        submitPrice.setUpdateTime(DateUtils.getNowDate());
        return submitPriceMapper.updateSubmitPrice(submitPrice);
    }

    /**
     * 批量删除客户端下单
     * 
     * @param ids 需要删除的客户端下单主键
     * @return 结果
     */
    @Override
    public int deleteSubmitPriceByIds(Long[] ids)
    {
        return submitPriceMapper.deleteSubmitPriceByIds(ids);
    }

    /**
     * 删除客户端下单信息
     * 
     * @param id 客户端下单主键
     * @return 结果
     */
    @Override
    public int deleteSubmitPriceById(Long id)
    {
        return submitPriceMapper.deleteSubmitPriceById(id);
    }
}
