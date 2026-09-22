package com.ruoyi.system.shouFromName.mapper;

import java.util.List;

import com.ruoyi.system.shouFromName.domain.ShouFromName;

/**
 * 收入渠道Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-22
 */
public interface ShouFromNameMapper {
    /**
     * 查询收入渠道
     *
     * @param id 收入渠道主键
     * @return 收入渠道
     */
    public ShouFromName selectShouFromNameById(Long id);

    /**
     * 查询收入渠道列表
     *
     * @param shouFromName 收入渠道
     * @return 收入渠道集合
     */
    public List<ShouFromName> selectShouFromNameList(ShouFromName shouFromName);

    /**
     * 新增收入渠道
     *
     * @param shouFromName 收入渠道
     * @return 结果
     */
    public int insertShouFromName(ShouFromName shouFromName);

    /**
     * 修改收入渠道
     *
     * @param shouFromName 收入渠道
     * @return 结果
     */
    public int updateShouFromName(ShouFromName shouFromName);

    /**
     * 删除收入渠道
     *
     * @param id 收入渠道主键
     * @return 结果
     */
    public int deleteShouFromNameById(Long id);

    /**
     * 批量删除收入渠道
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShouFromNameByIds(Long[] ids);
}
