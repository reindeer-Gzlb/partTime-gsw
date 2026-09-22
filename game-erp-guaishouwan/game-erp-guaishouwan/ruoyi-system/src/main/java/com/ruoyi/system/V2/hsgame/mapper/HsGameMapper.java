package com.ruoyi.system.V2.hsgame.mapper;

import com.ruoyi.system.model.domain.HsGame;

import java.util.List;

/**
 * 回收游戏配置Mapper接口
 * 
 * @author ruoyi
 * @date 2023-07-29
 */
public interface HsGameMapper 
{
    /**
     * 查询回收游戏配置
     * 
     * @param id 回收游戏配置主键
     * @return 回收游戏配置
     */
    public HsGame selectHsGameById(Long id);

    /**
     * 查询回收游戏配置列表
     * 
     * @param hsGame 回收游戏配置
     * @return 回收游戏配置集合
     */
    public List<HsGame> selectHsGameList(HsGame hsGame);

    /**
     * 新增回收游戏配置
     * 
     * @param hsGame 回收游戏配置
     * @return 结果
     */
    public int insertHsGame(HsGame hsGame);

    /**
     * 修改回收游戏配置
     * 
     * @param hsGame 回收游戏配置
     * @return 结果
     */
    public int updateHsGame(HsGame hsGame);

    /**
     * 删除回收游戏配置
     * 
     * @param id 回收游戏配置主键
     * @return 结果
     */
    public int deleteHsGameById(Long id);

    /**
     * 批量删除回收游戏配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHsGameByIds(Long[] ids);

    HsGame selectHsGameByCreateId(Long createId);
}
