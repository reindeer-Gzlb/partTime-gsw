package com.ruoyi.quartz.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.Constant;
import com.ruoyi.common.enums.SalerStatus;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoMapper;
import com.ruoyi.system.V2.inter.mapper.*;
import com.ruoyi.system.gen.IGenAPI;
import com.ruoyi.system.model.domain.*;
import com.ruoyi.system.model.dto.GoodReq;
import com.ruoyi.system.model.mapper.*;
import com.ruoyi.system.model.service.AsyncService;
import com.ruoyi.system.model.service.IAccountInfoService;
import com.ruoyi.system.model.service.IAccountLableService;
import com.ruoyi.system.V2.inter.service.IInterRecService;
import com.ruoyi.system.outto.core.LCConstant;
import com.ruoyi.system.outto.core.OutUrlConstant;
import com.ruoyi.system.outto.game.*;
import com.ruoyi.system.outto.service.IOutApi;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchOnsaleRequest;
import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchtaskQueryRequest;
import com.taobao.api.request.AlibabaJymItemExternalGoodsStatusBatchQueryRequest;
import com.taobao.api.response.AlibabaJymItemExternalGoodsBatchOnsaleResponse;
import com.taobao.api.response.AlibabaJymItemExternalGoodsBatchtaskQueryResponse;
import com.taobao.api.response.AlibabaJymItemExternalGoodsStatusBatchQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

//import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchOnsaleRequest;
//import com.taobao.api.request.AlibabaJymItemExternalGoodsBatchtaskQueryRequest;
//import com.taobao.api.request.AlibabaJymItemExternalGoodsStatusBatchQueryRequest;
//import com.taobao.api.response.AlibabaJymItemExternalGoodsBatchOnsaleResponse;
//import com.taobao.api.response.AlibabaJymItemExternalGoodsBatchtaskQueryResponse;
//import com.taobao.api.response.AlibabaJymItemExternalGoodsStatusBatchQueryResponse;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Slf4j
@Service
public class TaskService {

    @Autowired
    private IGenAPI iGenAPI;
    @Resource
    private ClassSkinMapper classSkinMapper;
    @Resource
    private SkinMapper skinMapper;
    @Autowired
    private IAccountLableService iAccountLableService;
    @Resource
    private AccountInfoMapper accountInfoMapper;

    @Resource
    private SalePartnerMapper salePartnerMapper;
    @Autowired
    private IOutApi iOutApi;

    @Resource
    private GoodsInfoMapper goodsInfoMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 状态码
     */
    public static final String CODE_TAG = "code";

    /**
     * 返回内容
     */
    public static final String MSG_TAG = "msg";


    @Resource
    private SelectPushMapper selectPushMapper;

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", "15778693755");
        params.put("password", "tz123456");
        params.put("device_type", "pc");
        String execute = HttpRequest.post("https://api.jingmengyou.com/api/user/public/login").body(JSONObject.toJSONString(params)).execute().body();
        System.out.println(execute);
    }

    @GetMapping("/delAll")
    public void delAll() {

        //获取供货商账号
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setSaleState(1);
        List<Long> ids = goodsInfoMapper.accIds(goodsInfo);
        for (Long id : ids) {
            iOutApi.del(id, null, 0, null);
        }
    }

    /**
     * 定时同步标签
     */
    public void synchronousLable() {
        /**
         * 1、查询未售的、未删除
         * 2、循环，获取标签
         * 3、转成字符串，存入account
         */

        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setSaleState(1);
        accountInfo.setDel(0);
        List<AccountInfo> accountInfoList = accountInfoMapper.selectAccountInfoList(accountInfo);
        for (AccountInfo info : accountInfoList) {
            if (StringUtils.isEmpty(info.getAcclable())) {
                AccountLable accountLable = new AccountLable();
                accountLable.setInfoId(info.getId());
                List<AccountLable> accountLables = iAccountLableService.selectAccountLableList(accountLable);
                List<String> collect = accountLables.stream().map(label -> label.getLable()).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(collect)) {
                    String result = collect.stream().map(String::valueOf).collect(Collectors.joining(","));
                    info.setAcclable(result);
                    accountInfoMapper.updateAccountInfo(info);
                }
            }
        }
    }


    public void ryNoParams() {

        //皮肤列表
        List<Map<String, Object>> skinList = iGenAPI.allSkin();
        for (Map<String, Object> stringObjectMap : skinList) {
            Long id = MapUtils.getLong(stringObjectMap, "id");

            String children = MapUtils.getString(stringObjectMap, "children");
            List<Skin> skins = JSONArray.parseArray(children, Skin.class);
            for (Skin skin : skins) {
                skin.setSkinId(skin.getId());
                skin.setParentId(id);
                skinMapper.insertSkin(skin);
            }

        }
        Long userId = 1l;
        log.info("开始请求皮肤类型");
        List<Map<String, Object>> list = iGenAPI.allSkinType();
        List<ClassSkin> classSkins = JSONArray.parseArray(JSONObject.toJSONString(list), ClassSkin.class);
        classSkins.stream().forEach(c -> c.setCreateId(userId));
        classSkins.stream().forEach(c -> c.setCreateTime(new Date()));
        for (ClassSkin classSkin : classSkins) {

            classSkin.setSkinId(classSkin.getId());
            ClassSkin classSelcet = new ClassSkin();
            classSelcet.setSkinId(classSkin.getId());

            List<ClassSkin> classSkins1 = classSkinMapper.selectClassSkinList(classSelcet);
            if (CollectionUtils.isEmpty(classSkins1)) {
                classSkin.setParentId(1l);
                classSkinMapper.insertClassSkin(classSkin);
            }
            List<Map<String, Object>> listTwo = iGenAPI.allSkinTypeTwo(classSkin.getSkinId());
            List<ClassSkin> classSkinsTwo = JSONArray.parseArray(JSONObject.toJSONString(listTwo), ClassSkin.class);
            classSkinsTwo.stream().forEach(c -> c.setCreateId(userId));
            classSkinsTwo.stream().forEach(c -> c.setCreateTime(new Date()));
            for (ClassSkin skin : classSkinsTwo) {
                ClassSkin kk = new ClassSkin();
                kk.setSkinId(skin.getId());
                List<ClassSkin> kkClass = classSkinMapper.selectClassSkinList(kk);
                skin.setParentId(classSkin.getId());
                if (CollectionUtils.isEmpty(kkClass)) {
                    skin.setSkinId(skin.getId());
                    classSkinMapper.insertClassSkin(skin);
                }
            }


            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("执行无参方法");
    }


    /**
     * 选择账号推送
     */
    public void selectPush(Integer pageSize,Long id) {

        SelectPush selectPush = new SelectPush();
        selectPush.setId(id);
        selectPush.setState("0");
        List<SelectPush> selectPushes = selectPushMapper.selectSelectPushList(selectPush);
        if (!CollectionUtils.isEmpty(selectPushes)){
            for (SelectPush push : selectPushes) {
                String config = push.getConfig();
                String games = push.getGames();
                String superId = push.getSuperId();

                GoodReq goodReq = new GoodReq();

                if (StringUtils.isNotEmpty(games)){
                    String[] split = games.split(",");
                    goodReq.setGameNames(Arrays.asList(split));
                }

                if (StringUtils.isNotEmpty(superId)){
                    String[] split = superId.split(",");
                    goodReq.setCreateIds(Arrays.asList(split));
                }
                goodReq.setPageSize(pageSize);
                if (StringUtils.isEmpty(config)){
                    break;
                }else {
                    String[] split = config.split(",");
                    Arrays.asList(split).stream().forEach(s ->{
                        SalePartner salePartner = salePartnerMapper.selectSalePartnerById(Long.parseLong(s));
                        if (Objects.nonNull(salePartner)){
                            goodReq.setSaleCode(salePartner.getSaleName());
                        }
                       List<GoodsInfo> goodsInfoList =  goodsInfoMapper.selectPush(goodReq);
                        for (GoodsInfo goodsInfo : goodsInfoList) {
                            iOutApi.add(goodsInfo.getId(),Long.parseLong(s));
                        }
                    });
                }
            }
        }
    }

    public void pushJMYGetTokenApi(Long config) {

        SalePartner salePartner = salePartnerMapper.selectSalePartnerById(config);
        String username = salePartner.getSaleKey();
        String password = salePartner.getOther();
        String device_type = "pc";

        String url = salePartner.getThreeOther() + "/api/user/public/login";

        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("device_type", device_type);

        HttpResponse execute = HttpRequest.post(url).body(JSONObject.toJSONString(params)).execute();
        String body = execute.body();
        String response = body;

        JSONObject jsonObject = JSONObject.parseObject(response);
        if ("1".equals(jsonObject.getString("code"))){
            String data = jsonObject.getString("data");
            JSONObject dataJson = JSON.parseObject(data);
            String token = dataJson.getString("token");
            log.info("token={}",token);
//            redisCache.setCacheObject(tokenRedis,token);
            salePartner.setTwoOther(token);
            salePartnerMapper.updateSalePartner(salePartner);
        }
    }

    public void pushSBOGetTokenApi(Long config,String tokenRedis) {

        SalePartner salePartner = salePartnerMapper.selectSalePartnerById(config);
        String username = salePartner.getSaleKey();
        String password = salePartner.getOther();
        String device_type = "pc";

        String url = OutUrlConstant.SBO + "/api/user/public/login";

        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("device_type", device_type);

        String response = HttpUtils.sendPost(url, JSONObject.toJSONString(params), "application/json");

        JSONObject jsonObject = JSONObject.parseObject(response);
        if ("1".equals(jsonObject.getString("code"))){
            String data = jsonObject.getString("data");
            JSONObject dataJson = JSON.parseObject(data);
            String token = dataJson.getString("token");
            log.info("token={}",token);
            redisCache.setCacheObject(tokenRedis,token);
        }
    }

}
