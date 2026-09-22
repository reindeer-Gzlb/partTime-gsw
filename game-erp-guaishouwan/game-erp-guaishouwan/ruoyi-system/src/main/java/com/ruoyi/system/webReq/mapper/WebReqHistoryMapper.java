package com.ruoyi.system.webReq.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.webReq.domain.WebReqHistory;

/**
 * 用户端请求记录Mapper接口
 * 
 * @author ruoyi
 * @date 2024-12-04
 */
public interface WebReqHistoryMapper 
{
    /**
     * 查询用户端请求记录
     * 
     * @param id 用户端请求记录主键
     * @return 用户端请求记录
     */
    public WebReqHistory selectWebReqHistoryById(Long id);

    /**
     * 查询用户端请求记录列表
     * 
     * @param webReqHistory 用户端请求记录
     * @return 用户端请求记录集合
     */
    public List<WebReqHistory> selectWebReqHistoryList(WebReqHistory webReqHistory);

    /**
     * 新增用户端请求记录
     * 
     * @param webReqHistory 用户端请求记录
     * @return 结果
     */
    public int insertWebReqHistory(WebReqHistory webReqHistory);

    /**
     * 修改用户端请求记录
     * 
     * @param webReqHistory 用户端请求记录
     * @return 结果
     */
    public int updateWebReqHistory(WebReqHistory webReqHistory);

    /**
     * 删除用户端请求记录
     * 
     * @param id 用户端请求记录主键
     * @return 结果
     */
    public int deleteWebReqHistoryById(Long id);

    /**
     * 批量删除用户端请求记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWebReqHistoryByIds(Long[] ids);

    List<Map<String,Object>> statistics(WebReqHistory webReqHistory);
}
