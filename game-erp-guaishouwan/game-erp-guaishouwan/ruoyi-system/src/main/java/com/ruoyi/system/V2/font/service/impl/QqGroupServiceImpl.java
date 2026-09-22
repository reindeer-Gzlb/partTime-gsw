package com.ruoyi.system.V2.font.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.model.domain.QqGroup;
import com.ruoyi.system.V2.font.mapper.QqGroupMapper;
import com.ruoyi.system.V2.font.service.IQqGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-10-04
 */
@Service
public class QqGroupServiceImpl implements IQqGroupService
{
    @Autowired
    private QqGroupMapper qqGroupMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public QqGroup selectQqGroupById(Long id)
    {
        return qqGroupMapper.selectQqGroupById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param qqGroup 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<QqGroup> selectQqGroupList(QqGroup qqGroup)
    {
        if (qqGroup.getTypeResource()==2){
            qqGroup.setCreateId(qqGroup.getBusinessId());
        }
        return qqGroupMapper.selectQqGroupList(qqGroup);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param qqGroup 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertQqGroup(QqGroup qqGroup)
    {
        qqGroup.setCreateTime(DateUtils.getNowDate());

        qqGroup.setCreateId(SecurityUtils.getUserId());
        if (!Objects.equals(SecurityUtils.getUserId(),1L)){
            qqGroup.setTypeResource(2);
        }
        return qqGroupMapper.insertQqGroup(qqGroup);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param qqGroup 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateQqGroup(QqGroup qqGroup)
    {
        qqGroup.setUpdateTime(DateUtils.getNowDate());
        return qqGroupMapper.updateQqGroup(qqGroup);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteQqGroupByIds(Long[] ids)
    {
        return qqGroupMapper.deleteQqGroupByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteQqGroupById(Long id)
    {
        return qqGroupMapper.deleteQqGroupById(id);
    }
}
