package com.ruoyi.system.saleInfo.mapper;

import java.util.List;

import com.ruoyi.system.saleInfo.domain.GoodsSaleInfo;

/**
 * 出售记录详情Mapper接口
 *
 * @author ruoyi
 * @date 2024-04-25
 */
public interface GoodsSaleInfoMapper {
    /**
     * 查询出售记录详情
     *
     * @param id 出售记录详情主键
     * @return 出售记录详情
     */
    public GoodsSaleInfo selectGoodsSaleInfoById(Long id);

    /**
     * 查询出售记录详情列表
     *
     * @param goodsSaleInfo 出售记录详情
     * @return 出售记录详情集合
     */
    public List<GoodsSaleInfo> selectGoodsSaleInfoList(GoodsSaleInfo goodsSaleInfo);

    /**
     * 统计数量
     *
     */
    public int selectGooodsSaleInfoCount(Long userId );

    /**
     * 新增出售记录详情
     *
     * @param goodsSaleInfo 出售记录详情
     * @return 结果
     */
    public int insertGoodsSaleInfo(GoodsSaleInfo goodsSaleInfo);

    /**
     * 修改出售记录详情
     *
     * @param goodsSaleInfo 出售记录详情
     * @return 结果
     */
    public int updateGoodsSaleInfo(GoodsSaleInfo goodsSaleInfo);

    /**
     * 删除出售记录详情
     *
     * @param id 出售记录详情主键
     * @return 结果
     */
    public int deleteGoodsSaleInfoById(Long id);

    /**
     * 批量删除出售记录详情
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsSaleInfoByIds(Long[] ids);
}
