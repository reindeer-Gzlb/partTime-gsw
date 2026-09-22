package com.ruoyi.web.controller.V2.web.conntroller;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.H5PageDomain;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.V2.retail.mapper.RetailAccountMapper;
import com.ruoyi.system.V2.retail.mapper.RetailAddPriceMapper;
import com.ruoyi.system.h5.GameDetails;
import com.ruoyi.system.h5.GetGameTypeH5;
import com.ruoyi.system.h5.Hreq;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.AccountInfo;
import com.ruoyi.system.model.domain.InfoImg;
import com.ruoyi.system.model.domain.RetailAccount;
import com.ruoyi.system.model.domain.RetailAddPrice;
import com.ruoyi.system.model.mapper.AddPriceMapper;
import com.ruoyi.system.model.mapper.InfoImgMapper;
import com.ruoyi.system.model.service.IAccountInfoService;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysUserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author lz
 * @version 1.0
 * @date 2022/5/10 18:34
 */
@RestController
@RequestMapping("/select")
public class SelectAccountController {

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private IAccountInfoService accountInfoService;

    @Autowired
    private InfoImgMapper infoImgMapper;

    @Resource
    private RetailAccountMapper retailAccountMapper;

    @Resource
    private AddPriceMapper addPriceMapper;

    @Resource
    private RetailAddPriceMapper retailAddPriceMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private ISysUserService userService;

    /**
     * 查询游戏类型
     */
    @ResponseBody
    @PostMapping("/GetGameType")
    public List<GetGameTypeH5> GameType() {
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("game_type");
        sysDictData.setStatus(Constants.STR_0);
        List<SysDictData> sysDictDataList = dictDataService.selectDictDataList(sysDictData);

//        sysDictData.setDictType("gameLogo");
//        List<SysDictData> gameLogos = dictDataService.selectDictDataList(sysDictData);

        List<GetGameTypeH5> getGameTypeH5s = new ArrayList<>();
        for (SysDictData dictData : sysDictDataList) {
            GetGameTypeH5 getGameTypeH5 = new GetGameTypeH5();
            getGameTypeH5.setDataDictionary_ID(dictData.getDictCode());
            getGameTypeH5.setDetail_Code(dictData.getDictType());
            getGameTypeH5.setDetail_ID(dictData.getDictCode());
            getGameTypeH5.setDetail_Name(dictData.getDictLabel());
            getGameTypeH5.setDetail_Value(dictData.getDictValue());
            getGameTypeH5.setImg(dictData.getRemark());

//            for (SysDictData gameLogo : gameLogos) {
//                if (gameLogo.getDictLabel().equals(dictData.getDictLabel())) {
//                    getGameTypeH5.setImg(gameLogo.getDictValue());
//                }
//                }else if (Constant.cyhx.equals(dictData.getDictLabel())){
//                    getGameTypeH5.setImg("https://game.ikbh.top/logo/cyhx.png");
//                }else if (Constant.hpjy.equals(dictData.getDictLabel())){
//                    getGameTypeH5.setImg("https://game.ikbh.top/logo/hpjy.png");
//                }else if (Constant.yxlm.equals(dictData.getDictLabel())){
//                    getGameTypeH5.setImg("https://game.ikbh.top/logo/yxlm.png");
//                }else if (Constant.ys.equals(dictData.getDictLabel())){
//                    getGameTypeH5.setImg("");
//                }
//            }
            getGameTypeH5s.add(getGameTypeH5);
        }
        return getGameTypeH5s;
    }

    /**
     * 查询游戏类型
     */
    @SneakyThrows
    @GetMapping("/GetGameDQ")
    public List<GetGameTypeH5> GetGameDQ(String YXLX_ID) {
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType(YXLX_ID);
        List<SysDictData> sysDictDataList = dictDataService.selectDictDataList(sysDictData);
        List<GetGameTypeH5> getGameTypeH5s = new ArrayList<>();
        for (SysDictData dictData : sysDictDataList) {
            GetGameTypeH5 getGameTypeH5 = new GetGameTypeH5();
            getGameTypeH5.setDataDictionary_ID(dictData.getDictCode());
            getGameTypeH5.setDetail_Code(dictData.getDictType());
            getGameTypeH5.setDetail_ID(dictData.getDictCode());
            getGameTypeH5.setDetail_Name(dictData.getDictLabel());
            getGameTypeH5.setDetail_Value(dictData.getDictValue());
            getGameTypeH5s.add(getGameTypeH5);
        }
        return getGameTypeH5s;
    }

    /**
     * 查询游戏段位
     */
    @SneakyThrows
    @GetMapping("/GetGameDW")
    public List<GetGameTypeH5> GetGameDW(String YXLX_ID) {
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType(YXLX_ID + "段位");
        List<SysDictData> sysDictDataList = dictDataService.selectDictDataList(sysDictData);
        List<GetGameTypeH5> getGameTypeH5s = new ArrayList<>();
        for (SysDictData dictData : sysDictDataList) {
            GetGameTypeH5 getGameTypeH5 = new GetGameTypeH5();
            getGameTypeH5.setDataDictionary_ID(dictData.getDictCode());
            getGameTypeH5.setDetail_Code(dictData.getDictType());
            getGameTypeH5.setDetail_ID(dictData.getDictCode());
            getGameTypeH5.setDetail_Name(dictData.getDictLabel());
            getGameTypeH5.setDetail_Value(dictData.getDictValue());
            getGameTypeH5s.add(getGameTypeH5);
        }
        return getGameTypeH5s;
    }

    /**
     * 查询游戏段位
     */
    @SneakyThrows
    @GetMapping("/getPriceLimit")
    public List<GetGameTypeH5> GetGameGJZ() {
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("priceLimit");
        List<SysDictData> sysDictDataList = dictDataService.selectDictDataList(sysDictData);
        List<GetGameTypeH5> getGameTypeH5s = new ArrayList<>();
        for (SysDictData dictData : sysDictDataList) {
            GetGameTypeH5 getGameTypeH5 = new GetGameTypeH5();
            getGameTypeH5.setDetail_Value(dictData.getRemark());
            getGameTypeH5s.add(getGameTypeH5);
        }
        return getGameTypeH5s;
    }

    /**
     * 查询游戏账号列表
     */
    @ResponseBody
    @GetMapping("/GetZHList")
    public H5PageDomain GetZHList(Hreq hreq, HttpServletRequest request) {
        H5PageDomain h5PageDomain = accountInfoService.accountListLimit(hreq, request);
        return h5PageDomain;
    }

//    public void sensitive(AccountInfo accountInfo){
//
//        List<String> sensitive = sensitive();
//        for (String vocabulary : sensitive) {
//            String orderNo = accountInfo.getOrderNo();
//            if (Objects.nonNull(orderNo)){
//                orderNo = orderNo.replace(vocabulary,"");
//                accountInfo.setOrderNo(orderNo);
//            }
//
//            String gameName = accountInfo.getGameName();
//            if (Objects.nonNull(gameName)){
//                gameName = gameName.replace(vocabulary,"");
//                accountInfo.setGameName(gameName);
//            }
//
//            String des = accountInfo.getDes();
//            if (Objects.nonNull(des)){
//                des = des.replace(vocabulary,"");
//                accountInfo.setDes(des);
//            }
//        }
//    }

//    private List<String> sensitive(){
//        SysDictData sysDictData = new SysDictData();
//        sysDictData.setDictType(LCConstant.sensitive);
//        sysDictData.setStatus(Constants.STR_0);
//        List<SysDictData> sysDictDataList = dictDataService.selectDictDataList(sysDictData);
//        return sysDictDataList.stream().map(s -> s.getDictValue()).collect(Collectors.toList());
//    }


    /**
     * 查询游戏账号列表
     */
    @ResponseBody
    @GetMapping("/GetZHInfo")
    public GameDetails GetZHInfo(Long zhid, Long id, HttpServletRequest request) {

        AccountInfo accountInfo = accountInfoService.selectAccountInfoById(zhid);

        accountInfoService.sensitive(accountInfo);

        GameDetails gameDetails = new GameDetails();
        gameDetails.setZHID(accountInfo.getId());
        gameDetails.setYXMZ(accountInfo.getGameName());
        gameDetails.setYXLX(accountInfo.getType());
        gameDetails.setYXDQ(accountInfo.getRegional());
        if (accountInfo.getSkins() == null) accountInfo.setSkins(0);
        gameDetails.setPF(accountInfo.getSkins());
        if (accountInfo.getHeroes() == null) accountInfo.setSkins(0);
        gameDetails.setYX(accountInfo.getHeroes());
        if (null == id) {
            gameDetails.setCSJG(accountInfo.getPrice());
            String serverName = request.getServerName();
            if (!serverName.contains("rt.")) {
                SysUser sysUser = new SysUser();
                sysUser.setDomain(serverName);
//                List<SysUser> sysUsers = sysUserMapper.selectUserList(sysUser);
//                if (!CollectionUtils.isEmpty(sysUsers)) {
//                    sysUser = sysUsers.get(0);
//                    Integer rise = sysUser.getRise();
//                    if (Objects.isNull(rise)) {
//                        rise = 0;
//                    }
//                    BigDecimal price = accountInfo.getPrice();
//                    BigDecimal add = new BigDecimal(100).add(new BigDecimal(rise));
//                    add = add.divide(new BigDecimal(100));
//                    BigDecimal multiply = price.multiply(add);
//                    gameDetails.setCSJG(multiply);
//
//                } else {
                if (Objects.equals(accountInfo.getAccountType(), "2") || Objects.equals(accountInfo.getAccountType(), "1")) {
//                                    Integer rise = addPrice.getRise();
//                                    BigDecimal price = accountInfo.getPrice();
//                                    BigDecimal add = new BigDecimal(100).add(new BigDecimal(rise));
//                                    add = add.divide(new BigDecimal(100));
//                                    BigDecimal multiply = price.multiply(add);
                    List<SysUser> supplyCustomer = userService.getUserByRoleCode("supplyCustomer");
                    BigDecimal bigDecimal = accountInfoService.addPrice(accountInfo.getPrice(), accountInfo.getCreateId(),supplyCustomer);
                    if (Objects.nonNull(bigDecimal)) {
                        gameDetails.setCSJG(bigDecimal);
                    } else {
                        gameDetails.setCSJG(accountInfo.getPrice().setScale(0, BigDecimal.ROUND_UP));
                    }
                }
//                }

                gameDetails.setAccType(accountInfo.getAccType());
                gameDetails.setSpecialPrice(accountInfo.getSpecialPrice());
            } else {
                throw new ServiceException("域名解析失败");
            }
        } else {

            List<AccountInfo> accountInfoList = new ArrayList<>();
            accountInfoList.add(accountInfo);
            List<SysUser> supplyCustomer = userService.getUserByRoleCode("supplyCustomer");
            accountInfoService.bacthAcc(accountInfoList,supplyCustomer);
            accountInfo.setPrice(accountInfoList.get(0).getNewPrice());

            RetailAddPrice retailAddPrice = new RetailAddPrice();
            retailAddPrice.setCreateId(Long.parseLong(id.toString()));
            List<RetailAddPrice> retailAddPrices = retailAddPriceMapper.selectRetailAddPriceList(retailAddPrice);

            if (!CollectionUtils.isEmpty(retailAddPrices)) {
                for (RetailAddPrice addPrice : retailAddPrices) {
                    if (Objects.nonNull(addPrice.getSuperId())) {
                        if (Objects.equals(accountInfo.getCreateId() + "", addPrice.getSuperId())) {
                            Long rise = addPrice.getRrise();
                            if (Objects.nonNull(rise)) {
                                BigDecimal add = new BigDecimal(100).add(new BigDecimal(rise));
                                BigDecimal divide = add.divide(new BigDecimal(100));
                                BigDecimal price = accountInfo.getPrice();
                                BigDecimal multiply = price.multiply(divide);
                                if (Objects.nonNull(multiply)) {
                                    gameDetails.setCSJG(multiply.setScale(0, BigDecimal.ROUND_UP));
                                } else {
                                    gameDetails.setCSJG(accountInfo.getPrice().setScale(0, BigDecimal.ROUND_UP));
                                }
                            }
                        }
                    }
                }
            }

            gameDetails.setAccType("0");
            gameDetails.setSpecialPrice(accountInfo.getSpecialPrice());

        }
        gameDetails.setDW(accountInfo.getLevel());
        if (accountInfo.getGuns() == null) accountInfo.setGuns(0);
        gameDetails.setGUN(accountInfo.getGuns());
        if (accountInfo.getSuit() == null) accountInfo.setSuit(0);
        gameDetails.setSUIT(accountInfo.getSuit());
        gameDetails.setJJ(accountInfo.getDes());
        InfoImg infoImg = new InfoImg();
        infoImg.setInfoId(zhid);
        List<InfoImg> infoImgs = infoImgMapper.selectInfoImgList(infoImg);
        System.out.println(System.currentTimeMillis());
        List<String> IMGS = new ArrayList<>();
        for (
                InfoImg img : infoImgs) {
            IMGS.add(img.getImgUrl());
            if (Objects.equals(img.getType(), Constants.STR_2)) {
                gameDetails.setVideoUrl(img.getImgUrl());
            }
        }
        gameDetails.setIMGS(IMGS);

//        infoImg.setType(Constants.STR_2);
//        System.out.println(System.currentTimeMillis());
//        List<InfoImg> vindeoUrl = infoImgMapper.selectInfoImgList(infoImg);
//        System.out.println(System.currentTimeMillis());
//        if (!CollectionUtils.isEmpty(vindeoUrl)) {
//            String imgUrl = vindeoUrl.get(0).getImgUrl();
//            gameDetails.setVideoUrl(imgUrl);
//        }
        return gameDetails;
    }

    public BigDecimal getNewPrice(Long accountId, Long id, BigDecimal price) {
        RetailAccount retailAccount = new RetailAccount();
        retailAccount.setCreateId(id);
        retailAccount.setAccountId(accountId);
        List<RetailAccount> retailAccountList = retailAccountMapper.selectRetailAccountList(retailAccount);
        if (!CollectionUtils.isEmpty(retailAccountList)) {
            int rise = retailAccountList.get(0).getRise();
            BigDecimal newPrice = price.multiply((new BigDecimal(100).add(new BigDecimal(rise))).divide(new BigDecimal(100)));
            return newPrice;
        }
        return null;
    }

}
