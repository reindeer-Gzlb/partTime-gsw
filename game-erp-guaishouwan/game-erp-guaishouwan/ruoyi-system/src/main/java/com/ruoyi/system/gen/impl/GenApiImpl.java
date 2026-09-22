package com.ruoyi.system.gen.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.system.V2.gameAttr.service.IGameAttributeService;
import com.ruoyi.system.V2.inter.mapper.SalePartnerMapper;
import com.ruoyi.system.V2.recycle.mapper.AttributeMapper;
import com.ruoyi.system.gen.IGenAPI;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.system.model.mapper.SkinImgMapper;
import com.ruoyi.system.model.service.UploadService;
import com.ruoyi.system.outto.core.MapUntils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author lz
 * @version 1.0
 * @date 2022/6/21 9:20
 */
@Slf4j
@Service
public class GenApiImpl implements IGenAPI {

    @Resource
    private AttributeMapper attributeMapper;

    @Resource
    private SkinImgMapper skinImgMapper;

    private static String key = "x8logf11d6o898l83botgg105c9ky83rjoi";

    private static String gengerImg = "genger-img/";

    @Autowired
    private UploadService uploadService;

    @Resource
    private SysDictDataMapper sysDictDataMapper;

    @Autowired
    IGameAttributeService gameAttributeService;

    @Resource
    SalePartnerMapper salePartnerMapper;


    @Override
    public List<JSONObject> getRoleId(String yingdiId) {

        return testRoleList(yingdiId);
    }

    @Override
    @Transactional
    public Map<String, Object> genTitleBack(Map<String, Object> titleParams) {
        String yingdiId = MapUtils.getString(titleParams, "campId");
        String roleId = MapUtils.getString(titleParams, "roleId");

        String url = "";
        String username = "";
        String pwd = "";
        String path = "qrCode/wzry/wzryData";

        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("gameDataGet");
        for (SysDictData dictData : sysDictDataList) {
            if ("url".equals(dictData.getDictLabel())) {
                url = dictData.getDictValue();
            }
            if ("username".equals(dictData.getDictLabel())) {
                username = dictData.getDictValue();
            } else if ("pwd".equals(dictData.getDictLabel())) {
                pwd = dictData.getDictValue();
            }
        }

        Map<String,Object> params = new HashMap<>();
        params.put("username",username);
        params.put("pwd",pwd);
        params.put("campId",yingdiId);
        params.put("roleId",roleId);

        String res = HttpUtil.get(url + path, params);
        JSONObject jsonObject = JSONObject.parseObject(res);
        String code = jsonObject.getString("code");
        if (Objects.equals("200",code)){
            String data = jsonObject.getString("data");
            return JSONObject.parseObject(data);
        }else {
            throw new GlobalException(jsonObject.getString("msg"));
        }
    }


    @Override
    public List<Map<String, Object>> allSkinType() {

        Map<String, Object> params = new TreeMap<>();
        params.put("pg", "apiWzAllQuFu");
        params.put("gameId", "10");
        log.info("角色列表请求参数={}", JSONObject.toJSONString(params));
        String url = "http://mh.poxiaowy.com/yingdi/yingdipifuController.php?pg=apiFindByIDGoodsClass&gameId=10";
        String response = HttpUtils.doPostForm(url, params, null);
        log.info("分类列表返回参数={}", response);
        JSONObject jsonObject = JSONObject.parseObject(response);
        String msg = jsonObject.getString("msg");
        msg = MapUntils.unicodeToString(msg);
        log.info("分类列表返回参数={}", msg);
        List<Map<String, Object>> resultMap = new ArrayList<>();
        if ("0".equals(jsonObject.getString("code"))) {
            String data = jsonObject.getString("data");
            JSONArray dataList = JSONObject.parseArray(data);
            for (Object o : dataList) {
                Map<String, Object> resu = new HashMap<>();
                String s = JSONObject.toJSONString(o);
                JSONObject jsonObject1 = JSONObject.parseObject(s);
                resu.put("id", jsonObject1.getString("id"));
                resu.put("brandname", jsonObject1.getString("brandname"));
                resu.put("pbrandname", jsonObject1.getString("pbrandname"));
                resultMap.add(resu);
            }
        }
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> allSkinTypeTwo(Long brandId) {

        Map<String, Object> params = new TreeMap<>();
        params.put("pg", "apiFindByIDGoodsSki");
        params.put("gameId", brandId);
        log.info("二级分类列表请求参数={}", JSONObject.toJSONString(params));
        String url =
                "http://mh.poxiaowy.com/yingdi/yingdipifuController.php?pg=apiFindByIDGoodsSkin&brandId=" + brandId;
        String response = HttpUtils.doPostForm(url, params, null);
        log.info("二级分类列表返回参数={}", response);
        JSONObject jsonObject = JSONObject.parseObject(response);
        String msg = jsonObject.getString("msg");
        msg = MapUntils.unicodeToString(msg);
        log.info("二级分类列表返回参数={}", msg);
        List<Map<String, Object>> resultMap = new ArrayList<>();
        if ("0".equals(jsonObject.getString("code"))) {
            String data = jsonObject.getString("data");
            JSONArray dataList = JSONObject.parseArray(data);
            for (Object o : dataList) {
                Map<String, Object> resu = new HashMap<>();
                String s = JSONObject.toJSONString(o);
                JSONObject jsonObject1 = JSONObject.parseObject(s);
                resu.put("id", jsonObject1.getString("id"));
                resu.put("brandname", jsonObject1.getString("brandname"));
                resu.put("pbrandname", jsonObject1.getString("pbrandname"));
                resultMap.add(resu);
            }
        }
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> allSkin() {
        Map<String, Object> params = new TreeMap<>();
        params.put("pg", "apiFindByAscriptionSortAll");
        params.put("gameId", 10);
        log.info("皮肤列表请求参数={}", JSONObject.toJSONString(params));
        String url = "http://mh.poxiaowy.com/yingdi/yingdipifuController.php?pg=apiFindByAscriptionSortAll&gameId=10";
        String response = HttpUtils.doPostForm(url, params, null);
        log.info("皮肤分类列表返回参数={}", response);
        JSONObject jsonObject = JSONObject.parseObject(response);
        String msg = jsonObject.getString("msg");
        msg = MapUntils.unicodeToString(msg);
        log.info("二级分类列表返回参数={}", msg);
        List<Map<String, Object>> resultMap = new ArrayList<>();
        if ("0".equals(jsonObject.getString("code"))) {
            String data = jsonObject.getString("data");
            JSONArray dataList = JSONObject.parseArray(data);
            for (Object o : dataList) {
                Map<String, Object> resu = new HashMap<>();
                String s = JSONObject.toJSONString(o);
                JSONObject jsonObject1 = JSONObject.parseObject(s);
                resu.put("id", jsonObject1.getString("id"));
                resu.put("title", jsonObject1.getString("title"));
                resu.put("children", jsonObject1.getString("children"));
                resultMap.add(resu);
            }
        }
        return resultMap;
    }



    private List<JSONObject> testRoleList(String yindi) {

        String url = "";
        String path = "qrCode/wzry/wzryRole";

        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("gameDataGet");
        for (SysDictData dictData : sysDictDataList) {
            if ("url".equals(dictData.getDictLabel())) {
                url = dictData.getDictValue();
            }
        }

        Map<String,Object> params = new HashMap<>();
        params.put("campId",yindi);

        String res = HttpUtil.get(url + path,params);
        System.out.println(res);
        JSONObject jsonObject = JSONObject.parseObject(res);
        String code = jsonObject.getString("code");
        if (Objects.equals("200",code)){
            return JSONObject.parseArray(jsonObject.getString("data"),JSONObject.class);
        }else {
            throw new GlobalException(jsonObject.getString("msg"));
        }
    }


    @Override
    public Map<String, Object> getQRcode(Map<String, Object> params) {
//
//        String yingdi = MapUtils.getString(params, "yingdi");
//        String authType = MapUtils.getString(params, "authType");
//
//        Map<String, Object> reqParams = new TreeMap<>();
//        reqParams.put("pg", "shouquanYingDi");
//        reqParams.put("authType", authType);
//        reqParams.put("yingdi", yingdi);
//
//        log.info("和平经营授权请求参数={}", JSONObject.toJSONString(reqParams));
//        String url = "http://mh.poxiaowy.com/generator/chiji/chijiApiController.php";
//        String response = HttpUtils.doPostForm(url, reqParams, null);
//        log.info("和平经营授权返回参数={}", response);
//        JSONObject jsonObject = JSONObject.parseObject(response);
//
//        Map<String, Object> result = new HashMap<>();
//
//        if (Objects.equals("1", jsonObject.getString("code"))) {
//            result.put("url", jsonObject.getString("url"));
//        } else {
//            new GlobalException(jsonObject.getString("msg"));
//        }
//        return result;
        String url = "";
        String path = "hpjyv2/getqrcode";
        String username = "";
        String pwd = "";
        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("gameDataGet");
        for (SysDictData dictData : sysDictDataList) {
            if ("url".equals(dictData.getDictLabel())) {
                url = dictData.getDictValue();
//                url = "http://192.168.3.105:9999/";
            }
            if ("username".equals(dictData.getDictLabel())) {
                username = dictData.getDictValue();
            } else if ("pwd".equals(dictData.getDictLabel())) {
                pwd = dictData.getDictValue();
            }
        }
        Map<String, Object> tokenPrams = new HashMap<>();
        tokenPrams.put("username", username);
        tokenPrams.put("pwd", pwd);

        String res = HttpUtil.get(url + path, params);
        JSONObject jsonObject = JSONObject.parseObject(res);
        String code = jsonObject.getString("code");
        if (Objects.equals("200", code)) {
            return JSONObject.parseObject(jsonObject.getString("data"));
        } else {
            throw new GlobalException(jsonObject.getString("msg"));
        }
    }

    /**
     * 字段说明
     * fushibi  服饰币
     * jinbi  金币
     * dianquan 点券
     * nowHeat 当前热力
     *
     * @param params
     * @return
     */

    @Override
    public Map<String, Object> getHPDatacode(Map<String, Object> params) {
        try {
            String url = "";
            String qrsig = params.get("qrsig").toString();
            String authType = params.get("authType").toString();
            String path = "hpjyv2/getHPDatacodeParam";
            String username = "";
            String pwd = "";
            List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("gameDataGet");
            for (SysDictData dictData : sysDictDataList) {
                if ("url".equals(dictData.getDictLabel())) {
                    url = dictData.getDictValue();
//                    url = "http://192.168.3.105:9999/";
                }
                if ("username".equals(dictData.getDictLabel())) {
                    username = dictData.getDictValue();
                } else if ("pwd".equals(dictData.getDictLabel())) {
                    pwd = dictData.getDictValue();
                }
            }
            Map<String, Object> tokenPrams = new HashMap<>();
            tokenPrams.put("username", username);
            tokenPrams.put("pwd", pwd);
            tokenPrams.put("qrsig", qrsig);
            tokenPrams.put("authType", authType);
            String res = HttpUtil.get(url + path, tokenPrams);
            JSONObject jsonObject = JSONObject.parseObject(res);
            String code = jsonObject.getString("code");
            if (Objects.equals("200", code)) {
                return JSONObject.parseObject(jsonObject.getString("data"));
            } else {
                throw new GlobalException(jsonObject.getString("msg"));
            }
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    public JSONObject tdCode() {

        String url = "";
        String path = "qrCode/tdCode";

        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("gameDataGet");
        for (SysDictData dictData : sysDictDataList) {
            if ("url".equals(dictData.getDictLabel())) {
                url = dictData.getDictValue();
            }
        }

        String res = HttpUtil.get(url + path);
        JSONObject jsonObject = JSONObject.parseObject(res);
        String code = jsonObject.getString("code");
        if (Objects.equals("200",code)){
            return JSONObject.parseObject(jsonObject.getString("data"));
        }else {
            throw new GlobalException(jsonObject.getString("msg"));
        }
    }

    @Override
    public String getToken(Map<String, Objects> params) {

        String uuid = MapUtils.getString(params, "uuid");
        String type = MapUtils.getString(params, "type");

        String url = "";
        String username = "";
        String pwd = "";
        String path = "qrCode/lol/getToken";

        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("gameDataGet");
        for (SysDictData dictData : sysDictDataList) {
            if ("url".equals(dictData.getDictLabel())) {
                url = dictData.getDictValue();
            }
            if ("username".equals(dictData.getDictLabel())) {
                username = dictData.getDictValue();
            } else if ("pwd".equals(dictData.getDictLabel())) {
                pwd = dictData.getDictValue();
            }
        }

        Map<String, Object> tokenPrams = new HashMap<>();
        tokenPrams.put("username", username);
        tokenPrams.put("pwd", pwd);
        tokenPrams.put("uuid", uuid);
        tokenPrams.put("type", type);

        String res = HttpUtil.get(url + path, tokenPrams);

        JSONObject jsonObject = JSONObject.parseObject(res);
        String code = jsonObject.getString("code");
        if (Objects.equals("200", code)) {
            String data = jsonObject.getString("data");
            return data;
        } else {
            throw new GlobalException(jsonObject.getString("msg"));
        }
    }
}
