package com.ruoyi.system.V2.api.service.impl;

import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.V2.api.service.IApiGoodsInfoService;
import com.ruoyi.system.V2.api.service.IApibdService;
import com.ruoyi.system.V2.gameAttr.mapper.GameAttrTemplateMapper;
import com.ruoyi.system.V2.gameAttr.mapper.GameAttributeMapper;
import com.ruoyi.system.V2.gameAttr.service.IGameAttributeService;
import com.ruoyi.system.V2.gameInfo.mapper.GameInfoMapper;
import com.ruoyi.system.model.domain.*;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueDto;
import com.ruoyi.system.model.dto.api.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

@Service
public class ApibdServiceImpl implements IApibdService {

    @Autowired
    private IGameAttributeService gameAttributeService;

    @Autowired
    private GameAttributeMapper gameAttributeMapper;

    @Autowired
    private GameInfoMapper gameInfoMapper;

    @Autowired
    private GameAttrTemplateMapper gameAttrTemplateMapper;

    @Autowired
    private IApiGoodsInfoService goodsInfoService;


    @Override
    public ApiGameInfoDto apiGetGameInfo(Long gameId) {
        ApiGameInfoDto apiGameInfoDto = new ApiGameInfoDto();
        GameInfo gameInfo = gameInfoMapper.selectGameInfoById(gameId);
        if (gameInfo == null) {
            throw new ServiceException("未找到此游戏");
        }

        apiGameInfoDto.setName(gameInfo.getName());
        apiGameInfoDto.setType(gameInfo.getType());

        GameAttribute gameAttribute = new GameAttribute();
        gameAttribute.setGameId(gameInfo.getId());
        List<ApiGameAttributeDto> gameAttributes;
        if (gameInfo.getType() == 0) {
            //系统
            gameAttribute.setSystemKey("system");
            gameAttributes = gameAttributeService.keySelect(gameAttribute);
            apiGameInfoDto.setSystems(gameAttributes);
            //运营商
            gameAttribute.setSystemKey("carrier");
            gameAttributes = gameAttributeService.keySelect(gameAttribute);
            apiGameInfoDto.setCarriers(gameAttributes);
        }
        //大区
        gameAttribute.setSystemKey("group");
        gameAttributes = gameAttributeService.likeKeySelectGameAttributeList(gameAttribute);
        apiGameInfoDto.setGroups(gameAttributes);

        //服
        List<Long> groupIds = new ArrayList();
        for (ApiGameAttributeDto attribute : gameAttributes) {
            groupIds.add(attribute.getId());
        }
        gameAttributes = gameAttributeMapper.getByParentIds(groupIds.stream().toArray(Long[]::new));
        apiGameInfoDto.setServers(gameAttributes);

        return apiGameInfoDto;
    }

    @Override
    public List ApiGetGameField(Long gameId) {
        GameInfo gameInfo = gameInfoMapper.selectGameInfoById(gameId);
        if (gameInfo == null) {
            throw new GlobalException("未找到此游戏");
        }
        GameAttrTemplate gameAttrTemplate = new GameAttrTemplate();
        gameAttrTemplate.setGameId(gameInfo.getId());
        gameAttrTemplate.setIsEnable("0");
        List<ApiGameAttrTemplateDto> gameAttrTemplates =
                gameAttrTemplateMapper.apiSelectGameAttrTemplateList(gameAttrTemplate);
        if (CollectionUtils.isNotEmpty(gameAttrTemplates)) {
            GameAttribute gameAttribute = new GameAttribute();
            GameAttribute gameAttributeReq = new GameAttribute();
            for (ApiGameAttrTemplateDto attrTemplate : gameAttrTemplates) {
                if (attrTemplate.getAttributeId() != null && attrTemplate.getAttributeId() != 0) {
                    if (attrTemplate.getFieldType().equals("boxradio")) {
                        GameAttribute gameAttributep =
                                gameAttributeMapper.selectGameAttributeById(attrTemplate.getAttributeId());
                        gameAttributeReq.setGameId(gameAttributep.getGameId());
                        gameAttributeReq.setParentId(gameAttributep.getParentId());
                        gameAttributeReq.setAcode(gameAttributep.getAcode());
                        List<ApiGameAttributeDto> gameAttributes =
                                gameAttributeService.apiListAllTree(gameAttributeReq);
                        if (CollectionUtils.isNotEmpty(gameAttributes)) {
                            attrTemplate.setValues(gameAttributes.get(0).getChild());
                        }
                    } else {
                        gameAttribute.setParentId(attrTemplate.getAttributeId());
                        List<ApiGameAttributeDto> gameAttributes =
                                gameAttributeMapper.apiSelectGameAttributeList(gameAttribute);
                        if (CollectionUtils.isNotEmpty(gameAttributes)) {
                            attrTemplate.setValues(gameAttributes);
                        }
                    }
                }
            }
        }
//        List<Long> attributesIds = new ArrayList<>();
//        Map<Long, ApiGameAttrTemplateDto> apiGameAttrTemplateDtoMap = new HashMap<>();
//        for (ApiGameAttrTemplateDto attrTemplate : gameAttrTemplates) {
//            if (Objects.nonNull(attrTemplate.getAttributeId())) {
//                attributesIds.add(attrTemplate.getAttributeId());
//                apiGameAttrTemplateDtoMap.put(attrTemplate.getAttributeId(), attrTemplate);
//            }
//        }
//        List<GameAttribute> attributes =
//                gameAttributeMapper.selectGameAttributeByParentIds(attributesIds.stream().toArray(Long[]::new));
//        ApiGameAttrTemplateDto apiGameAttrTemplateDto;
//        ApiGameAttributeDto apiGameAttributeDto;
//        for (GameAttribute attribute : attributes) {
//            apiGameAttrTemplateDto = apiGameAttrTemplateDtoMap.get(attribute.getParentId());
//            if (apiGameAttrTemplateDto != null) {
//                List valuesList = apiGameAttrTemplateDto.getValues();
//                if (CollectionUtils.isEmpty(valuesList)) {
//                    valuesList = new ArrayList();
//                }
//                apiGameAttributeDto = new ApiGameAttributeDto();
//                BeanUtils.copyBeanProp(apiGameAttributeDto, attribute);
//                valuesList.add(apiGameAttributeDto);
//                apiGameAttrTemplateDto.setValues(valuesList);
//            }
//        }
        return gameAttrTemplates;
    }

    @Override
    public Long ApiGoodsPublish(BDApiGoodsInfoAndAttrValueReq apiGoodsInfoAndAttrValueReq, HttpServletRequest request,
                                SupplyInfo supplyInfo) {
        GoodsInfo goodsInforeq = new GoodsInfo();
        goodsInforeq.setSupplyId(supplyInfo.getId());
        goodsInforeq.setThirdId(apiGoodsInfoAndAttrValueReq.getThirdId());
        List<GoodsInfo> goodsInfos = goodsInfoService.selectGoodsInfoList(goodsInforeq);
        if (CollectionUtils.isNotEmpty(goodsInfos) && goodsInfos.size() > 0) {
            throw new ServiceException(apiGoodsInfoAndAttrValueReq.getThirdId() + "此商品已存在");
        }

        GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto =
                checkBDApiGoodsInfoAndAttrValueReq(apiGoodsInfoAndAttrValueReq);
        goodsInfoAndAttrValueDto.setSupplyId(supplyInfo.getId());
        if (apiGoodsInfoAndAttrValueReq.getSaleState().longValue() == 7) {
            goodsInfoAndAttrValueDto.setSaleState(4);
        } else if (apiGoodsInfoAndAttrValueReq.getSaleState().longValue() == 5) {
            goodsInfoAndAttrValueDto.setSaleState(6);
        } else if (apiGoodsInfoAndAttrValueReq.getSaleState().longValue() == 4) {
            goodsInfoAndAttrValueDto.setSaleState(5);
        }
        goodsInfoAndAttrValueDto.setAccountType(1);
        goodsInfoAndAttrValueDto.setCreateId(supplyInfo.getUserId());
        Long goodsId = goodsInfoService.insertBDApiGoodsInfoAndAttr(goodsInfoAndAttrValueDto);
        return goodsId;
    }

    @Override
    public Long ApiGoodsEdit(ApiGoodsInfoEditReq apiGoodsInfoEditReq, HttpServletRequest request,
                             SupplyInfo supplyInfo) {
        GoodsInfo goodsInfo = goodsInfoService.selectGoodsInfoById(apiGoodsInfoEditReq.getGoodsId());
        if (goodsInfo == null) {
            throw new ServiceException("未找到此商品", 1000);
        }
        if (goodsInfo.getSupplyId() != null && goodsInfo.getSupplyId().longValue() != 0) {
            if (supplyInfo.getId().longValue() != goodsInfo.getSupplyId().longValue()) {
                throw new ServiceException("此商品不在此用户下,无权操作!", 1000);
            }
        } else {
            throw new ServiceException("此商品不在此用户下,无权操作!", 1000);
        }
        goodsInfo.setPrice(apiGoodsInfoEditReq.getPrice());
        goodsInfo.setDes(apiGoodsInfoEditReq.getDes());
        goodsInfo.setUpdateId(supplyInfo.getUserId());
        goodsInfoService.updateGoodsInfo(goodsInfo);
        return goodsInfo.getId();
    }

    @Override
    public Long ApiGoodsOn(ApiGoodsInfoOnOrOffReq apiGoodsInfoOnOrOffReq, HttpServletRequest request,
                           SupplyInfo supplyInfo) {
        GoodsInfo goodsInfo = goodsInfoService.selectGoodsInfoById(apiGoodsInfoOnOrOffReq.getGoodsId());
        if (goodsInfo == null) {
            throw new ServiceException("未找到此商品", 1000);
        }
        if (goodsInfo.getDel() == 1) {
            throw new ServiceException("未找到此商品", 1000);
        }
        if (goodsInfo.getSupplyId() != null && goodsInfo.getSupplyId().longValue() != 0) {
            if (supplyInfo.getId().longValue() != goodsInfo.getSupplyId().longValue()) {
                throw new ServiceException("此商品不在此用户下,无权操作!", 1000);
            }
        } else {
            throw new ServiceException("此商品不在此用户下,无权操作!", 1000);
        }
        if (goodsInfo.getSaleState() != 4) {
            throw new ServiceException("此商品处于无法上架状态", 1000);
        }
        goodsInfo.setSaleState(1);
        goodsInfoService.updateGoodsInfo(goodsInfo);
        return goodsInfo.getId();
    }

    @Override
    public Long ApiGoodsOff(ApiGoodsInfoOnOrOffReq apiGoodsInfoOnOrOffReq, HttpServletRequest request,
                            SupplyInfo supplyInfo) {
        GoodsInfo goodsInfo = goodsInfoService.selectGoodsInfoById(apiGoodsInfoOnOrOffReq.getGoodsId());
        if (goodsInfo == null) {
            throw new ServiceException("未找到此商品", 1000);
        }
        if (goodsInfo.getDel() == 1) {
            throw new ServiceException("未找到此商品", 1000);
        }
        if (goodsInfo.getSupplyId() != null && goodsInfo.getSupplyId().longValue() != 0) {
            if (supplyInfo.getId().longValue() != goodsInfo.getSupplyId().longValue()) {
                throw new ServiceException("此商品不在此用户下,无权操作!", 1000);
            }
        } else {
            throw new ServiceException("此商品不在此用户下,无权操作!", 1000);
        }
        if (goodsInfo.getSaleState() != 1) {
            throw new ServiceException("此商品处于无法下架状态", 1000);
        }
        goodsInfo.setSaleState(4);
        goodsInfoService.updateGoodsInfo(goodsInfo);
        return goodsInfo.getId();
    }

    @Override
    public Long ApiGoodsDelete(ApiGoodsInfoDeleteReq apiGoodsInfoOnOrOffReq, HttpServletRequest request,
                               SupplyInfo supplyInfo) {
        GoodsInfo goodsInfo = goodsInfoService.selectGoodsInfoById(apiGoodsInfoOnOrOffReq.getGoodsId());
        if (goodsInfo == null) {
            throw new ServiceException("未找到此商品", 1000);
        }
        if (goodsInfo.getDel() == 1) {
            throw new ServiceException("未找到此商品", 1000);
        }
        if (goodsInfo.getSupplyId() != null && goodsInfo.getSupplyId().longValue() != 0) {
            if (supplyInfo.getId().longValue() != goodsInfo.getSupplyId().longValue()) {
                throw new ServiceException("此商品不在此用户下,无权操作!", 1000);
            }
        } else {
            throw new ServiceException("此商品不在此用户下,无权操作!", 1000);
        }
        if (goodsInfo.getSaleState() != 4) {
            throw new ServiceException("此商品处于不能删除状态", 1000);
        }
        goodsInfo.setDel(1l);
        goodsInfoService.updateGoodsInfo(goodsInfo);
        return goodsInfo.getId();
    }

    @Override
    public Long ApiGoodsXiuGai(BDApiGoodsInfoAndAttrValueReq apiGoodsInfoAndAttrValueReq, HttpServletRequest request,
                               SupplyInfo supplyInfo) {
//        GoodsInfo goodsInforeq = new GoodsInfo();
//        goodsInforeq.setSupplyId(supplyInfo.getId());
//        goodsInforeq.setThirdId(apiGoodsInfoAndAttrValueReq.getThirdId());
//        List<GoodsInfo> goodsInfos = goodsInfoService.selectGoodsInfoList(goodsInforeq);
//        if (CollectionUtils.isEmpty(goodsInfos)) {
//            throw new ServiceException(apiGoodsInfoAndAttrValueReq.getThirdId() + "此商品不存在");
//        }
//
//        GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto =
//                checkBDApiGoodsInfoAndAttrValueReq(apiGoodsInfoAndAttrValueReq);
//        goodsInfoAndAttrValueDto.setSupplyId(supplyInfo.getId());
//        if (apiGoodsInfoAndAttrValueReq.getSaleState().longValue() == 7) {
//            goodsInfoAndAttrValueDto.setSaleState(4);
//        } else if (apiGoodsInfoAndAttrValueReq.getSaleState().longValue() == 5) {
//            goodsInfoAndAttrValueDto.setSaleState(6);
//        } else if (apiGoodsInfoAndAttrValueReq.getSaleState().longValue() == 4) {
//            goodsInfoAndAttrValueDto.setSaleState(5);
//        }
//        goodsInfoAndAttrValueDto.setAccountType(1);
//        goodsInfoAndAttrValueDto.setCreateId(supplyInfo.getUserId());
//        goodsInfoAndAttrValueDto.setId(goodsInfos.get(0).getId());
////        Long goodsId = goodsInfoService.insertBDApiGoodsInfoAndAttr(goodsInfoAndAttrValueDto);
//        goodsInfoService.bdupdateGoodsInfoAndAttr(goodsInfoAndAttrValueDto);
        return 1l;
    }

    GoodsInfoAndAttrValueDto checkBDApiGoodsInfoAndAttrValueReq(BDApiGoodsInfoAndAttrValueReq apiGoodsInfoAndAttrValueReq) {
        GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto = new GoodsInfoAndAttrValueDto();
        GameInfo gameInfo = gameInfoMapper.selectGameInfoById(apiGoodsInfoAndAttrValueReq.getGameId());
        if (gameInfo != null) {
            goodsInfoAndAttrValueDto.setGameName(gameInfo.getName());
        } else {
            throw new ServiceException("游戏不存在", 10000);
        }
        GameAttribute gameAttributeReq = new GameAttribute();
        gameAttributeReq.setGameId(gameInfo.getId());
        List<GameAttribute> gameAttributes;
        String oName = "";
        String cSystemKey = "";
        boolean flag = false;

        if (StringUtils.equals(apiGoodsInfoAndAttrValueReq.getGameType(), "0")) {
            if (Objects.isNull(apiGoodsInfoAndAttrValueReq.getSystemId())) {
                throw new ServiceException("systemId不能为空", 10000);
            }
            if (Objects.isNull(apiGoodsInfoAndAttrValueReq.getCarrierId())) {
                throw new ServiceException("carrierId不能为空", 10000);
            }
            //系统
            gameAttributeReq.setSystemKey("system");
            gameAttributes = gameAttributeService.keySelectGameAttributeList(gameAttributeReq);
            for (GameAttribute attribute : gameAttributes) {
                if (attribute.getId().longValue() == apiGoodsInfoAndAttrValueReq.getSystemId().longValue()) {
                    oName = attribute.getFieldName();
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                throw new ServiceException("systemId错误", 10000);
            }
            goodsInfoAndAttrValueDto.setSystemName(oName);

            //运营商
            flag = false;
            gameAttributeReq.setSystemKey("carrier");
            gameAttributes = gameAttributeService.keySelectGameAttributeList(gameAttributeReq);
            for (GameAttribute attribute : gameAttributes) {
                if (attribute.getId().longValue() == apiGoodsInfoAndAttrValueReq.getCarrierId().longValue()) {
                    oName = attribute.getFieldName();
                    cSystemKey = attribute.getSystemKey();
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                throw new ServiceException("carrierId错误", 10000);
            }
            goodsInfoAndAttrValueDto.setCarrierName(oName);
        } else if (StringUtils.equals(apiGoodsInfoAndAttrValueReq.getGameType(), "1")) {


        } else {
            throw new ServiceException("不支持的游戏类型", 10000);
        }
        //区
        flag = false;
        gameAttributeReq.setSystemKey(cSystemKey + "group");
        gameAttributes = gameAttributeService.keySelectGameAttributeList(gameAttributeReq);
        for (GameAttribute attribute : gameAttributes) {
            if (attribute.getId().longValue() == apiGoodsInfoAndAttrValueReq.getGroupId().longValue()) {
                oName = attribute.getFieldName();
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new ServiceException("groupId错误", 10000);
        }
        goodsInfoAndAttrValueDto.setGroupName(oName);

        //服
        flag = false;
        gameAttributes = gameAttributeMapper.getByPrentId(apiGoodsInfoAndAttrValueReq.getGroupId());
        for (GameAttribute attribute : gameAttributes) {
            if (attribute.getId().longValue() == apiGoodsInfoAndAttrValueReq.getServerId().longValue()) {
                oName = attribute.getFieldName();
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new ServiceException("serverId错误", 10000);
        }
        goodsInfoAndAttrValueDto.setServerName(oName);

        Field[] fields = apiGoodsInfoAndAttrValueReq.getClass().getDeclaredFields();
        Map map1 = new HashMap();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getName().startsWith("f")) {
                    map1.put(field.getName(), field.get(apiGoodsInfoAndAttrValueReq));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        List<GameAttrTemplate> gameAttrTemplates =
                gameAttrTemplateMapper.selectGameAttrTemplateByGameId(gameInfo.getId());
        if (CollectionUtils.isNotEmpty(gameAttrTemplates)) {
            for (GameAttrTemplate gameAttrTemplate : gameAttrTemplates) {
                Object aa = map1.get(gameAttrTemplate.getAavName());
                checkAttrValue(aa, gameAttrTemplate);
            }
        }

        BeanUtils.copyBeanProp(goodsInfoAndAttrValueDto, apiGoodsInfoAndAttrValueReq);

        return goodsInfoAndAttrValueDto;
    }


    void checkAttrValue(Object autoF, GameAttrTemplate gameAttrTemplate) {
        if (StringUtils.equals(gameAttrTemplate.getMust(), "true")) {
            if (Objects.isNull(autoF)) {
                throw new ServiceException(gameAttrTemplate.getAavName() + " 该字段不能为空", 10000);
            } else {
                if (StringUtils.isEmpty(autoF.toString())) {
                    throw new ServiceException(gameAttrTemplate.getAavName() + " 该字段不能为空", 10000);
                }
            }
        }
        if (Objects.nonNull(autoF)) {
            String val = autoF.toString();
            if (gameAttrTemplate.getAttributeId() != null && gameAttrTemplate.getAttributeId().longValue() != 0) {
                GameAttribute gameAttribute =
                        gameAttributeMapper.selectGameAttributeById(gameAttrTemplate.getAttributeId());
                if (gameAttribute != null) {
//                    List<GameAttribute> gameAttributes = gameAttributeMapper.getByPrentId(gameAttribute.getId());
                    if (StringUtils.equals(gameAttrTemplate.getFieldType(), "input")) {

                    } else if (StringUtils.equals(gameAttrTemplate.getFieldType(), "number")) {
                        if (!StringUtils.isNumeric(val)) {
                            throw new ServiceException(gameAttrTemplate.getAavName() + "该字段的值只能输入数字", 10000);
                        }
                    }
//                    else if (StringUtils.equals(gameAttrTemplate.getFieldType(), "checkbox") || StringUtils.equals
//                    (gameAttrTemplate.getFieldType(), "radio")) {
//                        List<String> rangeList = new ArrayList<>();
//                        for (GameAttribute attribute1 : gameAttributes) {
//                            rangeList.add(attribute1.getFieldName());
//                        }
//                        if (val.contains(",")) {
//                            String[] autoVs = val.split(",");
//                            for (String v : autoVs) {
//                                if (!rangeList.contains(v)) {
//                                    throw new ServiceException(gameAttrTemplate.getFieldName() + "该字段的值不在设置的取值范围内");
//                                }
//                            }
//                        } else {
//                            if (!rangeList.contains(val)) {
//                                throw new ServiceException(gameAttrTemplate.getFieldName() + "该字段的值不在设置的取值范围内");
//                            }
//                        }
//                    }
                } else {
                    throw new ServiceException(gameAttrTemplate.getAavName() + "该字段未找到设置的值属性,请联系运营人员!", 10000);
                }
            }
        }
    }

}
