package com.ruoyi.system.V2.inter.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.V2.inter.mapper.SalePartnerGroupMapper;
import com.ruoyi.system.V2.inter.service.ISalePartnerGroupService;
import com.ruoyi.system.model.domain.SalePartnerGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 接口配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-06
 */
@Service
public class SalePartnerGroupServiceImpl implements ISalePartnerGroupService
{
    @Autowired
    private SalePartnerGroupMapper salePartnerGroupMapper;

    /**
     * 查询接口配置
     * 
     * @param id 接口配置主键
     * @return 接口配置
     */
    @Override
    public SalePartnerGroup selectSalePartnerGroupById(Long id)
    {
        return salePartnerGroupMapper.selectSalePartnerGroupById(id);
    }

    /**
     * 查询接口配置列表
     * 
     * @param salePartnerGroup 接口配置
     * @return 接口配置
     */
    @Override
    public List<SalePartnerGroup> selectSalePartnerGroupList(SalePartnerGroup salePartnerGroup)
    {
        return salePartnerGroupMapper.selectSalePartnerGroupList(salePartnerGroup);
    }

    /**
     * 新增接口配置
     * 
     * @param salePartnerGroup 接口配置
     * @return 结果
     */
    @Override
    public int insertSalePartnerGroup(SalePartnerGroup salePartnerGroup)
    {
        salePartnerGroup.setCreateTime(DateUtils.getNowDate());
        return salePartnerGroupMapper.insertSalePartnerGroup(salePartnerGroup);
    }

    /**
     * 修改接口配置
     * 
     * @param salePartnerGroup 接口配置
     * @return 结果
     */
    @Override
    public int updateSalePartnerGroup(SalePartnerGroup salePartnerGroup)
    {
        salePartnerGroup.setUpdateTime(DateUtils.getNowDate());
        return salePartnerGroupMapper.updateSalePartnerGroup(salePartnerGroup);
    }

    /**
     * 批量删除接口配置
     * 
     * @param ids 需要删除的接口配置主键
     * @return 结果
     */
    @Override
    public int deleteSalePartnerGroupByIds(Long[] ids)
    {
        return salePartnerGroupMapper.deleteSalePartnerGroupByIds(ids);
    }

    /**
     * 删除接口配置信息
     * 
     * @param id 接口配置主键
     * @return 结果
     */
    @Override
    public int deleteSalePartnerGroupById(Long id)
    {
        return salePartnerGroupMapper.deleteSalePartnerGroupById(id);
    }
}
