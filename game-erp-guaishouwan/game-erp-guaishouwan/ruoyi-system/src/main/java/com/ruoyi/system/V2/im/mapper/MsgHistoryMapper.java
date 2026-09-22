package com.ruoyi.system.V2.im.mapper;

import com.ruoyi.system.model.domain.MsgHistory;
import com.ruoyi.system.model.dto.msghistory.MsgHistoryAllSessionRes;
import com.ruoyi.system.model.dto.msghistory.MsgHistoryReq;

import java.util.List;

/**
 * 消息历史记录Mapper接口
 *
 * @author ruoyi
 * @date 2023-06-02
 */
public interface MsgHistoryMapper {
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
     * 删除消息历史记录
     *
     * @param id 消息历史记录主键
     * @return 结果
     */
    public int deleteMsgHistoryById(Long id);

    /**
     * 批量删除消息历史记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMsgHistoryByIds(Long[] ids);


    public List<MsgHistory> selectUpMsgHistoryList(MsgHistoryReq msgHistoryReq);

    public List<MsgHistory> selectDownMsgHistoryList(MsgHistoryReq msgHistoryReq);

    public List<MsgHistory> selectUpAndDownMsgHistoryList(MsgHistoryReq msgHistoryReq);


    public List<MsgHistoryAllSessionRes> selectAllSessionMsgHistoryList(MsgHistory msgHistory);

}
