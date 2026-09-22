package com.ruoyi.system.saleInfo.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.SysDictDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.saleInfo.mapper.GoodsSaleInfoMapper;
import com.ruoyi.system.saleInfo.domain.GoodsSaleInfo;
import com.ruoyi.system.saleInfo.service.IGoodsSaleInfoService;

import javax.annotation.Resource;

/**
 * 出售记录详情Service业务层处理
 *
 * @author ruoyi
 * @date 2024-04-25
 */
@Service
public class GoodsSaleInfoServiceImpl implements IGoodsSaleInfoService {
    @Autowired
    private GoodsSaleInfoMapper goodsSaleInfoMapper;
    @Resource
    private SysDictDataMapper sysDictDataMapper;
    /**
     * 查询出售记录详情
     *
     * @param id 出售记录详情主键
     * @return 出售记录详情
     */
    @Override
    public GoodsSaleInfo selectGoodsSaleInfoById(Long id) {
        return goodsSaleInfoMapper.selectGoodsSaleInfoById(id);
    }

    /**
     * 查询出售记录详情列表
     *
     * @param goodsSaleInfo 出售记录详情
     * @return 出售记录详情
     */
    @Override
    public List<GoodsSaleInfo> selectGoodsSaleInfoList(GoodsSaleInfo goodsSaleInfo) {
        return goodsSaleInfoMapper.selectGoodsSaleInfoList(goodsSaleInfo);
    }

    /**
     * 新增出售记录详情
     *
     * @param goodsSaleInfo 出售记录详情
     * @return 结果
     */
    @Override
    public int insertGoodsSaleInfo(GoodsSaleInfo goodsSaleInfo) {
        goodsSaleInfo.setCreateId(SecurityUtils.getUserId());
        goodsSaleInfo.setCreateTime(DateUtils.getNowDate());
        return goodsSaleInfoMapper.insertGoodsSaleInfo(goodsSaleInfo);
    }

    /**
     * 修改出售记录详情
     *
     * @param goodsSaleInfo 出售记录详情
     * @return 结果
     */
    @Override
    public int updateGoodsSaleInfo(GoodsSaleInfo goodsSaleInfo) {
        return goodsSaleInfoMapper.updateGoodsSaleInfo(goodsSaleInfo);
    }

    /**
     * 批量删除出售记录详情
     *
     * @param ids 需要删除的出售记录详情主键
     * @return 结果
     */
    @Override
    public int deleteGoodsSaleInfoByIds(Long[] ids, String pwd) {
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
        return goodsSaleInfoMapper.deleteGoodsSaleInfoByIds(ids);
    }

    /**
     * 删除出售记录详情信息
     *
     * @param id 出售记录详情主键
     * @return 结果
     */
    @Override
    public int deleteGoodsSaleInfoById(Long id) {
        return goodsSaleInfoMapper.deleteGoodsSaleInfoById(id);
    }
}
