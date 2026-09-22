package com.ruoyi.system.V2.api.service;

import com.ruoyi.system.model.domain.SupplyInfo;
import com.ruoyi.system.model.dto.api.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IApibdService {
    /**
     * 获取游戏的商品信息
     *
     * @param gameId
     * @return
     */
    ApiGameInfoDto apiGetGameInfo(Long gameId);

    /**
     * 获取游戏的自定义属性配置
     *
     * @param gameId
     * @return
     */
    List<ApiGameInfoDto> ApiGetGameField(Long gameId);

    /**
     * 发布商品(新增 +上架)
     *
     * @param apiGoodsInfoAndAttrValueReq
     * @param request
     * @return
     */
    Long ApiGoodsPublish(BDApiGoodsInfoAndAttrValueReq apiGoodsInfoAndAttrValueReq, HttpServletRequest request,
                         SupplyInfo supplyInfo);

    /**
     * 修改商品信息
     *
     * @param apiGoodsInfoEditReq
     * @param request
     * @return
     */
    Long ApiGoodsEdit(ApiGoodsInfoEditReq apiGoodsInfoEditReq, HttpServletRequest request, SupplyInfo supplyInfo);

    /**
     * 商品上架
     *
     * @param apiGoodsInfoOnOrOffReq
     * @param request
     * @return
     */
    Long ApiGoodsOn(ApiGoodsInfoOnOrOffReq apiGoodsInfoOnOrOffReq, HttpServletRequest request, SupplyInfo supplyInfo);

    /**
     * 商品下架
     *
     * @param apiGoodsInfoOnOrOffReq
     * @param request
     * @return
     */
    Long ApiGoodsOff(ApiGoodsInfoOnOrOffReq apiGoodsInfoOnOrOffReq, HttpServletRequest request, SupplyInfo supplyInfo);

    /**
     * 商品删除
     *
     * @param apiGoodsInfoOnOrOffReq
     * @param request
     * @return
     */
    Long ApiGoodsDelete(ApiGoodsInfoDeleteReq apiGoodsInfoOnOrOffReq, HttpServletRequest request, SupplyInfo supplyInfo);


    /**
     * 本地修改属性
     *
     * @param apiGoodsInfoAndAttrValueReq
     * @param request
     * @return
     */
    Long ApiGoodsXiuGai(BDApiGoodsInfoAndAttrValueReq apiGoodsInfoAndAttrValueReq, HttpServletRequest request,
                         SupplyInfo supplyInfo);
}
