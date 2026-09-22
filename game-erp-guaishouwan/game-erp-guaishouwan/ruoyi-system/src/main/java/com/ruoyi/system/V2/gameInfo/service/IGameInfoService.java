package com.ruoyi.system.V2.gameInfo.service;

import com.ruoyi.system.model.domain.GameInfo;

import java.util.List;

/**
 * gameinfoService接口
 * 
 * @author ruoyi
 * @date 2023-04-08
 */
public interface IGameInfoService 
{
    /**
     * 查询gameinfo
     * 
     * @param id gameinfo主键
     * @return gameinfo
     */
    public GameInfo selectGameInfoById(Long id);

    /**
     * 查询gameinfo列表
     * 
     * @param gameInfo gameinfo
     * @return gameinfo集合
     */
    public List<GameInfo> selectGameInfoList(GameInfo gameInfo);

    /**
     * 新增gameinfo
     * 
     * @param gameInfo gameinfo
     * @return 结果
     */
    public int insertGameInfo(GameInfo gameInfo);

    /**
     * 修改gameinfo
     * 
     * @param gameInfo gameinfo
     * @return 结果
     */
    public int updateGameInfo(GameInfo gameInfo);

    /**
     * 批量删除gameinfo
     * 
     * @param ids 需要删除的gameinfo主键集合
     * @return 结果
     */
    public int deleteGameInfoByIds(Long[] ids);

    /**
     * 删除gameinfo信息
     * 
     * @param id gameinfo主键
     * @return 结果
     */
    public int deleteGameInfoById(Long id);
    
}
