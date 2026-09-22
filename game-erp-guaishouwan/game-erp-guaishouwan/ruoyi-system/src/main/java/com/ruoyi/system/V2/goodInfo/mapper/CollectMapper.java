package com.ruoyi.system.V2.goodInfo.mapper;

import com.ruoyi.system.model.domain.Collect;
import com.ruoyi.system.model.dto.CollectDto;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-09
 */
public interface CollectMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Collect selectCollectById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param collect 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Collect> selectCollectList(Collect collect);

    /**
     * 新增【请填写功能名称】
     * 
     * @param collect 【请填写功能名称】
     * @return 结果
     */
    public int insertCollect(Collect collect);

    /**
     * 修改【请填写功能名称】
     * 
     * @param collect 【请填写功能名称】
     * @return 结果
     */
    public int updateCollect(Collect collect);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteCollectById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCollectByIds(Long[] ids);

    int removeCollct(Collect collect);

    List<CollectDto> selectCollectDtoList(Map<String,Object> params);
}
