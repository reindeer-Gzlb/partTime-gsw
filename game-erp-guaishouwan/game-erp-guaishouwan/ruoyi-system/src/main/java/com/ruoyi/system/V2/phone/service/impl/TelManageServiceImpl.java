package com.ruoyi.system.V2.phone.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.V2.gameInfo.service.IGameInfoService;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoMapper;
import com.ruoyi.system.model.domain.GameInfo;
import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.model.domain.TelManage;
import com.ruoyi.system.V2.phone.mapper.TelManageMapper;
import com.ruoyi.system.V2.phone.service.ITelManageService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 手机管理Service业务层处理
 *
 * @author ruoyi
 * @date 2023-04-05
 */
@Service
public class TelManageServiceImpl implements ITelManageService {
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Resource
    private TelManageMapper telManageMapper;


    /**
     * 查询手机管理
     *
     * @param id 手机管理主键
     * @return 手机管理
     */
    @Override
    public TelManage selectTelManageById(Long id) {
        return telManageMapper.selectTelManageById(id);
    }

    /**
     * 查询手机管理列表
     *
     * @param telManage 手机管理
     * @return 手机管理
     */
    @Override
    public List<TelManage> selectTelManageList(TelManage telManage) {
        List<TelManage> telManages = telManageMapper.selectTelManageList(telManage);
        if (CollectionUtils.isNotEmpty(telManages)) {
            List<Long> ids = telManages.stream().map(s -> s.getId()).collect(Collectors.toList());
            List<Map<String, Object>> gameDataTotalMap = telManageMapper.selectTelManageListMap(ids);
            for (TelManage manage : telManages) {
                buildPhoneGame(gameDataTotalMap, manage);
            }
        }
        return telManages;
    }
    public void buildPhoneGame(List<Map<String, Object>> gameDataTotalMap, TelManage manage) {
        if (CollectionUtils.isNotEmpty(gameDataTotalMap)) {
            List<Map<String, Object>> mapList = gameDataTotalMap.stream()
                    .filter(s -> Objects.equals(MapUtils.getLong(s, "id"), manage.getId()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(mapList)) {
                return;
            }
            Map<Integer, List<Map<String, Object>>> collect = mapList.stream()
                    .filter(s -> Objects.nonNull(MapUtils.getInteger(s, "carrierType")))
                    .collect(Collectors.groupingBy(s -> MapUtils.getInteger(s, "carrierType")));
            for (Integer carrierType : collect.keySet()) {
                // 如果为空 直接跳过
                if (Objects.isNull(carrierType)) {
                    continue;
                }
                int num = 0;
                // 获取分类
                List<Map<String, Object>> maps = collect.get(carrierType);
                List<HashMap> contentMap = new ArrayList<>();

                for (Map<String, Object> map : maps) {
                    if (!Objects.equals(MapUtils.getLong(map, "id"), manage.getId())) {
                        continue;
                    }
                    // 如果为空 直接跳过
                    String gameName = MapUtils.getString(map, "gameName");
                    if (Objects.isNull(gameName)) {
                        continue;
                    }
                    HashMap<String, Object> content = new HashMap<>();
                    content.put("name", gameName);
                    String accountNumber = MapUtils.getString(map, "accountNumber");
                    List<String> accountList = Arrays.asList(accountNumber.split(","));
                    num += accountList.size(); // 游戏数量
                    content.put("list", accountList);
                    contentMap.add(content);
                }
                if (carrierType == 1) {
                    manage.setTxContent(contentMap);
                    manage.setTxCount(num);
                } else if (carrierType == 2) {
                    manage.setWyContent(contentMap);
                    manage.setWyCount(num);
                } else if (carrierType == 3) {
                    manage.setMhyContent(contentMap);
                    manage.setMhyCount(num);
                } else if (carrierType == 4) {
                    manage.setQtContent(contentMap);
                    manage.setQtCount(num);
                }
            }
        }
    }
    /**
     * 新增手机管理
     *
     * @param telManage 手机管理
     * @return 结果
     */
    @Override
    public int insertTelManage(TelManage telManage) {
        telManage.setCreateTime(DateUtils.getNowDate());
        telManage.setCreateId(SecurityUtils.getUserId());

        TelManage tel = new TelManage();
        tel.setTel(telManage.getTel());
        List<TelManage> telManages = telManageMapper.selectTelManageList(tel);
        if (CollectionUtils.isNotEmpty(telManages)) {
            throw new GlobalException("该手机号已存在");
        }

        return telManageMapper.insertTelManage(telManage);
    }

    /**
     * 修改手机管理
     *
     * @param telManage 手机管理
     * @return 结果
     */
    @Override
    public int updateTelManage(TelManage telManage) {
        telManage.setUpdateTime(DateUtils.getNowDate());
        telManage.setUpdateId(SecurityUtils.getUserId());

        TelManage tel = new TelManage();
        tel.setTel(telManage.getTel());
        List<TelManage> telManages = telManageMapper.selectTelManageList(tel);
        if (CollectionUtils.isNotEmpty(telManages) && telManage.getId() != telManages.get(0).getId()) {
            throw new GlobalException("该手机号已存在");
        }

        return telManageMapper.updateTelManage(telManage);
    }

    /**
     * 批量删除手机管理
     *
     * @param ids 需要删除的手机管理主键
     * @return 结果
     */
    @Override
    public int deleteTelManageByIds(Long[] ids) {
        return telManageMapper.deleteTelManageByIds(ids);
    }

    /**
     * 删除手机管理信息
     *
     * @param id 手机管理主键
     * @return 结果
     */
    @Override
    public int deleteTelManageById(Long id) {
        return telManageMapper.deleteTelManageById(id);
    }

    @Override
    public String countGame(Long id) {

        TelManage telManage = telManageMapper.selectTelManageById(id);
        if (Objects.isNull(telManage)) {
            throw new GlobalException("未查到此手机");
        }


        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setTelid(telManage.getId());
        goodsInfo.setSaleState(1);
        List<GoodsInfo> goodsInfoList = goodsInfoMapper.selectGoodsInfoList(goodsInfo);
        Set<String> collect = goodsInfoList.stream().map(a -> a.getGameName()).collect(Collectors.toSet());
        StringBuffer str = new StringBuffer();
        for (String s : collect) {
            str.append(s + ":");
            for (GoodsInfo info : goodsInfoList) {
                if (Objects.equals(s, info.getGameName())) {
                    str.append(info.getAccountNumber() + ",");
                }
            }
        }
        telManage.setGameStr(str.toString());

        return str.toString();
    }

    @Override
    public List<TelManage> allList(TelManage telManage) {
        List<TelManage> telManages = telManageMapper.selectTelManageList(new TelManage());
        return telManages;
    }
}
