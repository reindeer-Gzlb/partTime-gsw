package com.ruoyi.system.V2.recycle.service;

import com.ruoyi.system.model.domain.Krecycle;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2023-03-28
 */
public interface IKrecycleService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Krecycle selectKrecycleById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param krecycle 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Krecycle> selectKrecycleList(Krecycle krecycle);

    /**
     * 新增【请填写功能名称】
     * 
     * @param krecycle 【请填写功能名称】
     * @return 结果
     */
    public int insertKrecycle(Krecycle krecycle);

    /**
     * 修改【请填写功能名称】
     * 
     * @param krecycle 【请填写功能名称】
     * @return 结果
     */
    public int updateKrecycle(Krecycle krecycle);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteKrecycleByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteKrecycleById(Long id);


    public int insertH5Krecycle(Krecycle krecycle);
}
