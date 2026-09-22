package com.ruoyi.web.controller.V2.appVersion.controller;

import cn.hutool.http.HttpRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.V2.appVersion.service.IAppVersionService;
import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.system.mapper.SysConfigMapper;
import com.ruoyi.system.model.domain.AppVersion;
import com.ruoyi.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/app/version")
public class AppVersionController extends BaseController {
    @Autowired
    IAppVersionService appVersionService;
    @Autowired
    private SysConfigMapper configMapper;

    @GetMapping("/list")
    public TableDataInfo list(AppVersion appVersion) {
        LambdaQueryWrapper<AppVersion> queryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(appVersion.getAppType())) {
            queryWrapper.eq(AppVersion::getAppType, appVersion.getAppType());
        }
        queryWrapper.orderByDesc(AppVersion::getCreateTime);
        return getDataTable(appVersionService.list(queryWrapper));
    }

    /**
     * 获取AppVersion详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(appVersionService.getById(id));
    }

    /**
     * 新增AppVersion
     */
    @Log(title = "appversion", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppVersion appVersion) {
        return toAjax(appVersionService.save(appVersion));
    }

    /**
     * 修改AppVersion
     */
    @Log(title = "appversion", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppVersion appVersion) {
        return toAjax(appVersionService.updateById(appVersion));
    }

    /**
     * 删除AppVersion
     */
    @Log(title = "appversion", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(appVersionService.removeByIds(Arrays.asList(ids)));
    }

    /**
     * 获取最新appversion最新一条
     */
    @GetMapping(value = "/lastOne")
    public AjaxResult lastOne(AppVersion appVersion) {
        LambdaQueryWrapper<AppVersion> queryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(appVersion.getAppType())) {
            queryWrapper.eq(AppVersion::getAppType, appVersion.getAppType());
        }
        queryWrapper.orderByDesc(AppVersion::getCreateTime).last("limit 1");
        return AjaxResult.success(appVersionService.getOne(queryWrapper));
    }

    /**
     * app开关
     */
    @GetMapping(value = "/appSwitch")
    public boolean getConfigKey()
    {
        SysConfig config = new SysConfig();
        config.setConfigKey("iosSwitch");
        SysConfig retConfig = configMapper.selectConfig(config);
        String configValue = retConfig.getConfigValue();
        if (Objects.equals("2",configValue)){
            return true;
        }else {
            return false;
        }
    }

    public static void main(String[] args) {

        String url = "https://api.sybaopei.com/api/login";

        Map<String,Object> param = new HashMap<>();
        param.put("mobile","15532615950");
        param.put("type","0");
        param.put("password","123456");

        String body = HttpRequest.post(url).header("Content-Type", "multipart/form-data").body(String.valueOf(param)).execute().body();


        System.out.println(body);

    }
}
