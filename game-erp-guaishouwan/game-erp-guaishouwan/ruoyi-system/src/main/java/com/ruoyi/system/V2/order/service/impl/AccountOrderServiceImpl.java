package com.ruoyi.system.V2.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.SalerStatus;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoMapper;
import com.ruoyi.system.V2.goodInfo.service.IGoodsInfoService;
import com.ruoyi.system.V2.inter.service.IApiTaskPoolService;
import com.ruoyi.system.V2.order.mapper.AccountOrderMapper;
import com.ruoyi.system.V2.order.mapper.SaleImgMapper;
import com.ruoyi.system.V2.order.service.IAccountOrderService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.AccountOrder;
import com.ruoyi.system.model.domain.AccountRemake;
import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.model.domain.SaleImg;
import com.ruoyi.system.service.ISysUserService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 销售订单Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class AccountOrderServiceImpl implements IAccountOrderService
{
    @Resource
    private AccountOrderMapper accountOrderMapper;
    @Resource
    private GoodsInfoMapper goodsInfoMapper;
    @Resource
    private IGoodsInfoService goodsInfoService;

    @Autowired
    private IApiTaskPoolService apiTaskPoolService;

    @Resource
    private SaleImgMapper saleImgMapper;

    @Resource
    private SysUserMapper sysUserMapper;
    @Autowired
    private ISysUserService sysUserService;


    @Override
    public AccountOrder getInfoByAccount(Long id) {
        AccountOrder accountOrder = accountOrderMapper.selectAccountOrderByAccountId(id);
        return getOrder(accountOrder);
    }

    /**
     * 查询销售订单
     * 
     * @param id 销售订单主键
     * @return 销售订单
     */
    @Override
    public AccountOrder selectAccountOrderById(Long id)
    {
        AccountOrder accountOrder = accountOrderMapper.selectAccountOrderById(id);

        return getOrder(accountOrder);
    }

    private AccountOrder getOrder(AccountOrder accountOrder){
        SaleImg saleImg = new SaleImg();
        saleImg.setType("1");
        saleImg.setAccountId(accountOrder.getId());
        List<SaleImg> saleImgs = saleImgMapper.selectSaleImgList(saleImg);
        List<String> collect = saleImgs.stream().map(s -> s.getImgUrl()).collect(Collectors.toList());
        accountOrder.setJietuList(collect);
        saleImg.setType("2");
        saleImgs = saleImgMapper.selectSaleImgList(saleImg);
        collect = saleImgs.stream().map(s -> s.getImgUrl()).collect(Collectors.toList());
        accountOrder.setOtherList(collect);

        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(accountOrder.getAccountId());
        if (Objects.nonNull(goodsInfo)){

            accountOrder.setDes(goodsInfo.getDes());
        }
        return accountOrder;
    }

    /**
     * 查询销售订单列表
     * 
     * @param accountOrder 销售订单
     * @return 销售订单
     */
    @Override
    public List<AccountOrder> selectAccountOrderList(AccountOrder accountOrder)
    {
        Long userId = SecurityUtils.getUserId();
        accountOrder.setCreateBy(userId.toString());
        if (Objects.equals(1l,userId)){
            accountOrder.setCreateBy(null);
        }
        if (Objects.equals(Constants.ALL,accountOrder.getDataType())){
            accountOrder.setCreateBy(null);
        }

        List<AccountOrder> accountOrders = accountOrderMapper.selectAccountOrderList(accountOrder);
        for (AccountOrder order : accountOrders) {
            SysUser sysUser = sysUserMapper.selectUserById(order.getSuperId());
            if (Objects.nonNull(sysUser)){
                order.setSuperName(sysUser.getNickName());
            }

            if (!Objects.equals(Constants.ALL,accountOrder.getDataType())){
                accountOrder.setSupplyprice(null);
            }
        }
        return accountOrders;
    }

    /**
     * 新增销售订单
     * 
     * @param accountOrder 销售订单
     * @return 结果
     */
    @Override
    public int insertAccountOrder(AccountOrder accountOrder)
    {
        accountOrder.setCreateTime(DateUtils.getNowDate());
        return accountOrderMapper.insertAccountOrder(accountOrder);
    }

    /**
     * 修改销售订单
     * 
     * @param accountOrder 销售订单
     * @return 结果
     */
    @Override
    public int updateAccountOrder(AccountOrder accountOrder)
    {
        accountOrder.setUpdateTime(DateUtils.getNowDate());
        return accountOrderMapper.updateAccountOrder(accountOrder);
    }

    /**
     * 批量删除销售订单
     * 
     * @param ids 需要删除的销售订单主键
     * @return 结果
     */
    @Override
    public int deleteAccountOrderByIds(Long[] ids)
    {
        return accountOrderMapper.deleteAccountOrderByIds(ids);
    }

    /**
     * 删除销售订单信息
     * 
     * @param id 销售订单主键
     * @return 结果
     */
    @Override
    public int deleteAccountOrderById(Long id)
    {
        return accountOrderMapper.deleteAccountOrderById(id);
    }

    @Override
    @Transactional
    public void submitOrder(JSONObject params) {

        LoginUser loginUser = SecurityUtils.getLoginUser();

        //终端编号
        Long id = params.getLong("id");

        //手机号
        String tel = params.getString("tel");
        //备注
        String remake = params.getString("remark");
        //资料
        String ziliao = params.getString("ziliao");
        String shopprice = params.getString("realPrice");
        String price = params.getString("price");

        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(id);
        if (Objects.isNull(goodsInfo)){
            throw new GlobalException("未查询到此账号");
        }
        goodsInfo.setSaleState(2);
        goodsInfoMapper.updateGoodsInfo(goodsInfo);

        if (Objects.equals(SalerStatus.unsold.getCode(),goodsInfo.getSaleState())) {
            throw new GlobalException("账号交易中或已下架，不能提号");
        }

        AccountOrder accountOrder = new AccountOrder();
        accountOrder.setAccountId(goodsInfo.getId());
        accountOrder.setOrderStatus("1");
        List<AccountOrder> accountOrders = this.accountOrderMapper.selectAccountOrderList(accountOrder);
        if (!CollectionUtils.isEmpty(accountOrders)){
            throw new GlobalException("此账号订单已存在");
        }
        accountOrder.setCreateTime(new Date());
//        String orderCode = OrderNoCreate.randomOrderCode();
        accountOrder.setAccountId(goodsInfo.getId());
//        accountOrder.setRealName(accountInfo.getLevel());
        accountOrder.setType(goodsInfo.getGameName());
        accountOrder.setOrderStatus("1");
        accountOrder.setCreateBy(loginUser.getUserId().toString());
        accountOrder.setOrderNo(goodsInfo.getThirdId());
        accountOrder.setSuperId(goodsInfo.getCreateId());
        accountOrder.setSupplyprice(goodsInfo.getPrice());
        accountOrder.setPrice(new BigDecimal(price));
        accountOrder.setShopprice(new BigDecimal(shopprice));

        SysUser sysUser = sysUserMapper.selectUserById(goodsInfo.getCreateId());
        if (Objects.isNull(sysUser)){
            accountOrder.setSuperName(sysUser.getNickName());
        }
        accountOrder.setSaleId(loginUser.getUserId());
        accountOrder.setSaleName(loginUser.getUsername());
        accountOrder.setTel(tel);
        String regional = goodsInfo.getSystemName()+goodsInfo.getCarrierName();
        accountOrder.setRegional(regional);
        accountOrder.setRemark(remake);
        accountOrder.setZiliao(ziliao);
        accountOrder.setRealPrice(new BigDecimal(params.getString("realPrice")));
        this.accountOrderMapper.insertAccountOrder(accountOrder);
    }

    @Override
    @Transactional
    public JSONObject superSubmit(Map<String,Object> params) {


        Long id = MapUtils.getLong(params, "id");
        if (Objects.isNull(id)){
            throw new GlobalException("ID为空");
        }

        AccountOrder accountOrder = this.accountOrderMapper.selectAccountOrderById(id);
        if (Objects.isNull(accountOrder)){
            throw new GlobalException("未查到此订单");
        }
        if (!Objects.equals("1",accountOrder.getOrderStatus())){
            throw new GlobalException("此订单已完成或已销毁");
        }

        AccountRemake accountRemake = new AccountRemake();
        accountRemake.setAccountId(accountOrder.getAccountId());
        accountRemake.setOrderNo(accountOrder.getId().toString());


        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(accountOrder.getAccountId());
        if (Objects.isNull(goodsInfo)){
            throw new GlobalException("此账号有误");
        }

        JSONObject result = new JSONObject();
        result.put("sinNum",goodsInfo.getThirdId());
        result.put("xyrId",accountOrder.getAccountId());
        result.put("type",accountOrder.getType());
        String regional = goodsInfo.getSystemName()+goodsInfo.getCarrierName() + goodsInfo.getGroupName()+goodsInfo.getSystemName();
        result.put("regional",regional);
//        result.put("goodUrl","http://ikbh.top/show.html?ZHID=" + goodsInfo.getId());
        result.put("price",goodsInfo.getPrice());
        result.put("tel",accountOrder.getTel());
        result.put("remark",accountOrder.getRemark());
        result.put("ziliao",accountOrder.getZiliao());
        result.put("realName",accountOrder.getRealName());

        return result;
    }

    @Override
    @Transactional
    public void superSubmitRemake(JSONObject params) {

        Long orderId = params.getLong("id");

        AccountOrder accountOrder = this.accountOrderMapper.selectAccountOrderById(orderId);
        if (Objects.isNull(accountOrder)){
            throw new GlobalException("未查到此订单");
        }
        if (!Objects.equals("1",accountOrder.getOrderStatus())){
            throw new GlobalException("此订单已完成或已销毁");
        }

//        List<AccountRemake> accountRemakes = accountRemakeMapper.selectAccountRemakeList(accountRemake);
//        if (!CollectionUtils.isEmpty(accountRemakes)){
//            throw new GlobalException("销售方暂未提交相关资料");
//        }

        accountOrder.setAccount(params.getString("account"));
        accountOrder.setPwd(params.getString("pwd"));
        accountOrder.setSupyremake(params.getString("supyremake"));
        accountOrderMapper.updateAccountOrder(accountOrder);
        String jietuImg = params.getString("jietuImg");

        saleImgMapper.deleteSaleImgByAccountId(accountOrder.getId());

        if (StringUtils.isNotEmpty(jietuImg)){
            String[] split = jietuImg.split(",");
            for (String s : split) {
                SaleImg saleImg = new SaleImg();
                saleImg.setType("1");
                saleImg.setImgUrl(s);
                saleImg.setAccountId(accountOrder.getId());
                saleImgMapper.insertSaleImg(saleImg);
            }
        }

        String otherImg = params.getString("otherImg");
        if (StringUtils.isNotEmpty(otherImg)){
            String[] split = otherImg.split(",");
            for (String s : split) {
                SaleImg saleImg = new SaleImg();
                saleImg.setType("2");
                saleImg.setImgUrl(s);
                saleImg.setAccountId(accountOrder.getId());
                saleImgMapper.insertSaleImg(saleImg);
            }
        }
    }

    @Override
    @Transactional
    public void cancelOrder(Map<String,Object> params) {
        Long accountId = MapUtils.getLong(params, "accountId");
        String msg = MapUtils.getString(params, "msg");
        if (StringUtils.isEmpty(msg)){
            throw new GlobalException("请输入原因");
        }
        AccountOrder accountOrder = accountOrderMapper.selectAccountOrderByAccountId(accountId);
        if (Objects.isNull(accountOrder)){
            throw new GlobalException("未找到此订单");
        }
        accountOrder.setOrderStatus("3");
        accountOrder.setErrmsg(msg);
        accountOrderMapper.updateAccountOrder(accountOrder);

        GoodsInfo goodsInfo = goodsInfoMapper.selectGoodsInfoById(accountId);
        goodsInfo.setSaleState(5);
        if (Objects.isNull(goodsInfo)){
            throw new GlobalException("未找到此账号");
        }
        goodsInfo.setNote(msg);
        goodsInfo.setSaletime(new Date());
        goodsInfoMapper.updateGoodsInfo(goodsInfo);
        //接口下架
        apiTaskPoolService.insertBacth(goodsInfo.getId(), null,"del", goodsInfo.getCreateId());
    }
}
