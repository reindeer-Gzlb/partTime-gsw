package com.ruoyi.system.saleInfo.service;

import java.util.List;

import com.ruoyi.system.saleInfo.domain.GoodsSaleInfo;

/**
 * 出售记录详情Service接口
 *
 * @author ruoyi
 * @date 2024-04-25
 */
public interface IGoodsSaleInfoService {
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
     * 批量删除出售记录详情
     *
     * @param ids 需要删除的出售记录详情主键集合
     * @return 结果
     */
    public int deleteGoodsSaleInfoByIds(Long[] ids, String pwd);

    /**
     * 删除出售记录详情信息
     *
     * @param id 出售记录详情主键
     * @return 结果
     */
    public int deleteGoodsSaleInfoById(Long id);
}
