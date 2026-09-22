package com.ruoyi.system.V2.recycle.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.model.domain.Equation;
import com.ruoyi.system.V2.recycle.mapper.EquationMapper;
import com.ruoyi.system.V2.recycle.service.IEquationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公式配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-12-11
 */
@Service
public class EquationServiceImpl implements IEquationService
{
    @Autowired
    private EquationMapper equationMapper;

    /**
     * 查询公式配置
     * 
     * @param id 公式配置主键
     * @return 公式配置
     */
    @Override
    public Equation selectEquationById(Long id)
    {
        return equationMapper.selectEquationById(id);
    }

    /**
     * 查询公式配置列表
     * 
     * @param equation 公式配置
     * @return 公式配置
     */
    @Override
    public List<Equation> selectEquationList(Equation equation)
    {
        return equationMapper.selectEquationList(equation);
    }

    /**
     * 新增公式配置
     * 
     * @param equation 公式配置
     * @return 结果
     */
    @Override
    public int insertEquation(Equation equation)
    {
        equation.setCreateTime(DateUtils.getNowDate());
        return equationMapper.insertEquation(equation);
    }

    /**
     * 修改公式配置
     * 
     * @param equation 公式配置
     * @return 结果
     */
    @Override
    public int updateEquation(Equation equation)
    {
        return equationMapper.updateEquation(equation);
    }

    /**
     * 批量删除公式配置
     * 
     * @param ids 需要删除的公式配置主键
     * @return 结果
     */
    @Override
    public int deleteEquationByIds(Long[] ids)
    {
        return equationMapper.deleteEquationByIds(ids);
    }

    /**
     * 删除公式配置信息
     * 
     * @param id 公式配置主键
     * @return 结果
     */
    @Override
    public int deleteEquationById(Long id)
    {
        return equationMapper.deleteEquationById(id);
    }
}
