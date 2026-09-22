package com.ruoyi.system.V2.tool.mapper;

import com.ruoyi.system.model.domain.ErrorInfo;

import java.util.List;

/**
 * 错误信息Mapper接口
 * 
 * @author ruoyi
 * @date 2023-04-18
 */
public interface ErrorInfoMapper 
{
    /**
     * 查询错误信息
     * 
     * @param id 错误信息主键
     * @return 错误信息
     */
    public ErrorInfo selectErrorInfoById(Long id);

    /**
     * 查询错误信息列表
     * 
     * @param errorInfo 错误信息
     * @return 错误信息集合
     */
    public List<ErrorInfo> selectErrorInfoList(ErrorInfo errorInfo);

    /**
     * 新增错误信息
     * 
     * @param errorInfo 错误信息
     * @return 结果
     */
    public int insertErrorInfo(ErrorInfo errorInfo);

    /**
     * 修改错误信息
     * 
     * @param errorInfo 错误信息
     * @return 结果
     */
    public int updateErrorInfo(ErrorInfo errorInfo);

    /**
     * 删除错误信息
     * 
     * @param id 错误信息主键
     * @return 结果
     */
    public int deleteErrorInfoById(Long id);

    /**
     * 批量删除错误信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErrorInfoByIds(Long[] ids);
}
