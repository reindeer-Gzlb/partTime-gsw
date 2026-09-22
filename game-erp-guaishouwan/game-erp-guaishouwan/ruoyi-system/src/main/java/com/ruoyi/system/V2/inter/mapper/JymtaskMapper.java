package com.ruoyi.system.V2.inter.mapper;

import com.ruoyi.system.model.domain.Jymtask;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-15
 */
public interface JymtaskMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Jymtask selectJymtaskById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param jymtask 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Jymtask> selectJymtaskList(Jymtask jymtask);

    /**
     * 新增【请填写功能名称】
     * 
     * @param jymtask 【请填写功能名称】
     * @return 结果
     */
    public int insertJymtask(Jymtask jymtask);

    /**
     * 修改【请填写功能名称】
     * 
     * @param jymtask 【请填写功能名称】
     * @return 结果
     */
    public int updateJymtask(Jymtask jymtask);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteJymtaskById(Long id);

    public int deleteJymtaskBySaleCode(String saleCode);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteJymtaskByIds(Long[] ids);
}
