package com.ruoyi.system.V2.hsgame.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.V2.hsgame.mapper.HsGameMapper;
import com.ruoyi.system.V2.hsgame.service.IHsGameService;
import com.ruoyi.system.model.domain.HsGame;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 回收游戏配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-07-29
 */
@Service
public class HsGameServiceImpl implements IHsGameService
{
    @Autowired
    private HsGameMapper hsGameMapper;

    /**
     * 查询回收游戏配置
     * 
     * @param id 回收游戏配置主键
     * @return 回收游戏配置
     */
    @Override
    public HsGame selectHsGameById(Long id)
    {
        return hsGameMapper.selectHsGameById(id);
    }

    /**
     * 查询回收游戏配置列表
     * 
     * @param hsGame 回收游戏配置
     * @return 回收游戏配置
     */
    @Override
    public List<HsGame> selectHsGameList(HsGame hsGame)
    {
        return hsGameMapper.selectHsGameList(hsGame);
    }

    /**
     * 新增回收游戏配置
     * 
     * @param hsGame 回收游戏配置
     * @return 结果
     */
    @Override
    public int insertHsGame(HsGame hsGame)
    {

        HsGame hs = new HsGame();
        hs.setCreateId(hsGame.getCreateId());
        List<HsGame> hsGames = hsGameMapper.selectHsGameList(hs);
        if (CollectionUtils.isEmpty(hsGames)){
            hsGame.setCreateTime(DateUtils.getNowDate());
            hsGame.setCreateId(hsGame.getCreateId());
            return hsGameMapper.insertHsGame(hsGame);
        }else {
            HsGame game = hsGames.get(0);
            game.setGameName(hsGame.getGameName());
            return hsGameMapper.updateHsGame(game);
        }


    }

    /**
     * 修改回收游戏配置
     * 
     * @param hsGame 回收游戏配置
     * @return 结果
     */
    @Override
    public int updateHsGame(HsGame hsGame)
    {
        return hsGameMapper.updateHsGame(hsGame);
    }

    /**
     * 批量删除回收游戏配置
     * 
     * @param ids 需要删除的回收游戏配置主键
     * @return 结果
     */
    @Override
    public int deleteHsGameByIds(Long[] ids)
    {
        return hsGameMapper.deleteHsGameByIds(ids);
    }

    /**
     * 删除回收游戏配置信息
     * 
     * @param id 回收游戏配置主键
     * @return 结果
     */
    @Override
    public int deleteHsGameById(Long id)
    {
        return hsGameMapper.deleteHsGameById(id);
    }

    @Override
    public HsGame selectHsGameByCreateId(Long createId) {
        return hsGameMapper.selectHsGameByCreateId(createId);
    }
}
