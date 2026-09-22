package com.ruoyi.system.V2.gameAttr.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.model.domain.GameAttribute;
import com.ruoyi.system.model.dto.api.ApiGameAttributeDto;

import java.util.List;
import java.util.Map;

/**
 * 游戏配置Service接口
 * 
 * @author ruoyi
 * @date 2023-04-08
 */
public interface IGameAttributeService 
{
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
     * 批量删除游戏配置
     * 
     * @param ids 需要删除的游戏配置主键集合
     * @return 结果
     */
    public int deleteGameAttributeByIds(Long[] ids);

    /**
     * 删除游戏配置信息
     * 
     * @param id 游戏配置主键
     * @return 结果
     */
    public int deleteGameAttributeById(Long id);


    List<GameAttribute> listAllTree(GameAttribute gameAttribute);

    List<GameAttribute> getByPrentId(Long id);

    /**
     * 根据gameId和系统关键字查询下级
     *
     * @param gameAttribute 游戏配置
     * @return 游戏配置集合
     */
    List<GameAttribute> keySelectGameAttributeList(GameAttribute gameAttribute);

    List<ApiGameAttributeDto> likeKeySelectGameAttributeList(GameAttribute gameAttribute);

    List<ApiGameAttributeDto> keySelect(GameAttribute gameAttribute);

    List<ApiGameAttributeDto> apiListAllTree(GameAttribute gameAttribute);

    List<GameAttribute> allAttr(String ids);

    Map<String,Object> selectWebGameAttributeList(GameAttribute gameAttribute);

    List<JSONObject> selectGameAttributeBySId(List<String> skinIds);
}
