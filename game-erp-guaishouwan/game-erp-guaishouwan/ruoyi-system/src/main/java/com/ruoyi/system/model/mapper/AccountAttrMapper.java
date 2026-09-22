package com.ruoyi.system.model.mapper;

import com.ruoyi.system.model.domain.AccountAttr;

import java.util.List;

/**
 * 游戏属性Mapper接口
 * 
 * @author ruoyi
 * @date 2023-02-22
 */
public interface AccountAttrMapper 
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
     * 删除游戏属性
     * 
     * @param id 游戏属性主键
     * @return 结果
     */
    public int deleteAccountAttrById(Long id);

    /**
     * 批量删除游戏属性
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAccountAttrByIds(Long[] ids);
}
