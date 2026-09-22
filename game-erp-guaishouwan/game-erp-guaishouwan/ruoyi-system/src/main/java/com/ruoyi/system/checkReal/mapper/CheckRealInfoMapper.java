package com.ruoyi.system.checkReal.mapper;

import java.util.List;
import com.ruoyi.system.checkReal.domain.CheckRealInfo;

/**
 * 实名记录Mapper接口
 * 
 * @author ruoyi
 * @date 2024-11-15
 */
public interface CheckRealInfoMapper 
{
    /**
     * 查询实名记录
     * 
     * @param id 实名记录主键
     * @return 实名记录
     */
    public CheckRealInfo selectCheckRealInfoById(Long id);

    /**
     * 查询实名记录列表
     * 
     * @param checkRealInfo 实名记录
     * @return 实名记录集合
     */
    public List<CheckRealInfo> selectCheckRealInfoList(CheckRealInfo checkRealInfo);

    /**
     * 新增实名记录
     * 
     * @param checkRealInfo 实名记录
     * @return 结果
     */
    public int insertCheckRealInfo(CheckRealInfo checkRealInfo);

    /**
     * 修改实名记录
     * 
     * @param checkRealInfo 实名记录
     * @return 结果
     */
    public int updateCheckRealInfo(CheckRealInfo checkRealInfo);

    /**
     * 删除实名记录
     * 
     * @param id 实名记录主键
     * @return 结果
     */
    public int deleteCheckRealInfoById(Long id);

    /**
     * 批量删除实名记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCheckRealInfoByIds(Long[] ids);
}
