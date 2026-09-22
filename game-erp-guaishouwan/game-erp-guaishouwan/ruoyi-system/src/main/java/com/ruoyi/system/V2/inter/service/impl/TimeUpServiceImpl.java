package com.ruoyi.system.V2.inter.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.model.domain.SalePartner;
import com.ruoyi.system.model.domain.TimeUp;
import com.ruoyi.system.V2.inter.mapper.SalePartnerMapper;
import com.ruoyi.system.V2.inter.mapper.TimeUpMapper;
import com.ruoyi.system.V2.inter.service.ITimeUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-04-01
 */
@Service
public class TimeUpServiceImpl implements ITimeUpService
{
    @Autowired
    private TimeUpMapper timeUpMapper;

    @Autowired
    private SalePartnerMapper salePartnerMapper;


    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public TimeUp selectTimeUpById(Long id)
    {
        return timeUpMapper.selectTimeUpById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param timeUp 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<TimeUp> selectTimeUpList(TimeUp timeUp)
    {
        return timeUpMapper.selectTimeUpList(timeUp);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param timeUp 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertTimeUp(TimeUp timeUp)
    {
        timeUp.setCreateTime(DateUtils.getNowDate());
        timeUp.setCreateId(SecurityUtils.getUserId());
        SalePartner salePartner = salePartnerMapper.selectSalePartnerById(timeUp.getConfig());
        timeUp.setSaleCode(salePartner.getSaleName());
        timeUp.setSaleName(salePartner.getName());
        return timeUpMapper.insertTimeUp(timeUp);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param timeUp 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateTimeUp(TimeUp timeUp)
    {
        timeUp.setUpdateTime(DateUtils.getNowDate());
        timeUp.setUpdateId(SecurityUtils.getUserId());
        SalePartner salePartner = salePartnerMapper.selectSalePartnerById(timeUp.getConfig());
        timeUp.setSaleCode(salePartner.getSaleName());
        timeUp.setSaleName(salePartner.getName());
        return timeUpMapper.updateTimeUp(timeUp);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteTimeUpByIds(Long[] ids)
    {
        return timeUpMapper.deleteTimeUpByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteTimeUpById(Long id)
    {
        return timeUpMapper.deleteTimeUpById(id);
    }
}
