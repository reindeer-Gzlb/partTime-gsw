package com.ruoyi.system.logo.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.logo.domain.SysShopLogo;
import com.ruoyi.system.logo.mapper.SysShopLogoMapper;
import com.ruoyi.system.logo.service.ISysShopLogoService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * logoService业务层处理
 * 
 * @author ruoyi
 * @date 2024-08-09
 */
@Service
public class SysShopLogoServiceImpl implements ISysShopLogoService 
{
    @Autowired
    private SysShopLogoMapper sysShopLogoMapper;

    /**
     * 查询logo
     * 
     * @param id logo主键
     * @return logo
     */
    @Override
    public SysShopLogo selectSysShopLogoById(Long id)
    {
        return sysShopLogoMapper.selectSysShopLogoById(id);
    }

    /**
     * 查询logo列表
     * 
     * @param sysShopLogo logo
     * @return logo
     */
    @Override
    public List<SysShopLogo> selectSysShopLogoList(SysShopLogo sysShopLogo)
    {
        return sysShopLogoMapper.selectSysShopLogoList(sysShopLogo);
    }

    /**
     * 新增logo
     * 
     * @param sysShopLogo logo
     * @return 结果
     */
    @Override
    public int insertSysShopLogo(SysShopLogo sysShopLogo) {
        sysShopLogo.setCreateId(SecurityUtils.getUserId());
        SysShopLogo logo = new SysShopLogo();
        logo.setCreateId(SecurityUtils.getUserId());
        List<SysShopLogo> logos = selectSysShopLogoList(logo);
        if (CollectionUtils.isNotEmpty(logos)) {
            SysShopLogo shopLogo = logos.get(0);
            shopLogo.setLogoImg(sysShopLogo.getLogoImg());
            shopLogo.setBannerImg(sysShopLogo.getBannerImg());
             sysShopLogoMapper.updateSysShopLogo(shopLogo);
        } else {
             sysShopLogoMapper.insertSysShopLogo(sysShopLogo);
        }
        return 1;
    }

    /**
     * 修改logo
     * 
     * @param sysShopLogo logo
     * @return 结果
     */
    @Override
    public int updateSysShopLogo(SysShopLogo sysShopLogo)
    {
        return sysShopLogoMapper.updateSysShopLogo(sysShopLogo);
    }

    /**
     * 批量删除logo
     * 
     * @param ids 需要删除的logo主键
     * @return 结果
     */
    @Override
    public int deleteSysShopLogoByIds(Long[] ids)
    {
        return sysShopLogoMapper.deleteSysShopLogoByIds(ids);
    }

    /**
     * 删除logo信息
     * 
     * @param id logo主键
     * @return 结果
     */
    @Override
    public int deleteSysShopLogoById(Long id)
    {
        return sysShopLogoMapper.deleteSysShopLogoById(id);
    }
}
