package com.ruoyi.system.V2.order.mapper;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.model.domain.AccountRemake;
import com.ruoyi.system.V2.order.service.IAccountRemakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 销售订单备注Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class AccountRemakeServiceImpl implements IAccountRemakeService
{
    @Autowired
    private AccountRemakeMapper accountRemakeMapper;

    /**
     * 查询销售订单备注
     * 
     * @param id 销售订单备注主键
     * @return 销售订单备注
     */
    @Override
    public AccountRemake selectAccountRemakeById(Long id)
    {
        return accountRemakeMapper.selectAccountRemakeById(id);
    }

    /**
     * 查询销售订单备注列表
     * 
     * @param accountRemake 销售订单备注
     * @return 销售订单备注
     */
    @Override
    public List<AccountRemake> selectAccountRemakeList(AccountRemake accountRemake)
    {
        return accountRemakeMapper.selectAccountRemakeList(accountRemake);
    }

    /**
     * 新增销售订单备注
     * 
     * @param accountRemake 销售订单备注
     * @return 结果
     */
    @Override
    public int insertAccountRemake(AccountRemake accountRemake)
    {
        accountRemake.setCreateTime(DateUtils.getNowDate());
        return accountRemakeMapper.insertAccountRemake(accountRemake);
    }

    /**
     * 修改销售订单备注
     * 
     * @param accountRemake 销售订单备注
     * @return 结果
     */
    @Override
    public int updateAccountRemake(AccountRemake accountRemake)
    {
        accountRemake.setUpdateTime(DateUtils.getNowDate());
        return accountRemakeMapper.updateAccountRemake(accountRemake);
    }

    /**
     * 批量删除销售订单备注
     * 
     * @param ids 需要删除的销售订单备注主键
     * @return 结果
     */
    @Override
    public int deleteAccountRemakeByIds(Long[] ids)
    {
        return accountRemakeMapper.deleteAccountRemakeByIds(ids);
    }

    /**
     * 删除销售订单备注信息
     * 
     * @param id 销售订单备注主键
     * @return 结果
     */
    @Override
    public int deleteAccountRemakeById(Long id)
    {
        return accountRemakeMapper.deleteAccountRemakeById(id);
    }
}
