package com.ruoyi.system.model.service;

import com.ruoyi.system.model.domain.SuprAuth;

import java.util.List;

/**
 * 供货商授权表Service接口
 *
 * @author ruoyi
 * @date 2022-06-14
 */
public interface ISuprAuthService
{
    /**
     * 查询供货商授权表
     *
     * @param id 供货商授权表主键
     * @return 供货商授权表
     */
    public SuprAuth selectSuprAuthById(Long id);

    /**
     * 查询供货商授权表列表
     *
     * @param suprAuth 供货商授权表
     * @return 供货商授权表集合
     */
    public List<SuprAuth> selectSuprAuthList(SuprAuth suprAuth);

    /**
     * 新增供货商授权表
     *
     * @param suprAuth 供货商授权表
     * @return 结果
     */
    public int insertSuprAuth(SuprAuth suprAuth);

    /**
     * 修改供货商授权表
     *
     * @param suprAuth 供货商授权表
     * @return 结果
     */
    public int updateSuprAuth(SuprAuth suprAuth);

    /**
     * 批量删除供货商授权表
     *
     * @param ids 需要删除的供货商授权表主键集合
     * @return 结果
     */
    public int deleteSuprAuthByIds(Long[] ids);

    /**
     * 删除供货商授权表信息
     *
     * @param id 供货商授权表主键
     * @return 结果
     */
    public int deleteSuprAuthById(Long id);

    SuprAuth getSupeAuthByUserId(SuprAuth suprAuth);
}
