package com.ruoyi.web.controller.V2.web.conntroller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.gen.IGenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

/**
 * @author lz
 * @version 1.0
 * @date 2022/5/10 18:34
 */
@RestController
@RequestMapping("qrCode")
public class LOLDataController {


    @Autowired
    private IGenAPI genAPI;


    /**
     * 获取腾讯二维码
     */
    @GetMapping("/tdCode")
    public AjaxResult tdCode() {
        return AjaxResult.success(genAPI.tdCode());
    }


    /**
     * 获取lol端游数据
     */
    @GetMapping("/getToken")
    public AjaxResult getToken(@RequestParam Map<String,Objects> params) {

        String result = genAPI.getToken(params);
        if (StringUtils.isNotEmpty(result)){
            JSONObject jsonObject = JSONObject.parseObject(result);
            return AjaxResult.success(jsonObject);
        }else {
            return  AjaxResult.success(result);
        }
    }



}
