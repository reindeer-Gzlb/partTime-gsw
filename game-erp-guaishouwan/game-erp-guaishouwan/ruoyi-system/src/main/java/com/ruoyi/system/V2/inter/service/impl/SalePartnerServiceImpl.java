package com.ruoyi.system.V2.inter.service.impl;

import java.util.List;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.SalePartner;
import com.ruoyi.system.V2.inter.mapper.SalePartnerMapper;
import com.ruoyi.system.V2.inter.service.ISalePartnerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 销售伙伴Service业务层处理
 *
 * @author ruoyi
 * @date 2022-06-07
 */
@Service
public class SalePartnerServiceImpl implements ISalePartnerService
{
    @Resource
    private SalePartnerMapper salePartnerMapper;
    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 查询销售伙伴
     *
     * @param id 销售伙伴主键
     * @return 销售伙伴
     */
    @Override
    public SalePartner selectSalePartnerById(Long id)
    {
        return salePartnerMapper.selectSalePartnerById(id);
    }

    /**
     * 查询销售伙伴列表
     *
     * @param salePartner 销售伙伴
     * @return 销售伙伴
     */
    @Override
    public List<SalePartner> selectSalePartnerList(SalePartner salePartner)
    {
        List<SalePartner> salePartners = salePartnerMapper.selectSalePartnerList(salePartner);
        salePartners.stream().forEach(sale ->{
//            SysUser sysUser = sysUserMapper.selectUserById(sale.getUpdateId());
//            if (null != sysUser) {
//                sale.setUpdateName(sysUser.getNickName());
//            }
//            SysUser createUser = sysUserMapper.selectUserById(sale.getCreateId());
//            if (null != createUser) {
//                sale.setCreateName(createUser.getNickName());
//            }
            if (Constants.STR_1.equals(sale.getStatus())){
                sale.setStatus("可用");
            }else {
                sale.setStatus("禁用");
            }
        });

        return salePartners;
    }

    /**
     * 新增销售伙伴
     *
     * @param salePartner 销售伙伴
     * @return 结果
     */
    @Override
    public int insertSalePartner(SalePartner salePartner)
    {
        SalePartner selectPrams = new SalePartner();
        selectPrams.setName(salePartner.getName());
        selectPrams.setSaleName(salePartner.getSaleName());
        List<SalePartner> salePartners = salePartnerMapper.selectSalePartnerList(selectPrams);
//        if (!CollectionUtils.isEmpty(salePartners)){
//            throw new ServiceException("该供货上已新增"+salePartner.getSaleName());
//        }
        salePartner.setCreateTime(DateUtils.getNowDate());
        Long userId = SecurityUtils.getUserId();
        salePartner.setCreateId(userId);
        salePartner.setUpdateTime(null);
        return salePartnerMapper.insertSalePartner(salePartner);
    }

    /**
     * 修改销售伙伴
     *
     * @param salePartner 销售伙伴
     * @return 结果
     */
    @Override
    public int updateSalePartner(SalePartner salePartner)
    {
        salePartner.setUpdateTime(DateUtils.getNowDate());
        Long userId = SecurityUtils.getUserId();
        salePartner.setUpdateId(userId);
        return salePartnerMapper.updateSalePartner(salePartner);
    }

    /**
     * 批量删除销售伙伴
     *
     * @param ids 需要删除的销售伙伴主键
     * @return 结果
     */
    @Override
    public int deleteSalePartnerByIds(Long[] ids)
    {
        return salePartnerMapper.deleteSalePartnerByIds(ids);
    }

    /**
     * 删除销售伙伴信息
     *
     * @param id 销售伙伴主键
     * @return 结果
     */
    @Override
    public int deleteSalePartnerById(Long id)
    {
        return salePartnerMapper.deleteSalePartnerById(id);
    }
}
