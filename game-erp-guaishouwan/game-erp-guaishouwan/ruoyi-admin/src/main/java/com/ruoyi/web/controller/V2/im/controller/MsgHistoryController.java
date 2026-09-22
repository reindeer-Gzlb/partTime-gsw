package com.ruoyi.web.controller.V2.im.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.V2.im.mapper.MsgHistoryMapper;
import com.ruoyi.system.V2.im.service.IMsgHistoryService;
import com.ruoyi.system.model.domain.MsgHistory;
import com.ruoyi.system.model.dto.msghistory.MsgHistoryAllSessionRes;
import com.ruoyi.system.model.dto.msghistory.MsgHistoryReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 消息历史记录Controller
 *
 * @author ruoyi
 * @date 2023-06-02
 */
@RestController
@RequestMapping("/msg/history")
public class MsgHistoryController extends BaseController {
    @Autowired
    private IMsgHistoryService msgHistoryService;
    @Autowired
    private MsgHistoryMapper msgHistoryMapper;

    /**
     * 查询消息历史记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MsgHistory msgHistory) {
        startPage();
        List<MsgHistory> list = msgHistoryService.selectMsgHistoryList(msgHistory);
        Collections.sort(list, (a, b) -> {
            return Long.compare(a.getId(), b.getId());
        });
//        Collections.sort(list, (o1, o2) -> Long.compare(o2.getId(), o1.getId()));
        return getDataTable(list);
    }

    /**
     * 导出消息历史记录列表
     */
    @Log(title = "消息历史记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MsgHistory msgHistory) {
        List<MsgHistory> list = msgHistoryService.selectMsgHistoryList(msgHistory);
        ExcelUtil<MsgHistory> util = new ExcelUtil<MsgHistory>(MsgHistory.class);
        return util.exportExcel(list, "消息历史记录数据");
    }

    /**
     * 获取消息历史记录详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(msgHistoryService.selectMsgHistoryById(id));
    }

    /**
     * 新增消息历史记录
     */
    @Log(title = "消息历史记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MsgHistory msgHistory) {
        return toAjax(msgHistoryService.insertMsgHistory(msgHistory));
    }

    /**
     * 修改消息历史记录
     */
    @Log(title = "消息历史记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MsgHistory msgHistory) {
        return toAjax(msgHistoryService.updateMsgHistory(msgHistory));
    }

    /**
     * 删除消息历史记录
     */
    @Log(title = "消息历史记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(msgHistoryService.deleteMsgHistoryByIds(ids));
    }


    @GetMapping("/recentList")
    public TableDataInfo recentList(MsgHistoryReq msgHistoryReq) {
        List<MsgHistory> msgHistories = new ArrayList<>();
        if (msgHistoryReq.getType() == 1) {
            msgHistories = msgHistoryMapper.selectUpMsgHistoryList(msgHistoryReq);
            Collections.sort(msgHistories, (a, b) -> {
                return Long.compare(a.getId(), b.getId());
            });
        } else if (msgHistoryReq.getType() == 2) {
            msgHistories = msgHistoryMapper.selectDownMsgHistoryList(msgHistoryReq);
        } else if (msgHistoryReq.getType() == 3) {
            msgHistories = msgHistoryMapper.selectUpAndDownMsgHistoryList(msgHistoryReq);
            Collections.sort(msgHistories, (a, b) -> {
                return Long.compare(a.getId(), b.getId());
            });
        } else {
            throw new GlobalException("不支持的type查询");
        }
        return getDataTable(msgHistories);
    }

    /**
     * 会话内查询消息历史记录列表
     */
    @GetMapping("/searchInSession")
    public TableDataInfo searchInSession(MsgHistory msgHistory) {
        List<MsgHistory> list = msgHistoryService.selectMsgHistoryList(msgHistory);
        Collections.sort(list, (b, a) -> {
            return Long.compare(a.getId(), b.getId());
        });
        return getDataTable(list);
    }

    /**
     * 所有会话查询消息历史记录列表
     */
    @GetMapping("/searchInAllSession")
    public TableDataInfo searchInAllSession(MsgHistory msgHistory) {
        if (StringUtils.isEmpty(msgHistory.getContent())) {
            return null;
        }
        if(StringUtils.isEmpty(msgHistory.getContent().trim())){
            return null;
        }
        if(StringUtils.isEmpty(msgHistory.getFormUser())){
            return null;
        }
        startPage();
        List<MsgHistoryAllSessionRes> list = msgHistoryService.selectAllSessionMsgHistoryList(msgHistory);
        return getDataTable(list);
    }

    public static void main(String[] args) {
        String s = "fh1691554275199";
        String s1 = "fh1691392929090";
        System.out.println(s.compareTo(s1));

    }
}
