package com.ruoyi.system.V2.inter.mapper;

import com.ruoyi.system.model.domain.SalePartner;

import java.util.List;

/**
 * 销售伙伴Mapper接口
 *
 * @author ruoyi
 * @date 2022-06-07
 */
public interface SalePartnerMapper
{
    /**
     * 查询销售伙伴
     *
     * @param id 销售伙伴主键
     * @return 销售伙伴
     */
    public SalePartner selectSalePartnerById(Long id);

    /**
     * 查询销售伙伴列表
     *
     * @param salePartner 销售伙伴
     * @return 销售伙伴集合
     */
    public List<SalePartner> selectSalePartnerList(SalePartner salePartner);

    /**
     * 新增销售伙伴
     *
     * @param salePartner 销售伙伴
     * @return 结果
     */
    public int insertSalePartner(SalePartner salePartner);

    /**
     * 修改销售伙伴
     *
     * @param salePartner 销售伙伴
     * @return 结果
     */
    public int updateSalePartner(SalePartner salePartner);

    /**
     * 删除销售伙伴
     *
     * @param id 销售伙伴主键
     * @return 结果
     */
    public int deleteSalePartnerById(Long id);

    /**
     * 批量删除销售伙伴
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSalePartnerByIds(Long[] ids);

    SalePartner selectSalePartnerSaleCode(String saleNameLCC);
}
