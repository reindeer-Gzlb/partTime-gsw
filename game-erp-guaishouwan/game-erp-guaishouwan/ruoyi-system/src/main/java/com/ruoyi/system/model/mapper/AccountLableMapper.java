package com.ruoyi.system.model.mapper;

import com.ruoyi.system.model.domain.AccountLable;
import com.ruoyi.system.model.domain.LableOne;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 账号标签Mapper接口
 *
 * @author ruoyi
 * @date 2022-07-28
 */
public interface AccountLableMapper
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
     * @param accountLable 账号标签
     * @return 结果
     */
    public int insertAccountLable(AccountLable accountLable);

    /**
     * 修改账号标签
     *
     * @param accountLable 账号标签
     * @return 结果
     */
    public int updateAccountLable(AccountLable accountLable);

    /**
     * 删除账号标签
     *
     * @param id 账号标签主键
     * @return 结果
     */
    public int deleteAccountLableById(Long id);

    /**
     * 批量删除账号标签
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAccountLableByIds(Long[] ids);

    void deleteByInfoId(Long infoId);

    List<AccountLable> selectByLables(@Param("skinList") List<String> skinList);

    List<LableOne> selectByInfoIds(@Param("ids") Set<Long> ids);
}
