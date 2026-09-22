package com.ruoyi.system.V2.inter.service;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.model.domain.SalePartner;
import com.ruoyi.system.model.domain.SelectPush;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2022-10-08
 */
public interface ISelectPushService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public SelectPush selectSelectPushById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param selectPush 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SelectPush> selectSelectPushList(SelectPush selectPush);

    /**
     * 新增【请填写功能名称】
     * 
     * @param selectPush 【请填写功能名称】
     * @return 结果
     */
    public int insertSelectPush(SelectPush selectPush);

    /**
     * 修改【请填写功能名称】
     * 
     * @param selectPush 【请填写功能名称】
     * @return 结果
     */
    public int updateSelectPush(SelectPush selectPush);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteSelectPushByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteSelectPushById(Long id);

    Map<String,Object> getAllSupliar(String config);

    void addAupliar(SelectPush selectPush);

    List<SalePartner> selectAllConfigBySup(Long createId);

    void editConfigBySup(SelectPush selectPush);
}
