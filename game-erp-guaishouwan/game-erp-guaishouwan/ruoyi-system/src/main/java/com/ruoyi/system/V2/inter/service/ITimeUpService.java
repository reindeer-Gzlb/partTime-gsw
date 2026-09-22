package com.ruoyi.system.V2.inter.service;

import com.ruoyi.system.model.domain.TimeUp;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2023-04-01
 */
public interface ITimeUpService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public TimeUp selectTimeUpById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param timeUp 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<TimeUp> selectTimeUpList(TimeUp timeUp);

    /**
     * 新增【请填写功能名称】
     * 
     * @param timeUp 【请填写功能名称】
     * @return 结果
     */
    public int insertTimeUp(TimeUp timeUp);

    /**
     * 修改【请填写功能名称】
     * 
     * @param timeUp 【请填写功能名称】
     * @return 结果
     */
    public int updateTimeUp(TimeUp timeUp);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteTimeUpByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteTimeUpById(Long id);
}
