package com.ruoyi.system.V2.financial.service;

import com.ruoyi.system.model.domain.Finance;

import java.util.List;
import java.util.Map;

/**
 * 财务报表Service接口
 * 
 * @author ruoyi
 * @date 2023-02-24
 */
public interface IFinanceService 
{
    /**
     * 查询财务报表
     * 
     * @param id 财务报表主键
     * @return 财务报表
     */
    public Finance selectFinanceById(Long id);

    /**
     * 查询财务报表列表
     * 
     * @param finance 财务报表
     * @return 财务报表集合
     */
    public List<Finance> selectFinanceList(Finance finance);

    /**
     * 统计数据
     * @param finance
     * @return
     */
    public Map<String,Object> data(Finance finance);
    /**
     * 统计数据
     * @param finance
     * @return
     */
    public List<Map<String, Object>> statisticsData(Finance finance);

    /**
     * 新增财务报表
     * 
     * @param finance 财务报表
     * @return 结果
     */
    public int insertFinance(Finance finance);
    /**
     * 新增财务报表
     *
     * @param finance 财务报表H5用
     * @return 结果
     */
    public int insertFinanceH5(Finance finance);

    /**
     * 修改财务报表
     * 
     * @param finance 财务报表
     * @return 结果
     */
    public int updateFinance(Finance finance);

    /**
     * 批量删除财务报表
     * 
     * @param ids 需要删除的财务报表主键集合
     * @return 结果
     */
    public int deleteFinanceByIds(Long[] ids, String pwd);

    /**
     * 删除财务报表信息
     * 
     * @param id 财务报表主键
     * @return 结果
     */
    public int deleteFinanceById(Long id);

    String importAccount(List<Finance> financeList);
}
