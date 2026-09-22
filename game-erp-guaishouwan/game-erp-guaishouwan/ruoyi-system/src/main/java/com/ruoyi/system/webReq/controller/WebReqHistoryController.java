package com.ruoyi.system.webReq.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.webReq.domain.WebReqHistory;
import com.ruoyi.system.webReq.service.IWebReqHistoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户端请求记录Controller
 *
 * @author ruoyi
 * @date 2024-12-04
 */
@RestController
@RequestMapping("/webReq/history")
public class WebReqHistoryController extends BaseController {
    @Autowired
    private IWebReqHistoryService webReqHistoryService;

    /**
     * 查询用户端请求记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WebReqHistory webReqHistory) {
        startPage();
        List<WebReqHistory> list = webReqHistoryService.selectWebReqHistoryList(webReqHistory);
        return getDataTable(list);
    }

    /**
     * 导出用户端请求记录列表
     */
    @GetMapping("/export")
    public AjaxResult export(WebReqHistory webReqHistory) {
        List<WebReqHistory> list = webReqHistoryService.selectWebReqHistoryList(webReqHistory);
        ExcelUtil<WebReqHistory> util = new ExcelUtil<WebReqHistory>(WebReqHistory.class);
        return util.exportExcel(list, "用户端请求记录数据");
    }

    /**
     * 获取用户端请求记录详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(webReqHistoryService.selectWebReqHistoryById(id));
    }

    /**
     * 新增用户端请求记录
     */
    @PostMapping
    public AjaxResult add(@RequestBody WebReqHistory webReqHistory) {
        return toAjax(webReqHistoryService.insertWebReqHistory(webReqHistory));
    }

    /**
     * 修改用户端请求记录
     */
    @PutMapping
    public AjaxResult edit(@RequestBody WebReqHistory webReqHistory) {
        return toAjax(webReqHistoryService.updateWebReqHistory(webReqHistory));
    }

    /**
     * 删除用户端请求记录
     */
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(webReqHistoryService.deleteWebReqHistoryByIds(ids));
    }

    @GetMapping("/statistics/statistics")
    public AjaxResult statistics(WebReqHistory webReqHistory){
        return AjaxResult.success(webReqHistoryService.statistics(webReqHistory));
    }
}
