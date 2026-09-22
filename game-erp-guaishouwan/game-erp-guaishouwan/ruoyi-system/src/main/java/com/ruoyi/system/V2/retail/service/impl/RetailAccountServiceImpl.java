package com.ruoyi.system.V2.retail.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.SalerStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.gameInfo.mapper.GameInfoMapper;
import com.ruoyi.system.V2.retail.mapper.RetailAccountMapper;
import com.ruoyi.system.V2.retail.mapper.RetailAddPriceMapper;
import com.ruoyi.system.V2.retail.service.IRetailAccountService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.*;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueReq;
import com.ruoyi.system.model.mapper.AccountInfoMapper;
import com.ruoyi.system.model.mapper.AddPriceMapper;
import com.ruoyi.system.model.vo.SelectRetail;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 零售号Service业务层处理
 *
 * @author ruoyi
 * @date 2022-05-27
 */
@Service
public class RetailAccountServiceImpl implements IRetailAccountService {
    @Resource
    private RetailAccountMapper retailAccountMapper;
    @Resource
    private RetailAddPriceMapper retailAddPriceMapper;
    @Autowired
    private AccountInfoMapper accountInfoMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private AddPriceMapper addPriceMapper;
    @Resource
    private GameInfoMapper gameInfoMapper;

    /**
     * 查询零售号
     *
     * @param id 零售号主键
     * @return 零售号
     */
    @Override
    public RetailAccount selectRetailAccountById(Long id) {
        return retailAccountMapper.selectRetailAccountById(id);
    }


    @Override
    public void bacthAcc(List<GoodsInfo> goodsInfos, List<RetailAddPrice> retailAddPrices) {

        for (GoodsInfo goodsInfo : goodsInfos) {

            for (RetailAddPrice retailAddPrice : retailAddPrices) {

                if (Objects.nonNull(retailAddPrice.getSuperId())) {
                    if (Objects.equals(goodsInfo.getCreateId(), Long.parseLong(retailAddPrice.getSuperId()))) {
                        Long rise = retailAddPrice.getRrise();
                        BigDecimal price = goodsInfo.getPrice();
                        BigDecimal add = new BigDecimal(100).add(new BigDecimal(rise));
                        add = add.divide(new BigDecimal(100));
                        BigDecimal multiply = price.multiply(add);
                        if (Objects.nonNull(multiply)) {
                            goodsInfo.setNewPrice(multiply.setScale(0, BigDecimal.ROUND_UP));
                        } else {
                            goodsInfo.setNewPrice(price.setScale(0, BigDecimal.ROUND_UP));
                        }
                    }
                }

            }
        }
    }

    /**
     * 设置分销请求参数
     *
     * @param goodsInfo
     * @return
     */
    public List<RetailAddPrice> param(GoodsInfoAndAttrValueReq goodsInfo, Long userId) {
        /**
         * 1、查询分销改价
         * 2、获取供货上ID
         */
        RetailAddPrice retailAddPrice = new RetailAddPrice();
        retailAddPrice.setCreateId(userId);
        goodsInfo.setAccountType(null);
        List<RetailAddPrice> retailAddPrices = retailAddPriceMapper.selectRetailAddPriceList(retailAddPrice);
        if (!org.apache.commons.collections4.CollectionUtils.isEmpty(retailAddPrices)) {
            List<SelectRetail> selectRetailList = new ArrayList<>();
            for (RetailAddPrice addPrice : retailAddPrices) {
                SelectRetail selectRetail = new SelectRetail();
                selectRetail.setCreateId(addPrice.getSuperId());
                if (!StringUtils.isEmpty(addPrice.getGametype())) {
                    String[] split = addPrice.getGametype().split(",");
                    selectRetail.setType(Arrays.asList(split));
                }
                selectRetailList.add(selectRetail);
                goodsInfo.setSelectRetails(selectRetailList);
            }
        }
        return retailAddPrices;
    }

    /**
     * 查询零售号列表
     *
     * @param retailAccount 零售号
     * @return 零售号
     */
    @Override
    public List<RetailAccount> selectRetailAccountList(RetailAccount retailAccount) {
        Long userId = SecurityUtils.getUserId();
        retailAccount.setCreateId(userId);
        /**
         * 排序
         */
        if (!StringUtils.isNull(retailAccount.getSort())) {
            String sort = retailAccount.getSort().replace("ending", "");
            retailAccount.setSort(sort);
        }


        List<RetailAccount> retailAccountList = retailAccountMapper.selectRetailAccountList(retailAccount);
        List<Long> ids = retailAccountList.stream().map(retail -> retail.getAccountId()).collect(Collectors.toList());
        AccountInfo infoPrams = new AccountInfo();
        infoPrams.setIds(ids);
        List<AccountInfo> accs = accountInfoMapper.selectAccountInfoList(infoPrams);

        AddPrice addPrice = new AddPrice();
        addPrice.setPriceType("2");
        addPrice.setCreateId(SecurityUtils.getUserId());
        List<AddPrice> addPrices = addPriceMapper.selectAddPriceList(addPrice);

        for (RetailAccount account : retailAccountList) {
            Integer saleState = account.getSaleState();
            String info = SalerStatus.getInfo(saleState);
            account.setSaleStateName(info);
//            AccountInfo accountInfo = accountInfoMapper.selectAccountInfoById(account.getAccountId());

            Long accountId = account.getAccountId();
            for (AccountInfo acc : accs) {
                if (Objects.equals(acc.getId(), accountId)) {
                    account.setNewPrice(acc.getPrice());
                    account.setRise(0);
                    if (Objects.nonNull(acc)) {
                        SysUser sysUser = sysUserMapper.selectUserById(acc.getCreateId());
                        if (Objects.nonNull(sysUser)) {
                            account.setSupplyName(sysUser.getUserName());
                        }
                        if (Objects.nonNull(acc.getCmdSupplyId())) {
                            account.setSupplyName(acc.getCmdSupplyName());
                        }
                    }

                    if (Objects.nonNull(acc.getCmdSupplyId())) {
                        List<AddPrice> cmdAddList =
                                addPrices.stream().filter(ap -> ap.getPriceType().equals("2") && ap.getRoleType().equals("2")).collect(Collectors.toList());
                        for (AddPrice price : cmdAddList) {
                            if (Objects.equals(acc.getCmdSupplyId(), price.getSuperId())) {
                                Integer rise = price.getRise();
                                if (null == rise) rise = 0;
                                BigDecimal b = new BigDecimal(0);
                                if ("1".equals(price.getType())) {
                                    b = new BigDecimal(100).add(new BigDecimal(rise));
                                } else {
                                    b = new BigDecimal(100).subtract(new BigDecimal(rise));
                                }

                                BigDecimal newPrice = acc.getPrice().multiply(b).divide(new BigDecimal(100));
//                                gameList.setCsjg(newPrice);
                                account.setNewPrice(newPrice);
                                account.setRise(price.getRise());
                            }
                        }

                    } else {
                        List<AddPrice> cmdAddList =
                                addPrices.stream().filter(ap -> ap.getPriceType().equals("2") && ap.getRoleType().equals("1")).collect(Collectors.toList());
                        for (AddPrice price : cmdAddList) {
                            if (Objects.equals(acc.getSupplyId(), price.getSuperId())) {
                                Integer rise = price.getRise();
                                if (null == rise) rise = 0;
                                BigDecimal b = new BigDecimal(0);
                                if ("1".equals(price.getType())) {
                                    b = new BigDecimal(100).add(new BigDecimal(rise));
                                } else {
                                    b = new BigDecimal(100).subtract(new BigDecimal(rise));
                                }

                                BigDecimal newPrice = acc.getPrice().multiply(b).divide(new BigDecimal(100));
//                                gameList.setCsjg(newPrice);
                                account.setNewPrice(newPrice);
                                account.setRise(price.getRise());
                            }
                        }
                    }
                }
            }

        }
        return retailAccountList;
    }

    /**
     * 新增零售号
     *
     * @param retailAccount 零售号
     * @return 结果
     */
    @Override
    public int insertRetailAccount(RetailAccount retailAccount) {
        retailAccount.setCreateTime(DateUtils.getNowDate());
        return retailAccountMapper.insertRetailAccount(retailAccount);
    }

    /**
     * 修改零售号
     *
     * @param retailAccount 零售号
     * @return 结果
     */
    @Override
    public int updateRetailAccount(RetailAccount retailAccount) {
        return retailAccountMapper.updateRetailAccount(retailAccount);
    }

    /**
     * 批量删除零售号
     *
     * @param ids 需要删除的零售号主键
     * @return 结果
     */
    @Override
    public int deleteRetailAccountByIds(Long[] ids) {
        return retailAccountMapper.deleteRetailAccountByIds(ids);
    }

    /**
     * 删除零售号信息
     *
     * @param id 零售号主键
     * @return 结果
     */
    @Override
    public int deleteRetailAccountById(Long id) {
        return retailAccountMapper.deleteRetailAccountById(id);
    }


    public BigDecimal getNewPrice(Long accountId, Long id, BigDecimal price) {
        RetailAccount retailAccount = new RetailAccount();
        retailAccount.setCreateId(id);
        retailAccount.setAccountId(accountId);
        List<RetailAccount> retailAccountList = retailAccountMapper.selectRetailAccountList(retailAccount);
        if (!CollectionUtils.isEmpty(retailAccountList)) {
            int rise = retailAccountList.get(0).getRise();
            BigDecimal newPrice =
                    price.multiply((new BigDecimal(100).add(new BigDecimal(rise))).divide(new BigDecimal(100)));
            return newPrice;
        }
        return null;
    }

    @Override
    public String addMyAcount(Long[] ids) {
        StringBuffer result = new StringBuffer();
        Long userId = SecurityUtils.getUserId();
        Arrays.asList(ids).stream().forEach(id -> {
            RetailAccount retailAccount = new RetailAccount();
            retailAccount.setCreateId(userId);
            retailAccount.setCreateTime(new Date());
            retailAccount.setAccountId(id);
            retailAccount.setRise(0);
            AccountInfo accountInfo = accountInfoMapper.selectAccountInfoById(id);
            if (null != accountInfo && SalerStatus.unsold.getCode() == accountInfo.getSaleState()) {
                retailAccountMapper.insertRetailAccount(retailAccount);
            } else {
                result.append("序号：" + id + "已删除或者已为非未售状态 \n");
            }

        });
        return result.toString();
    }

    @Override
    public void bacthAddPrice(Map<String, Object> params) {

        Integer rise = MapUtils.getInteger(params, "rise");
        String type = MapUtils.getString(params, "jianjiajia");
        String saleType = MapUtils.getString(params, "saleType");
        String supplyIdAddr = MapUtils.getString(params, "supplyIdAddr");
        Long supplyId = MapUtils.getLong(params, "supplyId");
        Long cmdSupplyId = MapUtils.getLong(params, "cmdSupplyId");
        String[] supplyIds = {};
        if (Objects.nonNull(supplyIdAddr)) {
            supplyIds = supplyIdAddr.split(",");
        }


        if (null == rise) rise = 0;
        BigDecimal b = new BigDecimal(0);
        if ("1".equals(type)) {
            b = new BigDecimal(100).add(new BigDecimal(rise));
        } else {
            b = new BigDecimal(100).subtract(new BigDecimal(rise));
        }
        if (Objects.equals("sale", saleType)) {

//            if (Objects.nonNull(cmdSupplyId)){
//                AccountInfo accountInfo = new AccountInfo();
//                accountInfo.setCmdSupplyId(cmdSupplyId);
//                accountInfo.setAccountType("3");
//                List<AccountInfo> accountInfoList = accountInfoMapper.selectAccountInfoList(accountInfo);
//                for (AccountInfo info : accountInfoList) {
//                    BigDecimal newPrice = info.getPrice().multiply(b).divide(new BigDecimal(100));
//                    info.setNewPrice(newPrice);
//                    info.setRise(rise);
//                    this.accountInfoMapper.updateAccountInfo(info);
//                }
//            }

            if (!CollectionUtils.isEmpty(Arrays.asList(supplyIds))) {
                for (String o : Arrays.asList(supplyIds)) {
                    long userId = Long.parseLong(o);
                    AccountInfo accountInfo = new AccountInfo();
                    accountInfo.setCreateId(userId);
                    List<AccountInfo> accountInfoList = accountInfoMapper.selectAccountInfoList(accountInfo);
                    for (AccountInfo info : accountInfoList) {
                        BigDecimal newPrice = info.getPrice().multiply(b).divide(new BigDecimal(100));
                        info.setNewPrice(newPrice);
                        info.setRise(rise);
                        this.accountInfoMapper.updateAccountInfo(info);
                    }
                }
            }
        } else {
            Long userId = SecurityUtils.getUserId();

            //不能重复校验
            AddPrice addPrice = new AddPrice();
            addPrice.setCreateId(userId);
            addPrice.setPriceType("2");
            if (Objects.nonNull(cmdSupplyId)) {
                addPrice.setRoleType("2");
                addPrice.setSuperId(cmdSupplyId);
            } else {
                addPrice.setRoleType("1");
                addPrice.setSuperId(supplyId);
            }
            List<AddPrice> addPrices = addPriceMapper.selectAddPriceList(addPrice);
            boolean b1 = true;
            if (addPrices.size() > 0) {
                b1 = false;
                addPrice = addPrices.get(0);
            }
            addPrice.setRise(rise);
            addPrice.setType(type);
            addPrice.setPriceType("2");
            if (b1) {
                addPriceMapper.insertAddPrice(addPrice);
            } else {
                addPriceMapper.updateAddPrice(addPrice);
            }


//            retailAccount.setCreateId(userId);
//            List<RetailAccount> retailAccountList = retailAccountMapper.selectRetailAccountList(retailAccount);
//
//            List<Long> ids = retailAccountList.stream().map(retail -> retail.getAccountId()).collect(Collectors
//            .toList());
//
//            AccountInfo accountParams = new AccountInfo();
//            accountParams.setIds(ids);
//
//            if (Objects.nonNull(cmdSupplyId)){
//                accountParams.setCmdSupplyId(cmdSupplyId);
//            }else {
//                List<Long> suplyIds = Arrays.asList(supplyIds).stream().map(s -> Long.parseLong(s)).collect
//                (Collectors.toList());
//                accountParams.setSupplyIdAddr(suplyIds);
//            }
//            List<RetailAccount> bacthRetail = new ArrayList<>();
//            List<AccountInfo> accountInfoList = accountInfoMapper.selectAccountInfoList(accountParams);
//            for (AccountInfo accountInfo : accountInfoList) {
//                for (RetailAccount account : retailAccountList) {
//                    if (Objects.equals(account.getAccountId(),accountInfo.getId())) {
//                        BigDecimal newPrice = account.getPrice().multiply(b).divide(new BigDecimal(100));
//                        account.setNewPrice(newPrice);
//                        account.setRise(rise);
////                        this.retailAccountMapper.updateRetailAccount(account);
//                        bacthRetail.add(account);
//                    }
//                }
//            }
//            this.retailAccountMapper.bacthUpdata(bacthRetail);
        }
    }

    @Override
    public Map<String, Object> bacthAddPriceDetails(Map<String, Object> params) {

        Long userId = SecurityUtils.getUserId();

        Long supplyId = MapUtils.getLong(params, "supplyId");
        Long cmdSupplyId = MapUtils.getLong(params, "cmdSupplyId");

        AddPrice addPrice = new AddPrice();
        addPrice.setCreateId(userId);
        addPrice.setPriceType("2");
        if (Objects.nonNull(cmdSupplyId)) {
            addPrice.setRoleType("2");
            addPrice.setSuperId(cmdSupplyId);
        } else {
            addPrice.setRoleType("1");
            addPrice.setSuperId(supplyId);
        }
        Map<String, Object> result = new HashMap<>();
        List<AddPrice> addPrices = addPriceMapper.selectAddPriceList(addPrice);
        if (!CollectionUtils.isEmpty(addPrices)) {
            AddPrice ap = addPrices.get(0);
            result.put("jianjiajia", ap.getType());
            result.put("rise", ap.getRise());
        }
        return result;
    }

    @Override
    public List<GameInfo> getGameType(Long userId, Integer type) {

        if (Objects.isNull(userId)) {
            userId = SecurityUtils.getUserId();
        }

        RetailAddPrice addPrice = new RetailAddPrice();
        addPrice.setCreateId(userId);
        List<RetailAddPrice> addPrices = retailAddPriceMapper.selectRetailAddPriceList(addPrice);
        Set<String> gameList = new HashSet<>();
        for (RetailAddPrice price : addPrices) {
            String gtype = price.getGametype();
            if (StringUtils.isNotEmpty(gtype)) {
                String[] split = gtype.split(",");
                for (String s : split) {
                    gameList.add(s);
                }
            }
        }
        List<GameInfo> gameInfos = gameInfoMapper.selectGameInfoByIds(gameList, type);

        return gameInfos;
    }
}
