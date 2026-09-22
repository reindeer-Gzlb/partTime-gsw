package com.ruoyi.system.V2.recycle.mapper;

import com.ruoyi.system.model.domain.Attribute;

import java.util.List;

/**
 * 游戏配置Mapper接口
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
public interface AttributeMapper 
{
    /**
     * 查询游戏配置
     * 
     * @param id 游戏配置主键
     * @return 游戏配置
     */
    public Attribute selectAttributeById(Long id);

    /**
     * 查询游戏配置列表
     * 
     * @param attribute 游戏配置
     * @return 游戏配置集合
     */
    public List<Attribute> selectAttributeList(Attribute attribute);

    /**
     * 新增游戏配置
     * 
     * @param attribute 游戏配置
     * @return 结果
     */
    public int insertAttribute(Attribute attribute);

    /**
     * 修改游戏配置
     * 
     * @param attribute 游戏配置
     * @return 结果
     */
    public int updateAttribute(Attribute attribute);

    /**
     * 删除游戏配置
     * 
     * @param id 游戏配置主键
     * @return 结果
     */
    public int deleteAttributeById(Long id);

    /**
     * 批量删除游戏配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAttributeByIds(Long[] ids);

    List<Attribute> getByPrentId(Long id);
}
