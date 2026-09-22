package com.ruoyi.system.V2.inter.mapper;

import com.ruoyi.system.model.domain.ApiReqRec;

import java.util.List;

/**
 * api请求记录Mapper接口
 * 
 * @author ruoyi
 * @date 2022-10-27
 */
public interface ApiReqRecMapper 
{
    /**
     * 查询api请求记录
     * 
     * @param id api请求记录主键
     * @return api请求记录
     */
    public ApiReqRec selectApiReqRecById(Long id);

    /**
     * 查询api请求记录列表
     * 
     * @param apiReqRec api请求记录
     * @return api请求记录集合
     */
    public List<ApiReqRec> selectApiReqRecList(ApiReqRec apiReqRec);

    /**
     * 新增api请求记录
     * 
     * @param apiReqRec api请求记录
     * @return 结果
     */
    public int insertApiReqRec(ApiReqRec apiReqRec);

    /**
     * 修改api请求记录
     * 
     * @param apiReqRec api请求记录
     * @return 结果
     */
    public int updateApiReqRec(ApiReqRec apiReqRec);

    /**
     * 删除api请求记录
     * 
     * @param id api请求记录主键
     * @return 结果
     */
    public int deleteApiReqRecById(Long id);

    /**
     * 批量删除api请求记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteApiReqRecByIds(Long[] ids);
}
