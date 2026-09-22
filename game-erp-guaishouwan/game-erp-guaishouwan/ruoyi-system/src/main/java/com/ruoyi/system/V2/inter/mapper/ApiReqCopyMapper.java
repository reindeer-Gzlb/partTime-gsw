package com.ruoyi.system.V2.inter.mapper;

import com.ruoyi.system.model.domain.ApiReqCopy;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2023-02-16
 */
public interface ApiReqCopyMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public ApiReqCopy selectApiReqCopyById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param apiReqCopy 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ApiReqCopy> selectApiReqCopyList(ApiReqCopy apiReqCopy);

    /**
     * 新增【请填写功能名称】
     * 
     * @param apiReqCopy 【请填写功能名称】
     * @return 结果
     */
    public int insertApiReqCopy(ApiReqCopy apiReqCopy);

    /**
     * 修改【请填写功能名称】
     * 
     * @param apiReqCopy 【请填写功能名称】
     * @return 结果
     */
    public int updateApiReqCopy(ApiReqCopy apiReqCopy);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteApiReqCopyById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteApiReqCopyByIds(Long[] ids);
}
