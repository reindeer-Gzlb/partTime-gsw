package com.ruoyi.system.V2.operation.service.impl;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.V2.operation.service.IOperationHistoryService;
import com.ruoyi.system.model.domain.OperationHistory;
import com.ruoyi.system.V2.operation.mapper.OperationHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 操作历史记录Service业务层处理
 *
 * @author ruoyi
 * @date 2022-05-16
 */
@Service
public class OperationHistoryServiceImpl implements IOperationHistoryService
{
    @Autowired(required = false)
    private OperationHistoryMapper operationHistoryMapper;

    /**
     * 查询操作历史记录
     *
     * @param id 操作历史记录主键
     * @return 操作历史记录
     */
    @Override
    public OperationHistory selectOperationHistoryById(Long id)
    {
        return operationHistoryMapper.selectOperationHistoryById(id);
    }

    /**
     * 查询操作历史记录列表
     *
     * @param operationHistory 操作历史记录
     * @return 操作历史记录
     */
    @Override
    public List<OperationHistory> selectOperationHistoryList(OperationHistory operationHistory)
    {
        return operationHistoryMapper.selectOperationHistoryList(operationHistory);
    }

    /**
     * 新增操作历史记录
     *
     * @param operationHistory 操作历史记录
     * @return 结果
     */
    @Override
    public int insertOperationHistory(OperationHistory operationHistory)
    {
        return operationHistoryMapper.insertOperationHistory(operationHistory);
    }

    /**
     * 修改操作历史记录
     *
     * @param operationHistory 操作历史记录
     * @return 结果
     */
    @Override
    public int updateOperationHistory(OperationHistory operationHistory)
    {
        return operationHistoryMapper.updateOperationHistory(operationHistory);
    }

    /**
     * 批量删除操作历史记录
     *
     * @param ids 需要删除的操作历史记录主键
     * @return 结果
     */
    @Override
    public int deleteOperationHistoryByIds(Long[] ids)
    {
        return operationHistoryMapper.deleteOperationHistoryByIds(ids);
    }

    /**
     * 删除操作历史记录信息
     *
     * @param id 操作历史记录主键
     * @return 结果
     */
    @Override
    public int deleteOperationHistoryById(Long id)
    {
        return operationHistoryMapper.deleteOperationHistoryById(id);
    }

    @Override
    public List<Map<String,Object>> selectOperationStatus() {
        return operationHistoryMapper.selectOperationStatus();
    }
}
