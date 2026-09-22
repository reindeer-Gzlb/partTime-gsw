package com.ruoyi.system.feign;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.model.dto.GoodsInfoAndAttrValueDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

//@FeignClient(name = "interface-szh")
@FeignClient(name = "interface-prd")
public interface InterfaceFeignService {


    /**
     * 获取token
     *
     * @param timestamp
     * @param sign
     * @param appId
     * @return
     */
    @GetMapping("/webApi/api_token")
    public String api_token(@RequestHeader("timestamp") String timestamp,
                            @RequestHeader("sign") String sign,
                            @RequestHeader("appId") String appId);

    /**
     * 账号发布
     *
     * @param token
     * @param timestamp
     * @param sign
     * @param goodsInfoAndAttrValueDto
     * @return
     */
    @RequestMapping("/webApi/goods/publish")
    public AjaxResult publish(@RequestHeader("token") String token,
                              @RequestHeader("timestamp") String timestamp,
                              @RequestHeader("sign") String sign,
                              @RequestBody GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto);

    /**
     * 账号发布
     *
     * @param token
     * @param timestamp
     * @param sign
     * @param goodsInfoAndAttrValueDto
     * @return
     */
    @RequestMapping("/webApi/goods/edit")
    public AjaxResult edit(@RequestHeader("token") String token,
                           @RequestHeader("timestamp") String timestamp,
                           @RequestHeader("sign") String sign,
                           @RequestBody GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto);

    /**
     * 账号发布
     *
     * @param token
     * @param timestamp
     * @param sign
     * @param goodsInfoAndAttrValueDto
     * @return
     */
    @RequestMapping("/webApi/goods/off")
    public AjaxResult off(@RequestHeader("token") String token,
                          @RequestHeader("timestamp") String timestamp,
                          @RequestHeader("sign") String sign,
                          @RequestBody GoodsInfoAndAttrValueDto goodsInfoAndAttrValueDto);

    /**
     * 获取游戏属性
     *
     * @param gameId
     * @return
     */
    @RequestMapping("/webApi/gameInfo/{gameId}")
    public AjaxResult getGameInfo(@RequestHeader("token") String token,
                                  @RequestHeader("timestamp") String timestamp,
                                  @RequestHeader("sign") String sign,
                                  @PathVariable("gameId") Long gameId);

    /**
     * 获取游戏属性
     *
     * @param gameId
     * @return
     */
    @RequestMapping("/webApi/gameAttrTemp/{gameId}")
    public AjaxResult getGameAttrTemp(@RequestHeader("token") String token,
                                      @RequestHeader("timestamp") String timestamp,
                                      @RequestHeader("sign") String sign,
                                      @PathVariable("gameId") Long gameId);

}
