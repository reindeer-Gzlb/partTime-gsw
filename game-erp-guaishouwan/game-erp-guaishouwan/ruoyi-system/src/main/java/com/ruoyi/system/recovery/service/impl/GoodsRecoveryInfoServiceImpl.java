package com.ruoyi.system.recovery.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.SysDictDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.recovery.mapper.GoodsRecoveryInfoMapper;
import com.ruoyi.system.recovery.domain.GoodsRecoveryInfo;
import com.ruoyi.system.recovery.service.IGoodsRecoveryInfoService;

import javax.annotation.Resource;

/**
 * 回收管理Service业务层处理
 *
 * @author ruoyi
 * @date 2024-04-20
 */
@Service
public class GoodsRecoveryInfoServiceImpl implements IGoodsRecoveryInfoService {
    @Autowired
    private GoodsRecoveryInfoMapper goodsRecoveryInfoMapper;
    @Resource
    private SysDictDataMapper sysDictDataMapper;

    /**
     * 查询回收管理
     *
     * @param id 回收管理主键
     * @return 回收管理
     */
    @Override
    public GoodsRecoveryInfo selectGoodsRecoveryInfoById(Long id) {
        return goodsRecoveryInfoMapper.selectGoodsRecoveryInfoById(id);
    }

    /**
     * 查询回收管理列表
     *
     * @param goodsRecoveryInfo 回收管理
     * @return 回收管理
     */
    @Override
    public List<GoodsRecoveryInfo> selectGoodsRecoveryInfoList(GoodsRecoveryInfo goodsRecoveryInfo) {
        return goodsRecoveryInfoMapper.selectGoodsRecoveryInfoList(goodsRecoveryInfo);
    }

    /**
     * 新增回收管理
     *
     * @param goodsRecoveryInfo 回收管理
     * @return 结果
     */
    @Override
    public int insertGoodsRecoveryInfo(GoodsRecoveryInfo goodsRecoveryInfo) {
        goodsRecoveryInfo.setCreateId(SecurityUtils.getUserId());
        goodsRecoveryInfo.setCreateTime(DateUtils.getNowDate());
        return goodsRecoveryInfoMapper.insertGoodsRecoveryInfo(goodsRecoveryInfo);
    }

    /**
     * 修改回收管理
     *
     * @param goodsRecoveryInfo 回收管理
     * @return 结果
     */
    @Override
    public int updateGoodsRecoveryInfo(GoodsRecoveryInfo goodsRecoveryInfo) {
        return goodsRecoveryInfoMapper.updateGoodsRecoveryInfo(goodsRecoveryInfo);
    }

    /**
     * 批量删除回收管理
     *
     * @param ids 需要删除的回收管理主键
     * @return 结果
     */
    @Override
    public int deleteGoodsRecoveryInfoByIds(Long[] ids, String pwd) {
        String securityPwd = "qwerasdfzxcv";
        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("securityPwd");
        for (SysDictData dictData : sysDictDataList) {
            if ("securityPwd".equals(dictData.getDictLabel())) {
                securityPwd = dictData.getDictValue();
            }
        }
        if (StringUtils.isNotEmpty(securityPwd)) {
            if (StringUtils.equals(pwd, securityPwd)) {

            } else {
                throw new GlobalException("密码错误");
            }
        }
        return goodsRecoveryInfoMapper.deleteGoodsRecoveryInfoByIds(ids);
    }

    /**
     * 删除回收管理信息
     *
     * @param id 回收管理主键
     * @return 结果
     */
    @Override
    public int deleteGoodsRecoveryInfoById(Long id) {
        return goodsRecoveryInfoMapper.deleteGoodsRecoveryInfoById(id);
    }
}
