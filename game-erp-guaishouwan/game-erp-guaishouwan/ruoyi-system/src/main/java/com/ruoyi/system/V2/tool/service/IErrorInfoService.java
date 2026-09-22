package com.ruoyi.system.V2.tool.service;

import com.ruoyi.system.model.domain.ErrorInfo;

import java.util.List;

/**
 * 错误信息Service接口
 * 
 * @author ruoyi
 * @date 2023-04-18
 */
public interface IErrorInfoService 
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
     * 批量删除错误信息
     * 
     * @param ids 需要删除的错误信息主键集合
     * @return 结果
     */
    public int deleteErrorInfoByIds(Long[] ids);

    /**
     * 删除错误信息信息
     * 
     * @param id 错误信息主键
     * @return 结果
     */
    public int deleteErrorInfoById(Long id);
}
