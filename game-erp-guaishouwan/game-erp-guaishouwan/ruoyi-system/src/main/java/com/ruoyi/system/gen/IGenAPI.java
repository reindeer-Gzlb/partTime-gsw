package com.ruoyi.system.gen;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author lz
 * @version 1.0
 * @date 2022/6/21 9:20
 */
public interface IGenAPI {

    List<JSONObject> getRoleId(String yingdiid);


    List<Map<String, Object>> allSkinType();

    List<Map<String, Object>> allSkinTypeTwo(Long brandId);

    List<Map<String, Object>> allSkin();




    Map<String, Object> genTitleBack(Map<String,Object> params);


    Map<String, Object> getQRcode(Map<String, Object> params);

    Map<String, Object> getHPDatacode(Map<String, Object> params);

    JSONObject tdCode();

    String getToken(Map<String, Objects> params);
}
