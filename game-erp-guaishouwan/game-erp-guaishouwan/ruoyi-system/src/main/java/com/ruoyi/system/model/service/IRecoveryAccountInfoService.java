package com.ruoyi.system.model.service;

import com.ruoyi.system.model.domain.RecoveryAccountInfo;

import java.util.List;

/**
 * 回收游戏账号Service接口
 * 
 * @author ruoyi
 * @date 2022-06-28
 */
public interface IRecoveryAccountInfoService 
{
    /**
     * 查询回收游戏账号
     * 
     * @param id 回收游戏账号主键
     * @return 回收游戏账号
     */
    public RecoveryAccountInfo selectRecoveryAccountInfoById(Long id);

    /**
     * 查询回收游戏账号列表
     * 
     * @param recoveryAccountInfo 回收游戏账号
     * @return 回收游戏账号集合
     */
    public List<RecoveryAccountInfo> selectRecoveryAccountInfoList(RecoveryAccountInfo recoveryAccountInfo);

    /**
     * 新增回收游戏账号
     * 
     * @param recoveryAccountInfo 回收游戏账号
     * @return 结果
     */
    public int insertRecoveryAccountInfo(RecoveryAccountInfo recoveryAccountInfo);

    /**
     * 修改回收游戏账号
     * 
     * @param recoveryAccountInfo 回收游戏账号
     * @return 结果
     */
    public int updateRecoveryAccountInfo(RecoveryAccountInfo recoveryAccountInfo);

    /**
     * 批量删除回收游戏账号
     * 
     * @param ids 需要删除的回收游戏账号主键集合
     * @return 结果
     */
    public int deleteRecoveryAccountInfoByIds(Long[] ids);

    /**
     * 删除回收游戏账号信息
     * 
     * @param id 回收游戏账号主键
     * @return 结果
     */
    public int deleteRecoveryAccountInfoById(Long id);


    /**
     * 查询是否已存在游戏账号
     * @param accountId
     * @return
     */
    public RecoveryAccountInfo selectRecoveryAccountInfoById(String accountId);
}
