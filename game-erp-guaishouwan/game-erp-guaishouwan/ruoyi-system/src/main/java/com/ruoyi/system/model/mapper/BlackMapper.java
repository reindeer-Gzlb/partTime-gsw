package com.ruoyi.system.model.mapper;

import com.ruoyi.system.model.domain.Black;

import java.util.List;

/**
 * 黑名单列表Mapper接口
 *
 * @author ruoyi
 * @date 2022-06-27
 */
public interface BlackMapper
{
    /**
     * 查询黑名单列表
     *
     * @param id 黑名单列表主键
     * @return 黑名单列表
     */
    public Black selectBlackById(Long id);

    /**
     * 查询黑名单列表列表
     *
     * @param black 黑名单列表
     * @return 黑名单列表集合
     */
    public List<Black> selectBlackList(Black black);

    /**
     * 新增黑名单列表
     *
     * @param black 黑名单列表
     * @return 结果
     */
    public int insertBlack(Black black);

    /**
     * 修改黑名单列表
     *
     * @param black 黑名单列表
     * @return 结果
     */
    public int updateBlack(Black black);

    /**
     * 删除黑名单列表
     *
     * @param id 黑名单列表主键
     * @return 结果
     */
    public int deleteBlackById(Long id);

    /**
     * 批量删除黑名单列表
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBlackByIds(Long[] ids);
}
