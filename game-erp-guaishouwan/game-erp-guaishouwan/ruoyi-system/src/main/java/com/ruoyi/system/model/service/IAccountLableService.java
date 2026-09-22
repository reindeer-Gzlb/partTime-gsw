package com.ruoyi.system.model.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.model.domain.AccountLable;

import java.util.List;

/**
 * 账号标签Service接口
 *
 * @author ruoyi
 * @date 2022-07-28
 */
public interface IAccountLableService
{
    /**
     * 查询账号标签
     *
     * @param id 账号标签主键
     * @return 账号标签
     */
    public AccountLable selectAccountLableById(Long id);

    /**
     * 查询账号标签列表
     *
     * @param accountLable 账号标签
     * @return 账号标签集合
     */
    public List<AccountLable> selectAccountLableList(AccountLable accountLable);

    /**
     * 新增账号标签
     *
     * @param jsonObject 账号标签
     * @return 结果
     */
    public int insertAccountLable(JSONObject jsonObject,Long userId);

    /**
     * 修改账号标签
     *
     * @param accountLable 账号标签
     * @return 结果
     */
    public int updateAccountLable(AccountLable accountLable);

    /**
     * 批量删除账号标签
     *
     * @param ids 需要删除的账号标签主键集合
     * @return 结果
     */
    public int deleteAccountLableByIds(Long[] ids);

    /**
     * 删除账号标签信息
     *
     * @param id 账号标签主键
     * @return 结果
     */
    public int deleteAccountLableById(Long id);

    int oneKeyLable(Long userId);
}
