package com.ruoyi.system.V2.gameInfo.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.model.domain.GameInfo;
import com.ruoyi.system.V2.gameInfo.mapper.GameInfoMapper;
import com.ruoyi.system.V2.gameInfo.service.IGameInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * gameinfoService业务层处理
 *
 * @author ruoyi
 * @date 2023-04-08
 */
@Service
public class GameInfoServiceImpl implements IGameInfoService {
    @Autowired
    private GameInfoMapper gameInfoMapper;

    /**
     * 查询gameinfo
     *
     * @param id gameinfo主键
     * @return gameinfo
     */
    @Override
    public GameInfo selectGameInfoById(Long id) {
        return gameInfoMapper.selectGameInfoById(id);
    }

    /**
     * 查询gameinfo列表
     *
     * @param gameInfo gameinfo
     * @return gameinfo
     */
    @Override
    public List<GameInfo> selectGameInfoList(GameInfo gameInfo) {
        return gameInfoMapper.selectGameInfoList(gameInfo);
    }

    /**
     * 新增gameinfo
     *
     * @param gameInfo gameinfo
     * @return 结果
     */
    @Override
    public int insertGameInfo(GameInfo gameInfo) {
        gameInfo.setCreateTime(DateUtils.getNowDate());
        return gameInfoMapper.insertGameInfo(gameInfo);
    }

    /**
     * 修改gameinfo
     *
     * @param gameInfo gameinfo
     * @return 结果
     */
    @Override
    public int updateGameInfo(GameInfo gameInfo) {
        gameInfo.setUpdateTime(DateUtils.getNowDate());
        return gameInfoMapper.updateGameInfo(gameInfo);
    }

    /**
     * 批量删除gameinfo
     *
     * @param ids 需要删除的gameinfo主键
     * @return 结果
     */
    @Override
    public int deleteGameInfoByIds(Long[] ids) {
        return gameInfoMapper.deleteGameInfoByIds(ids);
    }

    /**
     * 删除gameinfo信息
     *
     * @param id gameinfo主键
     * @return 结果
     */
    @Override
    public int deleteGameInfoById(Long id) {
        return gameInfoMapper.deleteGameInfoById(id);
    }

}
