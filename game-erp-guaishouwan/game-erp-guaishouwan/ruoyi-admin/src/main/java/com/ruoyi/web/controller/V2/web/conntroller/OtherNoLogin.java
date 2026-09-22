package com.ruoyi.web.controller.V2.web.conntroller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.easemob.im.server.EMException;
import com.easemob.im.server.EMService;
import com.easemob.im.server.model.EMGroup;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.V2.im.mapper.SessionListMapper;
import com.ruoyi.system.V2.im.service.IMsgGroupInfoService;
import com.ruoyi.system.V2.im.service.ISessionListService;
import com.ruoyi.system.model.domain.MsgGroupInfo;
import com.ruoyi.system.model.domain.SessionList;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/other")
public class OtherNoLogin {
    @Autowired
    private IMsgGroupInfoService msgGroupInfoService;
    @Autowired
    private SessionListMapper sessionListMapper;
    @Autowired
    private ISessionListService sessionListService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private EMService service;


    @GetMapping("/getOtherUserHX")
    public AjaxResult getOtherUserHX(String otherUserId, String from) {
        HashMap<String, String> map = new HashMap<>();
        return AjaxResult.success(map);
    }

    @GetMapping("/getOtherGroupInfo")
    public AjaxResult getOtherUserInfo(String groupId, String fromHxId) {
        return AjaxResult.success(msgGroupInfoService.getAllNumbers(groupId, fromHxId));
    }

    /**
     * 环信group信息一键更新  (需要优化有才能用)
     */
//    @GetMapping("/demo")
    public AjaxResult upGroupInfos() {
        List<MsgGroupInfo> msgGroupInfos = msgGroupInfoService.selectMsgGroupInfoList(new MsgGroupInfo());
        Map<String, MsgGroupInfo> map = msgGroupInfos.stream().collect(Collectors.toMap(MsgGroupInfo::getToGroup,
                MsgGroupInfo -> MsgGroupInfo));
        String activeProfile = SpringUtils.getActiveProfile();
        for (String s : map.keySet()) {
            MsgGroupInfo info = map.get(s);
            if (info.getDel() == 2) {
                continue;
            }
            info.setDel(2);
            msgGroupInfoService.updateMsgGroupInfo(info);
            sessionListService.update(new LambdaUpdateWrapper<SessionList>()
                    .set(SessionList::getDel, 2)
                    .eq(SessionList::getSessionId, info.getToGroup()));
            System.out.println("走删除 " + JSONObject.toJSONString(info.getToGroup()));
        }
        return AjaxResult.success();
    }

    public MsgGroupInfo convertDomain(EMGroup group) {
        MsgGroupInfo info = new MsgGroupInfo();
        info.setToGroup(group.getGroupId());
        info.setGroupName(group.getName());
        info.setGroupDesc(group.getDescription());
        info.setDel(1);
        HashMap<String, String> map = new HashMap<>();
        map.put(group.getOwner(), "owner");
        for (String member : group.getAffiliations().getMembers()) {
            map.put(member, "user");
        }
        info.setNumbers(JSONObject.toJSONString(map));
        return info;
    }

    /**
     * 环信group信息一键更新
     */
    @GetMapping("/updateNN")
    public AjaxResult updateNN() {
        SysUser sysUserReq = new SysUser();
        List<SysUser> sysUsers = userService.selectUserList(sysUserReq);
        System.out.println(sysUsers.size());
        for (SysUser user : sysUsers) {
            if (StringUtils.isNotEmpty(user.getHxusername())) {
                if (StringUtils.equals(user.getNickName(), user.getPhonenumber())) {
                    System.out.println("user.getNickName：" + user.getPhonenumber());
                    System.out.println("user.getPhonenumber：" + user.getPhonenumber());
                    user.setNickName("用户" + DateUtils.randomTen());
                    if (StringUtils.isNotEmpty(user.getNickName()) || StringUtils.isNotEmpty(user.getAvatar()) ||
                            StringUtils.isNotEmpty(user.getPhonenumber())) {
                        HashMap<String, String> map = new HashMap<>();
                        if (StringUtils.isNotEmpty(user.getNickName())) {
                            map.put("nickname", user.getNickName());
                        }
                        if (StringUtils.isNotEmpty(user.getAvatar())) {
                            map.put("avatar", user.getAvatar());
                            map.put("avatarurl", user.getAvatar());
                        }
                        if (StringUtils.isNotEmpty(user.getPhonenumber())) {
                            map.put("phone", user.getPhonenumber());
                        }
                        try {
                            service.metadata().setMetadataToUser(user.getHxusername(), map).block();
                        } catch (EMException e) {
                            e.getErrorCode();
                            e.getMessage();
                        }
                        userService.updateUserProfile(user);
                    }
                }
            }
        }
        return AjaxResult.success();
    }



    public static void main(String[] args) {
//        System.out.println("用户" + randomTen());
        DateUtils.getTimeStempM();
    }

}