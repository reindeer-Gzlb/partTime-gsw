package com.ruoyi.system.backorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.font.mapper.CheckAccountMapper;
import com.ruoyi.system.V2.font.service.ICheckAccountService;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoMapper;
import com.ruoyi.system.V2.im.service.IMsgGroupInfoService;
import com.ruoyi.system.V2.tool.mapper.BacklistMapper;
import com.ruoyi.system.backorder.domain.BackOrder;
import com.ruoyi.system.backorder.mapper.BackOrderMapper;
import com.ruoyi.system.backorder.service.IBackOrderService;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.Backlist;
import com.ruoyi.system.model.domain.CheckAccount;
import com.ruoyi.system.model.domain.MsgGroupInfo;
import com.ruoyi.system.pay.domain.PayRecord;
import com.ruoyi.system.pay.service.IPayRecordService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 回收订单表(back_order)表服务实现类
 *
 * @author zyy
 * @date 2023-12-02 13:17:27
 */
@Service("backOrderService")
public class BackOrderServiceImpl extends ServiceImpl<BackOrderMapper, BackOrder> implements IBackOrderService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
    @Autowired
    private BacklistMapper backlistMapper;
    @Autowired
    private IMsgGroupInfoService msgGroupInfoService;
    @Autowired
    private IPayRecordService payRecordService;
    @Autowired
    private SysDictDataMapper sysDictDataMapper;
    @Autowired
    private IBackOrderService backOrderService;
    @Autowired
    private ICheckAccountService checkAccountService;
    //    @Autowired
//    private IFinancialStatementService financialStatementService;
    @Autowired
    private CheckAccountMapper checkAccountMapper;
    @Value("${desc.title}")
    private String descTitle;

    @Override
    public BackOrder createOrder(Backlist backlist) {
        LambdaQueryWrapper<BackOrder> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.select(BackOrder::getId);
        queryWrapper.eq(BackOrder::getBacklistId, backlist.getId());
        List<BackOrder> list = backOrderService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new GlobalException("此回收记录已生成回收订单,回收订单id为" + list.get(0).getId());
        }
        BackOrder backOrder = new BackOrder();
        backOrder.setCreateId(backlist.getCreateId());
        backOrder.setBacklistId(backlist.getId());
        backOrder.setGameId(backlist.getGameId());
        backOrder.setGameName(backlist.getType());
        backOrder.setAccountNumber(backlist.getAccount());
        backOrder.setDes(backlist.getDes());
        backOrder.setQufu(backlist.getRegion());
        backOrder.setImg(backlist.getImg());
        backOrder.setIp(backlist.getIp());
        backOrder.setIpAddr(backlist.getIpAddr());
        backOrder.setSellerId(backlist.getCreateId());
        SysUser sysUser = sysUserMapper.queryById(backlist.getCreateId());
        if (StringUtils.isNotNull(sysUser)) {
            backOrder.setSellerName(sysUser.getNickName());
        }
        backOrder.setBuyerId(backlist.getSupplyUserId());
        SysUser sysUser1 = sysUserMapper.queryById(backlist.getSupplyUserId());
        if (StringUtils.isNotNull(sysUser)) {
            backOrder.setBuyerName(sysUser1.getNickName());
        }
        backOrder.setSellerAcceptedAmount(new BigDecimal(backlist.getHsPrice()));
        backOrder.setSellerDepositAmount(backlist.getDepositAmount());
        backOrder.setChangeAmount(backOrder.getSellerAcceptedAmount());
        BigDecimal buyerService = null;
        BigDecimal sellerService = null;
        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("backRatio");
        for (SysDictData sysDictData : sysDictDataList) {
            if (sysDictData.getDictLabel().equals("buyerService")) {
                buyerService = new BigDecimal(sysDictData.getDictValue());
            }
            if (sysDictData.getDictLabel().equals("sellerService")) {
                sellerService = new BigDecimal(sysDictData.getDictValue());
            }
        }
        if (StringUtils.isNull(buyerService) || StringUtils.isNull(sellerService)) {
            throw new GlobalException("买卖双方未配置服务费比例，请联系管理员配置");
        }
        backOrder.setBuyerServiceRatio(buyerService);
        backOrder.setSellerServiceRatio(sellerService);
        backOrder.setOrderStatus(1);
        backOrder.setBuyerPayStatus(1);
        String no = generateNo(descTitle + "hs");
        backOrder.setNo(no);
        //计算金额
        calculatingAmount(backOrder);
        save(backOrder);
        //拉群
        pullGroup(backOrder);
        return backOrder;
    }

    public synchronized String generateNo(String prefix) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(DateUtils.dateTimeNow("yyyyMMddHHmmssSSS"));
        sb.append(RandomStringUtils.randomNumeric(4));
        return sb.toString();
    }

    /**
     * 计算相关金额
     *
     * @param backOrder
     * @return
     */
    public int calculatingAmount(BackOrder backOrder) {

        //商品金额
        BigDecimal changeAmount = backOrder.getChangeAmount();
        //买家平台服务费 商品金额*买家平台服务费比例
        BigDecimal buyerServiceRatio = backOrder.getBuyerServiceRatio().divide(new BigDecimal("100"), 2,
                BigDecimal.ROUND_HALF_UP);
        backOrder.setBuyerServiceAmout(changeAmount.multiply(buyerServiceRatio).setScale(2, BigDecimal.ROUND_HALF_UP));
        //卖家平台服务费 商品金额*卖家平台服务费比例
        BigDecimal sellerServiceRatio = backOrder.getSellerServiceRatio().divide(new BigDecimal("100"), 2,
                BigDecimal.ROUND_HALF_UP);
        backOrder.setSellerServiceAmout(changeAmount.multiply(sellerServiceRatio).setScale(2,
                BigDecimal.ROUND_HALF_UP));

        //后面换成包赔规则实体的查询
        BigDecimal guaranteeAmount = new BigDecimal("0.00");
        BigDecimal guaranteeServiceAmout = new BigDecimal("0.00");
        if (StringUtils.isNotNull(backOrder.getGuaranteeId())) {
            SysUser sysUser = sysUserMapper.selectUserById(backOrder.getGuaranteeId());
            if (StringUtils.isNull(sysUser.getGuaranteeRatio()) || StringUtils.isNull(sysUser.getGuaranteeRoyalty())) {
                throw new GlobalException("该包赔商未配置服务费比例，请联系管理员配置");
            }
            backOrder.setGuaranteeName(sysUser.getNickName());
            backOrder.setSellerGuaranteeRatio(sysUser.getGuaranteeRatio());
            BigDecimal sellerGuaranteeRatio = backOrder.getSellerGuaranteeRatio().divide(new BigDecimal("100"), 2,
                    BigDecimal.ROUND_HALF_UP);
            guaranteeAmount = changeAmount.multiply(sellerGuaranteeRatio).setScale(2,
                    BigDecimal.ROUND_HALF_UP);
            //包赔商平台服务费 包赔服务费*包赔商平台服务费比例
            backOrder.setGuaranteeServiceRatio(sysUser.getGuaranteeRoyalty());
            BigDecimal guaranteeServiceRatio = backOrder.getGuaranteeServiceRatio().divide(new BigDecimal("100"), 2,
                    BigDecimal.ROUND_HALF_UP);
            guaranteeServiceAmout = guaranteeAmount.multiply(guaranteeServiceRatio).setScale(2,
                    BigDecimal.ROUND_HALF_UP);
        }
        //包赔服务费 商品金额*包赔规则实体里的比例
        backOrder.setGuaranteeServiceAmout(guaranteeServiceAmout);

        backOrder.setGuaranteeAmount(guaranteeAmount);

        //买家实付金额(协商后商品金额 + 包赔服务费 + 买家平台服务费)
        backOrder.setBuyerActualAmount(changeAmount.add(backOrder.getGuaranteeAmount()).add(backOrder.getBuyerServiceAmout()));

        //卖家结算金额(协商后商品金额-卖家平台服务费)
        backOrder.setSellerSettlementAmount(changeAmount.subtract(backOrder.getSellerServiceAmout()));
        //包赔商结算金额(包赔服务费-包赔商平台服务费金额)
        backOrder.setGuaranteeSettlementAmount(backOrder.getGuaranteeAmount().subtract(backOrder.getGuaranteeServiceAmout()));

        //平台利润
        BigDecimal profits =
                backOrder.getBuyerServiceAmout().add(backOrder.getSellerServiceAmout()).add(backOrder.getGuaranteeServiceAmout());

        backOrder.setProfits(profits.toPlainString());

        return 1;
    }

    @Override
    public BackOrder pullGroup(BackOrder backOrder) {
        String groupName = "回收订单-" + backOrder.getAccountNumber();
        String description = "本群仅用于账号回收交流,您有什么疑问或者问题都可以在这里询问";
        List<JSONObject> members = new ArrayList<>();
//        backOrder.setCreateGroupUserId(1l);
        //拉群的人
        SysUser user1 = new SysUser();
        if (backOrder.getCreateGroupUserId() == null) {
            user1 = getKefu(backOrder);
        } else {
            user1 = sysUserMapper.selectUserById(backOrder.getCreateGroupUserId());
        }

        //买家
        SysUser user2 = sysUserMapper.selectUserById(backOrder.getBuyerId());
        //卖家
        SysUser user3 = sysUserMapper.selectUserById(backOrder.getSellerId());
        if (StringUtils.isNull(user1)) {
            throw new GlobalException("未找到拉群人");
        }
        if (StringUtils.isEmpty(user1.getHxusername())) {
            throw new GlobalException("拉群人未注册过IM");
        }
        if (StringUtils.isNull(user2)) {
            throw new GlobalException("未找到买家");
        }
        if (StringUtils.isEmpty(user2.getHxusername())) {
            throw new GlobalException("买家未注册过IM");
        }
        if (StringUtils.isNull(user3)) {
            throw new GlobalException("未找到卖家");
        }
        if (StringUtils.isEmpty(user3.getHxusername())) {
            throw new GlobalException("卖家未注册过IM");
        }

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        if (!StringUtils.equals(user1.getHxusername(), user2.getHxusername())) {
            jsonObject.put("hxid", user2.getHxusername());
            jsonObject.put("type", "买家");
            members.add(jsonObject);
        }
        if (!StringUtils.equals(user1.getHxusername(), user3.getHxusername())) {
            jsonObject1.put("hxid", user3.getHxusername());
            jsonObject1.put("type", "卖家");
            members.add(jsonObject1);
        }
        JSONObject createGroupParams = new JSONObject();
        createGroupParams.put("owner", user1.getHxusername());
        createGroupParams.put("groupName", groupName);
        createGroupParams.put("description", description);
        createGroupParams.put("members", members);
        createGroupParams.put("groupType", "3");
        StringBuffer sb = new StringBuffer();
        sb.append("您好，此群仅用于账号");
        sb.append(backOrder.getAccountNumber());
        sb.append("回收交易沟通交流，如有异议请咨询客服，谢谢！");
        JSONObject params = new JSONObject();
        params.put("content", sb.toString());
        params.put("senType", Constants.SENTYPEtext);
        params.put("userId", user1.getUserId());
        backOrder.setServiceUserId(user1.getUserId());
        backOrder.setServiceUserName(user1.getNickName());
        updateById(backOrder);
        return backOrder;
    }

    SysUser getKefu(BackOrder backOrder) {
        SysUser user1 = null;
        CheckAccount checkAccountReq = new CheckAccount();
        checkAccountReq.setType(1);
        checkAccountReq.setState("0");
        checkAccountReq.setGameIdsArr(new String[]{String.valueOf(backOrder.getGameId())});
        List<CheckAccount> checkAccounts = checkAccountMapper.selectCheckAccountNameNotEmptyList(checkAccountReq);
        if (CollectionUtils.isNotEmpty(checkAccounts)) {
            user1 = sysUserMapper.selectUserById(Long.valueOf(checkAccounts.get(0).getCustomer()));
        } else {
            throw new GlobalException("未找到回收客服，请设置回收客服人员。");
        }
        return user1;
    }

    @Override
    @Transactional
    public BackOrder cancelGroup(BackOrder backOrder) {
        LambdaUpdateWrapper<BackOrder> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(BackOrder::getToGroup, null).eq(BackOrder::getId, backOrder.getId());
        update(null, updateWrapper);
        msgGroupInfoService.deleteMsgGroupInfoByIds(new Long[]{backOrder.getMsgGroupInfoId()});
        return backOrder;
    }

    @Override
    @Transactional
    public BackOrder recoveryGroup(BackOrder backOrder) {
        if (StringUtils.isNull(backOrder.getMsgGroupInfoId())) {
            throw new GlobalException("此订单未创建过群组");
        }
        MsgGroupInfo msgGroupInfo = msgGroupInfoService.recoveryGroupInfo(backOrder.getMsgGroupInfoId());
        backOrder.setToGroup(msgGroupInfo.getToGroup());
        updateById(backOrder);
        return backOrder;
    }

    @Override
    public MsgGroupInfo getToGroup(BackOrder backOrder) {
        if (StringUtils.isNull(backOrder.getMsgGroupInfoId())) {
            throw new GlobalException("此订单未创建过群组");
        }
        MsgGroupInfo msgGroupInfo = msgGroupInfoService.selectMsgGroupInfoById(backOrder.getMsgGroupInfoId());
        if (StringUtils.isNull(msgGroupInfo)) {
            throw new GlobalException("未找到此群组信息");
        }
        if (msgGroupInfo.getDel() == 2) {
            LambdaUpdateWrapper<BackOrder> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(BackOrder::getToGroup, null).eq(BackOrder::getId, backOrder.getId());
            update(null, updateWrapper);
        }
        return msgGroupInfo;
    }


    @Override
    public PayRecord payment(BackOrder backOrder, PayRecord payRecordReq) {
//        backlist.setSupplyUserId(payRecordReq.getSupplyUserId());
//        backlist.setDepositAmount(new BigDecimal("0.01"));

        Long guaranteeId = payRecordReq.getGuaranteeId();
        if (StringUtils.isNotNull(guaranteeId)) {
            backOrder.setGuaranteeId(guaranteeId);
            calculatingAmount(backOrder);
            //判断下单金额是否正确
        }
        if (backOrder.getBuyerActualAmount().compareTo(BigDecimal.valueOf(payRecordReq.getPayTotalMoney())) != 0) {
            throw new GlobalException("非法操作,请务必联系客服(手动狗头)");
        }

        PayRecord payRecord = new PayRecord();
        payRecord.setPayWay(payRecordReq.getPayWay());
        payRecord.setPayScene(payRecordReq.getPayScene());
        payRecord.setReturnUrl(payRecordReq.getReturnUrl());
        payRecord.setAmountType(payRecordReq.getAmountType());
        payRecord.setGoodsTitle(payRecordReq.getGoodsTitle());
        payRecord.setQrPayMode(payRecordReq.getQrPayMode());
        payRecord.setQrcodeWidth(payRecordReq.getQrcodeWidth());

        payRecord.setPayTotalMoney(backOrder.getBuyerActualAmount().doubleValue());
        payRecord.setOrderType(7);
        payRecord.setBusinessId(backOrder.getId());
        payRecord.setNo(backOrder.getNo());

        payRecord = payRecordService.payment(payRecord);

        backOrder.setBuyerPayRecordId(payRecord.getId());
        backOrder.setBuyerPayStatus(payRecord.getOrderState());
        updateById(backOrder);
        return payRecord;
    }

    @Override
    public BackOrder sucessOrder(PayRecord payRecord) {
        BackOrder backOrder = getById(payRecord.getBusinessId());
        return changeSome(backOrder, payRecord);
    }

    @Override
    public BackOrder queryOrderById(BackOrder backOrder) {
        PayRecord payRecordReq = new PayRecord();
        payRecordReq.setId(backOrder.getBuyerPayRecordId());
        PayRecord payRecord = payRecordService.queryOrder(payRecordReq);
        return changeSome(backOrder, payRecord);
    }

    @Override
    public BackOrder changeAmount(BackOrder backOrder) {
        calculatingAmount(backOrder);
        updateById(backOrder);
        return backOrder;
    }

    @Override
    @Transactional
    public BackOrder finishOrder(BackOrder backOrder) {
        backOrder.setUpdateId(SecurityUtils.getUserId());
        backOrder.setFinishTime(DateUtils.getNowDate());
        backOrder.setOrderStatus(4);
        //更新状态
        updateById(backOrder);
        Backlist backlist = backlistMapper.selectBacklistById(backOrder.getBacklistId());
        backlist.setStatus(3l);
        backlistMapper.updateBacklist(backlist);
//        //订单结算
//        financialStatementService.settlementBackOrderAuto(backOrder);
        return backOrder;
    }

    @Override
    @Transactional
    public BackOrder cancelOrder(BackOrder backOrder) {
        Long userId = SecurityUtils.getUserId();
        Date nowDate = DateUtils.getNowDate();
        backOrder.setUpdateId(userId);
        backOrder.setCancelTime(nowDate);
        backOrder.setOrderStatus(5);
        if (StringUtils.isNotNull(backOrder.getIsReturn())) {
            if (backOrder.getIsReturn() == 2) {
                backOrder.setSellerDepositState(3);
            } else if (backOrder.getIsReturn() == 1) {
                backOrder.setSellerDepositState(2);
            }
        }
        //更新状态
        updateById(backOrder);
        Backlist backlist = backlistMapper.selectBacklistById(backOrder.getBacklistId());
        backlist.setUpdateId(userId);
        backlist.setUpdateTime(nowDate);
        backlist.setStatus(4l);
        backlistMapper.updateBacklist(backlist);
//        financialStatementService.settlementCancelBackOrder(backOrder);
        return backOrder;
    }

    /**
     * 支付成功后需要改的状态
     *
     * @param backOrder
     * @param payRecord
     * @return
     */
    public BackOrder changeSome(BackOrder backOrder, PayRecord payRecord) {
        if (StringUtils.isNotNull(payRecord) && StringUtils.isNotNull(backOrder)) {
            if (payRecord.getOrderState() == 2) {
                backOrder.setBuyerPayStatus(payRecord.getOrderState());
                backOrder.setBuyerPayRecordId(payRecord.getId());
                backOrder.setPayTime(payRecord.getPayEndTime());
                if (StringUtils.isNotNull(backOrder.getGuaranteeId())) {
                    backOrder.setOrderStatus(2);
                }
                updateById(backOrder);
            }
        }
        return backOrder;
    }
}

