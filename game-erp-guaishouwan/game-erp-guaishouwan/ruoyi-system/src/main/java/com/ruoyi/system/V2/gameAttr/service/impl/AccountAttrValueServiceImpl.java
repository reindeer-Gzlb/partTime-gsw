package com.ruoyi.system.V2.gameAttr.service.impl;

import java.util.*;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.model.domain.AccountAttrValue;
import com.ruoyi.system.V2.gameAttr.mapper.AccountAttrValueMapper;
import com.ruoyi.system.V2.gameAttr.service.IAccountAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 账号属性表Service业务层处理
 *
 * @author ruoyi
 * @date 2023-04-04
 */
@Service
public class AccountAttrValueServiceImpl implements IAccountAttrValueService {
    @Autowired
    private AccountAttrValueMapper accountAttrValueMapper;

    /**
     * 查询账号属性表
     *
     * @param id 账号属性表主键
     * @return 账号属性表
     */
    @Override
    public AccountAttrValue selectAccountAttrValueById(Long id) {
        return accountAttrValueMapper.selectAccountAttrValueById(id);
    }

    /**
     * 查询账号属性表列表
     *
     * @param accountAttrValue 账号属性表
     * @return 账号属性表
     */
    @Override
    public List<AccountAttrValue> selectAccountAttrValueList(AccountAttrValue accountAttrValue) {
        return accountAttrValueMapper.selectAccountAttrValueList(accountAttrValue);
    }

    /**
     * 新增账号属性表
     *
     * @param accountAttrValue 账号属性表
     * @return 结果
     */
    @Override
    public int insertAccountAttrValue(AccountAttrValue accountAttrValue) {
        accountAttrValue.setCreateTime(DateUtils.getNowDate());
//        checkAttr(accountAttrValue);
        return accountAttrValueMapper.insertAccountAttrValue(accountAttrValue);
    }

    /**
     * 修改账号属性表
     *
     * @param accountAttrValue 账号属性表
     * @return 结果
     */
    @Override
    public int updateAccountAttrValue(AccountAttrValue accountAttrValue) {
        accountAttrValue.setUpdateTime(DateUtils.getNowDate());
//        checkAttr(accountAttrValue);
        return accountAttrValueMapper.updateAccountAttrValue(accountAttrValue);
    }

    /**
     * 批量删除账号属性表
     *
     * @param ids 需要删除的账号属性表主键
     * @return 结果
     */
    @Override
    public int deleteAccountAttrValueByIds(Long[] ids) {
        return accountAttrValueMapper.deleteAccountAttrValueByIds(ids);
    }

    /**
     * 删除账号属性表信息
     *
     * @param id 账号属性表主键
     * @return 结果
     */
    @Override
    public int deleteAccountAttrValueById(Long id) {
        return accountAttrValueMapper.deleteAccountAttrValueById(id);
    }

    @Override
    public AccountAttrValue selectAccountAttrValueByAccountInfoId(Long accountInfoId) {
        return accountAttrValueMapper.selectAccountAttrValueByAccountInfoId(accountInfoId);
    }


    public static void main(String[] args) {
        String s1 = "1";
        String s2 = "-100";
        String s3 = "month";
        String s4 = "1023.89";
        System.out.println(StringUtils.isNumeric(s1));
        System.out.println(StringUtils.isNumeric(s2));
        System.out.println(StringUtils.isNumeric(s3));
        System.out.println(StringUtils.isNumeric(s4));

    }
}
