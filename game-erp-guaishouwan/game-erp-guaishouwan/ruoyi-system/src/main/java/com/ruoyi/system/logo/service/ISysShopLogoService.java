package com.ruoyi.system.logo.service;

import com.ruoyi.system.logo.domain.SysShopLogo;

import java.util.List;

/**
 * logoService接口
 * 
 * @author ruoyi
 * @date 2024-08-09
 */
public interface ISysShopLogoService 
{
    /**
     * 查询logo
     * 
     * @param id logo主键
     * @return logo
     */
    public SysShopLogo selectSysShopLogoById(Long id);

    /**
     * 查询logo列表
     * 
     * @param sysShopLogo logo
     * @return logo集合
     */
    public List<SysShopLogo> selectSysShopLogoList(SysShopLogo sysShopLogo);

    /**
     * 新增logo
     * 
     * @param sysShopLogo logo
     * @return 结果
     */
    public int insertSysShopLogo(SysShopLogo sysShopLogo);

    /**
     * 修改logo
     * 
     * @param sysShopLogo logo
     * @return 结果
     */
    public int updateSysShopLogo(SysShopLogo sysShopLogo);

    /**
     * 批量删除logo
     * 
     * @param ids 需要删除的logo主键集合
     * @return 结果
     */
    public int deleteSysShopLogoByIds(Long[] ids);

    /**
     * 删除logo信息
     * 
     * @param id logo主键
     * @return 结果
     */
    public int deleteSysShopLogoById(Long id);
}
