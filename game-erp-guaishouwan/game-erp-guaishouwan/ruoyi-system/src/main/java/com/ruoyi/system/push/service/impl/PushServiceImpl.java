package com.ruoyi.system.push.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.im.service.IUserRemarkService;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.MsgGroupInfo;
import com.ruoyi.system.model.domain.SessionList;
import com.ruoyi.system.model.domain.UserRemark;
import com.ruoyi.system.push.service.IPushService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zyy
 * @date 2023-10-02
 */
@Slf4j
@Service
public class PushServiceImpl implements IPushService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    @Autowired
    private IUserRemarkService userRemarkService;

    /**
     * @param getuiAlias 设备别名数组 用户的username字段  []
     * @param title      推送标题
     * @param content    推送内容
     * @param payload    自定义字段传入JSON 如 {"type":"1000"}
     *                   买家: 付款成功通知   1000      跳转 我的买单 交易中 列表
     *                   买家: 订单交易成功   1001      跳转 我的买单 交易成功 列表
     *                   卖家:商品上架成功消息推送 2000  跳转 我的卖单 已上架
     *                   卖家:商品上架失败消息推送 2001  跳转 我的卖单 审核失败
     *                   卖家:商品出售通知  2002       跳转 我的卖单 交易中
     *                   卖家:订单交易成功  2003       跳转 我的卖单 已出售
     *                   IM消息:8000                 跳转 消息列表
     * @param pushType   1、IM消息 2、订单消息
     */
    @Async("threadPoolTaskExecutor")
    @Override
    public void push(String[] getuiAlias, String title, String content, JSONObject payload, Integer pushType) {
        System.out.println("发送方法里面：" + Thread.currentThread().getName());
        if (StringUtils.isNull(getuiAlias) || getuiAlias.length < 1) {
            return;
        }
        Map<String, Object> pushMap = new HashMap<>();
        pushMap.put("type" , "sendMessage");
        pushMap.put("params" , getParams(getuiAlias, title, content, payload, pushType));
//        String response = HttpUtil.postRequest("https://uni.feihengdaishou.com/push" , pushMap,
//                ContentType.APPLICATION_JSON, null, null);
//        System.out.println(response);
    }

    @Async("threadPoolTaskExecutor")
    @Override
    public void pushIM(Map<String, Object> pushInfoMap) {
        System.out.println("发送IM方法里面：" + Thread.currentThread().getName());
        SessionList sessionList1 = (SessionList) pushInfoMap.get("sessionList");
        if (StringUtils.isNull(sessionList1)) {
            return;
        }
        String[] getuiAlias = null;
        String type = "用户";
        if (StringUtils.equals(sessionList1.getMsgType(), "1")) {
            SysUser toUser = (SysUser) pushInfoMap.get("toUser");
            SysUser fromUser = (SysUser) pushInfoMap.get("fromUser");
            type = fromUser.getNickName();
            if (StringUtils.isNotNull(toUser)) {
                LambdaQueryWrapper<UserRemark> queryWrapper = new LambdaQueryWrapper();
                queryWrapper.eq(UserRemark::getFromHxId, sessionList1.getToUser());
                queryWrapper.eq(UserRemark::getToHxId, sessionList1.getFormUser());
                List<UserRemark> userRemarkList = userRemarkService.list(queryWrapper);
                if (CollectionUtils.isNotEmpty(userRemarkList)) {
                    type = userRemarkList.get(0).getRemarkName();
                }
                getuiAlias = new String[]{toUser.getUserName()};
            }
        } else if (StringUtils.equals(sessionList1.getMsgType(), "2")) {
            MsgGroupInfo msgGroupInfo = (MsgGroupInfo) pushInfoMap.get("groupInfo");
            if (StringUtils.isNull(msgGroupInfo)) {
                return;
            }
            JSONObject numbersJSON = JSONObject.parseObject(msgGroupInfo.getNumbers());
            List<SysDictData> sysDictDatas = sysDictDataMapper.selectDictDataByType("IMGroupMembersType");
            Map<String, String> iMGroupMembersTypeMap = new HashMap<>();
            for (SysDictData sysDictData : sysDictDatas) {
                iMGroupMembersTypeMap.put(sysDictData.getDictValue(), sysDictData.getDictLabel());
            }
            List<String> hxuserNameList = new ArrayList<>();
            for (String key : numbersJSON.keySet()) {
                if (StringUtils.equals(sessionList1.getFormUser(), key)) {
                    Object value = numbersJSON.get(key);
                    String valueStr = String.valueOf(value);
                    if (StringUtils.isNotEmpty(valueStr)) {
                        if (StringUtils.equals(valueStr, "owner")) {
                            type = "群主";
                            continue;
                        }
                    }
                    if (StringUtils.isNotEmpty(iMGroupMembersTypeMap.get(value))) {
                        type = iMGroupMembersTypeMap.get(value);
                        continue;
                    }
                }
                hxuserNameList.add(key);
            }
            System.out.println(type);
            List<String> userNames = sysUserMapper.selectUserNameByHxUserName(hxuserNameList);
            getuiAlias = userNames.toArray(new String[userNames.size()]);
        } else {
            return;
        }
        String title = "【您有一条新消息】";
        String content = getContent(sessionList1);
        String pushContent = "【" + type + "：" + content + "】";
        JSONObject payload = new JSONObject();
        payload.put("type" , "8000");
        Integer pushType = 1;
        push(getuiAlias, title, pushContent, payload, pushType);
    }

    String getContent(SessionList sessionList1) {
        String content = sessionList1.getContent();
        if (StringUtils.equals(sessionList1.getCustomType(), "audio")) {
            content = "[语音]";
        } else if (StringUtils.equals(sessionList1.getCustomType(), "goods")) {
            content = "[商品]";
        } else if (StringUtils.equals(sessionList1.getCustomType(), "image")) {
            content = "[图片]";
        } else if (StringUtils.equals(sessionList1.getCustomType(), "order")) {

        } else if (StringUtils.equals(sessionList1.getCustomType(), "text")) {

        } else if (StringUtils.equals(sessionList1.getCustomType(), "video")) {
            content = "[视频]";
        }
        return content;
    }

    Map<String, Object> getParams(String[] getuiAlias, String title, String content, JSONObject payload,
                                  Integer pushType) {
        Map<String, Object> params = new HashMap<>();
        params.put("force_notification" , false);
        params.put("platform" , "ALL");
        params.put("content_available" , 0);

        params.put("title" , title);
        params.put("content" , content);
        params.put("getui_alias" , getuiAlias);

        params.put("payload" , getPayload(payload));
        params.put("channel" , getChannel(pushType));
        params.put("options" , getOptions(pushType));

        return params;
    }

    JSONObject getPayload(JSONObject payloadJson) {
        JSONObject payload = new JSONObject();
        payload.put("text" , payloadJson);
        return payload;
    }

    JSONObject getChannel(Integer pushType) {
        JSONObject channel = new JSONObject();
        if (pushType == 1) {
            channel.put("XM" , "111833");
            channel.put("OP" , "ltxx");
        } else if (pushType == 2) {
            channel.put("XM" , "111832");
            channel.put("OP" , "ddxx");
        }
        channel.put("HW" , "NORMAL");
        channel.put("VV" , "1");
        return channel;
    }

    JSONObject getOptions(Integer pushType) {
        JSONObject options = new JSONObject();
        JSONObject hw = new JSONObject();
        JSONObject vv = new JSONObject();
        if (pushType == 1) {
            hw.put("/message/android/category" , "IM");
            vv.put("/category" , "IM");
        } else if (pushType == 2) {
            hw.put("/message/android/category" , "EXPRESS");
            vv.put("/category" , "ORDER");
        }
        options.put("VV" , vv);
        options.put("HW" , hw);
        return options;
    }
}
