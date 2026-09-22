package com.ruoyi.system.V2.inter.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.model.domain.Jymdata;
import com.ruoyi.system.V2.inter.mapper.JymdataMapper;
import com.ruoyi.system.V2.inter.service.IJymdataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-27
 */
@Service
public class JymdataServiceImpl implements IJymdataService
{
    @Autowired
    private JymdataMapper jymdataMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Jymdata selectJymdataById(Long id)
    {
        return jymdataMapper.selectJymdataById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param jymdata 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Jymdata> selectJymdataList(Jymdata jymdata)
    {
        List<Jymdata> jymdataList = jymdataMapper.selectJymdataList(jymdata);
        for (Jymdata jymdata1 : jymdataList) {
            jymdata1.setShopurl(null);
        }
        return jymdataList;
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param jymdata 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertJymdata(Jymdata jymdata)
    {
        jymdata.setCreateTime(DateUtils.getNowDate());
        return jymdataMapper.insertJymdata(jymdata);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param jymdata 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateJymdata(Jymdata jymdata)
    {
        jymdata.setUpdateTime(DateUtils.getNowDate());
        return jymdataMapper.updateJymdata(jymdata);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteJymdataByIds(Long[] ids)
    {
        return jymdataMapper.deleteJymdataByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteJymdataById(Long id)
    {
        return jymdataMapper.deleteJymdataById(id);
    }

    @Override
    public List<String> allChannel() {
        return jymdataMapper.allChannel();
    }


}
