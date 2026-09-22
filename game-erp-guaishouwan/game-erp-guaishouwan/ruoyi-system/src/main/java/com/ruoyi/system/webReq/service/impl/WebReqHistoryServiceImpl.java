package com.ruoyi.system.webReq.service.impl;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.webReq.mapper.WebReqHistoryMapper;
import com.ruoyi.system.webReq.domain.WebReqHistory;
import com.ruoyi.system.webReq.service.IWebReqHistoryService;

/**
 * 用户端请求记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-12-04
 */
@Service
public class WebReqHistoryServiceImpl implements IWebReqHistoryService 
{
    @Autowired
    private WebReqHistoryMapper webReqHistoryMapper;

    /**
     * 查询用户端请求记录
     * 
     * @param id 用户端请求记录主键
     * @return 用户端请求记录
     */
    @Override
    public WebReqHistory selectWebReqHistoryById(Long id)
    {
        return webReqHistoryMapper.selectWebReqHistoryById(id);
    }

    /**
     * 查询用户端请求记录列表
     * 
     * @param webReqHistory 用户端请求记录
     * @return 用户端请求记录
     */
    @Override
    public List<WebReqHistory> selectWebReqHistoryList(WebReqHistory webReqHistory)
    {
        return webReqHistoryMapper.selectWebReqHistoryList(webReqHistory);
    }

    /**
     * 新增用户端请求记录
     * 
     * @param webReqHistory 用户端请求记录
     * @return 结果
     */
    @Override
    public int insertWebReqHistory(WebReqHistory webReqHistory)
    {
        webReqHistory.setCreateTime(DateUtils.getNowDate());
        return webReqHistoryMapper.insertWebReqHistory(webReqHistory);
    }

    /**
     * 修改用户端请求记录
     * 
     * @param webReqHistory 用户端请求记录
     * @return 结果
     */
    @Override
    public int updateWebReqHistory(WebReqHistory webReqHistory)
    {
        return webReqHistoryMapper.updateWebReqHistory(webReqHistory);
    }

    /**
     * 批量删除用户端请求记录
     * 
     * @param ids 需要删除的用户端请求记录主键
     * @return 结果
     */
    @Override
    public int deleteWebReqHistoryByIds(Long[] ids)
    {
        return webReqHistoryMapper.deleteWebReqHistoryByIds(ids);
    }

    /**
     * 删除用户端请求记录信息
     * 
     * @param id 用户端请求记录主键
     * @return 结果
     */
    @Override
    public int deleteWebReqHistoryById(Long id)
    {
        return webReqHistoryMapper.deleteWebReqHistoryById(id);
    }

    /**
     * 统计
     * @param webReqHistory
     * @return
     */
    @Override
    public List<Map<String, Object>> statistics(WebReqHistory webReqHistory){
        return webReqHistoryMapper.statistics(webReqHistory);
    }
}
