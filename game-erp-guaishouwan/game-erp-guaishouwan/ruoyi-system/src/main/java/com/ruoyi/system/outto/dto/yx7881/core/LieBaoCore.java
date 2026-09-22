package com.ruoyi.system.outto.dto.yx7881.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.system.model.domain.SalePartner;
import com.ruoyi.system.outto.dto.yx7881.bean.*;
import com.ruoyi.system.outto.dto.yx7881.utils.HttpUtil;
import com.ruoyi.system.outto.dto.yx7881.utils.LieBaoUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;

import java.util.List;
import java.util.Map;
import java.util.Objects;


public class LieBaoCore {

    /**
     * 请求方appId
     */
    public static String X_SERVICE_APP = "";

    /**
     * 授权凭证
     */
    public static String SECRET_KEY = "";

    /**
     * 猎宝openapi地址
     */
    public static String LIEBAO_URL = "";

    /**
     * 7881用户id
     */
    public static String USERID = "";

    /**
     * 合作方编码
     */
    public static String CPID = "";


    /**
     * 获取游戏基础数据
     *
     * @param gameId
     * @return
     */
    public static String get_game_info(String gameId, SalePartner salePartner) throws Exception {

        if (!checkBaseParam(salePartner)) {
            throw new GlobalException("基础参数为空");
        }

        if (StringUtils.isEmpty(gameId)) {
            throw new GlobalException("入参为空");
        }

        String url = LieBaoCore.LIEBAO_URL + "/games/get_game_info?game_id=" + gameId;

        String result = HttpUtil.doGet(url, null, LieBaoUtil.getHearder(LieBaoCore.X_SERVICE_APP,
                LieBaoCore.SECRET_KEY, ""));

        if (StringUtils.isEmpty(result)) {

            throw new GlobalException("返回为空");
        }

        return result;

    }


    /**
     * 商品下架
     *
     * @param params
     * @return
     */
    public static ResultEntity cp_goods_pull_off(Map<String, Object> params, SalePartner salePartner) throws Exception {

        if (!checkBaseParam(salePartner)) {
            throw new GlobalException("基础参数为空");
        }
        if (Objects.isNull(params.get("goodsId"))) {
            throw new GlobalException("goodsId入参为空");
        }

        Long[] ab = (Long[]) params.get("goodsId");
        String goodsId = "";
        for (int i = 0; i < ab.length; i++) {
            if (i == 0) {
                goodsId = String.valueOf(ab[i]);
            } else {
                goodsId = goodsId + "," + ab[i];
            }
        }
        String url = LieBaoCore.LIEBAO_URL + "/cp/" + LieBaoCore.CPID + "/goods/pull_off?userId=" + LieBaoCore.USERID +
                "&goodsId=" + goodsId + "&enforce=" + params.get("enforce") +
                "&closedReason=" + params.get("closedReason");


        String result = HttpUtil.doGet(url, null, LieBaoUtil.getHearder(LieBaoCore.X_SERVICE_APP,
                LieBaoCore.SECRET_KEY, ""));

        if (StringUtils.isEmpty(result)) {

            throw new GlobalException("返回为空");
        }

        return JSON.parseObject(result, new TypeReference<ResultEntity>() {
        });

    }


    /**
     * 商品删除
     *
     * @param params
     * @return
     */
    public static ResultEntity cp_goods_delete(Map<String, Object> params, SalePartner salePartner) throws Exception {

        if (!checkBaseParam(salePartner)) {
            throw new GlobalException("基础参数为空");
        }
        if (Objects.isNull(params.get("goodsId"))) {
            throw new GlobalException("goodsId入参为空");
        }

        Long[] ab = (Long[]) params.get("goodsId");
        String goodsId = "";
        for (int i = 0; i < ab.length; i++) {
            if (i == 0) {
                goodsId = String.valueOf(ab[i]);
            } else {
                goodsId = goodsId + "," + ab[i];
            }
        }
        String url = LieBaoCore.LIEBAO_URL + "/cp/" + LieBaoCore.CPID + "/goods/delete?userId=" + LieBaoCore.USERID +
                "&goodsId=" + goodsId;

        String result = HttpUtil.postRequest(url, null, null, "", LieBaoUtil.getHearder(LieBaoCore.X_SERVICE_APP,
                LieBaoCore.SECRET_KEY, ""));

        if (StringUtils.isEmpty(result)) {

            throw new GlobalException("返回为空");
        }

        return JSON.parseObject(result, new TypeReference<ResultEntity>() {
        });

    }

    /**
     * 获取游戏类目垂直属性
     *
     * @param gameId
     * @param gtId
     * @return
     * @throws Exception
     */
    public static String get_gamegt_vertical(String gameId, String gtId, SalePartner salePartner) throws Exception {

        if (!checkBaseParam(salePartner)) {
            throw new GlobalException("基础参数为空");
        }

        if (StringUtils.isEmpty(gameId) || StringUtils.isEmpty(gtId)) {
            throw new GlobalException("入参为空");
        }

        String url = LieBaoCore.LIEBAO_URL + "/games/get_gamegt_vertical/" + gameId + "/" + gtId;

        String result = HttpUtil.doGet(url, null, LieBaoUtil.getHearder(LieBaoCore.X_SERVICE_APP,
                LieBaoCore.SECRET_KEY, ""));

        if (StringUtils.isEmpty(result)) {

            throw new GlobalException("返回为空");
        }

        return result;
    }

    /**
     * 发布
     *
     * @param publishData
     * @return
     * @throws Exception
     */
    public static ResultEntity publish(List<?> publishData, SalePartner salePartner) throws Exception {

        if (!checkBaseParam(salePartner)) {
            throw new GlobalException("基础参数为空");
        }

        String url = LieBaoCore.LIEBAO_URL + "/cp/" + LieBaoCore.CPID + "/goods/publish";

        String result = HttpUtil.postRequest(url, publishData, ContentType.APPLICATION_JSON, "UTF-8",
                LieBaoUtil.getHearder(LieBaoCore.X_SERVICE_APP, LieBaoCore.SECRET_KEY, JSON.toJSONString(publishData)));

        if (StringUtils.isEmpty(result)) {
            throw new GlobalException("返回为空");
        }

        return JSON.parseObject(result, new TypeReference<ResultEntity>() {
        });

    }

    /**
     * 编辑
     *
     * @param editData
     * @return
     * @throws Exception
     */
    public static ResultEntity edit(List<EditGoodsBean> editData, SalePartner salePartner) throws Exception {

        if (!checkBaseParam(salePartner)) {
            throw new GlobalException("基础参数为空");
        }

        String url = LieBaoCore.LIEBAO_URL + "/cp/" + LieBaoCore.CPID + "/goods/edit";

        String result = HttpUtil.postRequest(url, editData, ContentType.APPLICATION_JSON, "UTF-8",
                LieBaoUtil.getHearder(LieBaoCore.X_SERVICE_APP, LieBaoCore.SECRET_KEY, JSON.toJSONString(editData)));

        if (StringUtils.isEmpty(result)) {

            throw new GlobalException("返回为空");
        }

        return JSON.parseObject(result, new TypeReference<ResultEntity>() {
        });


    }


    /**
     * 基础参数校验 并赋值
     *
     * @return
     */
    public static boolean checkBaseParam(SalePartner salePartner) {
        if (StringUtils.isEmpty(X_SERVICE_APP) || StringUtils.isEmpty(SECRET_KEY) || StringUtils.isEmpty(LIEBAO_URL)
                || StringUtils.isEmpty(USERID) || StringUtils.isEmpty(CPID)) {
            X_SERVICE_APP = salePartner.getSaleKey();
            SECRET_KEY = salePartner.getOther();
            LIEBAO_URL = salePartner.getTwoOther();
            String threeOther = salePartner.getThreeOther();
            try {
                USERID = threeOther.split(",")[0];
                CPID = threeOther.split(",")[1];
            } catch (Exception ex) {
                return false;
            }
        }
        if (StringUtils.isEmpty(X_SERVICE_APP) || StringUtils.isEmpty(SECRET_KEY) || StringUtils.isEmpty(LIEBAO_URL)
                || StringUtils.isEmpty(USERID) || StringUtils.isEmpty(CPID)) {
            return false;
        }

        return true;
    }

}
