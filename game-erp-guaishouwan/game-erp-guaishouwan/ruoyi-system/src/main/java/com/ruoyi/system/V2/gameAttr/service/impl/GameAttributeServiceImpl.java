package com.ruoyi.system.V2.gameAttr.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.gameAttr.mapper.GameAttributeMapper;
import com.ruoyi.system.V2.gameAttr.service.IGameAttributeService;
import com.ruoyi.system.model.domain.GameAttribute;
import com.ruoyi.system.model.dto.api.ApiGameAttributeDto;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 游戏配置Service业务层处理
 *
 * @author ruoyi
 * @date 2023-04-08
 */
@Service
public class GameAttributeServiceImpl implements IGameAttributeService {
    @Resource
    private GameAttributeMapper gameAttributeMapper;

    /**
     * 查询游戏配置
     *
     * @param id 游戏配置主键
     * @return 游戏配置
     */
    @Override
    public GameAttribute selectGameAttributeById(Long id) {
        return gameAttributeMapper.selectGameAttributeById(id);
    }

    /**
     * 查询游戏配置列表
     *
     * @param gameAttribute 游戏配置
     * @return 游戏配置
     */
    @Override
    public List<GameAttribute> selectGameAttributeList(GameAttribute gameAttribute) {
        return gameAttributeMapper.selectGameAttributeList(gameAttribute);
    }

    /**
     * 新增游戏配置
     *
     * @param gameAttribute 游戏配置
     * @return 结果
     */
    @Override
    public int insertGameAttribute(GameAttribute gameAttribute) {
        gameAttribute.setCreateTime(DateUtils.getNowDate());
        int count = gameAttributeMapper.insertGameAttribute(gameAttribute);
        GameAttribute pgameAttribute = selectGameAttributeById(gameAttribute.getParentId());
        if (gameAttribute.getParentId().longValue() == 0) {
            gameAttribute.setAcode(gameAttribute.getId().toString());
        } else {
            gameAttribute.setAcode(pgameAttribute.getAcode() + "," + gameAttribute.getId());
        }
        updateGameAttribute(gameAttribute);
        return count;
    }

    /**
     * 修改游戏配置
     *
     * @param gameAttribute 游戏配置
     * @return 结果
     */
    @Override
    public int updateGameAttribute(GameAttribute gameAttribute) {
        gameAttribute.setUpdateTime(DateUtils.getNowDate());
        return gameAttributeMapper.updateGameAttribute(gameAttribute);
    }

    /**
     * 批量删除游戏配置
     *
     * @param ids 需要删除的游戏配置主键
     * @return 结果
     */
    @Override
    public int deleteGameAttributeByIds(Long[] ids) {
        for (Long id : ids) {
            if (CollectionUtils.isNotEmpty(getByPrentId(id))) {
                throw new GlobalException("属性下含有子级,请先删除子级!");
            }
        }
        return gameAttributeMapper.deleteGameAttributeByIds(ids);
    }

    /**
     * 删除游戏配置信息
     *
     * @param id 游戏配置主键
     * @return 结果
     */
    @Override
    public int deleteGameAttributeById(Long id) {
        if (CollectionUtils.isNotEmpty(getByPrentId(id))) {
            throw new GlobalException("属性下含有子级,请先删除子级!");
        }
        return gameAttributeMapper.deleteGameAttributeById(id);
    }

    @Override
    public List<GameAttribute> listAllTree(GameAttribute gameAttribute) {
        List<GameAttribute> list = gameAttributeMapper.selectGameAttributeListByParams(gameAttribute);
        return getChildPerms(list, gameAttribute.getParentId().intValue());
    }

    @Override
    public List<GameAttribute> getByPrentId(Long id) {
        return gameAttributeMapper.getByPrentId(id);
    }

    @Override
    public List<GameAttribute> keySelectGameAttributeList(GameAttribute gameAttribute) {
        if (Objects.isNull(gameAttribute.getGameId()) || StringUtils.isEmpty(gameAttribute.getSystemKey())) {
            throw new GlobalException("游戏id或者系统关键字不能为空!");
        }
        List<GameAttribute> regameAttributes = new ArrayList<>();
        List<GameAttribute> gameAttributes = gameAttributeMapper.selectGameAttributeList(gameAttribute);
        if (CollectionUtils.isNotEmpty(gameAttributes) && gameAttributes.size() > 0) {
            regameAttributes = getByPrentId(gameAttributes.get(0).getId());
        }
        return regameAttributes;
    }

    @Override
    public List<ApiGameAttributeDto> likeKeySelectGameAttributeList(GameAttribute gameAttribute) {
        if (Objects.isNull(gameAttribute.getGameId()) || StringUtils.isEmpty(gameAttribute.getSystemKey())) {
            throw new GlobalException("游戏id或者系统关键字不能为空!");
        }
        List<ApiGameAttributeDto> regameAttributes = new ArrayList<>();
        List<ApiGameAttributeDto> gameAttributes = gameAttributeMapper.likeSystemkey(gameAttribute);
        if (CollectionUtils.isNotEmpty(gameAttributes) && gameAttributes.size() > 0) {
            for (ApiGameAttributeDto attribute : gameAttributes) {
                List<ApiGameAttributeDto> apiGameAttributeDtos =
                        gameAttributeMapper.getByParentIds(new Long[]{attribute.getId()});
                apiGameAttributeDtos.stream().forEach(apiGameAttributeDto -> {
                    apiGameAttributeDto.setParentId(null);
                    apiGameAttributeDto.setRemark(attribute.getFieldName());
                });
                regameAttributes.addAll(apiGameAttributeDtos);
            }

        }
        return regameAttributes;
    }

    @Override
    public List<ApiGameAttributeDto> keySelect(GameAttribute gameAttribute) {
        if (Objects.isNull(gameAttribute.getGameId()) || StringUtils.isEmpty(gameAttribute.getSystemKey())) {
            throw new GlobalException("游戏id或者系统关键字不能为空!");
        }
        List<ApiGameAttributeDto> regameAttributes = new ArrayList<>();
        List<ApiGameAttributeDto> gameAttributes = gameAttributeMapper.keySelect(gameAttribute);
        if (CollectionUtils.isNotEmpty(gameAttributes) && gameAttributes.size() > 0) {
            regameAttributes = gameAttributeMapper.getByParentIds(new Long[]{gameAttributes.get(0).getId()});
        }
        return regameAttributes;
    }

    @Override
    public List<ApiGameAttributeDto> apiListAllTree(GameAttribute gameAttribute) {
        List<ApiGameAttributeDto> list = gameAttributeMapper.apiSelectGameAttributeListByParams(gameAttribute);
        return apiGetChildPerms(list, gameAttribute.getParentId().intValue());
    }

    @Override
    public List<GameAttribute> allAttr(String ids) {


        if (StringUtils.isEmpty(ids)) {
            throw new GlobalException("id为null");
        }
        String[] split = ids.split(",");
        return gameAttributeMapper.allAttr(split);
    }


    @Override
    public Map<String, Object> selectWebGameAttributeList(GameAttribute gameAttribute) {


        List<GameAttribute> gameAttributes = gameAttributeMapper.selectGameAttributeList(gameAttribute);

        Map<String, Object> result = new HashMap<>();
        for (GameAttribute attribute : gameAttributes) {


            JSONObject data = new JSONObject();
            data.put("name", attribute.getFieldName());
            data.put("img", attribute.getImgUrl());

            result.put(attribute.getId() + "", data);
        }

        return result;
    }

    @Override
    public List<JSONObject> selectGameAttributeBySId(List<String> skinIds) {

        List<GameAttribute> gameAttributes = gameAttributeMapper.selectGameAttributeListBySids(skinIds);

        //获取父节点
        List<Long> collect =
                gameAttributes.stream().map(gameAttribute -> gameAttribute.getParentId()).collect(Collectors.toList());
        Long[] skuIds = collect.toArray(new Long[collect.size()]);
        List<GameAttribute> parentNames = gameAttributeMapper.selectGameAttributeListByIds(skuIds);


        List<JSONObject> fganeattr = new ArrayList<>();
        for (GameAttribute parentName : parentNames) {

            JSONObject parend = new JSONObject();
            parend.put("parendId", parentName.getId());
            parend.put("parendName", parentName.getFieldName());
            fganeattr.add(parend);
        }


        List<JSONObject> resultList = new ArrayList<>();
        for (JSONObject pname : fganeattr) {
            JSONObject result = new JSONObject();
            result.put("type", pname.getString("parendName"));
            result.put("parendId", pname.getLong("parendId"));
            List<GameAttribute> results = new ArrayList<>();
            for (GameAttribute attribute : gameAttributes) {
                if (Objects.equals(pname.getLong("parendId"), attribute.getParentId())) {
                    results.add(attribute);
                }
            }
            result.put("results", results);
            resultList.add(result);
        }
        return resultList;
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<GameAttribute> getChildPerms(List<GameAttribute> list, int parentId) {
        List<GameAttribute> returnList = new ArrayList<GameAttribute>();
        for (Iterator<GameAttribute> iterator = list.iterator(); iterator.hasNext(); ) {
            GameAttribute t = (GameAttribute) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<GameAttribute> list, GameAttribute t) {
        // 得到子节点列表
        List<GameAttribute> childList = getChildList(list, t);
        t.setChildGameAttributes(childList);
        for (GameAttribute tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<GameAttribute> getChildList(List<GameAttribute> list, GameAttribute t) {
        List<GameAttribute> tlist = new ArrayList<GameAttribute>();
        Iterator<GameAttribute> it = list.iterator();
        while (it.hasNext()) {
            GameAttribute n = (GameAttribute) it.next();
            if (n.getParentId().longValue() == t.getId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    private boolean hasChild(List<GameAttribute> list, GameAttribute t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }


    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<ApiGameAttributeDto> apiGetChildPerms(List<ApiGameAttributeDto> list, int parentId) {
        List<ApiGameAttributeDto> returnList = new ArrayList<ApiGameAttributeDto>();
        for (Iterator<ApiGameAttributeDto> iterator = list.iterator(); iterator.hasNext(); ) {
            ApiGameAttributeDto t = (ApiGameAttributeDto) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                apiRecursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void apiRecursionFn(List<ApiGameAttributeDto> list, ApiGameAttributeDto t) {
        // 得到子节点列表
        List<ApiGameAttributeDto> childList = apiGetChildList(list, t);
        t.setChild(childList);
        for (ApiGameAttributeDto tChild : childList) {
            if (apiHasChild(list, tChild)) {
                apiRecursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<ApiGameAttributeDto> apiGetChildList(List<ApiGameAttributeDto> list, ApiGameAttributeDto t) {
        List<ApiGameAttributeDto> tlist = new ArrayList<ApiGameAttributeDto>();
        Iterator<ApiGameAttributeDto> it = list.iterator();
        while (it.hasNext()) {
            ApiGameAttributeDto n = (ApiGameAttributeDto) it.next();
            if (n.getParentId().longValue() == t.getId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    private boolean apiHasChild(List<ApiGameAttributeDto> list, ApiGameAttributeDto t) {
        return apiGetChildList(list, t).size() > 0 ? true : false;
    }

}
