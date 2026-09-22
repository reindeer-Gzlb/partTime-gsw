package com.ruoyi.system.V2.font.service.impl;

import com.easemob.im.server.EMService;
import com.easemob.im.server.model.EMUser;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ToPinYin;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.CheckAccount;
import com.ruoyi.system.V2.font.mapper.CheckAccountMapper;
import com.ruoyi.system.V2.font.service.ICheckAccountService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 账号校验Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-10-06
 */
@Service
public class CheckAccountServiceImpl implements ICheckAccountService
{
    @Resource
    private CheckAccountMapper checkAccountMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private EMService service;
    /**
     * 查询账号校验
     * 
     * @param id 账号校验主键
     * @return 账号校验
     */
    @Override
    public CheckAccount selectCheckAccountById(Long id)
    {
        return checkAccountMapper.selectCheckAccountById(id);
    }

    /**
     * 查询账号校验列表
     * 
     * @param checkAccount 账号校验
     * @return 账号校验
     */
    @Override
    public List<CheckAccount> selectCheckAccountList(CheckAccount checkAccount)
    {
        return checkAccountMapper.selectCheckAccountList(checkAccount);
    }

    /**
     * 新增账号校验
     * 
     * @param checkAccount 账号校验
     * @return 结果
     */
    @Override
    public int insertCheckAccount(CheckAccount checkAccount)
    {
        Long userId = SecurityUtils.getUserId();
        checkAccount.setCreateId(userId);
        checkAccount.setCreateTime(DateUtils.getNowDate());
        return checkAccountMapper.insertCheckAccount(checkAccount);
    }

    /**
     * 修改账号校验
     * 
     * @param checkAccount 账号校验
     * @return 结果
     */
    @Override
    public int updateCheckAccount(CheckAccount checkAccount)
    {
        checkAccount.setUpdateTime(DateUtils.getNowDate());
        Long userId = SecurityUtils.getUserId();
        checkAccount.setUpdateId(userId);
        return checkAccountMapper.updateCheckAccount(checkAccount);
    }

    /**
     * 批量删除账号校验
     * 
     * @param ids 需要删除的账号校验主键
     * @return 结果
     */
    @Override
    public int deleteCheckAccountByIds(Long[] ids)
    {
        return checkAccountMapper.deleteCheckAccountByIds(ids);
    }

    /**
     * 删除账号校验信息
     * 
     * @param id 账号校验主键
     * @return 结果
     */
    @Override
    public int deleteCheckAccountById(Long id)
    {
        return checkAccountMapper.deleteCheckAccountById(id);
    }
}
