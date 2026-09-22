package com.ruoyi.system.V2.retail.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.RetailAddPrice;
import com.ruoyi.system.V2.retail.mapper.RetailAddPriceMapper;
import com.ruoyi.system.model.service.IAccountInfoService;
import com.ruoyi.system.V2.retail.service.IRetailAddPriceService;
import com.ruoyi.system.service.ISysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 分销加价Service业务层处理
 *
 * @author lz
 * @date 2022-09-01
 */
@Service
public class RetailAddPriceServiceImpl implements IRetailAddPriceService {
    @Resource
    private RetailAddPriceMapper retailAddPriceMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Autowired
    private IAccountInfoService accountInfoService;

    @Autowired
    private ISysUserService userService;
    /**
     * 查询分销加价
     *
     * @param id 分销加价主键
     * @return 分销加价
     */
    @Override
    public RetailAddPrice selectRetailAddPriceById(Long id) {
        RetailAddPrice retailAddPrice = retailAddPriceMapper.selectRetailAddPriceById(id);
        String superId = retailAddPrice.getSuperId();
        if (Objects.nonNull(superId)) {
            String[] split = superId.split(",");
            retailAddPrice.setSupplyIdAddr(Arrays.asList(split));
        }
        return retailAddPrice;
    }

    /**
     * 查询分销加价列表
     *
     * @param retailAddPrice 分销加价
     * @return 分销加价
     */
    @Override
    public List<RetailAddPrice> selectRetailAddPriceList(RetailAddPrice retailAddPrice) {
        Long userId = SecurityUtils.getUserId();
        retailAddPrice.setCreateId(userId);
        List<RetailAddPrice> retailAddPrices = retailAddPriceMapper.selectRetailAddPriceList(retailAddPrice);

//        List<JSONObject> jsonObjects = accountInfoService.supplyIdList();

        for (RetailAddPrice addPrice : retailAddPrices) {

            if (Objects.nonNull(addPrice.getSuperId())){
                String superId = addPrice.getSuperId();
                if (Objects.nonNull(superId)) {
                    Long aLong = Long.parseLong(superId);
                    SysUser sysUser = sysUserMapper.queryById(aLong);
                    if (Objects.nonNull(sysUser)) {
                        addPrice.setSuperId(sysUser.getUserName());
                    }
                }
            }
            Long createId = addPrice.getCreateId();
            SysUser sysUser = sysUserMapper.selectUserById(createId);
            if (Objects.nonNull(sysUser)) {
                addPrice.setCreateName(sysUser.getUserName());
            }


        }
        return retailAddPrices;
    }

    /**
     * 新增分销加价
     *
     * @param retailAddPrice 分销加价
     * @return 结果
     */
    @Override
    @Transactional
    public int insertRetailAddPrice(RetailAddPrice retailAddPrice) {
        cover(retailAddPrice);
        String gametype = retailAddPrice.getGametype();
        List<JSONObject> supplyIdAddrJson = retailAddPrice.getSupplyIdAddrJson();
        List<RetailAddPrice> retailAddPriceList = new ArrayList<>();
        retailAddPriceMapper.deleteRetailAddPriceByCeateId(SecurityUtils.getUserId());
        for (JSONObject jsonObject : supplyIdAddrJson) {
            RetailAddPrice price = new RetailAddPrice();
            String superId = jsonObject.getString("userId");
            if (StringUtil.isEmpty(superId)){
                throw new GlobalException("请选择供货商");
            }
            price.setSuperId(superId);
            Long rrise = jsonObject.getLong("rrise");
            if (Objects.isNull(rrise)){
                throw new GlobalException("加价比例为空");
            }
            price.setRrise(rrise);
            price.setCreateId(SecurityUtils.getUserId());
            price.setCreateTime(DateUtils.getNowDate());
            price.setGametype(gametype);
            retailAddPriceList.add(price);
        }
        retailAddPriceMapper.bacthInsertRetailAddPrice(retailAddPriceList);
        return 1;
    }

    /**
     * 修改分销加价
     *
     * @param retailAddPrice 分销加价
     * @return 结果
     */
    @Override
    public int updateRetailAddPrice(RetailAddPrice retailAddPrice) {
        retailAddPrice.setUpdateTime(DateUtils.getNowDate());
        retailAddPrice.setUpdateId(SecurityUtils.getUserId());
        cover(retailAddPrice);
        return retailAddPriceMapper.updateRetailAddPrice(retailAddPrice);
    }


    private void cover(RetailAddPrice retailAddPrice) {
        String gametype = retailAddPrice.getGametype();
        if (StringUtil.isEmpty(gametype)){
            throw new GlobalException("请选择游戏");
        }

        List<JSONObject> supplyIdAddr = retailAddPrice.getSupplyIdAddrJson();
        if (CollectionUtils.isEmpty(supplyIdAddr)){
            throw new GlobalException("请选择供货商");
        }
    }

    /**
     * 批量删除分销加价
     *
     * @param ids 需要删除的分销加价主键
     * @return 结果
     */
    @Override
    public int deleteRetailAddPriceByIds(Long[] ids) {
        return retailAddPriceMapper.deleteRetailAddPriceByIds(ids);
    }

    /**
     * 删除分销加价信息
     *
     * @param id 分销加价主键
     * @return 结果
     */
    @Override
    public int deleteRetailAddPriceById(Long id) {
        return retailAddPriceMapper.deleteRetailAddPriceById(id);
    }

    @Override
    public List<String> noAddRetailSuppply() {

        //获取供货商列表
        //获取已新增的列表
        //已新增的去掉
        List<SysUser> supplyCustomer = userService.getUserByRoleCode("supplyCustomer");
        RetailAddPrice retailAddPrice = new RetailAddPrice();
        retailAddPrice.setCreateId(SecurityUtils.getUserId());
        List<RetailAddPrice> retailAddPrices = retailAddPriceMapper.selectRetailAddPriceList(retailAddPrice);
        for (SysUser sysUser : supplyCustomer) {
            for (RetailAddPrice retail : retailAddPrices) {
                if (Objects.equals(sysUser.getUserId(),retail.getSuperId())){
                    supplyCustomer.remove(sysUser);
                }
            }
        }
        List<String> result = supplyCustomer.stream().map(s -> s.getUserName()).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<Map<String, Object>> disrank() {

        List<Map<String, Object>> result = new ArrayList<>();


        List<SysUser> supplyCustomer = userService.getSupplyUser();
        if (!CollectionUtils.isEmpty(supplyCustomer)){
            for (int i=0;i<supplyCustomer.size();i++) {
                List<Map<String,Object>> gameTypes = accountInfoService.onGameType(supplyCustomer.get(i).getUserId());
                StringBuffer buffer = new StringBuffer();
                for (Map<String, Object> gameType : gameTypes) {
                    buffer.append(MapUtils.getString(gameType,"type") + "--" + MapUtils.getString(gameType,"ctt") + "||");
                }
                if (CollectionUtils.isEmpty(gameTypes)){
                    supplyCustomer.remove(i);
                }else {
                    Map<String,Object> resultMap = new HashMap<>();
                    resultMap.put("superName",supplyCustomer.get(i).getUserName());
                    resultMap.put("discount",supplyCustomer.get(i).getDiscountt());
                    resultMap.put("game",buffer.toString());
                    result.add(resultMap);
                }
            }
        }
        return result;
    }

    @Override
    public JSONObject show() {
        JSONObject result = new JSONObject();
        RetailAddPrice retailAddPrice = new RetailAddPrice();
        retailAddPrice.setCreateId(SecurityUtils.getUserId());
        List<RetailAddPrice> retailAddPriceList = retailAddPriceMapper.selectRetailAddPriceList(retailAddPrice);
        if (CollectionUtils.isNotEmpty(retailAddPriceList)){
            RetailAddPrice price = retailAddPriceList.get(0);
            result.put("gametype",price.getGametype());
            result.put("retailAddPriceList",retailAddPriceList);
        }else {
            throw new GlobalException("暂未选择供货商，请选择");
        }
        return result;
    }
}
