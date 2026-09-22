package com.ruoyi.system.V2.phone.service;

import com.ruoyi.system.model.domain.TelManage;

import java.util.List;

/**
 * 手机管理Service接口
 * 
 * @author ruoyi
 * @date 2023-04-05
 */
public interface ITelManageService 
{
    /**
     * 查询手机管理
     * 
     * @param id 手机管理主键
     * @return 手机管理
     */
    public TelManage selectTelManageById(Long id);

    /**
     * 查询手机管理列表
     * 
     * @param telManage 手机管理
     * @return 手机管理集合
     */
    public List<TelManage> selectTelManageList(TelManage telManage);

    /**
     * 新增手机管理
     * 
     * @param telManage 手机管理
     * @return 结果
     */
    public int insertTelManage(TelManage telManage);

    /**
     * 修改手机管理
     * 
     * @param telManage 手机管理
     * @return 结果
     */
    public int updateTelManage(TelManage telManage);

    /**
     * 批量删除手机管理
     * 
     * @param ids 需要删除的手机管理主键集合
     * @return 结果
     */
    public int deleteTelManageByIds(Long[] ids);

    /**
     * 删除手机管理信息
     * 
     * @param id 手机管理主键
     * @return 结果
     */
    public int deleteTelManageById(Long id);

    String countGame(Long id);

    List<TelManage> allList(TelManage telManage);
}
