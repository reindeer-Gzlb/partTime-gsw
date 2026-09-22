package com.ruoyi.system.V2.gameAttr.service;

import com.ruoyi.system.model.domain.GameAttrTemplate;

import java.util.List;

/**
 * 游戏自定义属性对应模板Service接口
 *
 * @author ruoyi
 * @date 2023-04-04
 */
public interface IGameAttrTemplateService {
    /**
     * 查询游戏自定义属性对应模板
     *
     * @param id 游戏自定义属性对应模板主键
     * @return 游戏自定义属性对应模板
     */
    public GameAttrTemplate selectGameAttrTemplateById(Long id);

    /**
     * 查询游戏自定义属性对应模板列表
     *
     * @param gameAttrTemplate 游戏自定义属性对应模板
     * @return 游戏自定义属性对应模板集合
     */
    public List<GameAttrTemplate> selectGameAttrTemplateList(GameAttrTemplate gameAttrTemplate);

    /**
     * 新增游戏自定义属性对应模板
     *
     * @param gameAttrTemplate 游戏自定义属性对应模板
     * @return 结果
     */
    public int insertGameAttrTemplate(GameAttrTemplate gameAttrTemplate);

    /**
     * 修改游戏自定义属性对应模板
     *
     * @param gameAttrTemplate 游戏自定义属性对应模板
     * @return 结果
     */
    public int updateGameAttrTemplate(GameAttrTemplate gameAttrTemplate);

    /**
     * 批量删除游戏自定义属性对应模板
     *
     * @param ids 需要删除的游戏自定义属性对应模板主键集合
     * @return 结果
     */
    public int deleteGameAttrTemplateByIds(Long[] ids);

    /**
     * 删除游戏自定义属性对应模板信息
     *
     * @param id 游戏自定义属性对应模板主键
     * @return 结果
     */
    public int deleteGameAttrTemplateById(Long id);


    public List<GameAttrTemplate> selectGameAttrTemplateByGameIdAndAuto(Long gameId, List<String> auto);

    public List<GameAttrTemplate> selectGameAttrTemplateByGameId(Long gameId);

}
