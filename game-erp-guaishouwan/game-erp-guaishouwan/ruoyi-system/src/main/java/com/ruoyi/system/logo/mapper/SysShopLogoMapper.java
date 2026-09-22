package com.ruoyi.system.logo.mapper;

import com.ruoyi.system.logo.domain.SysShopLogo;

import java.util.List;

/**
 * logoMapper接口
 * 
 * @author ruoyi
 * @date 2024-08-09
 */
public interface SysShopLogoMapper 
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
     * 删除logo
     * 
     * @param id logo主键
     * @return 结果
     */
    public int deleteSysShopLogoById(Long id);

    /**
     * 批量删除logo
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysShopLogoByIds(Long[] ids);
}
