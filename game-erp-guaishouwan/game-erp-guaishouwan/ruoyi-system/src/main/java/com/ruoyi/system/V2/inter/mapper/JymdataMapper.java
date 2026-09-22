package com.ruoyi.system.V2.inter.mapper;

import com.ruoyi.system.model.domain.Jymdata;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-27
 */
public interface JymdataMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Jymdata selectJymdataById(Long id);
    public Jymdata selectJymdataByShopid(@Param("shopid") String shopid);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param jymdata 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Jymdata> selectJymdataList(Jymdata jymdata);

    /**
     * 新增【请填写功能名称】
     * 
     * @param jymdata 【请填写功能名称】
     * @return 结果
     */
    public int insertJymdata(Jymdata jymdata);

    /**
     * 修改【请填写功能名称】
     * 
     * @param jymdata 【请填写功能名称】
     * @return 结果
     */
    public int updateJymdata(Jymdata jymdata);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteJymdataById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteJymdataByIds(Long[] ids);

    void deleteJymdataHour();

    List<String> allChannel();
}
