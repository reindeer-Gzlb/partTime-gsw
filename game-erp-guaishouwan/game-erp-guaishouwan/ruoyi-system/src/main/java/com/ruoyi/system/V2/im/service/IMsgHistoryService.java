package com.ruoyi.system.V2.im.service;

import com.ruoyi.system.model.domain.MsgHistory;
import com.ruoyi.system.model.dto.msghistory.MsgHistoryAllSessionRes;

import java.util.List;

/**
 * 消息历史记录Service接口
 * 
 * @author ruoyi
 * @date 2023-06-02
 */
public interface IMsgHistoryService 
{
    /**
     * 查询消息历史记录
     * 
     * @param id 消息历史记录主键
     * @return 消息历史记录
     */
    public MsgHistory selectMsgHistoryById(Long id);

    /**
     * 查询消息历史记录列表
     * 
     * @param msgHistory 消息历史记录
     * @return 消息历史记录集合
     */
    public List<MsgHistory> selectMsgHistoryList(MsgHistory msgHistory);

    /**
     * 新增消息历史记录
     * 
     * @param msgHistory 消息历史记录
     * @return 结果
     */
    public int insertMsgHistory(MsgHistory msgHistory);

    /**
     * 修改消息历史记录
     * 
     * @param msgHistory 消息历史记录
     * @return 结果
     */
    public int updateMsgHistory(MsgHistory msgHistory);

    /**
     * 批量删除消息历史记录
     * 
     * @param ids 需要删除的消息历史记录主键集合
     * @return 结果
     */
    public int deleteMsgHistoryByIds(Long[] ids);

    /**
     * 删除消息历史记录信息
     * 
     * @param id 消息历史记录主键
     * @return 结果
     */
    public int deleteMsgHistoryById(Long id);

    public List<MsgHistoryAllSessionRes> selectAllSessionMsgHistoryList(MsgHistory msgHistory);
}
