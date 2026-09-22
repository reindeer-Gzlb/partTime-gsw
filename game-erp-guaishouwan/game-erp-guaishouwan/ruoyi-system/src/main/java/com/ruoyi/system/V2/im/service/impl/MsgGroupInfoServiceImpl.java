package com.ruoyi.system.V2.im.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easemob.im.server.api.group.settings.UpdateGroupRequest;
import com.easemob.im.server.model.EMGroup;
import com.easemob.im.server.model.EMRemoveMember;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.im.mapper.MsgGroupInfoMapper;
import com.ruoyi.system.V2.im.mapper.SessionListMapper;
import com.ruoyi.system.V2.im.service.IMsgGroupInfoService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.MsgGroupInfo;
import com.ruoyi.system.model.domain.SessionList;
import com.ruoyi.system.model.dto.SysUserIMRes;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;


/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2023-08-03
 */
@Service
public class MsgGroupInfoServiceImpl implements IMsgGroupInfoService {
    @Autowired
    private MsgGroupInfoMapper msgGroupInfoMapper;
    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SessionListMapper sessionListMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public MsgGroupInfo selectMsgGroupInfoById(Long id) {
        return msgGroupInfoMapper.selectMsgGroupInfoById(id);
    }

    @Override
    public List<MsgGroupInfo> selectMsgGroupInfoByGroups(List<String> userNames) {

        return null;
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param msgGroupInfo 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<MsgGroupInfo> selectMsgGroupInfoList(MsgGroupInfo msgGroupInfo) {
        return msgGroupInfoMapper.selectMsgGroupInfoList(msgGroupInfo);
    }

    @Override
    public MsgGroupInfo selectMsgGroupInfo(String group) {
        return msgGroupInfoMapper.selectMsgGroupInfo(group);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param msgGroupInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertMsgGroupInfo(MsgGroupInfo msgGroupInfo) {
        msgGroupInfo.setCreateTime(DateUtils.getNowDate());
        return msgGroupInfoMapper.insertMsgGroupInfo(msgGroupInfo);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param msgGroupInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateMsgGroupInfo(MsgGroupInfo msgGroupInfo) {
        msgGroupInfo.setUpdateTime(DateUtils.getNowDate());
        return msgGroupInfoMapper.updateMsgGroupInfo(msgGroupInfo);
    }

    @Override
    @Transactional
    public void addHxUserName(String group, List<JSONObject> members) {
        MsgGroupInfo info = msgGroupInfoMapper.selectMsgGroupInfo(group);
        if (ObjectUtils.isEmpty(info)) {
            throw new GlobalException("未获取到群信息");
        }
//        if (info.getNumbers().contains(username)) {
//            throw new GlobalException("成员已添加");
//        }
        try {
            List<String> hxIdList = new ArrayList<>();
            JSONObject content = JSONObject.parseObject(info.getNumbers());
            for (JSONObject member : members) {
                String hxid = member.getString("hxid");
                String type = member.getString("type");
                if (info.getNumbers().contains(hxid)) {
                    continue;
                }
                hxIdList.add(hxid);
                if (StringUtils.isEmpty(type)) {
                    type = "user";
                }
                content.put(hxid, type);
            }
            info.setNumbers(JSONObject.toJSONString(content));
            msgGroupInfoMapper.updateMsgGroupInfo(info);
            updateSessionList(info.getToGroup(), content.keySet());
        } catch (Exception e) {
            throw new ServiceException("添加失败！");
        }
    }

    @Override
    @Transactional
    public void removeHxUserName(String group, List<JSONObject> members) {
        MsgGroupInfo info = msgGroupInfoMapper.selectMsgGroupInfo(group);
        if (ObjectUtils.isEmpty(info)) {
            throw new GlobalException("未获取到群信息");
        }
//        if (info.getNumbers().contains(username)) {
//            throw new GlobalException("成员已添加");
//        }
        try {
            List<String> hxIdList = new ArrayList<>();
            JSONObject content = JSONObject.parseObject(info.getNumbers());
            for (JSONObject member : members) {
                String hxid = member.getString("hxid");
                if (StringUtils.isNotEmpty(hxid)) {
                    hxIdList.add(hxid);
                    content.remove(hxid);
                }
            }
            info.setNumbers(JSONObject.toJSONString(content));
            msgGroupInfoMapper.updateMsgGroupInfo(info);
            updateSessionList(info.getToGroup(), content.keySet());
        } catch (Exception e) {
            throw new ServiceException("移除失败！");
        }
    }

    //恢复群
    @Override
    public MsgGroupInfo recoveryGroupInfo(Long id) {
        MsgGroupInfo groupInfo = msgGroupInfoMapper.selectMsgGroupInfoById(id);
        if (Objects.nonNull(groupInfo)) {
            if (groupInfo.getDel() == 2) {
                JSONObject param = new JSONObject();
                param.put("groupId", groupInfo.getToGroup());
                param.put("groupName", groupInfo.getGroupName());
                param.put("description", groupInfo.getGroupDesc());
                String numbers = groupInfo.getNumbers();
                if (StringUtils.isNotEmpty(numbers)) {
                    JSONObject jsonObject = JSONObject.parseObject(numbers);
                    String member = "";
                    for (String s : jsonObject.keySet()) {
                        if (jsonObject.getString(s).equals("owner") || jsonObject.getString(s).equals("客服")) {
                            param.put("owner", s);
                        } else {
                            member += "," + s;
                        }
                    }
                    if (StringUtils.isNotEmpty(member)) {
                        member = member.substring(1);
                    }
                    param.put("members", member);
                }
                MsgGroupInfo info = new MsgGroupInfo();
                info.setId(groupInfo.getId());
                info.setDel(1);
                msgGroupInfoMapper.updateMsgGroupInfo(info);
                LambdaUpdateWrapper<SessionList> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.set(SessionList::getDel, 1).eq(SessionList::getSessionId, groupInfo.getToGroup()).eq(SessionList::getDel, 2);
                sessionListMapper.update(null, updateWrapper);
            }
        } else {
            throw new GlobalException("未获取到群信息");
        }
        return groupInfo;
    }

    public void updateSessionList(String groupId, Set<String> belongHxIdList) {
        SessionList sessionList =
                sessionListMapper.selectOne(new LambdaQueryWrapper<SessionList>().eq(SessionList::getSessionId,
                        groupId));
        if (StringUtils.isNotNull(sessionList)) {
            String belongHxIds = belongHxIdList.stream().collect(Collectors.joining(","));
            sessionList.setBelongHxIds(belongHxIds);
            sessionListMapper.updateById(sessionList);
        }
    }


    public static void main(String[] args) {
        //将json转换为key=value&key=value&...的形式
        String belongHxIds = "";
        JSONObject jsonObject = JSONObject.parseObject("{\"fh1691574664227\":\"owner\",\"fh1691554275199\":\"user\"}");
//        belongHxIds = jsonObject.keySet().stream().collect(Collectors.joining(","));
//        System.out.println(belongHxIds);
//        jsonObject.remove("fh1691574664227");
//        belongHxIds = jsonObject.keySet().stream().collect(Collectors.joining(","));
//        System.out.println(belongHxIds);
        System.out.println(jsonObject.get("123"));
        jsonObject.put("123", "44");
        System.out.println(jsonObject.get("123"));
        System.out.println(jsonObject);

    }

    @Override
    public JSONObject getAllNumbers(String groupId, String fromHxId) {
        if (StringUtils.isEmpty(fromHxId) || StringUtils.isEmpty(groupId)) {
            throw new GlobalException("群id或当前人hxId不能未空");
        }

        JSONObject object = new JSONObject();
        MsgGroupInfo info = msgGroupInfoMapper.selectMsgGroupInfo(groupId);
        if (ObjectUtils.isNotEmpty(info)) {
            if (StringUtils.isNotEmpty(info.getNumbers())) {
                Map<String, String> map = JSONObject.parseObject(info.getNumbers(), new TypeReference<Map<String,
                        String>>() {
                });
                List<String> collect = map.keySet().stream().collect(Collectors.toList()); //环信id集合
                List<SysUserIMRes> users = userMapper.getHXUserList(collect, fromHxId);
                for (SysUserIMRes user : users) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("hxid", user.getHxusername());
                    jsonObject.put("type", map.get(user.getHxusername()));
                    jsonObject.put("username", user.getNickName());
                    if (StringUtils.isNotEmpty(user.getAvatar())) {
                        jsonObject.put("avartarurl", user.getAvatar());
                    } else {
                        jsonObject.put("avartarurl", "https://game.ikbh.top/IMdefaultAvatar.png");
                    }
                    jsonObject.put("remarkName", user.getRemarkName());
                    object.put(user.getHxusername(), jsonObject);
                }
            } else {
                throw new GlobalException("缺少群成员信息");
            }
        } else {
            try {
            } catch (Exception e) {
                throw new GlobalException("群成员信息获取失败");
            }
        }
        return object;

    }

    @Override
    public void updateSettings(JSONObject params) {

        String groupId = params.getString("groupId");
        String groupname = params.getString("groupname");
        String description = params.getString("description");
        if (StringUtils.isEmpty(groupId)) {
            throw new GlobalException("群组id不能为空");
        }

        MsgGroupInfo msgGroupInfo = msgGroupInfoMapper.selectMsgGroupInfo(groupId);
        if (StringUtils.isNotNull(msgGroupInfo)) {
            if (StringUtils.isNotEmpty(groupname)) {
                msgGroupInfo.setGroupName(groupname);
            }
            if (StringUtils.isNotEmpty(description)) {
                msgGroupInfo.setGroupDesc(description);
            }
            msgGroupInfoMapper.updateMsgGroupInfo(msgGroupInfo);
        }
    }

    @Override
    public void updateGroupAnnouncement(JSONObject params) {
        String groupId = params.getString("groupId");
        String announcement = params.getString("announcement");
        if (StringUtils.isEmpty(groupId)) {
            throw new GlobalException("群组id不能为空");
        }
        MsgGroupInfo msgGroupInfo = msgGroupInfoMapper.selectMsgGroupInfo(groupId);
        if (StringUtils.isNotNull(msgGroupInfo)) {
            if (StringUtils.isNotEmpty(announcement)) {
                msgGroupInfo.setAnnouncement(announcement);
            }
            msgGroupInfoMapper.updateMsgGroupInfo(msgGroupInfo);
        }
    }

    @Override
    public void assignGroup​(JSONObject params) {
        String groupId = params.getString("groupId");
        String oldOwner = params.getString("oldOwner");
        String newOwner = params.getString("newOwner");
        String userType = params.getString("userType");
        if (StringUtils.isEmpty(groupId) || StringUtils.isEmpty(oldOwner) || StringUtils.isEmpty(newOwner)) {
            throw new GlobalException("群或者老群主或新群主不能为空");
        }
        MsgGroupInfo msgGroupInfo = msgGroupInfoMapper.selectMsgGroupInfo(groupId);
        if (StringUtils.isNotNull(msgGroupInfo)) {
            JSONObject content = JSONObject.parseObject(msgGroupInfo.getNumbers());
            if (StringUtils.isEmpty(userType)) {
                userType = "user";
            }
            if (StringUtils.isNotNull(content.get(oldOwner))) {
                content.put(oldOwner, userType);
            }
            if (StringUtils.isNotNull(content.get(newOwner))) {
                content.put(newOwner, "owner");
            }
            msgGroupInfo.setNumbers(JSONObject.toJSONString(content));
            msgGroupInfoMapper.updateMsgGroupInfo(msgGroupInfo);
        }
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteMsgGroupInfoByIds(Long[] ids) {
        List<Long> longs = Arrays.asList(ids);
        if (CollectionUtils.isNotEmpty(longs)) {
            List<MsgGroupInfo> groupInfo = msgGroupInfoMapper.selectMsgGroupInfoByIds(longs);
            for (MsgGroupInfo msgGroupInfo : groupInfo) {
            }
        }
        return 1;
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteMsgGroupInfoById(Long id) {
        return msgGroupInfoMapper.deleteMsgGroupInfoById(id);
    }
}
