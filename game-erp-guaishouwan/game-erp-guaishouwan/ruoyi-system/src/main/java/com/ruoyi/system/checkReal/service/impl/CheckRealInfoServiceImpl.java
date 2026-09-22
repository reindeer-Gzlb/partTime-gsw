package com.ruoyi.system.checkReal.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.checkReal.mapper.CheckRealInfoMapper;
import com.ruoyi.system.checkReal.domain.CheckRealInfo;
import com.ruoyi.system.checkReal.service.ICheckRealInfoService;

/**
 * 实名记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-15
 */
@Service
public class CheckRealInfoServiceImpl implements ICheckRealInfoService 
{
    @Autowired
    private CheckRealInfoMapper checkRealInfoMapper;

    /**
     * 查询实名记录
     * 
     * @param id 实名记录主键
     * @return 实名记录
     */
    @Override
    public CheckRealInfo selectCheckRealInfoById(Long id)
    {
        return checkRealInfoMapper.selectCheckRealInfoById(id);
    }

    /**
     * 查询实名记录列表
     * 
     * @param checkRealInfo 实名记录
     * @return 实名记录
     */
    @Override
    public List<CheckRealInfo> selectCheckRealInfoList(CheckRealInfo checkRealInfo)
    {
        return checkRealInfoMapper.selectCheckRealInfoList(checkRealInfo);
    }

    /**
     * 新增实名记录
     * 
     * @param checkRealInfo 实名记录
     * @return 结果
     */
    @Override
    public int insertCheckRealInfo(CheckRealInfo checkRealInfo)
    {
        checkRealInfo.setCreateTime(DateUtils.getNowDate());
        checkRealInfo.setCreateId(SecurityUtils.getUserId());
        return checkRealInfoMapper.insertCheckRealInfo(checkRealInfo);
    }

    /**
     * 修改实名记录
     * 
     * @param checkRealInfo 实名记录
     * @return 结果
     */
    @Override
    public int updateCheckRealInfo(CheckRealInfo checkRealInfo)
    {
        return checkRealInfoMapper.updateCheckRealInfo(checkRealInfo);
    }

    /**
     * 批量删除实名记录
     * 
     * @param ids 需要删除的实名记录主键
     * @return 结果
     */
    @Override
    public int deleteCheckRealInfoByIds(Long[] ids)
    {
        return checkRealInfoMapper.deleteCheckRealInfoByIds(ids);
    }

    /**
     * 删除实名记录信息
     * 
     * @param id 实名记录主键
     * @return 结果
     */
    @Override
    public int deleteCheckRealInfoById(Long id)
    {
        return checkRealInfoMapper.deleteCheckRealInfoById(id);
    }
}
