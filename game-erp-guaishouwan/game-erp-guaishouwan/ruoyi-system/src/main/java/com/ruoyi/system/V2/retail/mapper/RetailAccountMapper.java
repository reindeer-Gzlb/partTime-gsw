package com.ruoyi.system.V2.retail.mapper;

import com.ruoyi.system.h5.Hreq;
import com.ruoyi.system.model.domain.AccountInfo;
import com.ruoyi.system.model.domain.RetailAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 零售号Mapper接口
 *
 * @author ruoyi
 * @date 2022-05-27
 */
public interface RetailAccountMapper
{
    /**
     * 查询零售号
     *
     * @param id 零售号主键
     * @return 零售号
     */
    public RetailAccount selectRetailAccountById(Long id);

    /**
     * 查询零售号列表
     *
     * @param retailAccount 零售号
     * @return 零售号集合
     */
    public List<RetailAccount> selectRetailAccountList(RetailAccount retailAccount);

    /**
     * 新增零售号
     *
     * @param retailAccount 零售号
     * @return 结果
     */
    public int insertRetailAccount(RetailAccount retailAccount);

    /**
     * 修改零售号
     *
     * @param retailAccount 零售号
     * @return 结果
     */
    public int updateRetailAccount(RetailAccount retailAccount);

    /**
     * 删除零售号
     *
     * @param id 零售号主键
     * @return 结果
     */
    public int deleteRetailAccountById(Long id);

    /**
     * 批量删除零售号
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRetailAccountByIds(Long[] ids);

    List<RetailAccount> retailAccountList(Hreq hreq);

    Integer retailAccountListCount(Hreq hreq);

    void bacthUpdata(@Param("retailAccountList") List<RetailAccount> retailAccountList);

    List<Long> selectDisctSuperId(@Param("createId") Long createId);
}
