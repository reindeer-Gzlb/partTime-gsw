package com.ruoyi.system.V2.goodInfo.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.model.domain.Collect;
import com.ruoyi.system.V2.goodInfo.mapper.CollectMapper;
import com.ruoyi.system.V2.goodInfo.service.ICollectService;
import com.ruoyi.system.model.dto.CollectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-09
 */
@Service
public class CollectServiceImpl implements ICollectService {
    @Autowired
    private CollectMapper collectMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Collect selectCollectById(Long id) {
        return collectMapper.selectCollectById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param collect 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Collect> selectCollectList(Collect collect) {
        return collectMapper.selectCollectList(collect);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param collect 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertCollect(Collect collect) {
        collect.setCreateTime(DateUtils.getNowDate());
        collect.setCreateId(SecurityUtils.getUserId());
        return collectMapper.insertCollect(collect);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param collect 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateCollect(Collect collect) {
        collect.setUpdateTime(DateUtils.getNowDate());
        collect.setUpdateBy(SecurityUtils.getUserId().toString());
        return collectMapper.updateCollect(collect);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCollectByIds(Long[] ids) {
        return collectMapper.deleteCollectByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteCollectById(Long id) {
        return collectMapper.deleteCollectById(id);
    }

    @Override
    public int removeCollct(Collect collect) {
        return collectMapper.removeCollct(collect);
    }

    @Override
    public List<CollectDto> selectCollectDtoList(Map<String,Object> params) {
        return collectMapper.selectCollectDtoList(params);
    }
}
