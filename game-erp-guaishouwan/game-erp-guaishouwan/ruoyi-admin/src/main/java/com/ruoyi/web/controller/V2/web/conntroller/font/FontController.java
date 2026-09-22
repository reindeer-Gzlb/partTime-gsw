package com.ruoyi.web.controller.V2.web.conntroller.font;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ToPinYin;
import com.ruoyi.common.utils.sendMsg.Request;
import com.ruoyi.system.V2.font.service.ICheckAccountService;
import com.ruoyi.system.V2.font.service.IQqGroupService;
import com.ruoyi.system.V2.font.service.ISocialInfoService;
import com.ruoyi.system.V2.font.service.IZhuboService;
import com.ruoyi.system.V2.gameAttr.service.IGameAttrTemplateService;
import com.ruoyi.system.V2.gameAttr.service.IGameAttributeService;
import com.ruoyi.system.V2.gameInfo.mapper.GameInfoMapper;
import com.ruoyi.system.V2.gameInfo.service.IGameInfoService;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoMapper;
import com.ruoyi.system.V2.goodInfo.service.ICollectService;
import com.ruoyi.system.V2.goodInfo.service.IGoodsInfoService;
import com.ruoyi.system.V2.inter.service.IGoodsBrowseService;
import com.ruoyi.system.V2.inter.service.IGpzSearchService;
import com.ruoyi.system.V2.inter.service.IIndexResourcesService;
import com.ruoyi.system.V2.retail.mapper.RetailAddPriceMapper;
import com.ruoyi.system.V2.retail.service.IRetailAccountService;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.logo.domain.SysShopLogo;
import com.ruoyi.system.logo.service.ISysShopLogoService;
import com.ruoyi.system.model.domain.*;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueDto;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueReq;
import com.ruoyi.system.service.ISysNoticeService;
import com.ruoyi.system.service.ISysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/web")
public class FontController extends BaseController {

    @Autowired
    private IGoodsInfoService goodsInfoService;
    @Autowired
    private IGameInfoService gameInfoService;
    @Autowired
    private IRetailAccountService retailAccountService;
    @Autowired
    private IGameAttrTemplateService gameAttrTemplateService;
    @Resource
    private ISysUserService userService;
    @Autowired
    private IGameAttributeService gameAttributeService;
    @Resource
    private RetailAddPriceMapper retailAddPriceMapper;
    @Autowired
    private ICollectService collectService;
    @Autowired
    IGpzSearchService gpzSearchService;
    @Autowired
    IGoodsBrowseService goodsBrowseService;
    @Resource
    private GameInfoMapper gameInfoMapper;
    @Autowired
    IIndexResourcesService indexResourcesService;
    @Autowired
    private ISysNoticeService noticeService;
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
    @Autowired
    private ISocialInfoService socialInfoService;
    @Autowired
    private IZhuboService zhuboService;
    @Autowired
    private IQqGroupService qqGroupService;
    @Autowired
    private ICheckAccountService checkAccountService;
    @Autowired
    private ISysShopLogoService sysShopLogoService;

    /**
     * 查询logo列表
     */
    @GetMapping("/logoDetail")
    public AjaxResult list(HttpServletRequest request) {
        Long userId = getHostUserId(request);
        if (Objects.isNull(userId)) {
            throw new GlobalException("error 商户id为空");
        }
        SysShopLogo sysShopLogo = new SysShopLogo();
        sysShopLogo.setCreateId(userId);
        List<SysShopLogo> list = sysShopLogoService.selectSysShopLogoList(sysShopLogo);
        if (CollectionUtils.isEmpty(list)) {
            throw new GlobalException("未配置banner logo");
        }
        return AjaxResult.success(list.get(0));
    }

    //客服
    @ResponseBody
    @GetMapping("/account")
    public List<CheckAccount> account(HttpServletRequest request) {
        Long userId = getHostUserId(request);
        if (Objects.isNull(userId)) {
            throw new GlobalException("error 商户id为空");
        }
        CheckAccount checkAccount = new CheckAccount();
        checkAccount.setCreateId(userId);
        checkAccount.setState("0");
        checkAccount.setTypeResource(2);
        List<CheckAccount> checkAccounts = checkAccountService.selectCheckAccountList(checkAccount);
        return checkAccounts;
    }

    /**
     * 客服验证
     */
    @ResponseBody
    @GetMapping("/checkAccount")
    public AjaxResult checkAccount(CheckAccount checkAccount) {
        Long userId = checkAccount.getBusinessId();
        if (Objects.isNull(userId)) {
            throw new GlobalException("error 商户id为空");
        }
        checkAccount.setCreateId(userId);
        checkAccount.setState("0");
        checkAccount.setTypeResource(2);
        List<CheckAccount> checkAccounts = checkAccountService.selectCheckAccountList(checkAccount);
        if (!org.springframework.util.CollectionUtils.isEmpty(checkAccounts)) {
            return AjaxResult.success("账号正常");
        } else {
            return AjaxResult.error("账号异常");
        }
    }

    @GetMapping("/notice/{noticeId}")
    public AjaxResult getNotice(@PathVariable String noticeId) {
        try {
            Long.valueOf(noticeId);
        } catch (Exception e) {
            return AjaxResult.error("noticeId 类型参数必须是Long类型,请求入参 noticeId=" + noticeId);
        }
        return AjaxResult.success(noticeService.selectNoticeById(Long.valueOf(noticeId)));
    }

    /**
     * 获取资源列表
     *
     * @param indexResources
     * @return
     */
    @GetMapping("/indexResources/list")
    public TableDataInfo list(IndexResources indexResources, HttpServletRequest request) {
        Long businessId = getHostUserId(request);
        LambdaQueryWrapper<IndexResources> queryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(indexResources.getSystemType())) {
            queryWrapper.eq(IndexResources::getSystemType, indexResources.getSystemType());
        }
        if (Objects.nonNull(indexResources.getType())) {
            queryWrapper.eq(IndexResources::getType, indexResources.getType());
        }
        if (Objects.nonNull(businessId)) {
            queryWrapper.eq(IndexResources::getCreateId, businessId);
        }
        queryWrapper.orderBy(true, true, IndexResources::getSort);
        queryWrapper.orderBy(true, false, IndexResources::getId);
        return getDataTable(indexResourcesService.list(queryWrapper));
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @GetMapping("/qqGroupList")
    public AjaxResult qqGroupList(HttpServletRequest request, Long gameId) {
        Long userId = getHostUserId(request);
        if (Objects.isNull(userId)) {
            throw new GlobalException("error 商户id为空");
        }
        QqGroup qqGroup = new QqGroup();
        qqGroup.setBusinessId(userId);
        qqGroup.setTypeResource(2);
        if (Objects.nonNull(gameId)) {
            qqGroup.setGameId(gameId);
        }
        List<QqGroup> list = qqGroupService.selectQqGroupList(qqGroup);
        return AjaxResult.success(list);
    }

    /**
     *
     */
    @GetMapping("/leftDateGoodsDetail")
    public AjaxResult leftDateGoodsDetail(HttpServletRequest request) {
        Long businessId = getHostUserId(request);
        startPage();
        List<GoodsInfo> goodsInfos = goodsInfoMapper.leftDateGoodsDetail(businessId);
        return AjaxResult.success(goodsInfos);
    }


    //公告列表
    @ResponseBody
    @GetMapping("/gonggaoDetails")
    public List<SysNotice> backDetails(HttpServletRequest request, String noticeType) {
        Long businessId = getHostUserId(request);
        if (Objects.isNull(businessId)) {
            throw new GlobalException("error 商户id为空");
        }
        SysNotice sysNotice = new SysNotice();
        sysNotice.setStatus("0");
        if (StringUtils.isNotEmpty(noticeType)) {
            sysNotice.setNoticeType(noticeType);
        }
        sysNotice.setTypeResource(2);
        sysNotice.setCreateId(businessId);
        List<SysNotice> sysNotices = noticeService.selectNoticeList(sysNotice);
        return sysNotices;
    }


    /**
     * 查询【请填写功能名称】列表
     */
    @GetMapping("/zhuboList")
    public AjaxResult zhuboList(HttpServletRequest request, String type) {
        Long businessId = getHostUserId(request);
        if (Objects.isNull(businessId)) {
            throw new GlobalException("error 商户id为空");
        }
        Zhubo zhubo = new Zhubo();
        zhubo.setCreateId(businessId);
        if (StringUtils.isNotEmpty(type)) {
            zhubo.setType(type);
        }
        List<Zhubo> list = zhuboService.selectZhuboList(zhubo);
        return AjaxResult.success(list);
    }

    public Long getHostUserId(HttpServletRequest request) {
        String host = request.getHeader("host");
        logger.info("host : " + host);
        Long userByHost = userService.getUserByHost(host);
        return userByHost;
    }

    /**
     * 查询goods列表
     */
    @PostMapping("good/allList")
    public TableDataInfo allList(@RequestBody GoodsInfoAndAttrValueReq goodsInfoAndAttrValueReq, HttpServletRequest request) {
        Long hostUserId = getHostUserId(request);
        if (Objects.nonNull(hostUserId)) {
            goodsInfoAndAttrValueReq.setRetailId(hostUserId);
        }
        if (Objects.equals("pricedesc", goodsInfoAndAttrValueReq.getSortRank())) {
            goodsInfoAndAttrValueReq.setSort("desc");
            goodsInfoAndAttrValueReq.setSortColumn("price");
        }
        if (Objects.equals("priceasc", goodsInfoAndAttrValueReq.getSortRank())) {
            goodsInfoAndAttrValueReq.setSort("asc");
            goodsInfoAndAttrValueReq.setSortColumn("price");
        }
        if (Objects.equals("timeasc", goodsInfoAndAttrValueReq.getSortRank())) {
            goodsInfoAndAttrValueReq.setSort("asc");
            goodsInfoAndAttrValueReq.setSortColumn("create_time");
        }
        if (Objects.equals("timedesc", goodsInfoAndAttrValueReq.getSortRank())) {
            goodsInfoAndAttrValueReq.setSort("desc");
            goodsInfoAndAttrValueReq.setSortColumn("create_time");
        }
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(goodsInfoAndAttrValueReq.getPageNum());
        pageDomain.setPageSize(goodsInfoAndAttrValueReq.getPageSize());


        Long retailId = goodsInfoAndAttrValueReq.getRetailId();
        List<RetailAddPrice> param = new ArrayList<>();
        if (Objects.nonNull(retailId)) {
            param = retailAccountService.param(goodsInfoAndAttrValueReq, retailId);
        }
        goodsInfoAndAttrValueReq.setSaleState(1); //h5默认写死值查未出售的
        List<GoodsInfo> list = goodsInfoService.selectByResList(goodsInfoAndAttrValueReq, pageDomain);

        List<Long> goodsIds = list.stream().map(GoodsInfo::getId).collect(Collectors.toList());
        //根据账号list查询账号的浏览记录
        Map<Long, Integer> browsesMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(goodsIds)) {
            List<GoodsBrowse> browses =
                    goodsBrowseService.list(
                            new LambdaQueryWrapper<GoodsBrowse>()
                                    .select(GoodsBrowse::getGoodsId, GoodsBrowse::getCount)
                                    .in(GoodsBrowse::getGoodsId, goodsIds)
                                    .groupBy(GoodsBrowse::getGoodsId)
                    );
            browsesMap = browses
                    .stream()
                    .collect(Collectors.toMap(GoodsBrowse::getGoodsId,
                            GoodsBrowse::getCount));
        }

        List<SysUser> supplyCustomer = userService.getUserByRoleCode("supplyCustomer");
        if (Objects.nonNull(retailId)) {
            goodsInfoService.bacthAccRetail(list, supplyCustomer, retailId);
            for (GoodsInfo info : list) {
                info.setAccountPassword(null);
                if (Objects.isNull(info.getNewPrice())) {
                    info.setPrice(info.getPrice().setScale(0, BigDecimal.ROUND_UP));
                } else {
                    info.setPrice(info.getNewPrice().setScale(0, BigDecimal.ROUND_UP));
                }

                if (Objects.equals(info.getCreateId(), retailId)) {
                    info.setIfself(1);
                } else {
                    info.setIfself(2);
                }
                if (MapUtils.isNotEmpty(browsesMap)) {
                    info.setBrowseNum(browsesMap.get(info.getId()));
                }
//                info.setIcon(gameInfosMap.get(info.getGameId()));
            }
            retailAccountService.bacthAcc(list, param);
        } else {
            if (MapUtils.isNotEmpty(browsesMap)) {
                for (GoodsInfo info : list) {
                    info.setBrowseNum(browsesMap.get(info.getId()));
//                info.setIcon(gameInfosMap.get(info.getGameId()));
                }
            }
            goodsInfoService.bacthAddPrice(list, supplyCustomer);
        }
        for (GoodsInfo goodsInfo : list) {
            goodsInfo.setAccountPassword(null);
            goodsInfo.setCostPrice(null);
            goodsInfo.setCreateName(null);
            goodsInfo.setPrice(null);
            goodsInfo.setSupplyId(null);
            goodsInfo.setSupplyName(null);
            goodsInfo.setImportantRemark(null);
        }
        return getDataTable(list);
    }

    /**
     * good详情
     */
    @GetMapping("good/info")
    public AjaxResult goodInfo(@RequestParam Map<String, Object> params) {

        Long id = MapUtils.getLong(params, "id");
        Long retailId = MapUtils.getLong(params, "retailId");

        GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto = goodsInfoService.selectGoodsInfoAndAttrValueById(id);
        GoodsInfo goodsInfo = goodsInfoService.selectGoodsInfoById(id);
        List<GoodsInfo> goodsInfoList = new ArrayList<>();
        goodsInfoList.add(goodsInfo);

        List<SysUser> supplyCustomer = userService.getUserByRoleCode("supplyCustomer");
        LoginUser loginUser;
        try {
            loginUser = getLoginUser();
        } catch (ServiceException serviceException) {
//            System.out.println("抓到了");
            loginUser = null;
        }
        if (Objects.nonNull(retailId)) {
            //第一次商品加价
            goodsInfoService.bacthAccRetailByUserId(goodsInfoList, supplyCustomer, retailId);
            //第二次分销加价
            //加价逻辑
            RetailAddPrice retailAddPrice = new RetailAddPrice();
            retailAddPrice.setCreateId(retailId);
            if (Objects.isNull(goodsInfo.getNewPrice())) {
                goodsInfoList.get(0).setPrice(goodsInfo.getPrice().setScale(0, BigDecimal.ROUND_UP));
            } else {
                goodsInfoList.get(0).setPrice(goodsInfo.getNewPrice().setScale(0, BigDecimal.ROUND_UP));
            }

            if (Objects.equals(goodsInfo.getCreateId(), retailId)) {
                goodsInfo.setIfself(1);
            } else {
                goodsInfo.setIfself(2);
            }
            List<RetailAddPrice> retailAddPrices = retailAddPriceMapper.selectRetailAddPriceList(retailAddPrice);

            retailAccountService.bacthAcc(goodsInfoList, retailAddPrices);
        } else {
            goodsInfoService.bacthAddPrice(goodsInfoList, supplyCustomer);
            if (Objects.nonNull(loginUser)) {
                Collect collectqx = new Collect();
                collectqx.setCreateId(loginUser.getUserId());
                collectqx.setAccountId(goodsInfoAndAttrValueDto.getId());
                List<Collect> collects = collectService.selectCollectList(collectqx);
                if (CollectionUtils.isNotEmpty(collects)) {
                    goodsInfoAndAttrValueDto.setIsCollect(1);  //1代表已收藏
                }
            }
        }
        goodsInfoAndAttrValueDto.setNewPrice(goodsInfoList.get(0).getNewPrice());
        //查询该账号的浏览记录
        Long browseNum = goodsBrowseService.count(new LambdaQueryWrapper<GoodsBrowse>().eq(GoodsBrowse::getGoodsId,
                goodsInfoAndAttrValueDto.getId()));
        goodsInfoAndAttrValueDto.setBrowseNum(browseNum.intValue());
        //查询该账号的游戏图标
        GameInfo gameInfo = gameInfoService.selectGameInfoById(goodsInfoAndAttrValueDto.getGameId());
        if (Objects.nonNull(gameInfo)) {
            goodsInfoAndAttrValueDto.setIcon(gameInfo.getIcon());
        }
        //保存浏览记录
        GoodsBrowse goodsBrowse = new GoodsBrowse();
        goodsBrowse.setGameId(goodsInfoAndAttrValueDto.getGameId());
        goodsBrowse.setGameName(goodsInfoAndAttrValueDto.getGameName());
        goodsBrowse.setGoodsId(goodsInfoAndAttrValueDto.getId());
        if (Objects.nonNull(loginUser)) {
            goodsBrowse.setBrowseUserId(loginUser.getUserId());
        }
        goodsBrowseService.save(goodsBrowse);
        goodsInfoAndAttrValueDto.setAccountPassword(null);
        goodsInfoAndAttrValueDto.setCostPrice(null);
        goodsInfoAndAttrValueDto.setCreateName(null);
        goodsInfoAndAttrValueDto.setPrice(null);
        goodsInfoAndAttrValueDto.setSupplyId(null);
        goodsInfoAndAttrValueDto.setSupplyName(null);
        goodsInfoAndAttrValueDto.setImportantRemark(null);
        return AjaxResult.success(goodsInfoAndAttrValueDto);
    }

    /**
     * good属性
     */
    @GetMapping("good/attr/info")
    public AjaxResult goodAttr(@RequestParam Map<String, Object> params) {

        Long gameId = MapUtils.getLong(params, "gameId");

        GameAttribute gameAttribute = new GameAttribute();
        gameAttribute.setGameId(gameId);
        Map<String, Object> result = gameAttributeService.selectWebGameAttributeList(gameAttribute);

        return AjaxResult.success(result);
    }


    /**
     * 获取游戏类型
     */
    @GetMapping("game/Type")
    public AjaxResult gameType(String type, String initial, String name, HttpServletRequest request) {
        Long userId = getHostUserId(request);
        List<GameInfo> list = new ArrayList<>();
        Integer tt = null;
        if (StringUtils.isNotEmpty(type)) {
            tt = Integer.valueOf(type);
        }
        if (Objects.nonNull(userId)) {
            list = retailAccountService.getGameType(userId, tt);

        } else {
            GameInfo gameInfo = new GameInfo();
            if (StringUtils.isNotEmpty(type)) {
                gameInfo.setType(Integer.valueOf(type));
            }
            if (StringUtils.isNotEmpty(name)) {
                gameInfo.setName(name);
            }
            gameInfo.setState(0);
            list = gameInfoService.selectGameInfoList(gameInfo);
        }
        List<Map<String, Object>> result = goodsInfoService.countForSupply(new GoodsInfo());
        List<GameInfo> resList = new ArrayList<>();
        if (StringUtils.isNotEmpty(initial)) {
            String xiaoxie = initial.toLowerCase();
            for (GameInfo gameInfo : list) {
                String firstChar = ToPinYin.toFirstChar(gameInfo.getName());
                if (StringUtils.equals(xiaoxie, firstChar.substring(0, 1))) {
                    resList.add(gameInfo);
                }
            }
        } else {
            resList = list;
        }
        for (GameInfo gameInfo : resList) {
            for (Map<String, Object> stringObjectMap : result) {
                if (StringUtils.equals(String.valueOf(stringObjectMap.get("type")), gameInfo.getName())) {
                    if (StringUtils.isNotNull((stringObjectMap.get("cot")))) {
                        gameInfo.setCount(Integer.valueOf(String.valueOf(stringObjectMap.get("cot"))));
                    }
                }
            }
        }
        return AjaxResult.success(resList);
    }


    /**
     * 获取自定义属性
     */
    @GetMapping("/gameTemp")
    public TableDataInfo gameTemp(GameAttrTemplate gameAttrTemplate) {
        List<GameAttrTemplate> list = gameAttrTemplateService.selectGameAttrTemplateList(gameAttrTemplate);
        return getDataTable(list);
    }

    /**
     * 获取自定义属性
     */
    @GetMapping("/gameTempAttr")
    public TableDataInfo gameTempAttr(GameAttrTemplate gameAttrTemplate) {
        List<GameAttrTemplate> list =
                gameAttrTemplateService.selectGameAttrTemplateByGameId(gameAttrTemplate.getGameId());
        return getDataTable(list);
    }


    /**
     * 系统 运营商
     */
    @GetMapping("/keylist")
    public TableDataInfo keylist(GameAttribute gameAttribute) {
        List<GameAttribute> list = gameAttributeService.keySelectGameAttributeList(gameAttribute);
        return getDataTable(list);
    }

    /**
     * 区服
     */
    @GetMapping("/districtSuit")
    public TableDataInfo districtSuit(GameAttribute gameAttribute) {
        List<GameAttribute> list = gameAttributeService.selectGameAttributeList(gameAttribute);
        return getDataTable(list);
    }

    /**
     * 获取gpzSearch详细信息
     */
    @GetMapping(value = "/gpzSearch/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        if (Objects.isNull(id) || id.intValue() == 0) {
            throw new GlobalException("传入参数不能为空!");
        }
        return AjaxResult.success(gpzSearchService.getById(id));
    }

    /**
     * 新增GpzSearch
     */
    @Log(title = "gpzSearch", businessType = BusinessType.INSERT)
    @PostMapping(value = "/gpzSearch")
    public AjaxResult add(@RequestBody GpzSearch gpzSearch) {
        if (Objects.isNull(gpzSearch) || StringUtils.isEmpty(gpzSearch.getSearchValue())) {
            throw new GlobalException("传入参数不能为空!");
        }
        gpzSearchService.save(gpzSearch);
        return AjaxResult.success(gpzSearch);
    }


    @GetMapping("/pc/index/data")
    public AjaxResult pcIndexData() {
        return AjaxResult.success(goodsInfoService.pcIndexData());
    }

    @GetMapping("/notice/list")
    public TableDataInfo noticeList(SysNotice notice) {
        notice.setStatus("0");
        startPage();
        List<SysNotice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }


    @GetMapping("/recentlydeal")
    public TableDataInfo recentlydeal() {
        startPage();
        List<GoodsInfo> recentlydeal = goodsInfoMapper.recentlydeal();
        List<Long> goodsIds = recentlydeal.stream().map(GoodsInfo::getId).collect(Collectors.toList());
        Map<Long, Integer> browsesMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(goodsIds)) {
            List<GoodsBrowse> browses =
                    goodsBrowseService.list(new LambdaQueryWrapper<GoodsBrowse>().select(GoodsBrowse::getGoodsId,
                            GoodsBrowse::getCount).in(GoodsBrowse::getGoodsId, goodsIds).groupBy(GoodsBrowse::getGoodsId));
            browsesMap = browses.stream().collect(Collectors.toMap(GoodsBrowse::getGoodsId,
                    GoodsBrowse::getCount));
        }
//        List<SysUser> supplyCustomer = userService.getUserByRoleCode("supplyCustomer");
//        goodsInfoService.bacthAddPrice(recentlydeal, supplyCustomer);
        for (GoodsInfo goodsInfo : recentlydeal) {
            goodsInfo.setNewPrice(goodsInfo.getPrice());
            goodsInfo.setAccountPassword(null);
            goodsInfo.setCostPrice(null);
            goodsInfo.setCreateName(null);
            goodsInfo.setPrice(null);
            goodsInfo.setSupplyId(null);
            goodsInfo.setSupplyName(null);
            goodsInfo.setImportantRemark(null);
            if (MapUtils.isNotEmpty(browsesMap)) {
                goodsInfo.setBrowseNum(browsesMap.get(goodsInfo.getId()));
            }
        }
        return getDataTable(recentlydeal);
    }

    @GetMapping("/social/list")
    public TableDataInfo socialList() {
        LambdaQueryWrapper<SocialInfo> socialInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        socialInfoLambdaQueryWrapper.eq(SocialInfo::getIsEnable, 1);
        return getDataTable(socialInfoService.list(socialInfoLambdaQueryWrapper));
    }

}
