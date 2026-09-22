package com.ruoyi.system.V2.tool.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.model.domain.ErrorInfo;
import com.ruoyi.system.V2.tool.mapper.ErrorInfoMapper;
import com.ruoyi.system.V2.tool.service.IErrorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 错误信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-04-18
 */
@Service
public class ErrorInfoServiceImpl implements IErrorInfoService
{
    @Autowired
    private ErrorInfoMapper errorInfoMapper;

    /**
     * 查询错误信息
     * 
     * @param id 错误信息主键
     * @return 错误信息
     */
    @Override
    public ErrorInfo selectErrorInfoById(Long id)
    {
        return errorInfoMapper.selectErrorInfoById(id);
    }

    /**
     * 查询错误信息列表
     * 
     * @param errorInfo 错误信息
     * @return 错误信息
     */
    @Override
    public List<ErrorInfo> selectErrorInfoList(ErrorInfo errorInfo)
    {
        return errorInfoMapper.selectErrorInfoList(errorInfo);
    }

    /**
     * 新增错误信息
     * 
     * @param errorInfo 错误信息
     * @return 结果
     */
    @Override
    public int insertErrorInfo(ErrorInfo errorInfo)
    {
        errorInfo.setCreateTime(DateUtils.getNowDate());
        return errorInfoMapper.insertErrorInfo(errorInfo);
    }

    /**
     * 修改错误信息
     * 
     * @param errorInfo 错误信息
     * @return 结果
     */
    @Override
    public int updateErrorInfo(ErrorInfo errorInfo)
    {
        errorInfo.setUpdateTime(DateUtils.getNowDate());
        return errorInfoMapper.updateErrorInfo(errorInfo);
    }

    /**
     * 批量删除错误信息
     * 
     * @param ids 需要删除的错误信息主键
     * @return 结果
     */
    @Override
    public int deleteErrorInfoByIds(Long[] ids)
    {
        return errorInfoMapper.deleteErrorInfoByIds(ids);
    }

    /**
     * 删除错误信息信息
     * 
     * @param id 错误信息主键
     * @return 结果
     */
    @Override
    public int deleteErrorInfoById(Long id)
    {
        return errorInfoMapper.deleteErrorInfoById(id);
    }
}
