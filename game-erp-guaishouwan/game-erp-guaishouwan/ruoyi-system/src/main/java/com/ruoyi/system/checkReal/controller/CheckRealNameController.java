package com.ruoyi.system.checkReal.controller;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.system.V2.tool.service.IBlackApi;
import com.ruoyi.system.checkReal.domain.CheckRealInfo;
import com.ruoyi.system.checkReal.service.ICheckRealInfoService;
import com.ruoyi.system.model.domain.Black;
import com.ruoyi.system.model.domain.HBlack;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysDictTypeService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lz
 * @version 1.0
 * @date 2022/6/27 14:43
 */
@RestController
@RequestMapping("/checkRealName")
public class CheckRealNameController extends BaseController {

    @Autowired
    private ISysDictTypeService dictTypeService;
    @Autowired
    private ICheckRealInfoService checkRealInfoService;

    @PostMapping("/checkPhoneAndRealName")
    public AjaxResult checkPhoneAndRealName(@RequestBody Map<String, Object> map) {

        // 存字典
        String userId = "242";
        String uid = "2jpqifj7";

        String inputMobile = MapUtils.getString(map, "inputMobile");
        String inputName = MapUtils.getString(map, "inputName");
        if (StringUtils.isEmpty(inputMobile)) {
            throw new GlobalException("手机号 是空的");
        }
        if (StringUtils.isEmpty(inputName)) {
            throw new GlobalException("实名 是空的");
        }
        List<SysDictData> dataType = dictTypeService.selectDictDataByType("checkPhoneAndRealName");
             for (SysDictData sysDictData : dataType) {
            if (StringUtils.equals(sysDictData.getDictLabel(), "userId")) {
                userId = sysDictData.getDictValue();
            }
            if (StringUtils.equals(sysDictData.getDictLabel(), "uid")) {
                uid = sysDictData.getDictValue();
            }
        }

        String url = "http://mqtt.cc.maimaihao.com/api/realName";

        Map<String, Object> param = new HashMap<>();
        param.put("inputMobile", inputMobile);
        param.put("inputName", inputName);
        String body = null;
        try {
            body = HttpRequest.post(url)
                    .header("userId", userId)
                    .header("uid", uid)
                    .body(JSONObject.toJSONString(param))
                    .execute().body();
//            {"msg":"操作成功","code":200,"data":{"carrier":"china_union","result_message":"核验一致","result_code":8300,"state":1,"request_id":"TIDd87b984e5bba4d3c95372da054bb3961","status":"OK"}}
//            {"msg":"操作成功","code":200,"data":{"carrier":"china_union","result_message":"核验不一致","result_code":8301,"state":2,"request_id":"TIDf8338331974641868b5df6a6695f3a6e","status":"OK"}}
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(body)) {
            throw new GlobalException("核验失败,请联系管理人员");
        }
        JSONObject response = JSONObject.parseObject(body);
        if (!StringUtils.equals(response.getString("code"), "200")) {
            throw new GlobalException("核验失败 ," + response.getString("msg"));
        }
        JSONObject data = response.getJSONObject("data");

        CheckRealInfo realInfo = new CheckRealInfo();
        realInfo.setInputMobile(inputMobile);
        realInfo.setInputName(inputName);
        realInfo.setCarrier(data.getString("carrier"));
        realInfo.setResultMessage(data.getString("result_message"));
        realInfo.setResultCode(data.getString("result_code"));
        realInfo.setState(data.getString("state"));
        realInfo.setRequestId(data.getString("request_id"));
        realInfo.setStatus(data.getString("status"));

        checkRealInfoService.insertCheckRealInfo(realInfo);
        return AjaxResult.success(data);
    }
}
