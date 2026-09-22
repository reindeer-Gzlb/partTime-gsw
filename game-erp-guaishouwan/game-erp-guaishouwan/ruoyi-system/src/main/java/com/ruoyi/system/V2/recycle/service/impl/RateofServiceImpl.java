package com.ruoyi.system.V2.recycle.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.model.domain.Rateof;
import com.ruoyi.system.V2.recycle.mapper.RateofMapper;
import com.ruoyi.system.V2.recycle.service.IRateofService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 折扣率Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-12-11
 */
@Service
public class RateofServiceImpl implements IRateofService
{
    @Autowired
    private RateofMapper rateofMapper;

    /**
     * 查询折扣率
     * 
     * @param id 折扣率主键
     * @return 折扣率
     */
    @Override
    public Rateof selectRateofById(Long id)
    {
        return rateofMapper.selectRateofById(id);
    }

    /**
     * 查询折扣率列表
     * 
     * @param rateof 折扣率
     * @return 折扣率
     */
    @Override
    public List<Rateof> selectRateofList(Rateof rateof)
    {
        return rateofMapper.selectRateofList(rateof);
    }

    /**
     * 新增折扣率
     * 
     * @param rateof 折扣率
     * @return 结果
     */
    @Override
    public int insertRateof(Rateof rateof)
    {
        rateof.setCreateTime(DateUtils.getNowDate());
        rateof.setCreateBy(SecurityUtils.getUserId().toString());
        return rateofMapper.insertRateof(rateof);
    }

    /**
     * 修改折扣率
     * 
     * @param rateof 折扣率
     * @return 结果
     */
    @Override
    public int updateRateof(Rateof rateof)
    {
        return rateofMapper.updateRateof(rateof);
    }

    /**
     * 批量删除折扣率
     * 
     * @param ids 需要删除的折扣率主键
     * @return 结果
     */
    @Override
    public int deleteRateofByIds(Long[] ids)
    {
        return rateofMapper.deleteRateofByIds(ids);
    }

    /**
     * 删除折扣率信息
     * 
     * @param id 折扣率主键
     * @return 结果
     */
    @Override
    public int deleteRateofById(Long id)
    {
        return rateofMapper.deleteRateofById(id);
    }
}
