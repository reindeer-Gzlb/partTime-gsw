package com.ruoyi.system.V2.gameAttr.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.V2.gameAttr.mapper.GameAttrTemplateMapper;
import com.ruoyi.system.V2.gameAttr.mapper.GameAttributeMapper;
import com.ruoyi.system.V2.gameAttr.service.IGameAttrTemplateService;
import com.ruoyi.system.V2.gameAttr.service.IGameAttributeService;
import com.ruoyi.system.model.domain.GameAttrTemplate;
import com.ruoyi.system.model.domain.GameAttribute;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * 游戏自定义属性对应模板Service业务层处理
 *
 * @author ruoyi
 * @date 2023-04-04
 */
@Service
public class GameAttrTemplateServiceImpl implements IGameAttrTemplateService {
    @Autowired
    private GameAttrTemplateMapper gameAttrTemplateMapper;
    @Autowired
    private GameAttributeMapper gameAttributeMapper;
    @Autowired
    private IGameAttributeService gameAttributeService;

    /**
     * 查询游戏自定义属性对应模板
     *
     * @param id 游戏自定义属性对应模板主键
     * @return 游戏自定义属性对应模板
     */
    @Override
    public GameAttrTemplate selectGameAttrTemplateById(Long id) {
        return gameAttrTemplateMapper.selectGameAttrTemplateById(id);
    }

    /**
     * 查询游戏自定义属性对应模板列表
     *
     * @param gameAttrTemplate 游戏自定义属性对应模板
     * @return 游戏自定义属性对应模板
     */
    @Override
    public List<GameAttrTemplate> selectGameAttrTemplateList(GameAttrTemplate gameAttrTemplate) {
        List<GameAttrTemplate> gameAttrTemplates = gameAttrTemplateMapper.selectGameAttrTemplateList(gameAttrTemplate);
        if (CollectionUtils.isNotEmpty(gameAttrTemplates)) {
            GameAttribute gameAttribute = new GameAttribute();
            GameAttribute gameAttributeReq = new GameAttribute();
            for (GameAttrTemplate attrTemplate : gameAttrTemplates) {
                if (attrTemplate.getAttributeId() != null && attrTemplate.getAttributeId() != 0) {
                    if (attrTemplate.getFieldType().equals("boxradio")) {
                        GameAttribute gameAttributep =
                                gameAttributeMapper.selectGameAttributeById(attrTemplate.getAttributeId());
                        gameAttributeReq.setGameId(gameAttributep.getGameId());
                        gameAttributeReq.setParentId(gameAttributep.getParentId());
                        gameAttributeReq.setAcode(gameAttributep.getAcode());
                        List<GameAttribute> gameAttributes = gameAttributeService.listAllTree(gameAttributeReq);
                        if (CollectionUtils.isNotEmpty(gameAttributes)) {
                            attrTemplate.setChildAttributes(gameAttributes.get(0).getChildGameAttributes());
                        }
                    } else {
                        gameAttribute.setParentId(attrTemplate.getAttributeId());
                        List<GameAttribute> gameAttributes = gameAttributeMapper.selectGameAttributeList(gameAttribute);
                        if (CollectionUtils.isNotEmpty(gameAttributes)) {
                            attrTemplate.setChildAttributes(gameAttributes);
                        }
                    }
                }
            }
        }
        return gameAttrTemplates;
    }

    /**
     * 新增游戏自定义属性对应模板
     *
     * @param gameAttrTemplate 游戏自定义属性对应模板
     * @return 结果
     */
    @Override
    public int insertGameAttrTemplate(GameAttrTemplate gameAttrTemplate) {
        gameAttrTemplate.setCreateTime(DateUtils.getNowDate());
        return gameAttrTemplateMapper.insertGameAttrTemplate(gameAttrTemplate);
    }

    /**
     * 修改游戏自定义属性对应模板
     *
     * @param gameAttrTemplate 游戏自定义属性对应模板
     * @return 结果
     */
    @Override
    public int updateGameAttrTemplate(GameAttrTemplate gameAttrTemplate) {
        gameAttrTemplate.setUpdateTime(DateUtils.getNowDate());
        return gameAttrTemplateMapper.updateGameAttrTemplate(gameAttrTemplate);
    }

    /**
     * 批量删除游戏自定义属性对应模板
     *
     * @param ids 需要删除的游戏自定义属性对应模板主键
     * @return 结果
     */
    @Override
    public int deleteGameAttrTemplateByIds(Long[] ids) {
        return gameAttrTemplateMapper.deleteGameAttrTemplateByIds(ids);
    }

    /**
     * 删除游戏自定义属性对应模板信息
     *
     * @param id 游戏自定义属性对应模板主键
     * @return 结果
     */
    @Override
    public int deleteGameAttrTemplateById(Long id) {
        return gameAttrTemplateMapper.deleteGameAttrTemplateById(id);
    }

    @Override
    public List<GameAttrTemplate> selectGameAttrTemplateByGameIdAndAuto(Long gameId, List<String> auto) {
        return gameAttrTemplateMapper.selectGameAttrTemplateByGameIdAndAuto(gameId, auto);
    }

    @Override
    public List<GameAttrTemplate> selectGameAttrTemplateByGameId(Long gameId) {
        return gameAttrTemplateMapper.selectGameAttrTemplateByGameId(gameId);
    }

}
