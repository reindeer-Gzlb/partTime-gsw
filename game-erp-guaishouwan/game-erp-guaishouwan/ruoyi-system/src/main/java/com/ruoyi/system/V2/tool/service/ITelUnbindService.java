package com.ruoyi.system.V2.tool.service;

import com.ruoyi.system.model.domain.TelUnbind;

import java.util.List;

/**
 * 检测任务池Service接口
 * 
 * @author ruoyi
 * @date 2023-04-19
 */
public interface ITelUnbindService 
{
    /**
     * 查询检测任务池
     * 
     * @param id 检测任务池主键
     * @return 检测任务池
     */
    public TelUnbind selectTelUnbindById(Long id);

    /**
     * 查询检测任务池列表
     * 
     * @param telUnbind 检测任务池
     * @return 检测任务池集合
     */
    public List<TelUnbind> selectTelUnbindList(TelUnbind telUnbind);

    /**
     * 新增检测任务池
     * 
     * @param telUnbind 检测任务池
     * @return 结果
     */
    public int insertTelUnbind(TelUnbind telUnbind);

    /**
     * 修改检测任务池
     * 
     * @param telUnbind 检测任务池
     * @return 结果
     */
    public int updateTelUnbind(TelUnbind telUnbind);

    /**
     * 批量删除检测任务池
     * 
     * @param ids 需要删除的检测任务池主键集合
     * @return 结果
     */
    public int deleteTelUnbindByIds(Long[] ids);

    /**
     * 删除检测任务池信息
     * 
     * @param id 检测任务池主键
     * @return 结果
     */
    public int deleteTelUnbindById(Long id);
}
