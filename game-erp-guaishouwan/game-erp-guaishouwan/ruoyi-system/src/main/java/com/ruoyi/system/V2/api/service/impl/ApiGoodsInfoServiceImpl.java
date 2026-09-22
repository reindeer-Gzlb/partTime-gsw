package com.ruoyi.system.V2.api.service.impl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.Constant;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.V2.api.service.IApiGoodsInfoService;
import com.ruoyi.system.V2.financial.mapper.FinanceMapper;
import com.ruoyi.system.V2.gameAttr.mapper.AccountAttrValueMapper;
import com.ruoyi.system.V2.gameAttr.mapper.GameAttrTemplateMapper;
import com.ruoyi.system.V2.gameAttr.mapper.GameAttributeMapper;
import com.ruoyi.system.V2.gameAttr.service.IGameAttrTemplateService;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoImgMapper;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoMapper;
import com.ruoyi.system.V2.operation.service.IOperationHistoryService;
import com.ruoyi.system.V2.phone.mapper.TelManageMapper;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.*;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueDto;
import com.ruoyi.system.model.service.AsyncService;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;

/**
 * goodsService业务层处理
 *
 * @author ruoyi
 * @date 2023-04-06
 */
@Service
public class ApiGoodsInfoServiceImpl implements IApiGoodsInfoService {
    @Resource
    private GoodsInfoMapper goodsInfoMapper;
    @Resource
    private FinanceMapper financeMapper;
    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private AccountAttrValueMapper accountAttrValueMapper;

    @Autowired
    private IGameAttrTemplateService gameAttrTemplateService;

    @Resource
    private GameAttributeMapper gameAttributeMapper;

    @Resource
    private SysDictDataMapper sysDictDataMapper;

    @Resource
    private GoodsInfoImgMapper goodsInfoImgMapper;

    @Autowired
    private IOperationHistoryService operationHistoryService;

    @Resource
    private TelManageMapper telManageMapper;

    @Autowired
    @Lazy
    private AsyncService asyncService;

    @Autowired
    private GameAttrTemplateMapper gameAttrTemplateMapper;


    /**
     * 新增goods
     *
     * @param goodsInfo goods
     * @return 结果
     */
    @Override
    public int insertGoodsInfo(GoodsInfo goodsInfo) {
        goodsInfo.setCreateTime(DateUtils.getNowDate());
        return goodsInfoMapper.insertGoodsInfo(goodsInfo);
    }

    /**
     * 修改goods
     *
     * @param goodsInfo goods
     * @return 结果
     */
    @Override
    public int updateGoodsInfo(GoodsInfo goodsInfo) {
        goodsInfo.setUpdateTime(DateUtils.getNowDate());
        excuTaskPoolEdit(goodsInfo);
        return goodsInfoMapper.updateGoodsInfo(goodsInfo);
    }

    @Override
    public int ApiGoodsOn(GoodsInfo goodsInfo) {
        goodsInfo.setUpdateTime(DateUtils.getNowDate());
        return goodsInfoMapper.updateGoodsInfo(goodsInfo);
    }

    @Override
    public int ApiGoodsOff(GoodsInfo goodsInfo) {
        goodsInfo.setUpdateTime(DateUtils.getNowDate());
        excuTaskPoolDel(goodsInfo);
        return goodsInfoMapper.updateGoodsInfo(goodsInfo);
    }

    @Override
    public GoodsInfoAndAttrValueDto selectGoodsInfoAndAttrValueById(Long id) {
        GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto = goodsInfoMapper.selectGoodsInfoAndAttrValueById(id);
        GoodsInfoImg goodsInfoImg = new GoodsInfoImg();
        goodsInfoImg.setGoodsId(goodsInfoAndAttrValueDto.getId());
        goodsInfoImg.setType("1");
        List<GoodsInfoImg> goodsInfoImgs = goodsInfoImgMapper.selectGoodsInfoImgList(goodsInfoImg);
        List<Map<String, Object>> imgMaps = new ArrayList<>();
        for (GoodsInfoImg goodsImg : goodsInfoImgs) {
            Map<String, Object> imgMap = new HashMap<>();
            if (!StringUtils.isNull(goodsImg.getFileName()) && !StringUtils.isNull(goodsImg.getImgUrl())) {
                imgMap.put("name", goodsImg.getFileName());
                imgMap.put("url", goodsImg.getImgUrl());
                imgMaps.add(imgMap);
            }
        }
        if (!org.springframework.util.CollectionUtils.isEmpty(imgMaps)) {
            goodsInfoAndAttrValueDto.setUrlArr(imgMaps);
        }
        goodsInfoImg.setType("2");
        List<GoodsInfoImg> video = goodsInfoImgMapper.selectGoodsInfoImgList(goodsInfoImg);
        if (!org.springframework.util.CollectionUtils.isEmpty(video) && video.size() > 0) {
            goodsInfoAndAttrValueDto.setVideoName(video.get(0).getFileName());
            goodsInfoAndAttrValueDto.setVideoUrl(video.get(0).getImgUrl());
        }
        return goodsInfoAndAttrValueDto;
    }


    private void excuTaskPoolEdit(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        asyncService.excuTaskPool(goodsInfoAndAttrValueDto.getId(), null, Constant.edit,
                goodsInfoAndAttrValueDto.getCreateId(), 1l);
    }

    private void excuTaskPoolEdit(GoodsInfo goodsInfo) {
        asyncService.excuTaskPool(goodsInfo.getId(), null, Constant.edit,
                goodsInfo.getCreateId(), 1l);
    }

    private void excuTaskPoolDel(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        asyncService.excuTaskPool(goodsInfoAndAttrValueDto.getId(), null, Constant.del,
                goodsInfoAndAttrValueDto.getCreateId(), 1l);
    }

    private void excuTaskPoolDel(GoodsInfo goodsInfo) {
        asyncService.excuTaskPool(goodsInfo.getId(), null, Constant.del,
                goodsInfo.getCreateId(), 1l);
    }

    /**
     * 查询goods
     *
     * @param id goods主键
     * @return goods
     */
    @Override
    public GoodsInfo selectGoodsInfoById(Long id) {
        return goodsInfoMapper.selectGoodsInfoById(id);
    }

    /**
     * 查询goods列表
     *
     * @param goodsInfo goods
     * @return goods
     */
    @Override
    public List<GoodsInfo> selectGoodsInfoList(GoodsInfo goodsInfo) {
        return goodsInfoMapper.selectGoodsInfoList(goodsInfo);
    }

    @Override
    @Transactional
    public int bdupdateGoodsInfoAndAttr(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        GoodsInfo goodsInfo = new GoodsInfo();
        AccountAttrValue accountAttrValue =
                accountAttrValueMapper.selectAccountAttrValueByAccountInfoId(goodsInfoAndAttrValueDto.getId());
        if (accountAttrValue != null) {
            Long aavId = accountAttrValue.getId();
            BeanUtils.copyBeanProp(goodsInfo, goodsInfoAndAttrValueDto);
            BeanUtils.copyBeanProp(accountAttrValue, goodsInfoAndAttrValueDto);
            String allFeild = getAllField(goodsInfo, accountAttrValue);
            goodsInfo.setAllField(allFeild);
//            goodsInfo.setUpdateId(SecurityUtils.getUserId());
            updateGoodsInfo(goodsInfo);
//            if (!CollectionUtils.isEmpty(goodsInfoAndAttrValueDto.getUrlArr())) {
//                bathAddImg(goodsInfoAndAttrValueDto.getUrlArr(), goodsInfo.getId());
//            } else {
//                goodsInfoImgMapper.deleteByGoodsId(goodsInfo.getId(), Constants.STR_1);
//            }
//            if (!StringUtils.isNull(goodsInfoAndAttrValueDto.getVideoUrl())) {
//                addVideo(goodsInfoAndAttrValueDto.getVideoUrl(), goodsInfoAndAttrValueDto.getVideoName(),
//                        goodsInfoAndAttrValueDto.getId());
//            }
            accountAttrValue.setId(aavId);
            //添加到任务池
//            excuTaskPoolEdit(goodsInfoAndAttrValueDto);
            accountAttrValueMapper.updateAccountAttrValue(accountAttrValue);
        } else {
            throw new GlobalException("找不到此商品属性信息");
        }
        return 1;
    }

    @Override
    public Long insertApiGoodsInfoAndAttr(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
//        Long userId = SecurityUtils.getUserId();
//        SysUser sysUser = sysUserMapper.selectUserById(userId);
//        List<SysRole> roles = sysUser.getRoles();
//        int accountType = 2;
//        int saleState = 1;
//        if (CollectionUtils.isNotEmpty(roles)) {
//            List<String> collect = roles.stream().map(SysRole::getRoleKey).collect(Collectors.toList());
//            if (collect.contains(Constant.selfSale)) {
//                accountType = 1;
//                saleState = 0;
//            }
//        }
//        goodsInfoAndAttrValueDto.setAccountType(accountType);
//        goodsInfoAndAttrValueDto.setSaleState(saleState);

        GoodsInfo goodsInfo = new GoodsInfo();
        AccountAttrValue accountAttrValue = new AccountAttrValue();
        goodsInfoAndAttrValueDto.setSaleState(1);  //新增默认为 未出售状态
        BeanUtils.copyBeanProp(goodsInfo, goodsInfoAndAttrValueDto);
        BeanUtils.copyBeanProp(accountAttrValue, goodsInfoAndAttrValueDto);
        String allFeild = getAllField(goodsInfo, accountAttrValue);
        goodsInfo.setAllField(allFeild);
        findAndSetShiMing(goodsInfo, goodsInfoAndAttrValueDto);

        int count = insertGoodsInfo(goodsInfo);
        if (StringUtils.isNotEmpty(goodsInfoAndAttrValueDto.getImgUrls())) {
            apiBathAddImg2(goodsInfoAndAttrValueDto.getImgUrls(), goodsInfo.getId(), goodsInfo.getCreateId());
        }
        if (!StringUtils.isNull(goodsInfoAndAttrValueDto.getVideoUrl())) {
            apiAddVideo(goodsInfoAndAttrValueDto.getVideoUrl(), goodsInfoAndAttrValueDto.getVideoName(),
                    goodsInfo.getId(), goodsInfo.getCreateId());
        }
        accountAttrValue.setAccountInfoId(goodsInfo.getId());
        accountAttrValueMapper.insertAccountAttrValue(accountAttrValue);
        return goodsInfo.getId();
    }

    @Override
    @Transactional
    public Long updateApiGoodsInfoAndAttr(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        GoodsInfo goodsInfo = new GoodsInfo();
        AccountAttrValue accountAttrValue =
                accountAttrValueMapper.selectAccountAttrValueByAccountInfoId(goodsInfoAndAttrValueDto.getId());
        if (accountAttrValue != null) {
            Long aavId = accountAttrValue.getId();
            BeanUtils.copyBeanProp(goodsInfo, goodsInfoAndAttrValueDto);
            BeanUtils.copyBeanProp(accountAttrValue, goodsInfoAndAttrValueDto);
            String allFeild = getAllField(goodsInfo, accountAttrValue);
            goodsInfo.setAllField(allFeild);
            findAndSetShiMing(goodsInfo, goodsInfoAndAttrValueDto);
            updateGoodsInfo(goodsInfo);
            if (StringUtils.isNotEmpty(goodsInfoAndAttrValueDto.getImgUrls())) {
                apiBathAddImg2(goodsInfoAndAttrValueDto.getImgUrls(), goodsInfo.getId(), goodsInfo.getCreateId());
            } else {
                goodsInfoImgMapper.deleteByGoodsId(goodsInfo.getId(), Constants.STR_1);
            }
            if (!StringUtils.isNull(goodsInfoAndAttrValueDto.getVideoUrl())) {
                apiAddVideo(goodsInfoAndAttrValueDto.getVideoUrl(), goodsInfoAndAttrValueDto.getVideoName(),
                        goodsInfo.getId(), goodsInfo.getCreateId());
            }
            accountAttrValue.setId(aavId);
            //添加到任务池
            excuTaskPoolEdit(goodsInfoAndAttrValueDto);
            accountAttrValueMapper.updateAccountAttrValue(accountAttrValue);
        } else {
            throw new GlobalException("找不到此商品属性信息");
        }
        return goodsInfo.getId();
    }

    private void findAndSetShiMing(GoodsInfo goodsInfo, GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        GameAttrTemplate gameAttrTemplate = new GameAttrTemplate();
        gameAttrTemplate.setGameId(goodsInfo.getGameId());
        gameAttrTemplate.setFieldKey("sm");
        List<GameAttrTemplate> gameAttrTemplates =
                gameAttrTemplateMapper.selectGameAttrTemplateList(gameAttrTemplate);
        if (CollectionUtils.isNotEmpty(gameAttrTemplates)) {
            gameAttrTemplate = gameAttrTemplates.get(0);
            String fieldName = gameAttrTemplate.getAavName();
            try {
                Field field = GoodsInfoAndAttrValueDto.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object o = field.get(goodsInfoAndAttrValueDto);
                if (Objects.nonNull(o)) {
                    Long gameAttrId = Long.valueOf(String.valueOf(o));
                    GameAttribute gameAttribute = gameAttributeMapper.selectGameAttributeById(gameAttrId);
                    if (Objects.nonNull(gameAttribute)) {
                        goodsInfo.setShiming(gameAttribute.getFieldName());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Long insertBDApiGoodsInfoAndAttr(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        GoodsInfo goodsInfo = new GoodsInfo();
        AccountAttrValue accountAttrValue = new AccountAttrValue();
//        goodsInfoAndAttrValueDto.setSaleState(1);  //新增默认为 未出售状态
        BeanUtils.copyBeanProp(goodsInfo, goodsInfoAndAttrValueDto);
        BeanUtils.copyBeanProp(accountAttrValue, goodsInfoAndAttrValueDto);
        String allFeild = getAllField(goodsInfo, accountAttrValue);
        goodsInfo.setAllField(allFeild);
        int count = insertGoodsInfo(goodsInfo);
//        if (!CollectionUtils.isEmpty(goodsInfoAndAttrValueDto.getUrlArr())) {
//            apiBathAddImg(goodsInfoAndAttrValueDto.getUrlArr(), goodsInfo.getId(), goodsInfo.getCreateId());
//        }
        if (!StringUtils.isNull(goodsInfoAndAttrValueDto.getVideoUrl())) {
            apiAddVideo(goodsInfoAndAttrValueDto.getVideoUrl(), goodsInfoAndAttrValueDto.getVideoName(),
                    goodsInfo.getId(), goodsInfo.getCreateId());
        }
        accountAttrValue.setAccountInfoId(goodsInfo.getId());
        accountAttrValueMapper.insertAccountAttrValue(accountAttrValue);
        return goodsInfo.getId();
    }

    public void apiBathAddImg2(String imgUrls, Long goodsInfoId, Long createId) {
        String[] imgs = imgUrls.split(",");
        goodsInfoImgMapper.deleteByGoodsId(goodsInfoId, Constants.STR_1);
        List<GoodsInfoImg> goodsInfoImgList = new ArrayList<>();
        for (String url : imgs) {
            GoodsInfoImg goodsInfoImg = new GoodsInfoImg();
            goodsInfoImg.setGoodsId(goodsInfoId);
            goodsInfoImg.setImgUrl(url);
            goodsInfoImg.setCreateId(createId);
            goodsInfoImg.setFileName("default");
            goodsInfoImg.setType(Constants.STR_1);
            goodsInfoImgList.add(goodsInfoImg);
        }
        goodsInfoImgMapper.insertBacthGoodsInfoImg(goodsInfoImgList);
    }

    public void apiAddVideo(String videoUrl, String videoName, Long goodsId, Long createId) {
        goodsInfoImgMapper.deleteByGoodsId(goodsId, Constants.STR_2);
        if (!StringUtils.isEmpty(videoUrl)) {
            GoodsInfoImg goodsInfoImg = new GoodsInfoImg();
            goodsInfoImg.setGoodsId(goodsId);
            goodsInfoImg.setImgUrl(videoUrl);
            goodsInfoImg.setCreateId(createId);
            goodsInfoImg.setFileName(videoName);
            goodsInfoImg.setType(Constants.STR_2);
            goodsInfoImgMapper.insertGoodsInfoImg(goodsInfoImg);
        }
    }

    String getAllField(GoodsInfo goodsInfo, AccountAttrValue accountAttrValue) {
        Field[] fields = accountAttrValue.getClass().getDeclaredFields();
        Map map1 = new HashMap();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getName().startsWith("f")) {
                    map1.put(field.getName(), field.get(accountAttrValue));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        List<GameAttrTemplate> gameAttrTemplates =
                gameAttrTemplateService.selectGameAttrTemplateByGameId(goodsInfo.getGameId());
        StringBuffer sbuffer = new StringBuffer();
        if (CollectionUtils.isNotEmpty(gameAttrTemplates)) {
            for (GameAttrTemplate gameAttrTemplate : gameAttrTemplates) {
                Object aa = map1.get(gameAttrTemplate.getAavName());
                getGameAttrIds(aa, gameAttrTemplate, sbuffer);
            }
        }
        String gameAttrIds = sbuffer.toString();
        List<GameAttribute> gameAttributes =
                gameAttributeMapper.selectGameAttributeListByIds((Long[]) ConvertUtils.convert(gameAttrIds.split(","),
                        Long.class));
        StringBuffer sbufferV = new StringBuffer();
        for (GameAttribute gameAttribute : gameAttributes) {
            if (sbufferV != null && sbufferV.length() > 0) {
                sbufferV.append("," + gameAttribute.getFieldName());
            } else {
                sbufferV.append(gameAttribute.getFieldName());
            }
        }
        StringBuffer sbufferR = new StringBuffer();
        sbufferR.append(replaceSimpleName(goodsInfo.toString(), goodsInfo.getClass()));
        sbufferR.append(",");
        sbufferR.append(replaceSimpleName(accountAttrValue.toString(), accountAttrValue.getClass()));
        if (sbufferV != null && sbufferV.length() > 0) {
            sbufferR.append(",");
            sbufferR.append(sbufferV.toString());
        }
        return sbufferR.toString();
    }

    String getGameAttrIds(Object autoF, GameAttrTemplate gameAttrTemplate, StringBuffer sbuffer) {
        if (Objects.nonNull(autoF)) {
            String vlaues = autoF.toString();
            if (StringUtils.isNotEmpty(vlaues)) {
                if (StringUtils.equals(gameAttrTemplate.getFieldType(), "radio") || StringUtils.equals(gameAttrTemplate.getFieldType(), "checkbox") || StringUtils.equals(gameAttrTemplate.getFieldType(), "boxradio")) {
                    if (sbuffer != null && sbuffer.length() > 0) {
                        sbuffer.append("," + vlaues);
                    } else {
                        sbuffer.append(vlaues);
                    }
                }
            }
        }
        return sbuffer.toString();
    }

    String replaceSimpleName(String str, Class cla) {
        str = str.replace(cla.getSimpleName() + "(", "")
                .replace("null,", "")
                .replaceAll("\\s*", "")
                .replace(",null", "");
        str = str.substring(0, str.length() - 1);
        return str;
    }

    public static void main(String[] args) {
//        GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto = new GoodsInfoAndAttrValueDto();
//        goodsInfoAndAttrValueDto.setF1("1");
//        goodsInfoAndAttrValueDto.setF2("2");
//        goodsInfoAndAttrValueDto.setF3("3");
//        goodsInfoAndAttrValueDto.setF4("4");
//        goodsInfoAndAttrValueDto.setServerId(1l);
//        goodsInfoAndAttrValueDto.setServerName("哈哈");
//        goodsInfoAndAttrValueDto.setGameId(123l);
//        goodsInfoAndAttrValueDto.setGameName("游戏名字");
//
//        GoodsInfo goodsInfo = new GoodsInfo();
//        AccountAttrValue accountAttrValue = new AccountAttrValue();
//        BeanUtils.copyBeanProp(goodsInfo, goodsInfoAndAttrValueDto);
//        BeanUtils.copyBeanProp(accountAttrValue, goodsInfoAndAttrValueDto);
//        System.out.println(goodsInfo.toString());
//        System.out.println(goodsInfo.getClass().getSimpleName());
//        System.out.println(goodsInfo.getClass().getName());
    }

    //操作记录
    private void setOper(Long id, Map<String, Object> params) {
        String operation = MapUtils.getString(params, "operation");
        LoginUser loginUser = SecurityUtils.getLoginUser();
        OperationHistory operationHistory = new OperationHistory();
        operationHistory.setAccountId(id);
        operationHistory.setOperator(loginUser.getUsername());
        operationHistory.setOperatTime(new Date());
        operationHistory.setStatus(operation);
        operationHistory.setReason(String.valueOf(id));
        operationHistoryService.insertOperationHistory(operationHistory);
    }

    //推送财务
    public int pushFinance(GoodsInfo goodsInfo) {
        Finance finance = new Finance();
        finance.setCreateId(SecurityUtils.getUserId());
        finance.setCreateTime(DateUtils.getNowDate());
        finance.setIotype("1");
        long l = 13;
        finance.setBillType(l);
        finance.setPrice(goodsInfo.getCostPrice());
        finance.setField1(goodsInfo.getAccountNumber());
        finance.setRemark(goodsInfo.getThirdId());
        //平台编号
        finance.setField4(String.valueOf(goodsInfo.getId()));
        SysUser sysUser = sysUserMapper.selectUserById(SecurityUtils.getUserId());
        finance.setRegisteruser(sysUser.getNickName());
        return financeMapper.insertFinance(finance);
    }

}
