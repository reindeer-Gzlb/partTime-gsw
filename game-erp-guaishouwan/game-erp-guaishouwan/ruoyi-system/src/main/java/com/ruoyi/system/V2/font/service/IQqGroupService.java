package com.ruoyi.system.V2.font.service;

import com.ruoyi.system.model.domain.QqGroup;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2022-10-04
 */
public interface IQqGroupService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public QqGroup selectQqGroupById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param qqGroup 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<QqGroup> selectQqGroupList(QqGroup qqGroup);

    /**
     * 新增【请填写功能名称】
     * 
     * @param qqGroup 【请填写功能名称】
     * @return 结果
     */
    public int insertQqGroup(QqGroup qqGroup);

    /**
     * 修改【请填写功能名称】
     * 
     * @param qqGroup 【请填写功能名称】
     * @return 结果
     */
    public int updateQqGroup(QqGroup qqGroup);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteQqGroupByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteQqGroupById(Long id);
}
