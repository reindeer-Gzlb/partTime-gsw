package com.ruoyi.system.model.mapper;

import com.ruoyi.system.model.domain.Polish;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2023-02-27
 */
public interface PolishMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Polish selectPolishById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param polish 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Polish> selectPolishList(Polish polish);

    /**
     * 新增【请填写功能名称】
     * 
     * @param polish 【请填写功能名称】
     * @return 结果
     */
    public int insertPolish(Polish polish);

    /**
     * 修改【请填写功能名称】
     * 
     * @param polish 【请填写功能名称】
     * @return 结果
     */
    public int updatePolish(Polish polish);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deletePolishById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePolishByIds(Long[] ids);

    Polish selectDataNow(Polish polish);
}
