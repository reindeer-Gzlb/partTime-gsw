package com.ruoyi.system.model.service;

import com.ruoyi.system.model.domain.InterName;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ruoyi
 * @date 2022-06-30
 */
public interface IInterNameService
{
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public InterName selectInterNameById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param interName 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<InterName> selectInterNameList(InterName interName);

    /**
     * 新增【请填写功能名称】
     *
     * @param interName 【请填写功能名称】
     * @return 结果
     */
    public int insertInterName(InterName interName);

    /**
     * 修改【请填写功能名称】
     *
     * @param interName 【请填写功能名称】
     * @return 结果
     */
    public int updateInterName(InterName interName);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteInterNameByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteInterNameById(Long id);
}
