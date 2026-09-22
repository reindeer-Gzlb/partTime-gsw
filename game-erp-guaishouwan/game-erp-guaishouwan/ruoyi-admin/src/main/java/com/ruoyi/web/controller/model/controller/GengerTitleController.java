package com.ruoyi.web.controller.model.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.gen.IGenAPI;
import com.ruoyi.system.mapper.SysUserMapper;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author lz
 * @version 1.0
 * @date 2022/6/28 20:56
 */
@RestController
@RequestMapping("/gen")
public class GengerTitleController extends BaseController {

    @Autowired
    private IGenAPI iGenAPI;
    @Autowired
    private SysUserMapper sysUserMapper;

    @ResponseBody
    @GetMapping("/roleList")
    public AjaxResult GameType(@RequestParam Map<String,Object> params)
    {
        //459926613
        List<JSONObject> result = iGenAPI.getRoleId(MapUtils.getString(params,"campId"));
        return AjaxResult.success(result);
    }
    @ResponseBody
    @GetMapping("/gengerTitle")
    public AjaxResult gengerTitle(@RequestParam Map<String,Object> params)
    {
        //459926613

        SysUser sysUser= sysUserMapper.selectUserById(getUserId());
        Map<String, Object> stringObjectMap = iGenAPI.genTitleBack(params);
        return AjaxResult.success(stringObjectMap);
    }

    @ResponseBody
    @GetMapping("/getHePingParams")
    public AjaxResult getHePingParams(@RequestParam Map<String,Object> params)
    {
        //903956401
//        Map<String,Object> result = iGenAPI.getHePingParams(params);
        return AjaxResult.success(null);
    }

    @ResponseBody
    @GetMapping("/wzryGenger")
    public AjaxResult wzryGenger(@RequestParam Map<String,Object> params)
    {
        //903956401
//        Map<String,Object> result = iGenAPI.wzryGenger(params);
        return AjaxResult.success();
    }






}
