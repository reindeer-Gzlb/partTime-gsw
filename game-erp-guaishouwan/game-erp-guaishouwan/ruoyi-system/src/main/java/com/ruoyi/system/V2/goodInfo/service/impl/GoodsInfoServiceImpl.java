package com.ruoyi.system.V2.goodInfo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.enums.Constant;
import com.ruoyi.common.enums.SalerStatus;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.sql.SqlUtil;
import com.ruoyi.system.V2.financial.mapper.FinanceMapper;
import com.ruoyi.system.V2.gameAttr.mapper.AccountAttrValueMapper;
import com.ruoyi.system.V2.gameAttr.mapper.GameAttrTemplateMapper;
import com.ruoyi.system.V2.gameAttr.mapper.GameAttributeMapper;
import com.ruoyi.system.V2.gameAttr.service.IGameAttrTemplateService;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoImgMapper;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoMapper;
import com.ruoyi.system.V2.goodInfo.service.IGoodsInfoService;
import com.ruoyi.system.V2.operation.service.IOperationHistoryService;
import com.ruoyi.system.V2.phone.mapper.TelManageMapper;
import com.ruoyi.system.V2.submitAccount.mapper.OrderRecMapper;
import com.ruoyi.system.V2.submitAccount.service.IOrderRecService;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.*;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueDto;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueReq;
import com.ruoyi.system.model.service.AsyncService;
import com.ruoyi.system.model.vo.DeleteAllVo;
import com.ruoyi.system.outto.core.LCConstant;
import com.ruoyi.system.service.ISysUserService;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * goodsService业务层处理
 *
 * @author ruoyi
 * @date 2023-04-06
 */
@Service
public class GoodsInfoServiceImpl implements IGoodsInfoService {
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
    private AsyncService asyncService;

    @Autowired
    private ISysUserService userService;

    @Resource
    private GameAttrTemplateMapper gameAttrTemplateMapper;

    @Autowired
    private OrderRecMapper orderRecMapper;
    @Autowired
    @Lazy
    private IOrderRecService orderRecService;


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
     * 普通加价
     *
     * @param goodsInfos
     * @param sysUserList
     */
    @Override
    public void bacthAddPrice(List<GoodsInfo> goodsInfos, List<SysUser> sysUserList) {
        for (GoodsInfo goodsInfo : goodsInfos) {
            BigDecimal bigDecimal = addPrice(goodsInfo.getPrice(), goodsInfo.getCreateId(), sysUserList);
            goodsInfo.setNewPrice(bigDecimal);
            if (Objects.isNull(goodsInfo.getNewPrice())) {
                goodsInfo.setNewPrice(goodsInfo.getPrice());
            }
        }
    }


    public void bacthAccRetaiById(List<GoodsInfo> list, List<SysUser> supplyCustomer, Long id) {

        for (GoodsInfo goodsInfo : list) {
            if (Objects.equals(id, goodsInfo.getCreateId())) {
                goodsInfo.setNewPrice(goodsInfo.getPrice());
            } else {
                BigDecimal bigDecimal = addPrice(goodsInfo.getPrice(), goodsInfo.getCreateId(), supplyCustomer);
                goodsInfo.setNewPrice(bigDecimal);
            }
        }
    }

    public BigDecimal addPrice(BigDecimal price, Long createId, List<SysUser> supplyCustomer) {

        /**
         * 供货商价 * 折扣  == 给供货商的钱
         * 供货商价 * 0.05 == 利润
         * （利润 + 供货价的钱） / 0.9
         * 供货商价 * 0.1 = 利润
         * 供货价 *
         *
         */
        for (SysUser sysUser : supplyCustomer) {
            if (Objects.nonNull(sysUser) && Objects.equals(sysUser.getUserId(), createId)) {

                //给供货商的钱
                Integer discountt = sysUser.getDiscountt();
                BigDecimal zhekou = new BigDecimal(discountt).divide(new BigDecimal(100));
                BigDecimal multiply = price.multiply(zhekou);

                //利润
                BigDecimal bigDecimal1 = new BigDecimal(sysUser.getRise()).divide(new BigDecimal(100));
                BigDecimal lirun = price.multiply(bigDecimal1);

                //利润 + 供货价
                BigDecimal add = lirun.add(multiply);

                //供货价  / 0.9 = 售价
                BigDecimal bigDecimal = add.divide(new BigDecimal(0.9), 0, BigDecimal.ROUND_UP);
                return bigDecimal;
            }
        }
        return null;
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
        return goodsInfoMapper.updateGoodsInfo(goodsInfo);
    }

    /**
     * 批量删除goods
     *
     * @param ids 需要删除的goods主键
     * @return 结果
     */
    @Override
    public int deleteGoodsInfoByIds(Long[] ids, String pwd) {
        String securityPwd = "qwerasdfzxcv";
        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("securityPwd");
        for (SysDictData dictData : sysDictDataList) {
            if ("securityPwd".equals(dictData.getDictLabel())) {
                securityPwd = dictData.getDictValue();
            }
        }
        if (StringUtils.isNotEmpty(securityPwd)) {
            if (StringUtils.equals(pwd, securityPwd)) {

            } else {
                throw new GlobalException("密码错误");
            }
        }
        Arrays.asList(ids).stream().forEach(id -> {
            GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(id);
            if (goodsInfo.getSaleState() != 4 && goodsInfo.getSaleState() != 5 && goodsInfo.getSaleState() != 6) {
                throw new GlobalException("商品id为" + goodsInfo.getId() + "的状态不允许删除");
            }
            // 只有已下架 问题号 找回号状态的才可以删除,能走到删除,说明必须先下架,下架已经加入过线程池了,这个就没必要了。
//            asyncService.excuTaskPool(id, null, Constant.del, goodsInfo.getCreateId(), 1l);
        });

        return goodsInfoMapper.deleteGoodsInfoByIds(ids);
    }

    /**
     * 删除goods信息
     *
     * @param id goods主键
     * @return 结果
     */
    @Override
    public int deleteGoodsInfoById(Long id) {
        return goodsInfoMapper.deleteGoodsInfoById(id);
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

    @Override
    @Transactional
    public GoodsInfo insertGoodsInfoAndAttr(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        Long userId = SecurityUtils.getUserId();
        SysUser sysUser = sysUserMapper.selectUserById(userId);
        List<SysRole> roles = sysUser.getRoles();
        int accountType = 2;
        int saleState = 1;
        if (CollectionUtils.isNotEmpty(roles)) {
            List<String> collect = roles.stream().map(SysRole::getRoleKey).collect(Collectors.toList());
            if (collect.contains(Constant.selfSale)) {
                accountType = 1;
            }
        }
        if (Objects.isNull(goodsInfoAndAttrValueDto.getSaleState())) {
            goodsInfoAndAttrValueDto.setSaleState(saleState);
        }
        goodsInfoAndAttrValueDto.setAccountType(accountType);

        GoodsInfo goodsInfo = new GoodsInfo();
        AccountAttrValue accountAttrValue = new AccountAttrValue();
        BeanUtils.copyBeanProp(goodsInfo, goodsInfoAndAttrValueDto);
        BeanUtils.copyBeanProp(accountAttrValue, goodsInfoAndAttrValueDto);
        String allFeild = getAllField(goodsInfo, accountAttrValue);
        goodsInfo.setAllField(allFeild);
        goodsInfo.setCreateId(SecurityUtils.getUserId());
        findAndSetShiMing(goodsInfo, goodsInfoAndAttrValueDto);

        int count = insertGoodsInfo(goodsInfo);
        if (!CollectionUtils.isEmpty(goodsInfoAndAttrValueDto.getUrlArr())) {
            bathAddImg(goodsInfoAndAttrValueDto.getUrlArr(), goodsInfo.getId());
        }
        if (!StringUtils.isNull(goodsInfoAndAttrValueDto.getVideoUrl())) {
            addVideo(goodsInfoAndAttrValueDto.getVideoUrl(), goodsInfoAndAttrValueDto.getVideoName(),
                    goodsInfo.getId());
        }
        accountAttrValue.setAccountInfoId(goodsInfo.getId());
        //保存自定义字段
        accountAttrValueMapper.insertAccountAttrValue(accountAttrValue);
        //设置手机号
        setPhone(goodsInfoAndAttrValueDto);
        //异步推送接口
        excuTaskPoolAdd(goodsInfoAndAttrValueDto);
        goodsInfoAndAttrValueDto.setId(goodsInfo.getId());
//        pushFinance(goodsInfoAndAttrValueDto);
        return goodsInfo;
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
                if (Objects.nonNull(o) && StringUtils.isNotEmpty(String.valueOf(o))) {
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

    private void excuTaskPoolAdd(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {

        String salepartnerids = goodsInfoAndAttrValueDto.getSalepartnerids();
        if (StringUtils.isNotEmpty(salepartnerids)) {
            String[] split = salepartnerids.split(",");
            for (String s : split) {
                asyncService.excuAddTask(goodsInfoAndAttrValueDto, Long.parseLong(s));
            }
        }

    }

    private void excuTaskPoolEdit(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {

//        String salepartnerids = goodsInfoAndAttrValueDto.getSalepartnerids();
        asyncService.excuTaskPool(goodsInfoAndAttrValueDto.getId(), null, Constant.edit,
                goodsInfoAndAttrValueDto.getCreateId(), 1l);
//        if (StringUtils.isNotEmpty(salepartnerids)) {
//            String[] split = salepartnerids.split(",");
//            for (String s : split) {
//
//            }
//        }

    }


    @Override
    @Transactional
    public int updateGoodsInfoAndAttr(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        GoodsInfo goodsInfo = new GoodsInfo();
        AccountAttrValue accountAttrValue =
                accountAttrValueMapper.selectAccountAttrValueByAccountInfoId(goodsInfoAndAttrValueDto.getId());
        if (accountAttrValue != null) {
            Long aavId = accountAttrValue.getId();
            BeanUtils.copyBeanProp(goodsInfo, goodsInfoAndAttrValueDto);
            BeanUtils.copyBeanProp(accountAttrValue, goodsInfoAndAttrValueDto);
            String allFeild = getAllField(goodsInfo, accountAttrValue);
            goodsInfo.setAllField(allFeild);
            goodsInfo.setUpdateId(SecurityUtils.getUserId());
            findAndSetShiMing(goodsInfo, goodsInfoAndAttrValueDto);
            updateGoodsInfo(goodsInfo);
            if (!CollectionUtils.isEmpty(goodsInfoAndAttrValueDto.getUrlArr())) {
                bathAddImg(goodsInfoAndAttrValueDto.getUrlArr(), goodsInfo.getId());
            } else {
                goodsInfoImgMapper.deleteByGoodsId(goodsInfo.getId(), Constants.STR_1);
            }
            if (!StringUtils.isNull(goodsInfoAndAttrValueDto.getVideoUrl())) {
                addVideo(goodsInfoAndAttrValueDto.getVideoUrl(), goodsInfoAndAttrValueDto.getVideoName(),
                        goodsInfoAndAttrValueDto.getId());
            }
            accountAttrValue.setId(aavId);
            //添加到任务池
            excuTaskPoolEdit(goodsInfoAndAttrValueDto);
            accountAttrValueMapper.updateAccountAttrValue(accountAttrValue);
        } else {
            throw new GlobalException("找不到此商品属性信息");
        }
        setPhone(goodsInfoAndAttrValueDto);
        recOperHistory(goodsInfoAndAttrValueDto);
        return 1;
    }

    private void setPhone(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        //校验是否有telid
        //查询手机管理
        Long telid = goodsInfoAndAttrValueDto.getTelid();
        if (Objects.isNull(telid)) {
            TelManage telManage = new TelManage();
            telManage.setTel(goodsInfoAndAttrValueDto.getSecurityPhone());
            List<TelManage> telManages = telManageMapper.selectTelManageList(telManage);
            if (CollectionUtils.isNotEmpty(telManages)) {
                goodsInfoAndAttrValueDto.setTelid(telManages.get(0).getId());
            }
        }
    }

    @Override
    @Transactional
    public int isOnSpecialOffer(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        if (Objects.isNull(goodsInfoAndAttrValueDto.getIsOnSpecialOffer())) {
            throw new GlobalException("特价状态不可为空");
        }
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setId(goodsInfoAndAttrValueDto.getId());
        goodsInfo.setIsOnSpecialOffer(goodsInfoAndAttrValueDto.getIsOnSpecialOffer());


        Map<String, Object> params = new HashMap<>();
        params.put("operation", "特价状态");

        if (goodsInfoAndAttrValueDto.getIsOnSpecialOffer() == 1) {
            params.put("reason", "设为特价");
        } else {
            params.put("reason", "取消特价");
        }
        setOper(goodsInfoAndAttrValueDto.getId(), params);


        return goodsInfoMapper.updateGoodsInfo(goodsInfo);
    }



    @Override
    public List<GoodsInfoAndAttrValueDto> selectGoodsInfoAndAttrList(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        Field[] fields = goodsInfoAndAttrValueDto.getClass().getDeclaredFields();
        Map map1 = new HashMap();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getName().startsWith("f")) {
                    map1.put(field.getName(), field.get(goodsInfoAndAttrValueDto));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        GameAttrTemplate gameAttrTemplateCX = new GameAttrTemplate();
        gameAttrTemplateCX.setGameId(goodsInfoAndAttrValueDto.getGameId());
        List<GameAttrTemplate> gameAttrTemplates =
                gameAttrTemplateService.selectGameAttrTemplateList(gameAttrTemplateCX);
        StringBuffer sbuffer = new StringBuffer();
        if (CollectionUtils.isNotEmpty(gameAttrTemplates)) {
            for (GameAttrTemplate gameAttrTemplate : gameAttrTemplates) {
                Object aa = map1.get(gameAttrTemplate.getAavName());
                pinjie(aa, gameAttrTemplate, sbuffer);
            }
            goodsInfoAndAttrValueDto.setRemark(sbuffer.toString());
            System.out.println("sbuffer=" + goodsInfoAndAttrValueDto.getRemark());
        }
        List<GoodsInfoAndAttrValueDto> goodsInfoAndAttrValueDtos =
                goodsInfoMapper.selectGoodsInfoAndAttrList(goodsInfoAndAttrValueDto);
        System.out.println("goodsInfoAndAttrValueDtos的size：" + goodsInfoAndAttrValueDtos.size());
        return goodsInfoAndAttrValueDtos;
    }

    @Override
    @Transactional
    public int upGoodsById(Long id) {
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(id);
        if (Objects.isNull(goodsInfo)) {
            throw new GlobalException("未找到此商品");
        }
        goodsInfo.setSaleState(1);
        goodsInfo.setGsState("1");
        Map<String, Object> params = new HashMap<>();
        params.put("operation", "上架");
        setOper(id, params);
        return goodsInfoMapper.updateGoodsInfo(goodsInfo);
    }

    @Override
    @Transactional
    public int downGoodsById(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(goodsInfoAndAttrValueDto.getId());
        if (Objects.isNull(goodsInfo)) {
            throw new GlobalException("未找到此商品");
        }
        if (StringUtils.isEmpty(goodsInfoAndAttrValueDto.getDownDes())) {
            throw new GlobalException("请填写下架备注");
        }
        goodsInfo.setDownDes(goodsInfoAndAttrValueDto.getDownDes());
        goodsInfo.setSaleState(4);
        int i = goodsInfoMapper.updateGoodsInfo(goodsInfo);
        Map<String, Object> params = new HashMap<>();
        params.put("operation", "下架");
        setOper(goodsInfoAndAttrValueDto.getId(), params);
        asyncService.excuTaskPool(goodsInfoAndAttrValueDto.getId(), null, Constant.del, goodsInfo.getCreateId(), 1l);
        return i;
    }

    @Override
    @Transactional
    public int updateIsPush(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(goodsInfoAndAttrValueDto.getId());
        if (Objects.isNull(goodsInfo)) {
            throw new GlobalException("未找到");
        }
        goodsInfo.setIsPush(goodsInfoAndAttrValueDto.getIsPush());
        goodsInfoMapper.updateGoodsInfo(goodsInfo);

        if (Objects.equals(goodsInfoAndAttrValueDto.getIsPush(), 1L)) {
            upGoodsById(goodsInfoAndAttrValueDto.getId());
        } else if (Objects.equals(goodsInfoAndAttrValueDto.getIsPush(), 2L)) {

            Map<String, Object> params = new HashMap<>();
            params.put("operation", "下架");
            setOper(goodsInfoAndAttrValueDto.getId(), params);
            asyncService.excuTaskPool(goodsInfoAndAttrValueDto.getId(), null, Constant.del, goodsInfo.getCreateId(), 1l);
        } else {
            throw new GlobalException("isPush is null : " + goodsInfoAndAttrValueDto.getIsPush());
        }
        return 0;
    }

    @Override
    @Transactional
    public int problemGoods(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(goodsInfoAndAttrValueDto.getId());
        if (Objects.isNull(goodsInfo)) {
            throw new GlobalException("未找到此商品");
        }
        if (StringUtils.isEmpty(goodsInfoAndAttrValueDto.getProblemDes())) {
            throw new GlobalException("请填写问题备注");
        }
        goodsInfo.setProblemDes(goodsInfoAndAttrValueDto.getProblemDes());
        goodsInfo.setSaleState(5);

        Map<String, Object> params = new HashMap<>();
        params.put("operation", "转问题");
        setOper(goodsInfoAndAttrValueDto.getId(), params);
        asyncService.excuTaskPool(goodsInfoAndAttrValueDto.getId(), null, Constant.del, goodsInfo.getCreateId(), 1l);
        goodsInfoMapper.updateGoodsInfo(goodsInfo);

        if (StringUtils.isNotNull(goodsInfo.getOrderRecId())) {
            OrderRec orderRec = orderRecMapper.selectOrderRecById(goodsInfo.getOrderRecId());
            if (StringUtils.isNotNull(orderRec)) {
                orderRec.setRemake(orderRec.getRemake() + "---转问题原因：" + goodsInfoAndAttrValueDto.getProblemDes());
                orderRec.setChangeGoodsSaleState(5);
                orderRecService.myOrderCancel(orderRec);
            }
        }
        return 1;
    }

    @Override
    @Transactional
    public int retrieveGoods(GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto) {
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(goodsInfoAndAttrValueDto.getId());
        if (Objects.isNull(goodsInfo)) {
            throw new GlobalException("未找到此商品");
        }
        if (StringUtils.isEmpty(goodsInfoAndAttrValueDto.getRetrieveDes())) {
            throw new GlobalException("请填写找回备注");
        }
        goodsInfo.setRetrieveDes(goodsInfoAndAttrValueDto.getRetrieveDes());
        goodsInfo.setSaleState(6);

        Map<String, Object> params = new HashMap<>();
        params.put("operation", "转找回");
        setOper(goodsInfoAndAttrValueDto.getId(), params);
        return goodsInfoMapper.updateGoodsInfo(goodsInfo);
    }

    @Override
    public List<GoodsInfo> selectByResList(GoodsInfoAndAttrValueReq goodsInfoAndAttrValueReq, PageDomain pageDomain) {
        Field[] fields = goodsInfoAndAttrValueReq.getClass().getDeclaredFields();
        Map map1 = new HashMap();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getName().startsWith("f")) {
                    map1.put(field.getName(), field.get(goodsInfoAndAttrValueReq));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
//        GameAttrTemplate gameAttrTemplateCX = new GameAttrTemplate();
//        gameAttrTemplateCX.setGameId();
//        List<GameAttrTemplate> gameAttrTemplates =
//                gameAttrTemplateService.selectGameAttrTemplateList(gameAttrTemplateCX);
        if (StringUtils.isNotNull(goodsInfoAndAttrValueReq.getGameId())) {
            List<GameAttrTemplate> gameAttrTemplates =
                    gameAttrTemplateService.selectGameAttrTemplateByGameId(goodsInfoAndAttrValueReq.getGameId());
            StringBuffer sbuffer = new StringBuffer();
            if (CollectionUtils.isNotEmpty(gameAttrTemplates)) {
                for (GameAttrTemplate gameAttrTemplate : gameAttrTemplates) {
                    Object aa = map1.get(gameAttrTemplate.getAavName());
                    pinjie(aa, gameAttrTemplate, sbuffer);
                }
                List list = goodsInfoAndAttrValueReq.getBoxRadioValues();
                if (CollectionUtils.isNotEmpty(list)) {
                    for (Object o : list) {
                        String value = o.toString();
                        if (StringUtils.contains(value, "===")) {
                            String[] vlas = value.split("===");
                            for (int i = 0; i < vlas.length; i++) {
                                if (i == 0) {
                                    sbuffer.append("and ( all_field like \'%" + vlas[i] + "%\'");
                                } else {
                                    if (i == vlas.length - 1) {
                                        sbuffer.append("or all_field like \'%" + vlas[i] + "%\' )");
                                    } else {
                                        sbuffer.append("or all_field like \'%" + vlas[i] + "%\'");
                                    }
                                }
                            }
                        } else {
                            sbuffer.append(" and all_field like \'%" + value + "%\'");
                        }
                    }
                }
                goodsInfoAndAttrValueReq.setRemark(sbuffer.toString());
            }
        }
        if (StringUtils.isNotEmpty(goodsInfoAndAttrValueReq.getSort())) {
            goodsInfoAndAttrValueReq.setSort(goodsInfoAndAttrValueReq.getSort().replace("ending", ""));
        }
        if (StringUtils.isNotEmpty(goodsInfoAndAttrValueReq.getSortColumn())) {
            goodsInfoAndAttrValueReq.setSortColumn(humpToLine(goodsInfoAndAttrValueReq.getSortColumn()));
        }
        String[] keyWordArr = {};
        if (StringUtils.isNotEmpty(goodsInfoAndAttrValueReq.getKeyWord())) {
            keyWordArr = goodsInfoAndAttrValueReq.getKeyWord().split(" ");
            goodsInfoAndAttrValueReq.setKeyWordArr(Arrays.asList(keyWordArr));
        }
        if (Objects.equals(Constants.ONE, goodsInfoAndAttrValueReq.getDataType())) {
            goodsInfoAndAttrValueReq.setCreateId(SecurityUtils.getUserId());
        }
        //分页
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            Boolean reasonable = pageDomain.getReasonable();
            PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
        }

        List<GoodsInfo> goodsInfos = goodsInfoMapper.selectByResList(goodsInfoAndAttrValueReq);
        List<SysUser> supplyCustomer = userService.getUserByRoleCode("supplyCustomer");
        bacthAddPrice(goodsInfos, supplyCustomer);
        return goodsInfos;
    }

    /**
     * 批量审核通过
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public int auditGoods(Long[] ids) {

        Map<String, Object> params = new HashMap<>();
        params.put("operation", "转问题");
        for (Long id : ids) {
            setOper(id, params);
        }

        return goodsInfoMapper.auditGoods(ids);
    }

    /**
     * 批量审核不通过
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public int auditGoodsFailure(Long[] ids, String auditDes) {

        Map<String, Object> params = new HashMap<>();
        params.put("operation", "审核失败");
        for (Long id : ids) {
            setOper(id, params);
        }

        return goodsInfoMapper.auditGoodsFailure(ids, auditDes);
    }

    @Override
    public int topOnOrOff(GoodsInfo goodsInfo) {
        GoodsInfo goodsInfo1 = goodsInfoMapper.selectGoodsInfoById(goodsInfo.getId());
        if (Objects.isNull(goodsInfo1)) {
            throw new GlobalException("未找到此商品");
        }
        goodsInfo1.setIsTop(goodsInfo.getIsTop());
        int i = goodsInfoMapper.updateGoodsInfo(goodsInfo1);
        Map<String, Object> params = new HashMap<>();
        if (goodsInfo1.getIsTop() == 1) {
            params.put("operation", "设为顶级账号");
        } else {
            params.put("operation", "取消顶级账号");
        }
        setOper(goodsInfo1.getId(), params);

        return i;
    }


    @Override
    @Transactional
    public int submitAccount(Long[] ids) {

        Map<String, Object> params = new HashMap<>();
        params.put("operation", "提号");
        for (Long id : ids) {
            setOper(id, params);
        }
        return goodsInfoMapper.submitAccount(ids);
    }

    @Override
    public List<Map<String, Object>> countForSupply(GoodsInfo goodsInfo) {
        List<Map<String, Object>> list = goodsInfoMapper.countForSupply(goodsInfo.getCreateId());
        return list;
    }

    @Override
    public List<Map<String, Object>> userGameCount() {
        return goodsInfoMapper.userGameCount();
    }

    @Override
    public Map<String, Object> pcIndexData() {
        Map<String, Object> resultMap = new HashMap<>();
        GoodsInfo goodsInfo = new GoodsInfo();
        int count = goodsInfoMapper.getGoodsInfoCount(goodsInfo);
        goodsInfo.setSaleState(3);
        int count1 = goodsInfoMapper.getGoodsInfoCount(goodsInfo);
        int goodsYesterdaySold = goodsInfoMapper.getYesterdaySold();

        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("before_desk");
        if (CollectionUtils.isNotEmpty(sysDictDataList)) {
            for (SysDictData dictData : sysDictDataList) {
                if (StringUtils.equals("goodsNum", dictData.getDictLabel())) {
                    count = count + Integer.valueOf(dictData.getDictValue());
                }
                if (StringUtils.equals("goodsSoldNum", dictData.getDictLabel())) {
                    count1 = count1 + Integer.valueOf(dictData.getDictValue());
                }
                if (StringUtils.equals("goodsYesterdaySoldNum", dictData.getDictLabel())) {
                    goodsYesterdaySold = goodsYesterdaySold + Integer.valueOf(dictData.getDictValue());
                }
            }
        }

        resultMap.put("goodsNum", count);
        resultMap.put("goodsSoldNum", count1);
        resultMap.put("goodsYesterdaySoldNum", goodsYesterdaySold);
        return resultMap;
    }

    @Override
    public int down(Long[] ids) {

        int down = goodsInfoMapper.down(ids);
        Arrays.asList(ids).stream().forEach(id -> {
//            GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(id);
            asyncService.excuTaskPool(id, null, Constant.del, 1l, 1l);
        });

        return down;
    }

    @Override
    public int editStateGoods(Long id) {
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(id);
        if (Objects.isNull(goodsInfo)) {
            throw new GlobalException("未找到商品");
        }
        goodsInfo.setGsState("2");
        goodsInfoMapper.updateGoodsInfo(goodsInfo);
        return 1;
    }

    /**
     * 销售状态  0、待审核 1、未出售 2、出售中 3、已售 4、已下架 5、问题账号 6、找回账号 , 10 挂售,11 分期中 12 已封禁 13 已私下 ，14 赔付中  15 永久出售
     * 转分期
     *
     * @param id
     * @return
     */
    @Override
    public int convertFQ(Long id) {
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(id);
        if (Objects.isNull(goodsInfo)) {
            throw new GlobalException("未找到商品");
        }


        Map<String, Object> params = new HashMap<>();
        params.put("operation", "转分期");
        setOper(goodsInfo.getId(), params);
        asyncService.excuTaskPool(goodsInfo.getId(), null, Constant.del, goodsInfo.getCreateId(), 1l);

        goodsInfo.setSaleState(11);
        return goodsInfoMapper.updateGoodsInfo(goodsInfo);
    }

    /**
     * 已封禁
     *
     * @param id
     * @return
     */
    @Override
    public int convertYFJ(Long id) {
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(id);
        if (Objects.isNull(goodsInfo)) {
            throw new GlobalException("未找到商品");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("operation", "已封禁");
        setOper(goodsInfo.getId(), params);
        asyncService.excuTaskPool(goodsInfo.getId(), null, Constant.del, goodsInfo.getCreateId(), 1l);


        goodsInfo.setSaleState(12);
        return goodsInfoMapper.updateGoodsInfo(goodsInfo);
    }

    /**
     * 已私下
     *
     * @param id
     * @return
     */
    @Override
    public int convertYSX(Long id) {
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(id);
        if (Objects.isNull(goodsInfo)) {
            throw new GlobalException("未找到商品");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("operation", "已私下");
        setOper(goodsInfo.getId(), params);
        asyncService.excuTaskPool(goodsInfo.getId(), null, Constant.del, goodsInfo.getCreateId(), 1l);


        goodsInfo.setSaleState(13);
        return goodsInfoMapper.updateGoodsInfo(goodsInfo);
    }

    /**
     * 赔付中
     *
     * @param id
     * @return
     */
    @Override
    public int convertPFZ(Long id) {
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(id);
        if (Objects.isNull(goodsInfo)) {
            throw new GlobalException("未找到商品");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("operation", "赔付中");
        setOper(goodsInfo.getId(), params);
        asyncService.excuTaskPool(goodsInfo.getId(), null, Constant.del, goodsInfo.getCreateId(), 1l);


        goodsInfo.setSaleState(14);
        return goodsInfoMapper.updateGoodsInfo(goodsInfo);
    }

    /**
     * 永久出售
     *
     * @param id
     * @return
     */
    @Override
    public int convertYJCS(Long id) {
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(id);
        if (Objects.isNull(goodsInfo)) {
            throw new GlobalException("未找到商品");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("operation", "永久出售");
        setOper(goodsInfo.getId(), params);
        asyncService.excuTaskPool(goodsInfo.getId(), null, Constant.del, goodsInfo.getCreateId(), 1l);


        goodsInfo.setSaleState(15);
        return goodsInfoMapper.updateGoodsInfo(goodsInfo);
    }

    /**
     * 在手封禁
     *
     * @param id
     * @return
     */
    @Override
    public int convertZSFJ(Long id) {
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(id);
        if (Objects.isNull(goodsInfo)) {
            throw new GlobalException("未找到商品");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("operation", "在手封禁");
        setOper(goodsInfo.getId(), params);
        asyncService.excuTaskPool(goodsInfo.getId(), null, Constant.del, goodsInfo.getCreateId(), 1l);

        goodsInfo.setSaleState(16);
        return goodsInfoMapper.updateGoodsInfo(goodsInfo);
    }


    /**
     * 红锁赔付
     *
     * @param id
     * @return
     */
    @Override
    public int convertHSPF(Long id) {
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(id);
        if (Objects.isNull(goodsInfo)) {
            throw new GlobalException("未找到商品");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("operation", "红锁赔付");
        setOper(goodsInfo.getId(), params);
        asyncService.excuTaskPool(goodsInfo.getId(), null, Constant.del, goodsInfo.getCreateId(), 1l);


        goodsInfo.setSaleState(17);
        return goodsInfoMapper.updateGoodsInfo(goodsInfo);
    }

    public void downById(List<Long> ids, List<Long> configArr) {


        if (CollectionUtils.isNotEmpty(ids)) {
            Long[] array3 = ids.stream().toArray(Long[]::new);
            int down = goodsInfoMapper.down(array3);
            for (Long id : ids) {
                if (!org.springframework.util.CollectionUtils.isEmpty(configArr)) {
                    configArr.stream().forEach(a -> {
                        asyncService.excuTaskPool(id, a, Constant.del, 1l, 1l);
                    });
                } else {
                    asyncService.excuTaskPool(id, null, Constant.del, 1l, 1l);
                }
            }
        }
    }

    @Override
    @Transactional
    public int deleteAllAccount(DeleteAllVo deleteAllVo) {

        //供货人
        //配置
        List<String> supplyIdAddr = deleteAllVo.getSupplyIdAddr();
        if (CollectionUtils.isEmpty(supplyIdAddr)) {
            throw new GlobalException("请选择供货商");
        }
        List<Long> configArr = deleteAllVo.getConfigArr();

        if (!org.springframework.util.CollectionUtils.isEmpty(supplyIdAddr)) {
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setCreateIds(supplyIdAddr);
            goodsInfo.setSaleState(SalerStatus.unsold.getCode());
            List<GoodsInfo> goodsInfos = goodsInfoMapper.selectGoodsInfoList(goodsInfo);
            if (!org.springframework.util.CollectionUtils.isEmpty(goodsInfos)) {
                List<Long> collect = goodsInfos.stream().map(g -> g.getId()).collect(Collectors.toList());
                downById(collect, configArr);
//                for (GoodsInfo info : goodsInfos) {
////                    info.setSaleState(4);
////                    goodsInfoMapper.updateGoodsInfo(info);
//
//                    if (!org.springframework.util.CollectionUtils.isEmpty(configArr)) {
//                        configArr.stream().forEach(a -> {
//                            asyncService.excuTaskPool(info.getId(), a, Constant.del, info.getCreateId(), userId);
//                        });
//                    } else {
//                        asyncService.excuTaskPool(info.getId(), null, Constant.del, info.getCreateId(), userId);
//                    }
//                }
            }
        }
        return 1;
    }


    /**
     * 分销加价
     *
     * @param list
     * @param supplyCustomer
     */
    @Override
    public void bacthAccRetail(List<GoodsInfo> list, List<SysUser> supplyCustomer, Long userId) {

        for (GoodsInfo accountInfo : list) {
            if (Objects.equals(userId, accountInfo.getCreateId())) {
                accountInfo.setNewPrice(accountInfo.getPrice());
            } else {
                BigDecimal bigDecimal = addPrice(accountInfo.getPrice(), accountInfo.getCreateId(), supplyCustomer);
                accountInfo.setNewPrice(bigDecimal);
            }
            if (Objects.isNull(accountInfo.getCreateId())) {
                accountInfo.setNewPrice(accountInfo.getPrice());
            }
        }
    }

    /**
     * 分销加价
     *
     * @param list
     * @param supplyCustomer
     */
    @Override
    public void bacthAccRetailByUserId(List<GoodsInfo> list, List<SysUser> supplyCustomer, Long userId) {

        for (GoodsInfo accountInfo : list) {
            if (Objects.equals(userId, accountInfo.getCreateId())) {
                accountInfo.setNewPrice(accountInfo.getPrice());
            } else {
                BigDecimal bigDecimal = addPrice(accountInfo.getPrice(), accountInfo.getCreateId(), supplyCustomer);
                accountInfo.setNewPrice(bigDecimal);
            }
            if (Objects.isNull(accountInfo.getCreateId())) {
                accountInfo.setNewPrice(accountInfo.getPrice());
            }
        }
    }

    @Override
    public void sensitive(GoodsInfo goodsInfo) {

        List<String> sensitive = sensitive();
        for (String vocabulary : sensitive) {

            String gameName = goodsInfo.getTitle();
            if (Objects.nonNull(gameName)) {
                gameName = gameName.replace(vocabulary, "");
                goodsInfo.setTitle(gameName);
            }

            String des = goodsInfo.getDes();
            if (Objects.nonNull(des)) {
                des = des.replace(vocabulary, "");
                goodsInfo.setDes(des);
            }
        }
    }

    private List<String> sensitive() {
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType(LCConstant.sensitive);
        sysDictData.setStatus(Constants.STR_0);
        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataList(sysDictData);
        return sysDictDataList.stream().map(s -> s.getDictValue()).collect(Collectors.toList());
    }


    public void addVideo(String videoUrl, String videoName, Long goodsId) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        goodsInfoImgMapper.deleteByGoodsId(goodsId, Constants.STR_2);
        if (!StringUtils.isEmpty(videoUrl)) {
            GoodsInfoImg goodsInfoImg = new GoodsInfoImg();
            goodsInfoImg.setGoodsId(goodsId);
            goodsInfoImg.setImgUrl(videoUrl);
            goodsInfoImg.setCreateId(loginUser.getUserId());
            goodsInfoImg.setFileName(videoName);
            goodsInfoImg.setType(Constants.STR_2);
            goodsInfoImgMapper.insertGoodsInfoImg(goodsInfoImg);
        }
    }

    @Transactional
    public void bathAddImg(List<Map<String, Object>> imgs, Long goodsInfoId) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        goodsInfoImgMapper.deleteByGoodsId(goodsInfoId, Constants.STR_1);
        List<String> result = new ArrayList<>();
        List<GoodsInfoImg> goodsInfoImgList = new ArrayList<>();
        for (Map img : imgs) {
            String filePath = MapUtils.getString(img, "url");
            result.add(filePath);
            if (!StringUtils.isEmpty(filePath)) {
                GoodsInfoImg goodsInfoImg = new GoodsInfoImg();
                goodsInfoImg.setGoodsId(goodsInfoId);
                String fileName = MapUtils.getString(img, "name");
                goodsInfoImg.setImgUrl(filePath);
                goodsInfoImg.setCreateId(loginUser.getUserId());
                goodsInfoImg.setFileName(fileName);
                goodsInfoImg.setType(Constants.STR_1);
                goodsInfoImgList.add(goodsInfoImg);
            }
        }
        goodsInfoImgMapper.insertBacthGoodsInfoImg(goodsInfoImgList);
    }

    String pinjie(Object autoF, GameAttrTemplate gameAttrTemplate, StringBuffer sbuffer) {
        if (Objects.nonNull(autoF)) {
            String vlaues = autoF.toString();
            if (StringUtils.isNotEmpty(vlaues)) {
                if (StringUtils.equals(gameAttrTemplate.getFieldType(), "input")) {
                    sbuffer.append(" and " + gameAttrTemplate.getAavName() + " like \'%" + autoF + "%\'");
                } else if (StringUtils.equals(gameAttrTemplate.getFieldType(), "number") || StringUtils.equals(gameAttrTemplate.getFieldType(), "date")) {
                    if (vlaues.contains("===")) {
                        String[] vlas = vlaues.split("===");
                        if (vlas.length == 2) {
                            if (StringUtils.isNotEmpty(vlas[0])) {
                                sbuffer.append(" and " + gameAttrTemplate.getAavName() + " >= " + vlas[0]);
                            }
                            sbuffer.append(" and " + gameAttrTemplate.getAavName() + " <= " + vlas[1]);
                        } else if (vlas.length == 1) {
                            sbuffer.append(" and " + gameAttrTemplate.getAavName() + " >= " + vlas[0]);
                        }
                    } else {
                        sbuffer.append(" and " + gameAttrTemplate.getAavName() + " >= " + vlaues);
                    }
                } else if (StringUtils.equals(gameAttrTemplate.getFieldType(), "radio")) {
                    sbuffer.append(" and " + gameAttrTemplate.getAavName() + " = " + vlaues);
                } else if (
                        StringUtils.equals(gameAttrTemplate.getFieldType(), "checkbox") ||
                                StringUtils.equals(gameAttrTemplate.getFieldType(), "boxradio")
                ) {
                    if (vlaues.contains(",")) {
                        String[] vlas = vlaues.split(",");
                        for (String vla : vlas) {
                            sbuffer.append(" and FIND_IN_SET(\"" + vla + "\"," + gameAttrTemplate.getAavName() + ") >" +
                                    " 0 ");
                        }
                    } else {
                        sbuffer.append(" and FIND_IN_SET(\"" + vlaues + "\"," + gameAttrTemplate.getAavName() + ") >" +
                                " 0 ");
                    }
                }
            }
        }
        return sbuffer.toString();
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

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    private String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString().toUpperCase();
    }

    //操作记录
    private void setOper(Long id, Map<String, Object> params) {
        String operation = MapUtils.getString(params, "operation");
        String reason = MapUtils.getString(params, "reason");
        LoginUser loginUser = SecurityUtils.getLoginUser();
        OperationHistory operationHistory = new OperationHistory();
        operationHistory.setAccountId(id);
        operationHistory.setOperator(loginUser.getUsername());
        operationHistory.setOperatTime(new Date());
        operationHistory.setStatus(operation);
        operationHistory.setReason(reason);
        operationHistoryService.insertOperationHistory(operationHistory);
    }
    private void recOperHistory(GoodsInfoAndAttrValueDto goodsInfo) {
        List<String> filter = Arrays.asList(
                "updateTime",
                "createTime",
                "urlArr",
                ""
        );

        if (null != goodsInfo.getId()) {
            GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto =
                    goodsInfoMapper.selectGoodsInfoAndAttrValueById(goodsInfo.getId());

            LoginUser loginUser = SecurityUtils.getLoginUser();
            OperationHistory operationHistory = new OperationHistory();
            operationHistory.setAccountId(goodsInfo.getId());
            operationHistory.setOperator(loginUser.getUsername());
            operationHistory.setOperatTime(new Date());
            operationHistory.setStatus("编辑");
            StringBuilder sb = new StringBuilder();
            try {
                JSONObject reqJson = (JSONObject) JSON.toJSON(goodsInfo);
                JSONObject localJson = (JSONObject) JSON.toJSON(goodsInfoAndAttrValueDto);
                for (String key : reqJson.keySet()) {
                    if (filter.contains(key)){
                        continue;
                    }
                    String vL = localJson.getString(key);
                    String vR = reqJson.getString(key);
                    if (StringUtils.isNotEmpty(vR)) {
                        if (!StringUtils.equals(vL, vR)) {
                            sb.append(key).append(":").append(vL).append("=>").append(vR).append("\n");
                        }
                    }
                }
            } catch (Exception e) {
                sb.append("文本域处理异常");
            }
            operationHistory.setReason(sb.toString());
            operationHistoryService.insertOperationHistory(operationHistory);
        }
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
