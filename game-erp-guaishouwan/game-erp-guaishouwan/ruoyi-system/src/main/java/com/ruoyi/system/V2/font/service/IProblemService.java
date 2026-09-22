package com.ruoyi.system.V2.font.service;

import com.ruoyi.system.model.domain.Problem;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2023-03-09
 */
public interface IProblemService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Problem selectProblemById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param problem 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Problem> selectProblemList(Problem problem);

    /**
     * 新增【请填写功能名称】
     * 
     * @param problem 【请填写功能名称】
     * @return 结果
     */
    public int insertProblem(Problem problem);

    /**
     * 修改【请填写功能名称】
     * 
     * @param problem 【请填写功能名称】
     * @return 结果
     */
    public int updateProblem(Problem problem);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteProblemByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteProblemById(Long id);
}
