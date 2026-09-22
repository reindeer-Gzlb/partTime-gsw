package com.ruoyi.system.V2.submitAccount.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.SalerStatus;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.BigDecimalUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.financial.mapper.FinanceMapper;
import com.ruoyi.system.V2.financial.service.IFinanceService;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoMapper;
import com.ruoyi.system.V2.goodInfo.service.IGoodsInfoService;
import com.ruoyi.system.V2.inter.service.IApiTaskPoolService;
import com.ruoyi.system.V2.inter.service.ISysMessageService;
import com.ruoyi.system.V2.operation.service.IOperationHistoryService;
import com.ruoyi.system.V2.order.mapper.AccountOrderMapper;
import com.ruoyi.system.V2.submitAccount.mapper.OrderRecMapper;
import com.ruoyi.system.V2.submitAccount.service.IOrderRecService;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.*;
import com.ruoyi.system.pay.domain.PayRecord;
import com.ruoyi.system.pay.service.IPayRecordService;
import com.ruoyi.system.saleInfo.domain.GoodsSaleInfo;
import com.ruoyi.system.saleInfo.mapper.GoodsSaleInfoMapper;
import com.ruoyi.system.service.ISysUserService;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 销售订单记录Service业务层处理
 *
 * @author ruoyi
 * @date 2022-10-28
 */
@Service
public class OrderRecServiceImpl implements IOrderRecService {
    @Resource
    private OrderRecMapper orderRecMapper;
    @Resource
    private SysUserMapper sysUserMapper;
//    @Autowired
//    private AccountInfoMapper accountInfoMapper;

    @Resource
    private GoodsInfoMapper goodsInfoMapper;
    @Resource
    private SysDictDataMapper sysDictDataMapper;

    @Resource
    private AccountOrderMapper accountOrderMapper;

    @Resource
    private IApiTaskPoolService apiTaskPoolService;

    @Resource
    private IFinanceService financeService;

    @Resource
    private FinanceMapper financeMapper;

    @Resource
    private ISysMessageService sysMessageService;

    @Autowired
    @Lazy
    private IPayRecordService payRecordService;

    @Autowired
    private IGoodsInfoService goodsInfoService;

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private IOperationHistoryService operationHistoryService;
    @Autowired
    private GoodsSaleInfoMapper goodsSaleInfoMapper;
    @Value("${desc.title}")
    private String descTitle;

    /**
     * 查询销售订单记录
     *
     * @param id 销售订单记录主键
     * @return 销售订单记录
     */
    @Override
    public OrderRec selectOrderRecById(Long id) {
        return orderRecMapper.selectOrderRecById(id);
    }

    /**
     * 查询销售订单记录列表
     *
     * @param orderRec 销售订单记录
     * @return 销售订单记录
     */
    @Override
    public List<OrderRec> selectOrderRecList(OrderRec orderRec) {
//        if (Objects.isNull(orderRec.getCreateId())) {
//            orderRec.setCreateId(SecurityUtils.getUserId());
//        }
        List<OrderRec> orderRecs = orderRecMapper.selectOrderRecList(orderRec);
        for (OrderRec rec : orderRecs) {
            Long createId = rec.getCreateId();
            SysUser sysUser = sysUserMapper.selectUserById(createId);
            if (Objects.nonNull(sysUser)) {
                rec.setCreateName(sysUser.getNickName());
            }
        }
        return orderRecs;
    }

    /**
     * 新增销售订单记录
     *
     * @param orderRec 销售订单记录
     * @return 结果
     */
    @Override
    @Transactional
    public int insertOrderRec(OrderRec orderRec) {
        orderRec.setCreateTime(DateUtils.getNowDate());
        orderRec.setCreateId(SecurityUtils.getUserId());
        //计算利润
        //1、获取供货商
        SysUser sysUser = sysUserMapper.selectUserById(orderRec.getSupllyId());
        //2、供货折扣
        Integer rise = sysUser.getDiscountt();
        //3、计算供货价格
        BigDecimal divide = new BigDecimal(rise).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP);
        BigDecimal bigDecimal = new BigDecimal(orderRec.getSuplyprice()).multiply(divide).setScale(2,
                BigDecimal.ROUND_FLOOR);
        orderRec.setSupplyDiscountPrice(bigDecimal.toPlainString());
        orderRec.setSupplyChannel(sysUser.getNickName());
        orderRec.setSupplyDiscount(new BigDecimal(rise).divide(new BigDecimal(10)).toString());
        //4、获取销售渠道
        SysUser saleUser = sysUserMapper.selectUserById(orderRec.getSalechanelid());
        if (Objects.isNull(saleUser)) {
            throw new GlobalException("未获取到销售渠道,请联系管理员检查");
        }
        if (Objects.isNull(saleUser.getSupydiscount())) {
            throw new GlobalException("销售渠道未设置销售折扣,请联系管理员检查");
        }
        //5、计算销售折扣后价格
        Double supydiscount = saleUser.getSupydiscount();
        BigDecimal riseS =
                new BigDecimal(new Double(saleUser.getSupydiscount() * 10).intValue()).divide(new BigDecimal(100), 2,
                        BigDecimal.ROUND_UP);
        BigDecimal bigDecimalS = new BigDecimal(orderRec.getSalePrice()).multiply(riseS).setScale(2,
                BigDecimal.ROUND_FLOOR);
        BigDecimal subAmount = BigDecimalUtils.sub(orderRec.getSalePrice(), bigDecimalS.toPlainString());
        BigDecimal supydiscountminamount = saleUser.getSupydiscountminamount();
        if (Objects.nonNull(supydiscountminamount)) {
            if (BigDecimalUtils.compare(supydiscountminamount.toPlainString(), subAmount.toPlainString())) {
                String subMinAmout = BigDecimalUtils.sub(orderRec.getSalePrice(),
                        supydiscountminamount.toPlainString(), 2);
                orderRec.setSaleDiscountPrice(subMinAmout);
            } else {
                orderRec.setSaleDiscountPrice(bigDecimalS.toPlainString());
            }
        } else {
            orderRec.setSaleDiscountPrice(bigDecimalS.toPlainString());
        }
        orderRec.setSaleDiscount(supydiscount.toString());
        orderRec.setSaleChannel(saleUser.getNickName());
        orderRec.setSalechanelid(saleUser.getUserId());
        orderRec.setSaleChannelMinamount(supydiscountminamount);

        //6. 计算利润
        String saleDiscountPrice = orderRec.getSaleDiscountPrice();
        String supplyDiscountPrice = orderRec.getSupplyDiscountPrice();
        BigDecimal subtract = new BigDecimal(saleDiscountPrice).subtract(new BigDecimal(supplyDiscountPrice));
        orderRec.setProfits(String.valueOf(subtract));

        //更新状态
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(Long.parseLong(orderRec.getAccountid()));
        goodsInfo.setSaleState(SalerStatus.InTheSale.getCode());
        goodsInfo.setSaletime(new Date());
        goodsInfoMapper.updateGoodsInfo(goodsInfo);

        AccountOrder accountOrder = accountOrderMapper.selectAccountOrderByAccountId(goodsInfo.getId());
        if (Objects.nonNull(accountOrder)) {
            accountOrder.setOrderStatus("2");
            accountOrder.setOrderTime(new Date());
            accountOrderMapper.updateAccountOrder(accountOrder);
        }

        apiTaskPoolService.insertBacth(goodsInfo.getId(), null, "del", goodsInfo.getCreateId());

        int i = orderRecMapper.insertOrderRec(orderRec);
        if (i > 0) {
            //同步到财务
            synFinance(orderRec, goodsInfo);
            //存入系统消息表
            saveSysMessages(goodsInfo, orderRec);
        }
        return i;
    }


    @Override
    @Transactional
    public int insertOrderRecTwo(OrderRec orderRec) {
        orderRec.setCreateTime(DateUtils.getNowDate());
        orderRec.setCreateId(SecurityUtils.getUserId());
        //计算利润
        //1、获取供货商
        SysUser sysUser = sysUserMapper.selectUserById(orderRec.getSupllyId());
        if (Objects.isNull(sysUser)) {
            throw new GlobalException("未获取到供货商,请联系管理员检查");
        }
        if (Objects.isNull(sysUser.getSupydiscount())) {
            throw new GlobalException("供货商未设置供货折扣,请联系管理员检查");
        }
        //2、供货折扣
        Integer rise = sysUser.getDiscountt();
        //3、计算供货价格
        BigDecimal divide = new BigDecimal(rise).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP);
        BigDecimal bigDecimal = new BigDecimal(orderRec.getSuplyprice()).multiply(divide).setScale(2,
                BigDecimal.ROUND_FLOOR);
        orderRec.setSupplyDiscountPrice(bigDecimal.toPlainString());
        orderRec.setSupplyChannel(sysUser.getNickName());
        orderRec.setSupplyDiscount(new BigDecimal(rise).divide(new BigDecimal(10)).toString());
        //4、获取销售渠道

        SysUser saleUser = sysUserMapper.selectUserById(Long.parseLong(orderRec.getSaleid().toString()));
        if (Objects.isNull(saleUser)) {
            throw new GlobalException("未获取到销售渠道");
        }
        Double supydiscount = saleUser.getSupydiscount();
        BigDecimal multiply = new BigDecimal(supydiscount).multiply(new BigDecimal(10));
        BigDecimal riseS = multiply.divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP);
        BigDecimal bigDecimalS = new BigDecimal(orderRec.getSalePrice()).multiply(riseS).setScale(2,
                BigDecimal.ROUND_FLOOR);
        orderRec.setSaleDiscountPrice(bigDecimalS.toPlainString());
        orderRec.setSaleDiscount(supydiscount.toString());
        orderRec.setSaleChannel(saleUser.getNickName());
        orderRec.setIfPay("2");
        orderRec.setSupllypay("2");
        //5、计算销售折扣后价格

        String saleDiscountPrice = orderRec.getSaleDiscountPrice();
        String supplyDiscountPrice = orderRec.getSupplyDiscountPrice();
        BigDecimal subtract = new BigDecimal(saleDiscountPrice).subtract(new BigDecimal(supplyDiscountPrice));
        orderRec.setProfits(String.valueOf(subtract));

        //更新状态
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(Long.parseLong(orderRec.getAccountid()));
        goodsInfo.setSaleState(SalerStatus.InTheSale.getCode());
        goodsInfo.setSaletime(new Date());
        goodsInfo.setSaleid(SecurityUtils.getUserId());
        goodsInfoMapper.updateGoodsInfo(goodsInfo);

        AccountOrder accountOrder = accountOrderMapper.selectAccountOrderByAccountId(goodsInfo.getId());
        if (Objects.nonNull(accountOrder)) {
            accountOrder.setOrderStatus("2");
            accountOrder.setOrderTime(new Date());
            accountOrderMapper.updateAccountOrder(accountOrder);
        }


        apiTaskPoolService.insertBacth(goodsInfo.getId(), null, "del", goodsInfo.getCreateId());
        int i = orderRecMapper.insertOrderRec(orderRec);
        if (i > 0) {
            //同步到财务---供货商
            synFinance(orderRec, goodsInfo);
            //同步到财务---分销商提号收入
            synFinances(orderRec, goodsInfo);
            //同步财务---分销商提号支出
            synFinancess(orderRec, goodsInfo);
            return i;
        } else {
            return i;
        }
    }

    /**
     * 转自售同步财务
     */
    @Override
    @Transactional
    public int selfSale(Finance finance) {
        Long userId = SecurityUtils.getUserId();
        String username = SecurityUtils.getUsername();
        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("local_user_account_config");
        if (CollectionUtils.isNotEmpty(sysDictDataList)) {
            for (SysDictData sysDictData : sysDictDataList) {
                if (StringUtils.isNotEmpty(sysDictData.getDictValue())) {
                    if (StringUtils.equals(sysDictData.getDictValue(), username)) {
                        userId = 106L;
                    }
                }
            }
        }

        SysUser sysUser = sysUserMapper.selectUserById(SecurityUtils.getUserId());
        if (Objects.isNull(finance.getFinance())) {
            throw new GlobalException("财务账单信息填写缺少");
        }
        Finance financeFinance = finance.getFinance();
        financeFinance.setCreateId(userId);
        financeFinance.setRegisteruser(sysUser.getUserName());

        int i = financeService.insertFinance(financeFinance);
        if (i > 0) {
            //更新账号状态
            GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(Long.parseLong(finance.getField4()));
            goodsInfo.setSaleState(3);
            goodsInfo.setSaletime(new Date());
            goodsInfo.setSaleid(SecurityUtils.getUserId());
            goodsInfo.setSelltype("2");
            goodsInfoMapper.updateGoodsInfo(goodsInfo);

            AccountOrder accountOrder = accountOrderMapper.selectAccountOrderByAccountId(goodsInfo.getId());
            if (Objects.nonNull(accountOrder)) {
                accountOrder.setOrderStatus("2");
                accountOrderMapper.updateAccountOrder(accountOrder);
            }
            apiTaskPoolService.insertBacth(goodsInfo.getId(), null, "del", goodsInfo.getCreateId());

            if (Objects.nonNull(finance.getGoodsSaleInfo())) {
                GoodsSaleInfo goodsSaleInfo = finance.getGoodsSaleInfo();
                goodsSaleInfo.setCreateId(SecurityUtils.getUserId());
                goodsSaleInfoMapper.insertGoodsSaleInfo(goodsSaleInfo);
            }

            return i;
        } else {
            return i;
        }
    }

    /**
     * 转自售同步财务
     */
    @Override
    @Transactional
    public int selfSaleFQ(Finance finance) {
        Long userId = SecurityUtils.getUserId();
        String username = SecurityUtils.getUsername();
        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("local_user_account_config");
        if (CollectionUtils.isNotEmpty(sysDictDataList)) {
            for (SysDictData sysDictData : sysDictDataList) {
                if (StringUtils.isNotEmpty(sysDictData.getDictValue())) {
                    if (StringUtils.equals(sysDictData.getDictValue(), username)) {
                        userId = 106L;
                    }
                }
            }
        }

        SysUser sysUser = sysUserMapper.selectUserById(SecurityUtils.getUserId());
        if (Objects.isNull(finance.getFinance())) {
            throw new GlobalException("财务账单信息填写缺少");
        }
        Finance financeFinance = finance.getFinance();
        financeFinance.setCreateId(userId);
        financeFinance.setRegisteruser(sysUser.getUserName());

        int i = financeService.insertFinance(financeFinance);
        if (i > 0) {
            //更新账号状态
            GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(Long.parseLong(finance.getField4()));
            goodsInfo.setSaleState(11);
            goodsInfo.setSaletime(new Date());
            goodsInfo.setSaleid(SecurityUtils.getUserId());
            goodsInfo.setSelltype("2");
            goodsInfoMapper.updateGoodsInfo(goodsInfo);

            AccountOrder accountOrder = accountOrderMapper.selectAccountOrderByAccountId(goodsInfo.getId());
            if (Objects.nonNull(accountOrder)) {
                accountOrder.setOrderStatus("2");
                accountOrderMapper.updateAccountOrder(accountOrder);
            }
            apiTaskPoolService.insertBacth(goodsInfo.getId(), null, "del", goodsInfo.getCreateId());

            if (Objects.nonNull(finance.getGoodsSaleInfo())) {
                GoodsSaleInfo goodsSaleInfo = finance.getGoodsSaleInfo();
                goodsSaleInfo.setCreateId(SecurityUtils.getUserId());
                goodsSaleInfoMapper.insertGoodsSaleInfo(goodsSaleInfo);
            }

            return i;
        } else {
            return i;
        }
    }

    @Override
    public List<Map<String, Object>> subOrder() {
        return accountOrderMapper.subOrder();
    }

    @Override
    public List<Map<String, Object>> sellOrder() {
        return accountOrderMapper.sellOrder();
    }

    @Async
    @Override
    public int payInsertOrderRec(OrderRec orderRec) {
        orderRec.setCreateTime(DateUtils.getNowDate());

//        orderRec.setCreateId(SecurityUtils.getUserId());

        //计算利润
        //1、获取供货商
        SysUser sysUser = sysUserMapper.selectUserById(orderRec.getSupllyId());
        if (Objects.isNull(sysUser)) {
            throw new GlobalException("未获取到供货商,请联系管理员检查");
        }
        if (Objects.isNull(sysUser.getSupydiscount())) {
            throw new GlobalException("供货商未设置供货折扣,请联系管理员检查");
        }
        //2、供货折扣
        Integer rise = sysUser.getDiscountt();
        //3、计算供货价格
        BigDecimal divide = new BigDecimal(rise).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP);
        BigDecimal bigDecimal = new BigDecimal(orderRec.getSuplyprice()).multiply(divide).setScale(2,
                BigDecimal.ROUND_FLOOR);
        orderRec.setSupplyDiscountPrice(bigDecimal.toPlainString());
        orderRec.setSupplyChannel(sysUser.getNickName());
        orderRec.setSupplyDiscount(new BigDecimal(rise).divide(new BigDecimal(10)).toString());
        //4、获取销售渠道
        SysUser saleUser = sysUserMapper.selectUserById(orderRec.getSalechanelid());
        if (Objects.isNull(saleUser)) {
            throw new GlobalException("未获取到销售渠道,请联系管理员检查");
        }
        if (Objects.isNull(saleUser.getSupydiscount())) {
            throw new GlobalException("销售渠道未设置销售折扣,请联系管理员检查");
        }
        //5、计算销售折扣后价格
        Double supydiscount = saleUser.getSupydiscount();
        BigDecimal riseS =
                new BigDecimal(new Double(saleUser.getSupydiscount() * 10).intValue()).divide(new BigDecimal(100), 2,
                        BigDecimal.ROUND_UP);
        BigDecimal bigDecimalS = new BigDecimal(orderRec.getSalePrice()).multiply(riseS).setScale(2,
                BigDecimal.ROUND_FLOOR);
        BigDecimal subAmount = BigDecimalUtils.sub(orderRec.getSalePrice(), bigDecimalS.toPlainString());
        BigDecimal supydiscountminamount = saleUser.getSupydiscountminamount();
        if (Objects.nonNull(supydiscountminamount)) {
            if (BigDecimalUtils.compare(supydiscountminamount.toPlainString(), subAmount.toPlainString())) {
                String subMinAmout = BigDecimalUtils.sub(orderRec.getSalePrice(),
                        supydiscountminamount.toPlainString(), 2);
                orderRec.setSaleDiscountPrice(subMinAmout);
            } else {
                orderRec.setSaleDiscountPrice(bigDecimalS.toPlainString());
            }
        } else {
            orderRec.setSaleDiscountPrice(bigDecimalS.toPlainString());
        }
        orderRec.setSaleDiscount(supydiscount.toString());
        orderRec.setSaleChannel(saleUser.getNickName());
        orderRec.setSalechanelid(saleUser.getUserId());
        orderRec.setSaleChannelMinamount(supydiscountminamount);

        //6. 计算利润
        String saleDiscountPrice = orderRec.getSaleDiscountPrice();
        String supplyDiscountPrice = orderRec.getSupplyDiscountPrice();
        BigDecimal subtract = new BigDecimal(saleDiscountPrice).subtract(new BigDecimal(supplyDiscountPrice));
        orderRec.setProfits(String.valueOf(subtract));

        //更新状态
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(Long.parseLong(orderRec.getAccountid()));
        goodsInfo.setSaleState(SalerStatus.InTheSale.getCode());
        goodsInfo.setSaletime(new Date());
        goodsInfoMapper.updateGoodsInfo(goodsInfo);

        apiTaskPoolService.insertBacth(goodsInfo.getId(), null, "del", goodsInfo.getCreateId());

        int i = orderRecMapper.insertOrderRec(orderRec);
        if (i > 0) {
            //同步到财务
            synFinance(orderRec, goodsInfo);
            //存入系统消息表
            saveSysMessages(goodsInfo, orderRec);
        }
        return i;
    }

    @Override
    @Transactional
    public int tihao(OrderRec orderRec) {
        orderRec.setCreateTime(DateUtils.getNowDate());
        orderRec.setCreateId(SecurityUtils.getUserId());
        //计算利润
        //1、获取供货商
        SysUser sysUser = sysUserMapper.selectUserById(orderRec.getSupllyId());
        //2、供货折扣
        Integer rise = sysUser.getDiscountt();
        //3、计算供货价格
        BigDecimal divide = new BigDecimal(rise).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP);
        BigDecimal bigDecimal = new BigDecimal(orderRec.getSuplyprice()).multiply(divide).setScale(2,
                BigDecimal.ROUND_FLOOR);
        orderRec.setSupplyDiscountPrice(bigDecimal.toPlainString());
        orderRec.setSupplyChannel(sysUser.getNickName());
        orderRec.setSupplyDiscount(new BigDecimal(rise).divide(new BigDecimal(10)).toString());
        //4、获取销售渠道
        SysUser saleUser = sysUserMapper.selectUserById(orderRec.getSalechanelid());
        if (Objects.isNull(saleUser)) {
            throw new GlobalException("未获取到销售渠道,请联系管理员检查");
        }
        if (Objects.isNull(saleUser.getSupydiscount())) {
            throw new GlobalException("销售渠道未设置销售折扣,请联系管理员检查");
        }
        //5、计算销售折扣后价格
        Double supydiscount = saleUser.getSupydiscount();
        BigDecimal riseS =
                new BigDecimal(new Double(saleUser.getSupydiscount() * 10).intValue()).divide(new BigDecimal(100), 2,
                        BigDecimal.ROUND_UP);
        BigDecimal bigDecimalS = new BigDecimal(orderRec.getSalePrice()).multiply(riseS).setScale(2,
                BigDecimal.ROUND_FLOOR);
        BigDecimal subAmount = BigDecimalUtils.sub(orderRec.getSalePrice(), bigDecimalS.toPlainString());
        BigDecimal supydiscountminamount = saleUser.getSupydiscountminamount();
        if (Objects.nonNull(supydiscountminamount)) {
            if (BigDecimalUtils.compare(supydiscountminamount.toPlainString(), subAmount.toPlainString())) {
                String subMinAmout = BigDecimalUtils.sub(orderRec.getSalePrice(),
                        supydiscountminamount.toPlainString(), 2);
                orderRec.setSaleDiscountPrice(subMinAmout);
            } else {
                orderRec.setSaleDiscountPrice(bigDecimalS.toPlainString());
            }
        } else {
            orderRec.setSaleDiscountPrice(bigDecimalS.toPlainString());
        }
        orderRec.setSaleDiscount(supydiscount.toString());
        orderRec.setSaleChannel(saleUser.getNickName());
        orderRec.setSalechanelid(saleUser.getUserId());
        orderRec.setSaleChannelMinamount(supydiscountminamount);

        //6. 计算利润
        String saleDiscountPrice = orderRec.getSaleDiscountPrice();
        String supplyDiscountPrice = orderRec.getSupplyDiscountPrice();
        BigDecimal subtract = new BigDecimal(saleDiscountPrice).subtract(new BigDecimal(supplyDiscountPrice));
        orderRec.setProfits(String.valueOf(subtract));
        orderRec.setOderstatus("3");
        orderRec.setSupllypay("2");
        orderRec.setIfPay("2");
        orderRec.setSuplyid(orderRec.getSupllyId());
        String no = generateNo(descTitle + "xs");
        orderRec.setNo(no);
        int i = orderRecMapper.insertOrderRec(orderRec);
        //更新状态
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(Long.parseLong(orderRec.getAccountid()));
        goodsInfo.setSaleState(SalerStatus.sold.getCode());
        goodsInfo.setOrderRecId(orderRec.getId());
        goodsInfoMapper.updateGoodsInfo(goodsInfo);

        Map<String, Object> params = new HashMap<>();
        params.put("operation", "提号");
        setOper(Long.valueOf(orderRec.getAccountid()), params);


        return i;
    }

    @Override
    @Transactional
    public int finishOrder(OrderRec orderRec) {
        orderRec.setUpdateTime(DateUtils.getNowDate());
        orderRec.setUpdateId(SecurityUtils.getUserId());
        //计算利润
        //1、获取供货商
        SysUser sysUser = sysUserMapper.selectUserById(orderRec.getSupllyId());
        //2、供货折扣
        Integer rise = sysUser.getDiscountt();
        //3、计算供货价格
        BigDecimal divide = new BigDecimal(rise).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP);
        BigDecimal bigDecimal = new BigDecimal(orderRec.getSuplyprice()).multiply(divide).setScale(2,
                BigDecimal.ROUND_FLOOR);
        orderRec.setSupplyDiscountPrice(bigDecimal.toPlainString());
        orderRec.setSupplyChannel(sysUser.getNickName());
        orderRec.setSupplyDiscount(new BigDecimal(rise).divide(new BigDecimal(10)).toString());
        //4、获取销售渠道
        SysUser saleUser = sysUserMapper.selectUserById(orderRec.getSalechanelid());
        if (Objects.isNull(saleUser)) {
            throw new GlobalException("未获取到销售渠道,请联系管理员检查");
        }
        if (Objects.isNull(saleUser.getSupydiscount())) {
            throw new GlobalException("销售渠道未设置销售折扣,请联系管理员检查");
        }
        //5、计算销售折扣后价格
        Double supydiscount = saleUser.getSupydiscount();
        BigDecimal riseS =
                new BigDecimal(new Double(saleUser.getSupydiscount() * 10).intValue()).divide(new BigDecimal(100), 2,
                        BigDecimal.ROUND_UP);
        BigDecimal bigDecimalS = new BigDecimal(orderRec.getSalePrice()).multiply(riseS).setScale(2,
                BigDecimal.ROUND_FLOOR);
        BigDecimal subAmount = BigDecimalUtils.sub(orderRec.getSalePrice(), bigDecimalS.toPlainString());
        BigDecimal supydiscountminamount = saleUser.getSupydiscountminamount();
        if (Objects.nonNull(supydiscountminamount)) {
            if (BigDecimalUtils.compare(supydiscountminamount.toPlainString(), subAmount.toPlainString())) {
                String subMinAmout = BigDecimalUtils.sub(orderRec.getSalePrice(),
                        supydiscountminamount.toPlainString(), 2);
                orderRec.setSaleDiscountPrice(subMinAmout);
            } else {
                orderRec.setSaleDiscountPrice(bigDecimalS.toPlainString());
            }
        } else {
            orderRec.setSaleDiscountPrice(bigDecimalS.toPlainString());
        }
        orderRec.setSaleDiscount(supydiscount.toString());
        orderRec.setSaleChannel(saleUser.getNickName());
        orderRec.setSalechanelid(saleUser.getUserId());
        orderRec.setSaleChannelMinamount(supydiscountminamount);

        //6. 计算利润
        String saleDiscountPrice = orderRec.getSaleDiscountPrice();
        String supplyDiscountPrice = orderRec.getSupplyDiscountPrice();
        BigDecimal subtract = new BigDecimal(saleDiscountPrice).subtract(new BigDecimal(supplyDiscountPrice));
        orderRec.setProfits(String.valueOf(subtract));

        //更新状态
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(Long.parseLong(orderRec.getAccountid()));
        goodsInfo.setSaleState(SalerStatus.InTheSale.getCode());
        goodsInfo.setSaletime(orderRec.getFinishTime());
        goodsInfoMapper.updateGoodsInfo(goodsInfo);

        apiTaskPoolService.insertBacth(goodsInfo.getId(), null, "del", goodsInfo.getCreateId());

        orderRec.setSuplyid(orderRec.getSupllyId());
        orderRec.setOderstatus("1");
        int i = orderRecMapper.updateOrderRec(orderRec);
        if (i > 0) {
            //同步到财务
            synFinance(orderRec, goodsInfo);
            //存入系统消息表
            saveSysMessages(goodsInfo, orderRec);
        }
        return 1;
    }

    @Override
    @Transactional
    public int myOrderCancel(OrderRec orderRec) {
        orderRec.setOderstatus("2");
        orderRec.setCancelTime(DateUtils.getNowDate());
        orderRecMapper.updateOrderRec(orderRec);
        if (StringUtils.isNotNull(orderRec.getPayRecordId())) {
            LambdaQueryWrapper<PayRecord> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.eq(PayRecord::getBusinessId, orderRec.getId());
            queryWrapper.eq(PayRecord::getOrderState, 1);
            List<PayRecord> list = payRecordService.list(queryWrapper);
            if (CollectionUtils.isNotEmpty(list)) {
                for (PayRecord record : list) {
                    payRecordService.closeOrder(record);
                }
            }
        }
        if (StringUtils.isNotNull(orderRec.getAccountid())) {
            GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(Long.valueOf(orderRec.getAccountid()));
            if (StringUtils.isNotNull(goodsInfo)) {
                if (StringUtils.isNotNull(orderRec.getChangeGoodsSaleState())) {
                    goodsInfo.setSaleState(orderRec.getChangeGoodsSaleState());
                } else {
                    goodsInfo.setSaleState(1);

                    Map<String, Object> params = new HashMap<>();
                    params.put("operation", "转自售");
                    setOper(goodsInfo.getId(), params);
                }
                goodsInfoMapper.updateGoodsInfo(goodsInfo);
            }
        }
        return 1;
    }

    @Override
    @Transactional
    public int afterSale(OrderRec orderRec) {
        if (StringUtils.isNotEmpty(orderRec.getProfits()) && StringUtils.isNotNull(orderRec.getRefundAmount())) {
            BigDecimal profits = new BigDecimal(orderRec.getProfits());
            profits = profits.subtract(orderRec.getRefundAmount()).setScale(2);
            orderRec.setProfits(profits.toPlainString());
        }
        if (orderRec.getRefundType() == 1) {
            //仅退款 只保存信息 不改变订单状态
        } else if (orderRec.getRefundType() == 2) {
            orderRec.setOderstatus("5");
            //退款 + 商品上架
            if (StringUtils.isNotNull(orderRec.getAccountid())) {
                GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(Long.valueOf(orderRec.getAccountid()));
                if (StringUtils.isNotNull(goodsInfo)) {
                    if (StringUtils.isNotNull(orderRec.getChangeGoodsSaleState())) {
                        goodsInfo.setSaleState(orderRec.getChangeGoodsSaleState());
                    } else {
                        goodsInfo.setSaleState(1);
                    }
                    goodsInfoMapper.updateGoodsInfo(goodsInfo);
                }
            }
        } else {
            throw new GlobalException("不支持的退款类型");
        }
        orderRecMapper.updateOrderRec(orderRec);
        return 1;
    }

    /**
     * 修改销售订单记录
     *
     * @param orderRec 销售订单记录
     * @return 结果
     */
    @Override
    public int updateOrderRec(OrderRec orderRec) {
        orderRec.setUpdateTime(DateUtils.getNowDate());
        orderRec.setUpdateId(SecurityUtils.getUserId());
        String saleDiscountPrice = orderRec.getSaleDiscountPrice();
        String supplyDiscountPrice = orderRec.getSupplyDiscountPrice();
        BigDecimal subtract = new BigDecimal(saleDiscountPrice).subtract(new BigDecimal(supplyDiscountPrice));
        orderRec.setProfits(String.valueOf(subtract));
        return orderRecMapper.updateOrderRec(orderRec);
    }

    /**
     * 批量删除销售订单记录
     *
     * @param ids 需要删除的销售订单记录主键
     * @return 结果
     */
    @Override
    public int deleteOrderRecByIds(Long[] ids) {
        return orderRecMapper.deleteOrderRecByIds(ids);
    }

    /**
     * 删除销售订单记录信息
     *
     * @param id 销售订单记录主键
     * @return 结果
     */
    @Override
    public int deleteOrderRecById(Long id) {
        return orderRecMapper.deleteOrderRecById(id);
    }

    @Override
    public Map<String, Object> sumPrice(OrderRec orderRec) {
        orderRec.setOderstatus("1");
        Map<String, Object> result = orderRecMapper.sumPrice(orderRec);
        return result;
    }

    @Override
    public List<Map<String, Object>> createList() {
        List<Map<String, Object>> result = orderRecMapper.createList();
        return result;
    }

    /**
     * 同步到财务
     */
    public int synFinance(OrderRec orderRec, GoodsInfo goodsInfo) {
        Finance finance = new Finance();
        //供货人id
        finance.setCreateId(goodsInfo.getCreateId());
        finance.setCreateTime(orderRec.getCreateTime());
        finance.setRemark(orderRec.getRemake());
        finance.setIotype("2");
        long l = 9;
        finance.setBillType(l);
        //结算金额金额
        finance.setPrice(new BigDecimal(orderRec.getSupplyDiscountPrice()));
        //账号id
        finance.setField1(orderRec.getAccount());
        //利润
        BigDecimal costPrice = goodsInfo.getCostPrice();
        if (Objects.isNull(costPrice)) {
            costPrice = new BigDecimal(0);
        }
        BigDecimal bigDecimal = new BigDecimal(orderRec.getSupplyDiscountPrice()).subtract(costPrice);
        finance.setField2(String.valueOf(bigDecimal));
        //自编号
        finance.setField3(orderRec.getSumNum());
        //平台编号
        finance.setField4(orderRec.getAccountid());
        //游戏类型
        finance.setField5(orderRec.getGames());
        //成本价
        if (goodsInfo.getPrice() != null) {
            finance.setField6(costPrice.toString());
        } else {
            finance.setField6("0");
        }
        //finance.setSelltype("1");
        finance.setRegisteruser(sysUserMapper.selectUserById(SecurityUtils.getUserId()).getUserName());
        BigDecimal price = finance.getPrice();
        int i = 0;
        if (Objects.nonNull(price)) {
            financeMapper.insertFinance(finance);
        }
        GoodsInfo info = goodsInfoMapper.selectGoodsInfoById(goodsInfo.getId());
        info.setSelltype("1");
        goodsInfoMapper.updateGoodsInfo(info);
        return i;
    }

    @Override
    public OrderRec createOrder(OrderRec orderRecReq) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        if (StringUtils.isNull(orderRecReq.getAccountid())) {
            throw new GlobalException("商品编号不能为空");
        }
        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(Long.valueOf(orderRecReq.getAccountid()));
        if (StringUtils.isNull(goodsInfo)) {
            throw new GlobalException("未找到此商品");
        }
        if (goodsInfo.getSaleState() != 1 && goodsInfo.getSaleState() != 2) {
            throw new GlobalException("此商品状态异常");
        }
        if (StringUtils.isNull(orderRecReq.getGoodsAmount())) {
            throw new GlobalException("订单的商品金额不能为空");
        }
        if (StringUtils.isNull(orderRecReq.getSalePrice())) {
            throw new GlobalException("订单的总金额不能为空");
        }
        if (StringUtils.isNotNull(goodsInfo.getOrderRecId())) {
            OrderRec orderRecRes = orderRecMapper.selectOrderRecById(goodsInfo.getOrderRecId());
            if (StringUtils.isNotNull(orderRecRes)) {
                if (StringUtils.equals(orderRecRes.getOderstatus(), "1")
                        || StringUtils.equals(orderRecRes.getOderstatus(), "4")) {
                    throw new GlobalException("已出售");
                }
                if (StringUtils.equals(orderRecRes.getOderstatus(), "3")) {
                    if (user.getUserId().equals(orderRecRes.getSalechanelid())) {
                        throw new GlobalException("待支付");
                    } else {
                        throw new GlobalException("被下单");
                    }
                }
            }
        }

        BigDecimal goodsPrice = addPirce(goodsInfo);
        BigDecimal guaranteeAmount;
        if (orderRecReq.getGuaranteeType() == 1) {
            guaranteeAmount = goodsPrice.multiply(new BigDecimal("0.1"));
            if (guaranteeAmount.compareTo(new BigDecimal("50")) < 0) {
                guaranteeAmount = new BigDecimal("50");
            }
        } else if (orderRecReq.getGuaranteeType() == 2) {
            guaranteeAmount = goodsPrice.multiply(new BigDecimal("0.15"));
            if (guaranteeAmount.compareTo(new BigDecimal("100")) < 0) {
                guaranteeAmount = new BigDecimal("100");
            }
        } else if (orderRecReq.getGuaranteeType() == 3) {
            guaranteeAmount = new BigDecimal("0");
        } else {
            throw new GlobalException("不支持的包赔类型");
        }
        guaranteeAmount = guaranteeAmount.setScale(0, BigDecimal.ROUND_HALF_UP);
        BigDecimal salePrice = goodsPrice.add(guaranteeAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
        if (salePrice.compareTo(new BigDecimal(orderRecReq.getSalePrice())) != 0) {
            throw new GlobalException("非法操作,请务必联系客服");
        }
        OrderRec orderRec = new OrderRec();
        orderRec.setSalechanelid(user.getUserId());
        orderRec.setSumNum(goodsInfo.getThirdId());
        orderRec.setGames(goodsInfo.getGameName());
        orderRec.setGameid(goodsInfo.getGameId());
        orderRec.setAccountid(String.valueOf(goodsInfo.getId()));
        orderRec.setAccount(goodsInfo.getAccountNumber());
        if (StringUtils.isNotNull(goodsInfo.getPrice())) {
            orderRec.setSuplyprice(goodsInfo.getPrice().toPlainString());
        }
        orderRec.setSupllyId(goodsInfo.getCreateId());
        orderRec.setOderstatus("3");
        orderRec.setSumNum(goodsInfo.getThirdId());

        orderRec.setGoodsAmount(goodsPrice);
        orderRec.setGuaranteeType(orderRecReq.getGuaranteeType());
        orderRec.setGuaranteeAmount(guaranteeAmount);
        orderRec.setSalePrice(salePrice.toPlainString());
        SysDictData sysDictDataReq = new SysDictData();
        sysDictDataReq.setDictType("payCenter");
        sysDictDataReq.setDictLabel("timeOut");
        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataList(sysDictDataReq);
        int timeOut = 15;
        if (CollectionUtils.isNotEmpty(sysDictDataList) && sysDictDataList.size() == 1) {
            String dictValue = sysDictDataList.get(0).getDictValue();
            try {
                timeOut = Integer.parseInt(dictValue);
            } catch (Exception e) {
                System.out.println("timeOut转换失败");
            }
        }
        orderRec.setCloseTime(DateUtils.addMinutes(DateUtils.getNowDate(), timeOut));
        tihao(orderRec);
        return orderRec;
    }

    BigDecimal addPirce(GoodsInfo goodsInfo) {
        List<GoodsInfo> goodsInfoList = new ArrayList<>();
        goodsInfoList.add(goodsInfo);
        List<SysUser> supplyCustomer = sysUserService.getUserByRoleCode("supplyCustomer");
        goodsInfoService.bacthAddPrice(goodsInfoList, supplyCustomer);
        return goodsInfoList.get(0).getNewPrice();
    }

    /**
     * 同步到财务-----分销商提号收入
     */
    public int synFinances(OrderRec orderRec, GoodsInfo goodsInfo) {
        Finance finance = new Finance();
        AccountOrder a = accountOrderMapper.selectAccountOrderByAccountId(goodsInfo.getId());
        //提号人id
        finance.setCreateId(Long.valueOf(a.getCreateBy()));
        finance.setCreateTime(orderRec.getCreateTime());
        finance.setRemark(orderRec.getRemake());
        finance.setIotype("2");
        long l = 11;
        finance.setBillType(l);
        //结算金额金额
        finance.setPrice(a.getRealPrice());
        //账号
        finance.setField1(orderRec.getAccount());
        //利润
        BigDecimal realPrice = a.getRealPrice();
        if (Objects.isNull(realPrice)) {
            realPrice = new BigDecimal(0);
        }
        BigDecimal bigDecimal = realPrice.subtract(new BigDecimal(orderRec.getSaleDiscountPrice()));
        finance.setField2(String.valueOf(bigDecimal));
        //供货商
        finance.setField3("平台");
        //平台编号
        finance.setField4(orderRec.getAccountid());
        //游戏类型
        finance.setField5(orderRec.getGames());
        //供货金额
        finance.setField6(orderRec.getSaleDiscountPrice());
        //finance.setSelltype("3");
        finance.setRegisteruser(sysUserMapper.selectUserById(SecurityUtils.getUserId()).getUserName());
        int i = financeMapper.insertFinance(finance);
        return i;
    }

    /**
     * 同步财务---分销商提号支出
     */
    public int synFinancess(OrderRec orderRec, GoodsInfo goodsInfo) {
        Finance finance = new Finance();
        AccountOrder a = accountOrderMapper.selectAccountOrderByAccountId(goodsInfo.getId());//bs
        //提号人id
        finance.setCreateId(Long.valueOf(a.getCreateBy()));
        finance.setCreateTime(orderRec.getCreateTime());
        finance.setRemark(orderRec.getRemake());
        finance.setIotype("1");
        long l = 12;
        finance.setBillType(l);
        //结算金额金额
        finance.setPrice(new BigDecimal(orderRec.getSaleDiscountPrice()));
        //账号
        finance.setField1(orderRec.getAccount());
        //利润
        BigDecimal realPrice = a.getRealPrice();
        if (Objects.isNull(realPrice)) {
            realPrice = new BigDecimal(0);
        }
        BigDecimal bigDecimal = realPrice.subtract(new BigDecimal(orderRec.getSaleDiscountPrice()));
        finance.setField2(String.valueOf(bigDecimal));
        //供货商
        finance.setField3("平台");
        //平台编号
        finance.setField4(orderRec.getAccountid());
        //游戏类型
        finance.setField5(orderRec.getGames());
        //销售金额
        finance.setField6(orderRec.getSaleDiscountPrice());
        //finance.setSelltype("3");
        finance.setRegisteruser(sysUserMapper.selectUserById(SecurityUtils.getUserId()).getUserName());
        int i = financeMapper.insertFinance(finance);
        return i;
    }

    void saveSysMessages(GoodsInfo goodsInfo, OrderRec orderRec) {
        SysMessage sysMessage = new SysMessage();
        sysMessage.setSendType(3);
        sysMessage.setSendTypeName("商品交易成功");
        sysMessage.setSendTitle("您售卖的编号为" + goodsInfo.getId() + "的商品 交易成功");
        sysMessage.setSendContent("您售卖的编号为" + goodsInfo.getId() + "的商品 交易成功");
        sendMessage(goodsInfo, sysMessage);
        SysMessage sysMessage1 = new SysMessage();
        sysMessage1.setSendType(4);
        sysMessage1.setSendTypeName("商品交易成功");
        sysMessage1.setSendTitle("您购买的编号为" + goodsInfo.getId() + "的商品 交易成功");
        sysMessage1.setSendContent("您购买的编号为" + goodsInfo.getId() + "的商品 交易成功");
        sendMessageBuy(goodsInfo, sysMessage1, orderRec);
    }

    void sendMessage(GoodsInfo goodsInfo, SysMessage sysMessage) {
        sysMessage.setRelatedDataId(goodsInfo.getId());
        sysMessage.setSenderUserId(1l);
        sysMessage.setSenderUserName("系统");
        sysMessage.setSendTime(DateUtils.getNowDate());
        sysMessage.setReceiveUserId(goodsInfo.getCreateId());
        SysUser sysUser = sysUserMapper.selectUserById(goodsInfo.getCreateId());
        if (StringUtils.isNull(sysUser)) {
            sysMessage.setReceiveUserName(sysUser.getNickName());
        }
        sysMessageService.save(sysMessage);
    }

    void sendMessageBuy(GoodsInfo goodsInfo, SysMessage sysMessage, OrderRec orderRec) {
        sysMessage.setRelatedDataId(goodsInfo.getId());
        sysMessage.setSenderUserId(1l);
        sysMessage.setSenderUserName("系统");
        sysMessage.setSendTime(DateUtils.getNowDate());
        sysMessage.setReceiveUserId(orderRec.getSalechanelid());
        SysUser sysUser = sysUserMapper.selectUserById(orderRec.getSalechanelid());
        if (StringUtils.isNull(sysUser)) {
            sysMessage.setReceiveUserName(sysUser.getNickName());
        }
        sysMessageService.save(sysMessage);
    }

    public synchronized String generateNo(String prefix) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(DateUtils.dateTimeNow("yyyyMMddHHmmssSSS"));
        sb.append(RandomStringUtils.randomNumeric(4));
        return sb.toString();
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
}
