package com.ruoyi.system.steam.controller;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.steam.domain.SteamCollectTask;
import com.ruoyi.system.steam.service.ISteamCollectTaskService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * steam任务执行Controller
 *
 * @author ruoyi
 * @date 2025-03-21
 */
@RestController
@RequestMapping("/steam/collect")
public class SteamCollectTaskController extends BaseController {
    @Autowired
    private ISteamCollectTaskService steamCollectTaskService;

    /**
     * 查询steam任务执行列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SteamCollectTask steamCollectTask) {

        Long userId = SecurityUtils.getUserId();
        if (!Objects.equals(userId,1L)){
            steamCollectTask.setCreateId(userId);
        }

        startPage();
        List<SteamCollectTask> list = steamCollectTaskService.selectSteamCollectTaskList(steamCollectTask);
        return getDataTable(list);
    }

    /**
     * 导出steam任务执行列表
     */
    @Log(title = "steam任务执行", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SteamCollectTask steamCollectTask) {
        List<SteamCollectTask> list = steamCollectTaskService.selectSteamCollectTaskList(steamCollectTask);
        ExcelUtil<SteamCollectTask> util = new ExcelUtil<SteamCollectTask>(SteamCollectTask.class);
        return util.exportExcel(list, "steam任务执行数据");
    }

    /**
     * 获取steam任务执行详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(steamCollectTaskService.selectSteamCollectTaskById(id));
    }

    /**
     * 新增steam任务执行
     */
    @Log(title = "steam任务执行", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SteamCollectTask steamCollectTask) {
        steamCollectTask.setCreateId(SecurityUtils.getUserId());
        return toAjax(steamCollectTaskService.insertSteamCollectTask(steamCollectTask));
    }

    /**
     * 修改steam任务执行
     */
    @Log(title = "steam任务执行", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SteamCollectTask steamCollectTask) {
        return toAjax(steamCollectTaskService.updateSteamCollectTask(steamCollectTask));
    }

    /**
     * 回收
     */
    @Log(title = "回收", businessType = BusinessType.UPDATE)
    @PutMapping("/backList")
    public AjaxResult backList(@RequestBody SteamCollectTask steamCollectTask) {
        return toAjax(steamCollectTaskService.backList(steamCollectTask));
    }

    /**
     * 重新校验
     */
    @Log(title = "重新校验", businessType = BusinessType.UPDATE)
    @PutMapping("/restart")
    @RepeatSubmit
    public AjaxResult restart(@RequestBody SteamCollectTask steamCollectTask) {
        return toAjax(steamCollectTaskService.restart(steamCollectTask.getId()));
    }

    @GetMapping("/demo")
    public void demo(){
        SteamCollectTask task = new SteamCollectTask();
        task.setStatus(4);
        task.setEmailStatus(4);
        task.setPhoneStatus(4);
        List<SteamCollectTask> tasks = steamCollectTaskService.selectSteamCollectTaskList(task);
        for (SteamCollectTask steamCollectTask : tasks) {
            steamCollectTaskService.restart(steamCollectTask.getId());
        }
    }

    public static void main(String[] args) {
        System.out.println("15670543721".length());
    }

    /**
     * 删除steam任务执行
     */
    @Log(title = "steam任务执行", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(steamCollectTaskService.deleteSteamCollectTaskByIds(ids));
    }
}
