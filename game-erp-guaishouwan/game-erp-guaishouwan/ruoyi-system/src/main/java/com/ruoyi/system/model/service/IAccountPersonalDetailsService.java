package com.ruoyi.system.model.service;

import com.ruoyi.system.model.domain.AccountPersonalDetails;

import java.util.List;


/**
 * 游戏账号审核包赔个人详情Service接口
 * 
 * @author ruoyi
 * @date 2022-05-08
 */
public interface IAccountPersonalDetailsService 
{
    /**
     * 查询游戏账号审核包赔个人详情
     * 
     * @param id 游戏账号审核包赔个人详情主键
     * @return 游戏账号审核包赔个人详情
     */
    public AccountPersonalDetails selectAccountPersonalDetailsById(Long id);

    /**
     * 查询游戏账号审核包赔个人详情列表
     * 
     * @param accountPersonalDetails 游戏账号审核包赔个人详情
     * @return 游戏账号审核包赔个人详情集合
     */
    public List<AccountPersonalDetails> selectAccountPersonalDetailsList(AccountPersonalDetails accountPersonalDetails);

    /**
     * 新增游戏账号审核包赔个人详情
     * 
     * @param accountPersonalDetails 游戏账号审核包赔个人详情
     * @return 结果
     */
    public int insertAccountPersonalDetails(AccountPersonalDetails accountPersonalDetails);

    /**
     * 修改游戏账号审核包赔个人详情
     * 
     * @param accountPersonalDetails 游戏账号审核包赔个人详情
     * @return 结果
     */
    public int updateAccountPersonalDetails(AccountPersonalDetails accountPersonalDetails);

    /**
     * 批量删除游戏账号审核包赔个人详情
     * 
     * @param ids 需要删除的游戏账号审核包赔个人详情主键集合
     * @return 结果
     */
    public int deleteAccountPersonalDetailsByIds(Long[] ids);

    /**
     * 删除游戏账号审核包赔个人详情信息
     * 
     * @param id 游戏账号审核包赔个人详情主键
     * @return 结果
     */
    public int deleteAccountPersonalDetailsById(Long id);
}
