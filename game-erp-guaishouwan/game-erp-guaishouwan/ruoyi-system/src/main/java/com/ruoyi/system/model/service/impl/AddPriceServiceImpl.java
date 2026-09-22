package com.ruoyi.system.model.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.AccountInfo;
import com.ruoyi.system.model.domain.AddPrice;
import com.ruoyi.system.model.mapper.AccountInfoMapper;
import com.ruoyi.system.model.mapper.AddPriceMapper;
import com.ruoyi.system.model.service.IAccountInfoService;
import com.ruoyi.system.model.service.IAddPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

/**
 * 批量加价Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-17
 */
@Service
public class AddPriceServiceImpl implements IAddPriceService
{
    @Resource
    private AddPriceMapper addPriceMapper;

    @Resource
    private IAccountInfoService accountInfoService;

    @Resource
    private SysUserMapper sysUserMapper;
    @Autowired
    private AccountInfoMapper accountInfoMapper;

    /**
     * 查询批量加价
     *
     * @param id 批量加价主键
     * @return 批量加价
     */
    @Override
    public AddPrice selectAddPriceById(Long id)
    {
        return addPriceMapper.selectAddPriceById(id);
    }

    /**
     * 查询批量加价列表
     *
     * @param addPrice 批量加价
     * @return 批量加价
     */
    @Override
    public List<AddPrice> selectAddPriceList(AddPrice addPrice)
    {

//        List<JSONObject> jsonObjects = accountInfoService.supplyIdList();

        addPrice.setPriceType("1");
        List<AddPrice> addPrices = addPriceMapper.selectAddPriceList(addPrice);
        addPrices.stream().forEach(price->{
            if (Objects.nonNull(price.getSuperId())){
                if (Objects.equals(price.getRoleType(),"1")){
                    SysUser sysUser = sysUserMapper.selectUserById(price.getSuperId());
                    price.setSuperName(sysUser.getUserName());
                }
//                if (Objects.equals(price.getRoleType(),"2")){
//                    List<JSONObject> userList = jsonObjects.stream().filter(j -> j.getString("userId").equals(price.getSuperId() + "")).collect(Collectors.toList());
//                    if (!CollectionUtils.isEmpty(userList)){
//                        price.setSuperName(userList.get(0).getString("nickName"));
//                    }
//                }
            }
            if (Objects.nonNull(price.getUpdateId())){
                SysUser sysUser = sysUserMapper.selectUserById(price.getUpdateId());
                price.setUpdateName(sysUser.getUserName());
            }
            if (Objects.nonNull(price.getCreateId())){
                SysUser sysUser = sysUserMapper.selectUserById(price.getCreateId());
                price.setCreateName(sysUser.getUserName());
            }
            if (Objects.equals("1",String.valueOf(price.getType()))){
                price.setTypeName("加价");
            }else {
                price.setTypeName("减价");
            }

            if (Objects.equals("0",price.getIson())){
                price.setIson("不同步");
            }else {
                price.setIson("同步");
            }

            if (Objects.equals("1",String.valueOf(price.getRoleType()))){
                price.setRoleTypeName("自营账号");
            }else {
                price.setRoleTypeName("终端账号");
            }

        });
        return addPrices;
    }

    /**
     * 新增批量加价
     *
     * @param addPrice 批量加价
     * @return 结果
     */
    @Override
    @Transactional
    public int insertAddPrice(AddPrice addPrice)
    {
        addPrice.setCreateTime(DateUtils.getNowDate());
        Long userId = SecurityUtils.getUserId();
        addPrice.setCreateId(userId);

        AddPrice priceParam = new AddPrice();
        priceParam.setSuperId(addPrice.getSuperId());
        priceParam.setRoleType(addPrice.getRoleType());
        priceParam.setPriceType("1");
        List<AddPrice> addPrices = addPriceMapper.selectAddPriceList(priceParam);
        if (!CollectionUtils.isEmpty(addPrices)){
            throw new ServiceException("该供货商已设置");
        }

//        addPice(addPrice);
        addPrice.setPriceType("1");

        return addPriceMapper.insertAddPrice(addPrice);
    }


    private void addPice(AddPrice addPrice){

        Integer rise = addPrice.getRise();
        if (null == addPrice.getRise()) rise = 0;
        BigDecimal b = new BigDecimal(0);
        if (Objects.equals("1",addPrice.getType())){
            b = new BigDecimal(100).add(new BigDecimal(rise));
        }else {
            b = new BigDecimal(100).subtract(new BigDecimal(rise));
        }

        AccountInfo accountInfo = new AccountInfo();

        if (Objects.equals(addPrice.getRoleType(),"1")){
            accountInfo.setCreateId(addPrice.getSuperId());
        }else {
            accountInfo.setCmdSupplyId(addPrice.getSuperId());
            accountInfo.setAccountType("3");
        }
        accountInfo.setSaleState(1);
        List<AccountInfo> accountInfoList = accountInfoMapper.selectAccountInfoList(accountInfo);
        if (!CollectionUtils.isEmpty(accountInfoList)){
            for (AccountInfo info : accountInfoList) {
                BigDecimal newPrice = info.getPrice().multiply(b).divide(new BigDecimal(100));
                info.setNewPrice(newPrice);
                info.setRise(rise);
                this.accountInfoMapper.updateAccountInfo(info);
            }
        }
    }

    /**
     * 修改批量加价
     *
     * @param addPrice 批量加价
     * @return 结果
     */
    @Override
    public int updateAddPrice(AddPrice addPrice)
    {
        addPrice.setUpdateTime(DateUtils.getNowDate());
        Long userId = SecurityUtils.getUserId();
        addPrice.setUpdateId(userId);
//        addPice(addPrice);
        addPrice.setPriceType("1");
        return addPriceMapper.updateAddPrice(addPrice);
    }

    /**
     * 批量删除批量加价
     *
     * @param ids 需要删除的批量加价主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteAddPriceByIds(Long[] ids)
    {
//        Arrays.asList(ids).stream().forEach(id ->{
//            AddPrice addPrice = addPriceMapper.selectAddPriceById(id);
//
//            AccountInfo accountInfo = new AccountInfo();
//
//            if (Objects.equals(addPrice.getRoleType(),"1")){
//                accountInfo.setCreateId(addPrice.getSuperId());
//            }else {
//                accountInfo.setCmdSupplyId(addPrice.getSuperId());
//                accountInfo.setAccountType("3");
//            }
//
//            List<AccountInfo> accountInfoList = accountInfoMapper.selectAccountInfoList(accountInfo);
//            for (AccountInfo info : accountInfoList) {
//                info.setNewPrice(null);
//                info.setRise(null);
//                accountInfoMapper.updatePrice(info);
//            }
//        });
        return addPriceMapper.deleteAddPriceByIds(ids);
    }

    /**
     * 删除批量加价信息
     *
     * @param id 批量加价主键
     * @return 结果
     */
    @Override
    public int deleteAddPriceById(Long id)
    {
        return addPriceMapper.deleteAddPriceById(id);
    }
}
