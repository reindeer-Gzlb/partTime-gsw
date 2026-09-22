package com.ruoyi.system.V2.tool.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.UUIDGenerator;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.system.V2.goodInfo.service.IGoodsInfoService;
import com.ruoyi.system.model.domain.Black;
import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.model.domain.HBlack;
import com.ruoyi.system.model.mapper.BlackMapper;
import com.ruoyi.system.V2.tool.service.IBlackApi;
import com.ruoyi.system.service.ISysDictDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author lz
 * @version 1.0
 * @date 2022/6/25 8:09
 */
@Slf4j
@Service
public class BlackApiImpl implements IBlackApi {

    @Autowired
    private BlackMapper blackMapper;
    @Autowired
    private ISysDictDataService dictDataService;

    public static void main(String[] args) {
        String url = "http://test.zhanghaocha.com";
        String app_id = "2021042241860089";
        String app_key = "a440b6883c5c1da928ab1c27f639ab6a";


        Map<String, Object> params = new HashMap<>();
        params.put("x-zhc-token", app_id);
        long time = DateUtils.getNowDate().getTime() / 1000;
        params.put("x-zhc-timestamp", time);
        String uuid = UUIDGenerator.generate12();
        params.put("x-zhc-nonce", uuid);
        String sign = DigestUtils.md5Hex(app_id + app_key + time + uuid).toUpperCase();
        params.put("x-zhc-signature", sign);


//        String s = JSONObject.toJSONString(params);
        String response = HttpUtils.doPostForm(url + "/v2/accountList", new HashMap(), params);
//        String response = HttpUtils.doPostForm(url + "/v2/gameList", new HashMap(),params);
        System.out.println(response);


    }

    @Override
    public List<Map> allGameType() {


        String url = "";
        String app_id = "";
        String app_key = "";


        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("black_config");
        sysDictData.setStatus(Constants.STR_0);
        List<SysDictData> sysDictDataList = dictDataService.selectDictDataList(sysDictData);
        for (SysDictData dictData : sysDictDataList) {
            if ("url".equals(dictData.getDictLabel())) {
                url = dictData.getDictValue();
            }
            if ("app_id".equals(dictData.getDictLabel())) {
                app_id = dictData.getDictValue();
            }
            if ("app_key".equals(dictData.getDictLabel())) {
                app_key = dictData.getDictValue();
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("x-zhc-token", app_id);
        long time = DateUtils.getNowDate().getTime() / 1000;
        params.put("x-zhc-timestamp", time);
        String uuid = UUIDGenerator.generate12();
        params.put("x-zhc-nonce", uuid);
        String sign = DigestUtils.md5Hex(app_id + app_key + time + uuid).toUpperCase();
        params.put("x-zhc-signature", sign);
        String response = HttpUtils.doPostForm(url + "/v2/gameList", new HashMap(), params);
        log.info(response);
        JSONObject resultJson = JSONObject.parseObject(response);
        if (Objects.equals("SUCCESS", resultJson.getString("code"))) {
            String data = resultJson.getString("data");
            List<Map> mapList = JSONArray.parseArray(data, Map.class);
            return mapList;
        }
        return null;
    }

    @Override
    public List<HBlack> allAcount(Map<String, Object> params) {
        List<HBlack> arrayList = new ArrayList<>();

        String accountNumber = MapUtils.getString(params, "content");
        Black blackReq = new Black();
        blackReq.setAccount(accountNumber);
        List<Black> blackss = blackMapper.selectBlackList(blackReq);
        if (CollectionUtils.isNotEmpty(blackss)) {
            for (Black black : blackss) {
                HBlack hBlack = new HBlack();
                hBlack.setChannel_name(black.getChannelName());
                hBlack.setAccount(black.getAccount());
                hBlack.setRemark(black.getRemark());
                hBlack.setGame_sn(black.getGameSn());
                hBlack.setGame_name(black.getGameName());
                arrayList.add(hBlack);
            }
            return arrayList;
        }

        String url = "";
        String app_id = "";
        String app_key = "";


        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("black_config");
        sysDictData.setStatus(Constants.STR_0);
        List<SysDictData> sysDictDataList = dictDataService.selectDictDataList(sysDictData);
        for (SysDictData dictData : sysDictDataList) {
            if ("url".equals(dictData.getDictLabel())) {
                url = dictData.getDictValue();
            }
            if ("app_id".equals(dictData.getDictLabel())) {
                app_id = dictData.getDictValue();
            }
            if ("app_key".equals(dictData.getDictLabel())) {
                app_key = dictData.getDictValue();
            }
        }

        Map<String, Object> head = new HashMap<>();
        head.put("x-zhc-token", app_id);
        long time = DateUtils.getNowDate().getTime() / 1000;
        head.put("x-zhc-timestamp", time);
        String uuid = UUIDGenerator.generate12();
        head.put("x-zhc-nonce", uuid);
        String sign = DigestUtils.md5Hex(app_id + app_key + time + uuid).toUpperCase();
        head.put("x-zhc-signature", sign);
        String paramsStr = JSONObject.toJSONString(params);
        log.info("黑名单查询参数={}", paramsStr);
        String response = HttpUtils.doPostForm(url + "/v2/accountSearch", params, head);
        log.info("黑名单账号列表返回参数={}", response);
        JSONObject resultJson = JSONObject.parseObject(response);
        List<HBlack> blacks = new ArrayList<>();
        if (Objects.equals("SUCCESS", resultJson.getString("code"))) {
            String data = resultJson.getString("data");
            String data_list = JSONObject.parseObject(data).getString("data_list");
            log.info(data_list);
            blacks = JSONArray.parseArray(data_list, HBlack.class);
//            Long userId = SecurityUtils.getUserId();
            blacks.stream().forEach(b -> {
                Black black = new Black();
                black.setAccount(b.getAccount());
                black.setCreateId(1L);
                black.setCreateTime(new Date());
                black.setAccountId(b.getAccount_id());
                black.setGameSn(b.getGame_sn());
                black.setGameName(b.getGame_name());
                black.setMobile(b.getMobile());
                black.setWx(b.getWx());
                black.setIdNo(b.getId_no());
                black.setName(b.getName());
                black.setPaymentAccount(b.getPayment_account());
                black.setAddTime(b.getCreate_time());
                black.setChannelName(b.getChannel_name());
                black.setProvinceName(b.getProvince_name());
                black.setCityName(b.getCity_name());
                black.setAreaName(b.getArea_name());
                black.setStatus(1l);
                blackMapper.insertBlack(black);
            });
            return blacks;
        }
        return blacks;
    }

    @Autowired
    private IGoodsInfoService goodsInfoService;

    @Override
    @Transactional
    public Black addBlack(Black black) {
        GoodsInfo goodsInfo = goodsInfoService.selectGoodsInfoById(black.getAccountId());
        if (StringUtils.isNotNull(goodsInfo)) {
            Long userId;
            try {
                userId = SecurityUtils.getUserId();
            } catch (Exception e) {
                userId = 1l;
            }
            Date now = DateUtils.getNowDate();
            black = new Black();
            black.setAccount(goodsInfo.getAccountNumber());
            black.setCreateId(userId);
            black.setCreateTime(now);
            black.setAccountId(goodsInfo.getId());
            black.setGameSn(String.valueOf(goodsInfo.getGameId()));
            black.setGameName(goodsInfo.getGameName());
            black.setMobile(goodsInfo.getSecurityPhone());
//            black.setWx(goodsInfo.get);
//            black.setIdNo(b.getId_no());
//            black.setName(b.getName());
//            black.setPaymentAccount(b.getPayment_account());
//            black.setAddTime(now);
            black.setChannelName("怪兽玩");
//            black.setProvinceName(b.getProvince_name());
//            black.setCityName(b.getCity_name());
//            black.setAreaName(b.getArea_name());
//            goodsInfo.setIsBlack(2);
            goodsInfoService.updateGoodsInfo(goodsInfo);
        }
        black.setStatus(99l);
        blackMapper.insertBlack(black);
        return black;
    }
}
