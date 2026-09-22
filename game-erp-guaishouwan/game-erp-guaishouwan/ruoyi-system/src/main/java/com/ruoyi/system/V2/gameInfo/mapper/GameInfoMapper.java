package com.ruoyi.system.V2.gameInfo.mapper;

import com.ruoyi.system.model.domain.GameInfo;
import com.ruoyi.system.model.dto.api.ApiGameListDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * gameinfoMapper接口
 *
 * @author ruoyi
 * @date 2023-04-08
 */
public interface GameInfoMapper {
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
     * 删除gameinfo
     *
     * @param id gameinfo主键
     * @return 结果
     */
    public int deleteGameInfoById(Long id);

    /**
     * 批量删除gameinfo
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGameInfoByIds(Long[] ids);

    List<GameInfo> selectGameInfoByIds(@Param("ids") Set<String> gameList,@Param("type")Integer type);

    List<ApiGameListDto> apiSelectGameInfoList(GameInfo gameInfo);
}
