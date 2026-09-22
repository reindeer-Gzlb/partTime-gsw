package com.ruoyi.system.model.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.model.domain.ImportHistory;
import com.ruoyi.system.model.mapper.ImportHistoryMapper;
import com.ruoyi.system.model.service.IImportHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 联调记录Service业务层处理
 *
 * @author ruoyi
 * @date 2022-05-26
 */
@Service
public class ImportHistoryServiceImpl implements IImportHistoryService
{
    @Autowired
    private ImportHistoryMapper importHistoryMapper;

    /**
     * 查询联调记录
     *
     * @param id 联调记录主键
     * @return 联调记录
     */
    @Override
    public ImportHistory selectImportHistoryById(Long id)
    {
        return importHistoryMapper.selectImportHistoryById(id);
    }

    /**
     * 查询联调记录列表
     *
     * @param importHistory 联调记录
     * @return 联调记录
     */
    @Override
    public List<ImportHistory> selectImportHistoryList(ImportHistory importHistory)
    {
        return importHistoryMapper.selectImportHistoryList(importHistory);
    }

    /**
     * 新增联调记录
     *
     * @param importHistory 联调记录
     * @return 结果
     */
    @Override
    public int insertImportHistory(ImportHistory importHistory)
    {
        importHistory.setCreateTime(DateUtils.getNowDate());
        return importHistoryMapper.insertImportHistory(importHistory);
    }

    /**
     * 修改联调记录
     *
     * @param importHistory 联调记录
     * @return 结果
     */
    @Override
    public int updateImportHistory(ImportHistory importHistory)
    {
        return importHistoryMapper.updateImportHistory(importHistory);
    }

    /**
     * 批量删除联调记录
     *
     * @param ids 需要删除的联调记录主键
     * @return 结果
     */
    @Override
    public int deleteImportHistoryByIds(Long[] ids)
    {
        return importHistoryMapper.deleteImportHistoryByIds(ids);
    }

    /**
     * 删除联调记录信息
     *
     * @param id 联调记录主键
     * @return 结果
     */
    @Override
    public int deleteImportHistoryById(Long id)
    {
        return importHistoryMapper.deleteImportHistoryById(id);
    }
}
