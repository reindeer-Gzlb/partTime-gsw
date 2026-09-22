package com.ruoyi.system.V2.font.mapper;

import com.ruoyi.system.model.domain.CheckAccount;

import java.util.List;

/**
 * 账号校验Mapper接口
 * 
 * @author ruoyi
 * @date 2022-10-06
 */
public interface CheckAccountMapper 
{
    /**
     * 查询账号校验
     * 
     * @param id 账号校验主键
     * @return 账号校验
     */
    public CheckAccount selectCheckAccountById(Long id);

    /**
     * 查询账号校验列表
     * 
     * @param checkAccount 账号校验
     * @return 账号校验集合
     */
    public List<CheckAccount> selectCheckAccountList(CheckAccount checkAccount);

    /**
     * 新增账号校验
     * 
     * @param checkAccount 账号校验
     * @return 结果
     */
    public int insertCheckAccount(CheckAccount checkAccount);

    /**
     * 修改账号校验
     * 
     * @param checkAccount 账号校验
     * @return 结果
     */
    public int updateCheckAccount(CheckAccount checkAccount);

    /**
     * 删除账号校验
     * 
     * @param id 账号校验主键
     * @return 结果
     */
    public int deleteCheckAccountById(Long id);

    /**
     * 批量删除账号校验
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCheckAccountByIds(Long[] ids);

    /**
     * 查询账号校验列表
     *
     * @param checkAccount 账号校验
     * @return 账号校验集合
     */
    public List<CheckAccount> selectCheckAccountNameNotEmptyList(CheckAccount checkAccount);
}
