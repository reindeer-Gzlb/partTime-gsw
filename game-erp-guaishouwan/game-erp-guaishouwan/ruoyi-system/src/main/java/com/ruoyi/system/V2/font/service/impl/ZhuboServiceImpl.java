package com.ruoyi.system.V2.font.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.model.domain.Zhubo;
import com.ruoyi.system.V2.font.mapper.ZhuboMapper;
import com.ruoyi.system.V2.font.service.IZhuboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-09
 */
@Service
public class ZhuboServiceImpl implements IZhuboService
{
    @Autowired
    private ZhuboMapper zhuboMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Zhubo selectZhuboById(Long id)
    {
        return zhuboMapper.selectZhuboById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param zhubo 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Zhubo> selectZhuboList(Zhubo zhubo)
    {
        return zhuboMapper.selectZhuboList(zhubo);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param zhubo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertZhubo(Zhubo zhubo)
    {
        zhubo.setCreateTime(DateUtils.getNowDate());
        return zhuboMapper.insertZhubo(zhubo);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param zhubo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateZhubo(Zhubo zhubo)
    {
        zhubo.setUpdateTime(DateUtils.getNowDate());
        return zhuboMapper.updateZhubo(zhubo);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteZhuboByIds(Long[] ids)
    {
        return zhuboMapper.deleteZhuboByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteZhuboById(Long id)
    {
        return zhuboMapper.deleteZhuboById(id);
    }
}
