package com.ruoyi.system.V2.financial.mapper;

import com.ruoyi.system.model.domain.Finance;

import java.util.List;
import java.util.Map;

/**
 * 财务报表Mapper接口
 * 
 * @author ruoyi
 * @date 2023-02-24
 */
public interface FinanceMapper 
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

    public Map<String, Object> data(Finance finance);

    public List<Map<String, Object>> statisticsData(Finance finance);

    /**
     * 新增财务报表
     * 
     * @param finance 财务报表
     * @return 结果
     */
    public int insertFinance(Finance finance);

    /**
     * 修改财务报表
     * 
     * @param finance 财务报表
     * @return 结果
     */
    public int updateFinance(Finance finance);

    /**
     * 删除财务报表
     * 
     * @param id 财务报表主键
     * @return 结果
     */
    public int deleteFinanceById(Long id);

    /**
     * 批量删除财务报表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceByIds(Long[] ids);

    Map<String, Object> getData(Finance finance);

    List<Map<String, Object>> todayHSSC(Finance finance);

    List<Map<String, Object>> todaySCinfo(Finance finance);
}
