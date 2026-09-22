package com.ruoyi.system.V2.gameAttr.mapper;

import com.ruoyi.system.model.domain.GameAttribute;
import com.ruoyi.system.model.dto.api.ApiGameAttributeDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 游戏配置Mapper接口
 *
 * @author ruoyi
 * @date 2023-04-08
 */
public interface GameAttributeMapper {
    /**
     * 查询游戏配置
     *
     * @param id 游戏配置主键
     * @return 游戏配置
     */
    public GameAttribute selectGameAttributeById(Long id);

    /**
     * 查询游戏配置列表
     *
     * @param gameAttribute 游戏配置
     * @return 游戏配置集合
     */
    public List<GameAttribute> selectGameAttributeList(GameAttribute gameAttribute);

    /**
     * 新增游戏配置
     *
     * @param gameAttribute 游戏配置
     * @return 结果
     */
    public int insertGameAttribute(GameAttribute gameAttribute);

    /**
     * 修改游戏配置
     *
     * @param gameAttribute 游戏配置
     * @return 结果
     */
    public int updateGameAttribute(GameAttribute gameAttribute);

    /**
     * 删除游戏配置
     *
     * @param id 游戏配置主键
     * @return 结果
     */
    public int deleteGameAttributeById(Long id);

    /**
     * 批量删除游戏配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGameAttributeByIds(Long[] ids);

    List<GameAttribute> getByPrentId(Long id);


    List<ApiGameAttributeDto> likeSystemkey(GameAttribute gameAttribute);

    List<ApiGameAttributeDto> getByParentIds(Long[] ids);

    List<ApiGameAttributeDto> keySelect(GameAttribute gameAttribute);

    List<ApiGameAttributeDto> apiSelectGameAttributeListByParams(GameAttribute gameAttribute);

    List<ApiGameAttributeDto> apiSelectGameAttributeList(GameAttribute gameAttribute);

    List<GameAttribute> selectGameAttributeListByIds(Long[] ids);

    List<GameAttribute> selectGameAttributeByParentIds(Long[] ids);

    List<GameAttribute> allAttr(@Param("ids") String[] ids);

    public List<GameAttribute> selectGameAttributeListByParams(GameAttribute gameAttribute);

    List<GameAttribute> selectGameAttributeListBySids(@Param("skinIds") List<String> skinIds);
}
