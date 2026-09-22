package com.ruoyi.system.model.mapper;

import com.ruoyi.system.model.domain.CmdAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 终端附表Mapper接口
 *
 * @author ruoyi
 * @date 2022-08-06
 */
public interface CmdAccountMapper
{
    /**
     * 查询终端附表
     *
     * @param id 终端附表主键
     * @return 终端附表
     */
    public CmdAccount selectCmdAccountById(Long id);

    /**
     * 查询终端附表列表
     *
     * @param cmdAccount 终端附表
     * @return 终端附表集合
     */
    public List<CmdAccount> selectCmdAccountList(CmdAccount cmdAccount);

    /**
     * 新增终端附表
     *
     * @param cmdAccount 终端附表
     * @return 结果
     */
    public int insertCmdAccount(CmdAccount cmdAccount);

    /**
     * 修改终端附表
     *
     * @param cmdAccount 终端附表
     * @return 结果
     */
    public int updateCmdAccount(CmdAccount cmdAccount);

    /**
     * 删除终端附表
     *
     * @param id 终端附表主键
     * @return 结果
     */
    public int deleteCmdAccountById(Long id);

    /**
     * 批量删除终端附表
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCmdAccountByIds(Long[] ids);

    void saveBatch(@Param("cmdAccountList") List<CmdAccount> cmdAccountList);

    int deleteCmdAccountBySuplyId(Long suplyId);

    void deleteCmdAccountByAccountId(@Param("id") Long id);
}
