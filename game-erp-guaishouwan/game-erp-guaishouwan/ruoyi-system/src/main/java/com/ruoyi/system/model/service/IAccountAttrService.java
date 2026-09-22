package com.ruoyi.system.model.service;

import com.ruoyi.system.model.domain.AccountAttr;

import java.util.List;

/**
 * 游戏属性Service接口
 * 
 * @author ruoyi
 * @date 2023-02-22
 */
public interface IAccountAttrService 
{
    /**
     * 查询游戏属性
     * 
     * @param id 游戏属性主键
     * @return 游戏属性
     */
    public AccountAttr selectAccountAttrById(Long id);

    /**
     * 查询游戏属性列表
     * 
     * @param accountAttr 游戏属性
     * @return 游戏属性集合
     */
    public List<AccountAttr> selectAccountAttrList(AccountAttr accountAttr);

    /**
     * 新增游戏属性
     * 
     * @param accountAttr 游戏属性
     * @return 结果
     */
    public int insertAccountAttr(AccountAttr accountAttr);

    /**
     * 修改游戏属性
     * 
     * @param accountAttr 游戏属性
     * @return 结果
     */
    public int updateAccountAttr(AccountAttr accountAttr);

    /**
     * 批量删除游戏属性
     * 
     * @param ids 需要删除的游戏属性主键集合
     * @return 结果
     */
    public int deleteAccountAttrByIds(Long[] ids);

    /**
     * 删除游戏属性信息
     * 
     * @param id 游戏属性主键
     * @return 结果
     */
    public int deleteAccountAttrById(Long id);

    AccountAttr getAttrByType(AccountAttr accountAttr);
}
