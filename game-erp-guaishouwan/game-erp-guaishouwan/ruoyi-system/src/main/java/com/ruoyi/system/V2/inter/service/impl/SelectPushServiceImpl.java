package com.ruoyi.system.V2.inter.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.SalePartner;
import com.ruoyi.system.model.domain.SelectPush;
import com.ruoyi.system.V2.inter.mapper.SalePartnerMapper;
import com.ruoyi.system.V2.inter.mapper.SelectPushMapper;
import com.ruoyi.system.V2.inter.service.ISelectPushService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-10-08
 */
@Service
public class SelectPushServiceImpl implements ISelectPushService
{
    @Resource
    private SelectPushMapper selectPushMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SalePartnerMapper salePartnerMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public SelectPush selectSelectPushById(Long id)
    {
        return selectPushMapper.selectSelectPushById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param selectPush 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SelectPush> selectSelectPushList(SelectPush selectPush)
    {
        List<SelectPush> selectPushes = selectPushMapper.selectSelectPushList(selectPush);

        for (SelectPush push : selectPushes) {
            String superId = push.getSuperId();
            if (!StringUtils.isEmpty(superId)){
                push.setSuperId("");
                String[] split = superId.split(",");
                List<String> collect = Arrays.asList(split).stream().filter(s -> StringUtils.isNotEmpty(s)).collect(Collectors.toList());
//                collect.stream().forEach(s ->{
//                    SysUser sysUser = sysUserMapper.selectUserById(Long.parseLong(s));
//                    if (Objects.nonNull(sysUser)){
//                        push.setSuperId(push.getSuperId()+"," + sysUser.getUserName());
//                    }
//                });
                List<SysUser> sysUsers = sysUserMapper.selectUserByIds(collect);
                List<String> nikeNames = sysUsers.stream().map(u -> u.getNickName()).collect(Collectors.toList());
                String join = StringUtils.join(nikeNames, ",");
                push.setSuperId(join);
            }
//            String exids = push.getExids();
//            if (!StringUtils.isEmpty(push.getExids())){
//                push.setExids("");
//                String[] split = exids.split(",");
//                Arrays.asList(split).stream().forEach(s ->{
//                    SysUser sysUser = sysUserMapper.selectUserById(Long.parseLong(s));
//                    if (Objects.nonNull(sysUser)){
//                        push.setExids(push.getExids()+"," + sysUser.getUserName());
//                    }
//                });
//            }
            String config = push.getConfig();
            SalePartner salePartner = salePartnerMapper.selectSalePartnerById(Long.parseLong(config));
            if (Objects.nonNull(salePartner)) {
                push.setConfig(salePartner.getName());
            }
//            if (!StringUtils.isEmpty(config)){
//                push.setConfig("");
//                String[] split = config.split(",");
//                Arrays.asList(split).stream().forEach(s ->{
//                    SalePartner salePartner = salePartnerMapper.selectSalePartnerById(Long.parseLong(s));
//                    if (Objects.nonNull(salePartner)){
//                        push.setConfig(push.getConfig()+"," + salePartner.getName());
//                    }
//                });
//            }

        }
        return selectPushes;
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param selectPush 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSelectPush(SelectPush selectPush)
    {
        selectPush.setCreateTime(DateUtils.getNowDate());
        String superId = selectPush.getSuperId();
        if (StringUtils.isNotEmpty(superId)){
            superId = "," + superId + ",";
            selectPush.setSuperId(superId);
        }
        return selectPushMapper.insertSelectPush(selectPush);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param selectPush 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSelectPush(SelectPush selectPush)
    {
        selectPush.setUpdateTime(DateUtils.getNowDate());
        String superId = selectPush.getSuperId();
        if (StringUtils.isNotEmpty(superId)){
            superId = "," + superId + ",";
        }
        return selectPushMapper.updateSelectPush(selectPush);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteSelectPushByIds(Long[] ids)
    {
        return selectPushMapper.deleteSelectPushByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteSelectPushById(Long id)
    {
        return selectPushMapper.deleteSelectPushById(id);
    }

    @Override
    public  Map<String,Object> getAllSupliar(String config) {

        Map<String,Object> resultMap = new HashMap<>();

        SelectPush push = selectPushMapper.selectSelectPushByConfig(config,"0");
        if (Objects.nonNull(push)){
            String superId = push.getSuperId();
            if (StringUtils.isNotEmpty(superId)){
                String[] split = superId.split(",");
                List<String> userIdList = Arrays.asList(split).stream().filter(s -> StringUtils.isNotEmpty(s)).collect(Collectors.toList());
                SysUser sysUser = new SysUser();
                sysUser.setCreateIds(userIdList);
                List<SysUser> sysUsers = sysUserMapper.selectUserList(sysUser);
                resultMap.put("sysUsers",sysUsers);
                resultMap.put("type",push.getGames());
                return resultMap;
            }
        }
        return new HashMap<>();
    }

    @Override
    @Transactional
    public void addAupliar(SelectPush selectPush) {
        List<String> createIds = selectPush.getCreates();
        if (CollectionUtils.isNotEmpty(createIds)){
            String join = StringUtils.join(createIds, ",");
            join = "," + join + ",";
            SelectPush push = selectPushMapper.selectSelectPushByConfig(selectPush.getConfig(),null);
            if (Objects.nonNull(push)){
                push.setSuperId(join);
                push.setGames(selectPush.getType());
                push.setType("1");
                push.setState("0");
                selectPushMapper.updateSelectPush(push);
            }else {
                SelectPush sp = new SelectPush();
                sp.setConfig(selectPush.getConfig());
                sp.setSuperId(join);
                sp.setGames(selectPush.getType());
                sp.setCreateId(SecurityUtils.getUserId());
                sp.setCreateTime(new Date());
                sp.setState("0");
                sp.setType("1");
                selectPushMapper.insertSelectPush(sp);
            }
        }else {
            SelectPush push = selectPushMapper.selectSelectPushByConfig(selectPush.getConfig(),"0");
            if (Objects.nonNull(push)){
                push.setState("1");
                selectPushMapper.updateSelectPush(push);
            }
        }
    }

    @Override
    public List<SalePartner> selectAllConfigBySup(Long createId) {

        SelectPush selectPush = new SelectPush();
        selectPush.setSuperIdLike(String.valueOf(createId));
        List<SelectPush> selectPushes = selectPushMapper.selectSelectPushList(selectPush);
        if (CollectionUtils.isNotEmpty(selectPushes)){
            List<String> configs = selectPushes.stream().map(s -> s.getConfig()).collect(Collectors.toList());
            SalePartner salePartner = new SalePartner();
            salePartner.setIds(configs);
            List<SalePartner> salePartnerList = salePartnerMapper.selectSalePartnerList(salePartner);
            for (SalePartner partner : salePartnerList) {
                for (SelectPush p : selectPushes) {
                    if (Objects.equals(p.getConfig(),partner.getId().toString())){
                        partner.setGameType(p.getGames());
                    }
                }
            }
            return salePartnerList;
        }
        return new ArrayList<SalePartner>();
    }

    @Override
    @Transactional
    public void editConfigBySup(SelectPush selectPush) {

        List<SalePartner> salePartnerList = selectPush.getSalePartnerList();
        if (CollectionUtils.isNotEmpty(salePartnerList)){
            List<Long> collect = salePartnerList.stream().map(s -> s.getId()).collect(Collectors.toList());
            selectPush.setConfigs(collect);
            List<SelectPush> selectPushes = selectPushMapper.selectSelectPushList(selectPush);
            Long createId = selectPush.getSuplair();
            if (CollectionUtils.isNotEmpty(selectPushes)){
                for (SelectPush push : selectPushes) {
                    if (!push.getSuperId().contains(","+createId+",")){
                        push.setSuperId(push.getSuperId()+createId+",");
                        List<SalePartner> typeSa = salePartnerList.stream().filter(sa -> sa.getId().toString().equals(push.getConfig())).collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(typeSa)){
                            push.setGames(typeSa.get(0).getGameType());
                        }else {
                            throw new GlobalException("请查看销售渠道有未配置的游戏类型");
                        }
                        selectPushMapper.updateSelectPush(push);
                    }else {
                        SelectPush sp = new SelectPush();
                        sp.setSuperId(","+createId+",");
                        sp.setCreateId(SecurityUtils.getUserId());
                        sp.setCreateTime(new Date());
                        sp.setState("0");
                        sp.setType("1");
                        for (SalePartner salePartner : salePartnerList) {
                            sp.setGames(salePartner.getGameType());
                            sp.setConfig(salePartner.getId().toString());
                            selectPushMapper.insertSelectPush(sp);
                        }
                    }
                }
            }else {
                SelectPush sp = new SelectPush();
                sp.setSuperId(","+createId+",");
                sp.setCreateId(SecurityUtils.getUserId());
                sp.setCreateTime(new Date());
                sp.setState("0");
                sp.setType("1");
                for (SalePartner salePartner : salePartnerList) {
                    sp.setGames(salePartner.getGameType());
                    sp.setConfig(salePartner.getId().toString());
                    selectPushMapper.insertSelectPush(sp);
                }
            }

        }else {
            throw new GlobalException("销售渠道不能为空");
        }
    }
}
