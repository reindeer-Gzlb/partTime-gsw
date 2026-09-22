package com.ruoyi.system.V2.inter.service;

import com.ruoyi.system.model.domain.SalePartnerGroup;

import java.util.List;

/**
 * 接口配置Service接口
 * 
 * @author ruoyi
 * @date 2023-05-06
 */
public interface ISalePartnerGroupService 
{
    /**
     * 查询接口配置
     * 
     * @param id 接口配置主键
     * @return 接口配置
     */
    public SalePartnerGroup selectSalePartnerGroupById(Long id);

    /**
     * 查询接口配置列表
     * 
     * @param salePartnerGroup 接口配置
     * @return 接口配置集合
     */
    public List<SalePartnerGroup> selectSalePartnerGroupList(SalePartnerGroup salePartnerGroup);

    /**
     * 新增接口配置
     * 
     * @param salePartnerGroup 接口配置
     * @return 结果
     */
    public int insertSalePartnerGroup(SalePartnerGroup salePartnerGroup);

    /**
     * 修改接口配置
     * 
     * @param salePartnerGroup 接口配置
     * @return 结果
     */
    public int updateSalePartnerGroup(SalePartnerGroup salePartnerGroup);

    /**
     * 批量删除接口配置
     * 
     * @param ids 需要删除的接口配置主键集合
     * @return 结果
     */
    public int deleteSalePartnerGroupByIds(Long[] ids);

    /**
     * 删除接口配置信息
     * 
     * @param id 接口配置主键
     * @return 结果
     */
    public int deleteSalePartnerGroupById(Long id);
}
