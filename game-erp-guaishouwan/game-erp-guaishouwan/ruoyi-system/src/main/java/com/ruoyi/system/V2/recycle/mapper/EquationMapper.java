package com.ruoyi.system.V2.recycle.mapper;

import com.ruoyi.system.model.domain.Equation;

import java.util.List;

/**
 * 公式配置Mapper接口
 * 
 * @author ruoyi
 * @date 2022-12-11
 */
public interface EquationMapper 
{
    /**
     * 查询公式配置
     * 
     * @param id 公式配置主键
     * @return 公式配置
     */
    public Equation selectEquationById(Long id);

    /**
     * 查询公式配置列表
     * 
     * @param equation 公式配置
     * @return 公式配置集合
     */
    public List<Equation> selectEquationList(Equation equation);

    /**
     * 新增公式配置
     * 
     * @param equation 公式配置
     * @return 结果
     */
    public int insertEquation(Equation equation);

    /**
     * 修改公式配置
     * 
     * @param equation 公式配置
     * @return 结果
     */
    public int updateEquation(Equation equation);

    /**
     * 删除公式配置
     * 
     * @param id 公式配置主键
     * @return 结果
     */
    public int deleteEquationById(Long id);

    /**
     * 批量删除公式配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquationByIds(Long[] ids);
}
