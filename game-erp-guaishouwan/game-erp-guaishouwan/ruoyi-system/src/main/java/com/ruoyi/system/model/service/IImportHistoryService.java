package com.ruoyi.system.model.service;

import com.ruoyi.system.model.domain.ImportHistory;

import java.util.List;

/**
 * 联调记录Service接口
 *
 * @author ruoyi
 * @date 2022-05-26
 */
public interface IImportHistoryService
{
    /**
     * 查询联调记录
     *
     * @param id 联调记录主键
     * @return 联调记录
     */
    public ImportHistory selectImportHistoryById(Long id);

    /**
     * 查询联调记录列表
     *
     * @param importHistory 联调记录
     * @return 联调记录集合
     */
    public List<ImportHistory> selectImportHistoryList(ImportHistory importHistory);

    /**
     * 新增联调记录
     *
     * @param importHistory 联调记录
     * @return 结果
     */
    public int insertImportHistory(ImportHistory importHistory);

    /**
     * 修改联调记录
     *
     * @param importHistory 联调记录
     * @return 结果
     */
    public int updateImportHistory(ImportHistory importHistory);

    /**
     * 批量删除联调记录
     *
     * @param ids 需要删除的联调记录主键集合
     * @return 结果
     */
    public int deleteImportHistoryByIds(Long[] ids);

    /**
     * 删除联调记录信息
     *
     * @param id 联调记录主键
     * @return 结果
     */
    public int deleteImportHistoryById(Long id);
}
