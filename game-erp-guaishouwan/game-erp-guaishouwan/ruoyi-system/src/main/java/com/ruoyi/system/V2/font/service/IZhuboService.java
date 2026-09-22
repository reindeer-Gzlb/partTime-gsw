package com.ruoyi.system.V2.font.service;

import com.ruoyi.system.model.domain.Zhubo;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2023-03-09
 */
public interface IZhuboService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Zhubo selectZhuboById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param zhubo 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Zhubo> selectZhuboList(Zhubo zhubo);

    /**
     * 新增【请填写功能名称】
     * 
     * @param zhubo 【请填写功能名称】
     * @return 结果
     */
    public int insertZhubo(Zhubo zhubo);

    /**
     * 修改【请填写功能名称】
     * 
     * @param zhubo 【请填写功能名称】
     * @return 结果
     */
    public int updateZhubo(Zhubo zhubo);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteZhuboByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteZhuboById(Long id);
}
