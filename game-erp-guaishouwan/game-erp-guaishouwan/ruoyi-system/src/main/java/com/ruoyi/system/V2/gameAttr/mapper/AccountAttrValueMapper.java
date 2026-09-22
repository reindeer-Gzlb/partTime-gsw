package com.ruoyi.system.V2.gameAttr.mapper;

import com.ruoyi.system.model.domain.AccountAttrValue;

import java.util.List;

/**
 * 账号属性表Mapper接口
 * 
 * @author ruoyi
 * @date 2023-04-04
 */
public interface AccountAttrValueMapper 
{
    /**
     * 查询账号属性表
     * 
     * @param id 账号属性表主键
     * @return 账号属性表
     */
    public AccountAttrValue selectAccountAttrValueById(Long id);

    /**
     * 查询账号属性表列表
     * 
     * @param accountAttrValue 账号属性表
     * @return 账号属性表集合
     */
    public List<AccountAttrValue> selectAccountAttrValueList(AccountAttrValue accountAttrValue);

    /**
     * 新增账号属性表
     * 
     * @param accountAttrValue 账号属性表
     * @return 结果
     */
    public int insertAccountAttrValue(AccountAttrValue accountAttrValue);

    /**
     * 修改账号属性表
     * 
     * @param accountAttrValue 账号属性表
     * @return 结果
     */
    public int updateAccountAttrValue(AccountAttrValue accountAttrValue);

    /**
     * 删除账号属性表
     * 
     * @param id 账号属性表主键
     * @return 结果
     */
    public int deleteAccountAttrValueById(Long id);

    /**
     * 批量删除账号属性表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAccountAttrValueByIds(Long[] ids);

    /**
     * 根据账号信息的id返回账号属性实体
     * @param accountInfoId
     * @return
     */
    public AccountAttrValue selectAccountAttrValueByAccountInfoId(Long accountInfoId);
}
