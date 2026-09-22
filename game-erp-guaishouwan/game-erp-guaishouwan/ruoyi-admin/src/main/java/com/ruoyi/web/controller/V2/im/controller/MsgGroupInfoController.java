package com.ruoyi.web.controller.V2.im.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.V2.im.service.IMsgGroupInfoService;
import com.ruoyi.system.model.domain.MsgGroupInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 【请填写功能名称】Controller
 *
 * @author ruoyi
 * @date 2023-08-03
 */
@RestController
@RequestMapping("/group/info")
public class MsgGroupInfoController extends BaseController {
    @Autowired
    private IMsgGroupInfoService msgGroupInfoService;

    /**
     * 查询群列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MsgGroupInfo msgGroupInfo) {
        startPage();
        List<MsgGroupInfo> list = msgGroupInfoService.selectMsgGroupInfoList(msgGroupInfo);
        return getDataTable(list);
    }

    /**
     * 导出
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MsgGroupInfo msgGroupInfo) {
        List<MsgGroupInfo> list = msgGroupInfoService.selectMsgGroupInfoList(msgGroupInfo);
        ExcelUtil<MsgGroupInfo> util = new ExcelUtil<MsgGroupInfo>(MsgGroupInfo.class);
        return util.exportExcel(list, "【请填写功能名称】数据");
    }

    /**
     * 获取获取群信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(msgGroupInfoService.selectMsgGroupInfoById(id));
    }

    /**
     * 获取获取群信息
     */
    @GetMapping(value = "/{group}")
    public AjaxResult getInfos(@PathVariable("group") String group) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isNotEmpty(group)) {
            list = Arrays.asList(group.split(","));
        } else {
            list.add("0");
        }
        return AjaxResult.success(msgGroupInfoService.selectMsgGroupInfoByGroups(list));
    }

    /**
     * 新增
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MsgGroupInfo msgGroupInfo) {
        return toAjax(msgGroupInfoService.insertMsgGroupInfo(msgGroupInfo));
    }

    /**
     * 修改
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MsgGroupInfo msgGroupInfo) {
        return toAjax(msgGroupInfoService.updateMsgGroupInfo(msgGroupInfo));
    }

    /**
     * 添加群成员
     */
    @PostMapping("/addHxUserName")
    public AjaxResult addUser(@RequestBody JSONObject params) {
        String groupId = params.getString("groupId");
        List<JSONObject> members = params.getJSONArray("members").toJavaList(JSONObject.class);
//        String type = params.getString("type");
        if (StringUtils.isEmpty(groupId)) {
            throw new GlobalException("groupId不能为空");
        }
        if (CollectionUtils.isEmpty(members)) {
            throw new GlobalException("members不能为空");
        }
        msgGroupInfoService.addHxUserName(groupId, members);
        return AjaxResult.success();
    }

    /**
     * 删除群成员
     */
    @PostMapping("/removeHxUserName")
    public AjaxResult removeHxUserName(@RequestBody JSONObject params) {
        String groupId = params.getString("groupId");
        List<JSONObject> members = params.getJSONArray("members").toJavaList(JSONObject.class);
//        String type = params.getString("type");
        if (StringUtils.isEmpty(groupId)) {
            throw new GlobalException("groupId不能为空");
        }
        if (CollectionUtils.isEmpty(members)) {
            throw new GlobalException("members不能为空");
        }
        msgGroupInfoService.removeHxUserName(groupId, members);
        return AjaxResult.success();
    }

    /**
     * 更新群信息
     *
     * @return
     */
    @PostMapping("/updateSettings")
    public AjaxResult updateSettings(@RequestBody JSONObject contentBody) {
        msgGroupInfoService.updateSettings(contentBody);
        return AjaxResult.success();
    }

    /**
     * 更新群公告
     *
     * @return
     */
    @PostMapping("/updateAnnouncement")
    public AjaxResult updateAnnouncement(@RequestBody JSONObject contentBody) {
        msgGroupInfoService.updateGroupAnnouncement(contentBody);
        return AjaxResult.success();
    }

    /**
     * 转让群组
     *
     * @return
     */
    @PostMapping("/assignGroup")
    public AjaxResult assignGroup​(@RequestBody JSONObject params) {
        msgGroupInfoService.assignGroup​(params);
        return AjaxResult.success();
    }

    /**
     * 删除
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(msgGroupInfoService.deleteMsgGroupInfoByIds(ids));
    }

    /**
     * 获取群成员信息
     */
    @GetMapping("/getAllNumbers")
    public AjaxResult getAllNumbers(String groupId, String fromHxId) {
        return AjaxResult.success(msgGroupInfoService.getAllNumbers(groupId, fromHxId));
    }


}
