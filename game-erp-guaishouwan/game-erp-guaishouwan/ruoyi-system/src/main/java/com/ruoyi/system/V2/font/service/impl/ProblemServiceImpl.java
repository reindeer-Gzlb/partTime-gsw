package com.ruoyi.system.V2.font.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.font.mapper.ProblemMapper;
import com.ruoyi.system.V2.font.service.IProblemService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-09
 */
@Service
public class ProblemServiceImpl implements IProblemService {
    @Autowired
    private ProblemMapper problemMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Problem selectProblemById(Long id) {
        return problemMapper.selectProblemById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param problem 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Problem> selectProblemList(Problem problem) {
        List<Problem> problems = problemMapper.selectProblemList(problem);
        problems.stream().forEach(p -> {
            if (StringUtils.isNotNull(p.getCreateId())) {
                SysUser sysUser = sysUserMapper.selectUserById(p.getCreateId());
                if (null != sysUser) {
                    p.setCreateName(sysUser.getNickName());
                }
            }
        });
        return problems;
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param problem 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertProblem(Problem problem) {
        problem.setCreateTime(DateUtils.getNowDate());
        return problemMapper.insertProblem(problem);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param problem 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateProblem(Problem problem) {
        problem.setUpdateTime(DateUtils.getNowDate());
        return problemMapper.updateProblem(problem);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteProblemByIds(Long[] ids) {
        return problemMapper.deleteProblemByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteProblemById(Long id) {
        return problemMapper.deleteProblemById(id);
    }
}
