package com.ruoyi.system.recovery.service;

import java.util.List;
import com.ruoyi.system.recovery.domain.GoodsRecoveryInfo;

/**
 * 回收管理Service接口
 * 
 * @author ruoyi
 * @date 2024-04-20
 */
public interface IGoodsRecoveryInfoService 
{
    /**
     * 查询回收管理
     * 
     * @param id 回收管理主键
     * @return 回收管理
     */
    public GoodsRecoveryInfo selectGoodsRecoveryInfoById(Long id);

    /**
     * 查询回收管理列表
     * 
     * @param goodsRecoveryInfo 回收管理
     * @return 回收管理集合
     */
    public List<GoodsRecoveryInfo> selectGoodsRecoveryInfoList(GoodsRecoveryInfo goodsRecoveryInfo);

    /**
     * 新增回收管理
     * 
     * @param goodsRecoveryInfo 回收管理
     * @return 结果
     */
    public int insertGoodsRecoveryInfo(GoodsRecoveryInfo goodsRecoveryInfo);

    /**
     * 修改回收管理
     * 
     * @param goodsRecoveryInfo 回收管理
     * @return 结果
     */
    public int updateGoodsRecoveryInfo(GoodsRecoveryInfo goodsRecoveryInfo);

    /**
     * 批量删除回收管理
     * 
     * @param ids 需要删除的回收管理主键集合
     * @return 结果
     */
    public int deleteGoodsRecoveryInfoByIds(Long[] ids, String pwd);

    /**
     * 删除回收管理信息
     * 
     * @param id 回收管理主键
     * @return 结果
     */
    public int deleteGoodsRecoveryInfoById(Long id);
}
