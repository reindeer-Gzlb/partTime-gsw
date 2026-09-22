package com.ruoyi.system.V2.tool.service.impl;

import java.util.List;

import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.model.domain.TelUnbind;
import com.ruoyi.system.V2.tool.mapper.TelUnbindMapper;
import com.ruoyi.system.V2.tool.service.ITelUnbindService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 检测任务池Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-04-19
 */
@Service
public class TelUnbindServiceImpl implements ITelUnbindService
{
    @Autowired
    private TelUnbindMapper telUnbindMapper;

    /**
     * 查询检测任务池
     * 
     * @param id 检测任务池主键
     * @return 检测任务池
     */
    @Override
    public TelUnbind selectTelUnbindById(Long id)
    {
        return telUnbindMapper.selectTelUnbindById(id);
    }

    /**
     * 查询检测任务池列表
     * 
     * @param telUnbind 检测任务池
     * @return 检测任务池
     */
    @Override
    public List<TelUnbind> selectTelUnbindList(TelUnbind telUnbind)
    {
        return telUnbindMapper.selectTelUnbindList(telUnbind);
    }

    /**
     * 新增检测任务池
     * 
     * @param telUnbind 检测任务池
     * @return 结果
     */
    @Override
    public int insertTelUnbind(TelUnbind telUnbind)
    {
        telUnbind.setCreateTime(DateUtils.getNowDate());
        telUnbind.setCreateBy(SecurityUtils.getUserId().toString());

        TelUnbind select = new TelUnbind();
        select.setCreateBy(SecurityUtils.getUserId().toString());
        List<TelUnbind> telUnbinds = telUnbindMapper.selectTelUnbindList(select);
        if (telUnbinds.size() > 50){
            throw new GlobalException("最多绑定50条");
        }
        select.setAccountId(telUnbind.getAccountId());
        telUnbinds = telUnbindMapper.selectTelUnbindList(select);
        if (CollectionUtils.isNotEmpty(telUnbinds)){
            throw new GlobalException("已存在该账号");
        }
        telUnbind.setStatus("2");
        return telUnbindMapper.insertTelUnbind(telUnbind);
    }

    /**
     * 修改检测任务池
     * 
     * @param telUnbind 检测任务池
     * @return 结果
     */
    @Override
    public int updateTelUnbind(TelUnbind telUnbind)
    {
        telUnbind.setUpdateTime(DateUtils.getNowDate());
        telUnbind.setUpdateBy(SecurityUtils.getUserId().toString());
        return telUnbindMapper.updateTelUnbind(telUnbind);
    }

    /**
     * 批量删除检测任务池
     * 
     * @param ids 需要删除的检测任务池主键
     * @return 结果
     */
    @Override
    public int deleteTelUnbindByIds(Long[] ids)
    {
        return telUnbindMapper.deleteTelUnbindByIds(ids);
    }

    /**
     * 删除检测任务池信息
     * 
     * @param id 检测任务池主键
     * @return 结果
     */
    @Override
    public int deleteTelUnbindById(Long id)
    {
        return telUnbindMapper.deleteTelUnbindById(id);
    }
}
