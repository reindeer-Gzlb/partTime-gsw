package com.ruoyi.system.V2.customerAudit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.Constant;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.customerAudit.mapper.CustomerAuditMapper;
import com.ruoyi.system.V2.customerAudit.service.ICustomerAuditService;
import com.ruoyi.system.mapper.SysRoleMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.CustomerAudit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author zyy
 * @date 2023-05-05
 */
@Service
public class CustomerAuditServiceImpl extends ServiceImpl<CustomerAuditMapper, CustomerAudit> implements ICustomerAuditService {
    @Autowired
    CustomerAuditMapper customerAuditMapper;
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper roleMapper;

    @Override
    @Transactional
    public int auditPass(CustomerAudit customerAudit) {
        CustomerAudit customerAuditRK = customerAuditMapper.selectById(customerAudit.getId());
        LoginUser loginUser = SecurityUtils.getLoginUser();
        customerAuditRK.setAuditId(loginUser.getUserId());
        customerAuditRK.setAuditName(loginUser.getUser().getNickName());
        customerAuditRK.setAuditState(1);
        customerAuditRK.setAuditRemark(customerAudit.getAuditRemark());
        customerAuditMapper.updateById(customerAuditRK);
        if (Objects.isNull(customerAuditRK.getCreateId())) {
            throw new GlobalException("申请成为商户的用户id为空");
        }
        SysUser sysUser = sysUserMapper.selectUserById(customerAuditRK.getCreateId());
        if (Objects.isNull(sysUser)) {
            throw new GlobalException("未找到申请商户的用户");
        }
        sysUser.setUserNewType(1);
        sysUser.setUserUse(2);
        sysUser.setRise(customerAudit.getRise()); //利润比例
        sysUser.setDiscountt(customerAudit.getDiscountt());//供应商折扣
        sysUser.setIdCard(customerAuditRK.getIdCard());
        sysUser.setAdress(customerAuditRK.getAdress());
        sysUser.setNickName(customerAuditRK.getName());
        sysUser.setRealName(customerAuditRK.getName());
        sysUser.setPhonenumber(customerAuditRK.getPhoneNumber());
        sysUser.setBusinessLicenseImg(customerAuditRK.getBusinessLicenseImg());
        sysUser.setHandIdCardImg(customerAuditRK.getHandIdCardImg());
        sysUser.setFrontIdCardImg(customerAuditRK.getFrontIdCardImg());
        sysUser.setReverseIdCardImg(customerAuditRK.getReverseIdCardImg());
        Long[] roles = sysUser.getRoleIds();
        List<Long> roleList = new ArrayList<>();
        if (Objects.nonNull(roles)) {
            roleList = Arrays.asList(roles);
        }
        SysRole role1 = roleMapper.selectRoleByRoleKey(Constant.supplyCustomer);
        if (Objects.nonNull(role1)) {
            roleList.add(role1.getRoleId());
        }
        SysRole role2 = roleMapper.selectRoleByRoleKey(Constant.saleChannel);
        if (Objects.nonNull(role2)) {
            roleList.add(role2.getRoleId());
        }
        sysUser.setRoleIds(roleList.toArray(new Long[roleList.size()]));
        int count = sysUserMapper.updateUser(sysUser);
        return count;
    }

    @Override
    public CustomerAudit selectLastOne(Long id) {
        return customerAuditMapper.selectOne(
                new LambdaQueryWrapper<CustomerAudit>()
                        .eq(CustomerAudit::getCreateId, id)
                        .orderByDesc(CustomerAudit::getCreateTime)
                        .last("limit 1"));
    }

    @Override
    public List<CustomerAudit> selectByEntity(CustomerAudit customerAudit) {
        LambdaQueryWrapper<CustomerAudit> wrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(customerAudit)) {
            if (Objects.nonNull(customerAudit.getAuditState())) {
                wrapper.eq(CustomerAudit::getAuditState, customerAudit.getAuditState());
            }
            if (StringUtils.isNotEmpty(customerAudit.getPhoneNumber())) {
                wrapper.like(CustomerAudit::getPhoneNumber, customerAudit.getPhoneNumber());
            }
            if (StringUtils.isNotEmpty(customerAudit.getName())) {
                wrapper.like(CustomerAudit::getName, customerAudit.getName());
            }
        }
        wrapper.orderByDesc(CustomerAudit::getCreateTime);
        return customerAuditMapper.selectList(wrapper);
    }
}
