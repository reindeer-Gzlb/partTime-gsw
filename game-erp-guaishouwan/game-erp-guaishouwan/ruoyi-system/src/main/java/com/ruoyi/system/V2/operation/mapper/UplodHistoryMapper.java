package com.ruoyi.system.V2.operation.mapper;

import com.ruoyi.system.model.domain.UplodHistory;

import java.util.List;

/**
 * 导入记录Mapper接口
 *
 * @author ruoyi
 * @date 2022-05-16
 */
public interface UplodHistoryMapper
{
    /**
     * 查询导入记录
     *
     * @param id 导入记录主键
     * @return 导入记录
     */
    public UplodHistory selectUplodHistoryById(Long id);

    /**
     * 查询导入记录列表
     *
     * @param uplodHistory 导入记录
     * @return 导入记录集合
     */
    public List<UplodHistory> selectUplodHistoryList(UplodHistory uplodHistory);

    /**
     * 新增导入记录
     *
     * @param uplodHistory 导入记录
     * @return 结果
     */
    public int insertUplodHistory(UplodHistory uplodHistory);

    /**
     * 修改导入记录
     *
     * @param uplodHistory 导入记录
     * @return 结果
     */
    public int updateUplodHistory(UplodHistory uplodHistory);

    /**
     * 删除导入记录
     *
     * @param id 导入记录主键
     * @return 结果
     */
    public int deleteUplodHistoryById(Long id);

    /**
     * 批量删除导入记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUplodHistoryByIds(Long[] ids);
}
