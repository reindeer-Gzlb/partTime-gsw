package com.ruoyi.system.V2.operatorTwoElements.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.operatorTwoElements.mapper.OperatorTwoElementsMapper;
import com.ruoyi.system.V2.operatorTwoElements.service.IOperatorTwoElementsService;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.model.domain.OperatorTwoElements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OperatorTwoElementsServiceImpl extends ServiceImpl<OperatorTwoElementsMapper, OperatorTwoElements> implements IOperatorTwoElementsService {

    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    @Override
    public boolean saveAndGetRresult(OperatorTwoElements operatorTwoElements) {
        String url = "";
        String username = "";
        String pwd = "";
        String path = "/api/shiming/detection";

        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("gameDataGet");
        for (SysDictData dictData : sysDictDataList) {
            if ("url".equals(dictData.getDictLabel())) {
                url = dictData.getDictValue();
                url = "192.168.3.105:9999";
            }
            if ("username".equals(dictData.getDictLabel())) {
                username = dictData.getDictValue();
            } else if ("pwd".equals(dictData.getDictLabel())) {
                pwd = dictData.getDictValue();
            }
        }
        // 构建入参
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("pwd", pwd);
        params.put("inputName", operatorTwoElements.getInputName());
        params.put("inputMobile", operatorTwoElements.getInputMobile());
        String response = HttpUtil.post(url + path, params);
        // 获取响应
        JSONObject resultJson = JSONObject.parseObject(response);
        if (resultJson.getString("code").equals("200")) {
            resultJson = resultJson.getJSONObject("data");
        } else {
            throw new GlobalException("error operator two elements");
        }
        // 获取codeid
        String codeid = resultJson.getString("codeid");
        String message = resultJson.getString("message");
        String time = resultJson.getString("time");
        OperatorTwoElements operator = new OperatorTwoElements();
        // 返回成果结值
        if ("10000".equals(codeid)) {
            String data = resultJson.getString("retdata");
            operator = JSON.parseObject(data, OperatorTwoElements.class);
        }
        operator.setCreateId(SecurityUtils.getUserId());
        operator.setInputMobile(operatorTwoElements.getInputMobile());
        operator.setInputName(operatorTwoElements.getInputName());
        operator.setCodeid(codeid);
        operator.setMessage(message);
        operator.setTime(time);
        operator.setReq(JSONObject.toJSONString(params));
        operator.setRes(response);
        return save(operator);
    }
}
