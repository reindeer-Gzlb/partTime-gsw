package com.ruoyi.system.V2.im.service.impl;

import com.easemob.im.server.EMException;
import com.easemob.im.server.EMService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.V2.im.mapper.MsgGroupInfoMapper;
import com.ruoyi.system.V2.im.mapper.MsgHistoryMapper;
import com.ruoyi.system.V2.im.service.IMsgHistoryService;
import com.ruoyi.system.model.domain.MsgHistory;
import com.ruoyi.system.model.dto.msghistory.MsgHistoryAllSessionRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消息历史记录Service业务层处理
 *
 * @author ruoyi
 * @date 2023-06-02
 */
@Service
public class MsgHistoryServiceImpl implements IMsgHistoryService {
    @Autowired
    private MsgHistoryMapper msgHistoryMapper;
    @Autowired
    private MsgGroupInfoMapper msgGroupInfoMapper;
    @Autowired
    EMService service;

    /**
     * 查询消息历史记录
     *
     * @param id 消息历史记录主键
     * @return 消息历史记录
     */
    @Override
    public MsgHistory selectMsgHistoryById(Long id) {
        return msgHistoryMapper.selectMsgHistoryById(id);
    }

    /**
     * 查询消息历史记录列表
     *
     * @param msgHistory 消息历史记录
     * @return 消息历史记录
     */
    @Override
    public List<MsgHistory> selectMsgHistoryList(MsgHistory msgHistory) {
        return msgHistoryMapper.selectMsgHistoryList(msgHistory);
    }

    /**
     * 新增消息历史记录
     *
     * @param msgHistory 消息历史记录
     * @return 结果
     */
    @Override
    public int insertMsgHistory(MsgHistory msgHistory) {
        msgHistory.setCreateTime(DateUtils.getNowDate());
        return msgHistoryMapper.insertMsgHistory(msgHistory);
    }

    /**
     * 修改消息历史记录
     *
     * @param msgHistory 消息历史记录
     * @return 结果
     */
    @Override
    public int updateMsgHistory(MsgHistory msgHistory) {
        msgHistory.setUpdateTime(DateUtils.getNowDate());
        return msgHistoryMapper.updateMsgHistory(msgHistory);
    }

    /**
     * 批量删除消息历史记录
     *
     * @param ids 需要删除的消息历史记录主键
     * @return 结果
     */
    @Override
    public int deleteMsgHistoryByIds(Long[] ids) {
        return msgHistoryMapper.deleteMsgHistoryByIds(ids);
    }

    /**
     * 删除消息历史记录信息
     *
     * @param id 消息历史记录主键
     * @return 结果
     */
    @Override
    public int deleteMsgHistoryById(Long id) {
        return msgHistoryMapper.deleteMsgHistoryById(id);
    }

    @Override
    public List<MsgHistoryAllSessionRes> selectAllSessionMsgHistoryList(MsgHistory msgHistory) {
//        List<String> allGroups = getAllGroups(msgHistory.getFormUser());
        List<String> allGroups =msgGroupInfoMapper.getAllGroup(msgHistory.getFormUser());
        msgHistory.setHxGroupIds(allGroups);
        List<MsgHistoryAllSessionRes> list = msgHistoryMapper.selectAllSessionMsgHistoryList(msgHistory);
        return list;
    }

    List<String> getAllGroups(String hxId) {
        try {
            List<String> groups = service.group().listGroupsUserJoined(hxId).collectList().block();
            return groups;
        } catch (EMException e) {
            e.getErrorCode();
            e.getMessage();
        }
        return null;
    }
}
