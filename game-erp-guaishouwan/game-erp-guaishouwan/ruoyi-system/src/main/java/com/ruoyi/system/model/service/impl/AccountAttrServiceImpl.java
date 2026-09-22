package com.ruoyi.system.model.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.model.domain.AccountAttr;
import com.ruoyi.system.model.mapper.AccountAttrMapper;
import com.ruoyi.system.model.service.IAccountAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 游戏属性Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-22
 */
@Service
public class AccountAttrServiceImpl implements IAccountAttrService
{
    @Autowired
    private AccountAttrMapper accountAttrMapper;

    /**
     * 查询游戏属性
     * 
     * @param id 游戏属性主键
     * @return 游戏属性
     */
    @Override
    public AccountAttr selectAccountAttrById(Long id)
    {
        return accountAttrMapper.selectAccountAttrById(id);
    }

    /**
     * 查询游戏属性列表
     * 
     * @param accountAttr 游戏属性
     * @return 游戏属性
     */
    @Override
    public List<AccountAttr> selectAccountAttrList(AccountAttr accountAttr)
    {
        return accountAttrMapper.selectAccountAttrList(accountAttr);
    }

    /**
     * 新增游戏属性
     * 
     * @param accountAttr 游戏属性
     * @return 结果
     */
    @Override
    public int insertAccountAttr(AccountAttr accountAttr)
    {
        accountAttr.setCreateTime(DateUtils.getNowDate());
        return accountAttrMapper.insertAccountAttr(accountAttr);
    }

    /**
     * 修改游戏属性
     * 
     * @param accountAttr 游戏属性
     * @return 结果
     */
    @Override
    public int updateAccountAttr(AccountAttr accountAttr)
    {
        accountAttr.setUpdateTime(DateUtils.getNowDate());
        return accountAttrMapper.updateAccountAttr(accountAttr);
    }

    /**
     * 批量删除游戏属性
     * 
     * @param ids 需要删除的游戏属性主键
     * @return 结果
     */
    @Override
    public int deleteAccountAttrByIds(Long[] ids)
    {
        return accountAttrMapper.deleteAccountAttrByIds(ids);
    }

    /**
     * 删除游戏属性信息
     * 
     * @param id 游戏属性主键
     * @return 结果
     */
    @Override
    public int deleteAccountAttrById(Long id)
    {
        return accountAttrMapper.deleteAccountAttrById(id);
    }

    @Override
    public AccountAttr getAttrByType(AccountAttr accountAttr) {

        List<AccountAttr> accountAttrs = accountAttrMapper.selectAccountAttrList(accountAttr);
        if (!CollectionUtils.isEmpty(accountAttrs)){
            return accountAttrs.get(0);
        }

        return null;
    }
}
