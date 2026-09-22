package com.ruoyi.system.model.mapper;

import com.ruoyi.system.model.domain.RecoveryAccountInfo;

import java.util.List;


/**
 * 回收游戏账号Mapper接口
 * 
 * @author ruoyi
 * @date 2022-06-28
 */
public interface RecoveryAccountInfoMapper 
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
     * 删除回收游戏账号
     * 
     * @param id 回收游戏账号主键
     * @return 结果
     */
    public int deleteRecoveryAccountInfoById(Long id);

    /**
     * 批量删除回收游戏账号
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRecoveryAccountInfoByIds(Long[] ids);


    /**
     * 查询是否已存在游戏账号
     * @param accountId
     * @return
     */
    public RecoveryAccountInfo selectAccountInfoByAccountId(String accountId);
}
