package com.ruoyi.system.V2.operation.service;

import com.ruoyi.system.model.domain.OperationHistory;

import java.util.List;
import java.util.Map;

/**
 * 操作历史记录Service接口
 *
 * @author ruoyi
 * @date 2022-05-16
 */
public interface IOperationHistoryService
{
    /**
     * 查询操作历史记录
     *
     * @param id 操作历史记录主键
     * @return 操作历史记录
     */
    public OperationHistory selectOperationHistoryById(Long id);

    /**
     * 查询操作历史记录列表
     *
     * @param operationHistory 操作历史记录
     * @return 操作历史记录集合
     */
    public List<OperationHistory> selectOperationHistoryList(OperationHistory operationHistory);

    /**
     * 新增操作历史记录
     *
     * @param operationHistory 操作历史记录
     * @return 结果
     */
    public int insertOperationHistory(OperationHistory operationHistory);

    /**
     * 修改操作历史记录
     *
     * @param operationHistory 操作历史记录
     * @return 结果
     */
    public int updateOperationHistory(OperationHistory operationHistory);

    /**
     * 批量删除操作历史记录
     *
     * @param ids 需要删除的操作历史记录主键集合
     * @return 结果
     */
    public int deleteOperationHistoryByIds(Long[] ids);

    /**
     * 删除操作历史记录信息
     *
     * @param id 操作历史记录主键
     * @return 结果
     */
    public int deleteOperationHistoryById(Long id);

    /**
     * 查询操作历史状态
     * @return
     */
    List<Map<String,Object>> selectOperationStatus();
}
