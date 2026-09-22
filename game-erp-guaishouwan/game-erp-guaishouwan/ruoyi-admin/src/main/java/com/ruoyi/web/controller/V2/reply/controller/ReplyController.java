package com.ruoyi.web.controller.V2.reply.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.V2.reply.service.IReplyService;
import com.ruoyi.system.model.domain.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 快捷回复Controller
 * 
 * @author ruoyi
 * @date 2023-08-01
 */
@RestController
@RequestMapping("/account/reply")
public class ReplyController extends BaseController
{
    @Autowired
    private IReplyService replyService;

    /**
     * 查询快捷回复列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Reply reply)
    {
        startPage();
        List<Reply> list = replyService.selectReplyList(reply);
        return getDataTable(list);
    }

    /**
     * 查询快捷回复列表
     */
    @GetMapping("/allList")
    public AjaxResult allList(Reply reply)
    {
        List<Reply> list = replyService.allList(reply);
        return AjaxResult.success(list);
    }

    /**
     * 导出快捷回复列表
     */
    @Log(title = "快捷回复", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Reply reply)
    {
        List<Reply> list = replyService.selectReplyList(reply);
        ExcelUtil<Reply> util = new ExcelUtil<Reply>(Reply.class);
        return util.exportExcel(list, "快捷回复数据");
    }

    /**
     * 获取快捷回复详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(replyService.selectReplyById(id));
    }

    /**
     * 新增快捷回复
     */
    @Log(title = "快捷回复", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Reply reply)
    {
        return toAjax(replyService.insertReply(reply));
    }

    /**
     * 修改快捷回复
     */
    @Log(title = "快捷回复", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Reply reply)
    {
        return toAjax(replyService.updateReply(reply));
    }

    /**
     * 删除快捷回复
     */
    @Log(title = "快捷回复", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(replyService.deleteReplyByIds(ids));
    }
}
