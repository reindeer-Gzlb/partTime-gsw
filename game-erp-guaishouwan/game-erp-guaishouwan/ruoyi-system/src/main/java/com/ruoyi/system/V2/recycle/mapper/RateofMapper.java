package com.ruoyi.system.V2.recycle.mapper;

import com.ruoyi.system.model.domain.Rateof;

import java.util.List;

/**
 * 折扣率Mapper接口
 * 
 * @author ruoyi
 * @date 2022-12-11
 */
public interface RateofMapper 
{
    /**
     * 查询折扣率
     * 
     * @param id 折扣率主键
     * @return 折扣率
     */
    public Rateof selectRateofById(Long id);

    /**
     * 查询折扣率列表
     * 
     * @param rateof 折扣率
     * @return 折扣率集合
     */
    public List<Rateof> selectRateofList(Rateof rateof);

    /**
     * 新增折扣率
     * 
     * @param rateof 折扣率
     * @return 结果
     */
    public int insertRateof(Rateof rateof);

    /**
     * 修改折扣率
     * 
     * @param rateof 折扣率
     * @return 结果
     */
    public int updateRateof(Rateof rateof);

    /**
     * 删除折扣率
     * 
     * @param id 折扣率主键
     * @return 结果
     */
    public int deleteRateofById(Long id);

    /**
     * 批量删除折扣率
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRateofByIds(Long[] ids);
}
