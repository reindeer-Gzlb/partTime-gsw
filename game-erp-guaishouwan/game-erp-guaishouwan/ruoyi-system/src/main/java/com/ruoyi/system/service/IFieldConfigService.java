package com.ruoyi.system.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.model.domain.FieldConfig;

import java.util.List;


/**
 * 游戏类型字段配置Service接口
 *
 * @author ruoyi
 * @date 2022-07-03
 */
public interface IFieldConfigService
{
    /**
     * 查询游戏类型字段配置
     *
     * @param id 游戏类型字段配置主键
     * @return 游戏类型字段配置
     */
    public FieldConfig selectFieldConfigById(Long id);

    /**
     * 查询游戏类型字段配置列表
     *
     * @param fieldConfig 游戏类型字段配置
     * @return 游戏类型字段配置集合
     */
    public List<FieldConfig> selectFieldConfigList(FieldConfig fieldConfig);


    /**
     * 根据游戏类型查询配置字段
     * @param type
     * @return
     */
    public List<FieldConfig> selectFieldConfigByType(String type);

    /**
     * 新增游戏类型字段配置
     *
     * @param fieldConfig 游戏类型字段配置
     * @return 结果
     */
    public int insertFieldConfig(FieldConfig fieldConfig);

    /**
     * 修改游戏类型字段配置
     *
     * @param fieldConfig 游戏类型字段配置
     * @return 结果
     */
    public int updateFieldConfig(FieldConfig fieldConfig);

    /**
     * 批量删除游戏类型字段配置
     *
     * @param ids 需要删除的游戏类型字段配置主键集合
     * @return 结果
     */
    public int deleteFieldConfigByIds(Long[] ids);

    /**
     * 删除游戏类型字段配置信息
     *
     * @param id 游戏类型字段配置主键
     * @return 结果
     */
    public int deleteFieldConfigById(Long id);

    List<FieldConfig> selectFieldConfigByLbelType(String type);

    int addJson(JSONObject params);

    int updateJson(JSONObject params);
}
