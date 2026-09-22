package com.ruoyi.system.V2.im.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.model.domain.MsgGroupInfo;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ruoyi
 * @date 2023-08-03
 */
public interface IMsgGroupInfoService {
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public MsgGroupInfo selectMsgGroupInfoById(Long id);


    public List<MsgGroupInfo> selectMsgGroupInfoByGroups(List<String> userNames);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param msgGroupInfo 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<MsgGroupInfo> selectMsgGroupInfoList(MsgGroupInfo msgGroupInfo);

    public MsgGroupInfo selectMsgGroupInfo(String group);

    /**
     * 新增【请填写功能名称】
     *
     * @param msgGroupInfo 【请填写功能名称】
     * @return 结果
     */
    public int insertMsgGroupInfo(MsgGroupInfo msgGroupInfo);

    /**
     * 修改【请填写功能名称】
     *
     * @param msgGroupInfo 【请填写功能名称】
     * @return 结果
     */
    public int updateMsgGroupInfo(MsgGroupInfo msgGroupInfo);

    public void addHxUserName(String group, List<JSONObject> members);

    public void removeHxUserName(String group, List<JSONObject> members);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteMsgGroupInfoByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteMsgGroupInfoById(Long id);

    /**
     * 获取群内所有成员(包括群主)
     * @return
     */
    JSONObject getAllNumbers(String groupId,String fromHxId);

    /**
     * 更新群信息
     *
     * @param contentBody
     */
    void updateSettings(JSONObject contentBody);

    /**
     * 更新群公告
     *
     * @param contentBody
     */
    void updateGroupAnnouncement(JSONObject contentBody);

    /**
     * 转让群组
     *
     * @param params
     */
    void assignGroup​(JSONObject params);


    public MsgGroupInfo recoveryGroupInfo(Long id);


}

