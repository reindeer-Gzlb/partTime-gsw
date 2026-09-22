package com.ruoyi.system.V2.inter.mapper;

import com.ruoyi.system.model.domain.PriceRec;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-29
 */
public interface PriceRecMapper 
{
    public PriceRec selectPriceRecById(Long id);

    public List<PriceRec> selectPriceRecList(PriceRec priceRec);

    public int insertPriceRec(PriceRec priceRec);

    public int updatePriceRec(PriceRec priceRec);

    public int deletePriceRecById(Long id);

    public int deletePriceRecByIds(Long[] ids);
}
