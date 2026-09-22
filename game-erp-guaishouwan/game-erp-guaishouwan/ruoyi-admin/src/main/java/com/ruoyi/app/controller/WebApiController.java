package com.ruoyi.app.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.system.V2.api.service.IApiService;
import com.ruoyi.system.V2.gameInfo.service.IGameInfoService;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoMapper;
import com.ruoyi.system.V2.inter.service.IApiReqRecService;
import com.ruoyi.system.V2.inter.service.ISupplyInfoService;
import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.model.domain.SupplyInfo;
import com.ruoyi.system.model.dto.api.*;
import com.ruoyi.system.token.annotation.NotRepeatSubmit;
import com.ruoyi.system.token.utils.ApiUtil;
import com.ruoyi.system.token.utils.MD5Util;
import com.ruoyi.system.token.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/webApi")
@Slf4j
public class WebApiController extends BaseController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ISupplyInfoService supplyInfoService;

    @Autowired
    IGameInfoService gameInfoService;

    @Autowired
    IApiService apiService;

    @Autowired
    private IApiReqRecService apiReqRecService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @GetMapping(value = "/gettest")
    public AjaxResult gettest(String id, String name) {
        System.out.println("id=" + id);
        System.out.println("name=" + name);
        return AjaxResult.success("成功进入get方法");
    }

    @PostMapping(value = "/posttest")
    public AjaxResult posttest(@RequestBody GoodsInfo goodsInfo) {
        System.out.println("goodsInfo=" + goodsInfo.toString());
        return AjaxResult.success("成功进入post方法");
    }

    @GetMapping("/api_token")
    public ApiResponse<AccessToken> apiToken(String appId, @RequestHeader("timestamp") String timestamp,
                                             @RequestHeader("sign") String sign) {
        Assert.isTrue(!StringUtils.isEmpty(appId) && !StringUtils.isEmpty(timestamp) && !StringUtils.isEmpty(sign),
                "参数错误");

        long reqeustInterval = System.currentTimeMillis() - Long.valueOf(timestamp);
//        System.out.println("本地时间" + System.currentTimeMillis());
//        System.out.println("拿到的时间" + Long.valueOf(timestamp));
//        System.out.println("时间相减" + reqeustInterval);
//        Assert.isTrue(reqeustInterval < 5 * 60 * 1000, "请求过期，请重新请求");
        LambdaQueryWrapper<SupplyInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SupplyInfo::getAppId, appId).eq(SupplyInfo::getIsEnble, 0);
        SupplyInfo supplyInfo = supplyInfoService.getOne(queryWrapper);
        if (supplyInfo == null) {
            throw new GlobalException("找不到此app信息");
        }
        // 1. 根据appId查询数据库获取appSecret
        AppInfo appInfo = new AppInfo(supplyInfo.getAppId(), supplyInfo.getAppSecret());

        // 2. 校验签名
        String signString = timestamp + appId + appInfo.getAppSecret();
        String signature = MD5Util.encode(signString);
        Assert.isTrue(signature.equals(sign), "签名错误");

        // 3. 如果正确生成一个token保存到redis中，如果错误返回错误信息
        AccessToken accessToken = this.saveToken(0, appInfo, null);

        return ApiResponse.success(accessToken);
    }


    @NotRepeatSubmit(5000)
    @PostMapping("user_token")
    public ApiResponse<UserInfo> userToken(String username, String password) {
        // 根据用户名查询密码, 并比较密码(密码可以RSA加密一下)
        UserInfo userInfo = new UserInfo(username, "81255cb0dca1a5f304328a70ac85dcbd", "111111");
        String pwd = password + userInfo.getSalt();
        String passwordMD5 = MD5Util.encode(pwd);
        Assert.isTrue(passwordMD5.equals(userInfo.getPassword()), "密码错误");

        // 2. 保存Token
        AppInfo appInfo = new AppInfo("1", "12345678954556");
        AccessToken accessToken = this.saveToken(1, appInfo, userInfo);
        userInfo.setAccessToken(accessToken);
        return ApiResponse.success(userInfo);
    }

    @NotRepeatSubmit(1000)
    @GetMapping(value = "/gameList", name = "gameList")
    public ApiResponse getGameList(HttpServletRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            List<ApiGameListDto> list = apiService.getGameList();
            apiResponse = ApiResponse.success(list);
        } catch (Exception exception) {
            exception.printStackTrace();
            apiResponse = ApiResponse.error("9999", exception.getMessage());
        }
        apiReqRecService.saveInRec(null, JSON.toJSONString(apiResponse), request, "getGameList");
        return apiResponse;

    }

    @NotRepeatSubmit(1000)
    @GetMapping(value = "gameInfo/{gameId}", name = "gameInfo")
    public ApiResponse getGameInfo(@PathVariable("gameId") Long gameId, HttpServletRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            ApiGameInfoDto apiGameInfoDto = apiService.apiGetGameInfo(gameId);
            apiResponse = ApiResponse.success(apiGameInfoDto);
        } catch (Exception exception) {
            exception.printStackTrace();
            apiResponse = ApiResponse.error("9999", exception.getMessage());
        }
        apiReqRecService.saveInRec(JSON.toJSONString(gameId), JSON.toJSONString(apiResponse), request, "getGameInfo");
        return apiResponse;
    }

    @NotRepeatSubmit(1000)
    @GetMapping(value = "gameAttrTemp/{gameId}", name = "gameInfo")
    public ApiResponse gameAttrTemp(@PathVariable("gameId") Long gameId, HttpServletRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            List<ApiGameInfoDto> gameAttrTemplates = apiService.ApiGetGameField(gameId);
            apiResponse = ApiResponse.success(gameAttrTemplates);
        } catch (Exception exception) {
            exception.printStackTrace();
            apiResponse = ApiResponse.error("9999", exception.getMessage());
        }
        apiReqRecService.saveInRec(JSON.toJSONString(gameId), JSON.toJSONString(apiResponse), request, "gameAttrTemp");
        return apiResponse;
    }

    @NotRepeatSubmit(1000)
    @PostMapping(value = "goods/publish", name = "goodsInfo")
    public ApiResponse apiGoodsPublish(@Validated @RequestBody ApiGoodsInfoAndAttrValueReq apiGoodsInfoAndAttrValueReq,
                                       HttpServletRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        String token = request.getHeader("token");
        SupplyInfo supplyInfo = getSupplyInfo(token);
        if (supplyInfo == null) {
            throw new ServiceException("未找到此供货商,请联系运营!");
        }
        if (!checkMaxNum(supplyInfo)) {
            throw new ServiceException("已超出商品最大上架数量!");
        }
        Long goodsId = null;
        try {
            Map map = apiService.ApiGoodsPublish(apiGoodsInfoAndAttrValueReq, request, supplyInfo);
//            Map res = new HashMap();
//            res.put("goodsId", goodsId);
            if (StringUtils.isNotNull(map.get("goodsId"))) {
                goodsId = Long.valueOf(String.valueOf(map.get("goodsId")));
            }
            apiResponse = ApiResponse.success(map);
        } catch (Exception exception) {
            exception.printStackTrace();
            apiResponse = ApiResponse.error("9999", exception.getMessage());
        }
        apiReqRecService.saveInRec(JSON.toJSONString(apiGoodsInfoAndAttrValueReq), JSON.toJSONString(apiResponse),
                request, "goodsPublish", supplyInfo, goodsId);
        return apiResponse;
    }

    @NotRepeatSubmit(1000)
    @PostMapping(value = "goods/edit", name = "goodsInfo")
    public ApiResponse apiGoodsEdit(@Validated @RequestBody ApiGoodsInfoEditReq apiGoodsInfoEditReq,
                                    HttpServletRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        String token = request.getHeader("token");
        SupplyInfo supplyInfo = getSupplyInfo(token);
        if (supplyInfo == null) {
            throw new ServiceException("未找到此供货商,请联系运营!");
        }
        try {
            Long goodsId = apiService.ApiGoodsEdit(apiGoodsInfoEditReq, request, supplyInfo);
            Map res = new HashMap();
            res.put("goodsId", goodsId);
            apiResponse = ApiResponse.success(res);
        } catch (Exception exception) {
            exception.printStackTrace();
            apiResponse = ApiResponse.error("9999", exception.getMessage());
        }
        apiReqRecService.saveInRec(JSON.toJSONString(apiGoodsInfoEditReq), JSON.toJSONString(apiResponse),
                request, "goodsEdit", supplyInfo, apiGoodsInfoEditReq.getGoodsId());
        return apiResponse;
    }

    @NotRepeatSubmit(1000)
    @PostMapping(value = "goods/on", name = "goodsInfo")
    public ApiResponse apiGoodsOn(@Validated @RequestBody ApiGoodsInfoOnOrOffReq apiGoodsInfoOnOrOffReq,
                                  HttpServletRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        String token = request.getHeader("token");
        SupplyInfo supplyInfo = getSupplyInfo(token);
        if (supplyInfo == null) {
            throw new ServiceException("未找到此供货商,请联系运营!");
        }
        if (!checkMaxNum(supplyInfo)) {
            throw new ServiceException("已超出商品最大上架数量!");
        }
        try {
            Long goodsId = apiService.ApiGoodsOn(apiGoodsInfoOnOrOffReq, request, supplyInfo);
            Map res = new HashMap();
            res.put("goodsId", goodsId);
            apiResponse = ApiResponse.success(res);
        } catch (Exception exception) {
            exception.printStackTrace();
            apiResponse = ApiResponse.error("9999", exception.getMessage());
        }
        apiReqRecService.saveInRec(JSON.toJSONString(apiGoodsInfoOnOrOffReq), JSON.toJSONString(apiResponse),
                request, "goodsOn", supplyInfo, apiGoodsInfoOnOrOffReq.getGoodsId());
        return apiResponse;
    }

    @NotRepeatSubmit(1000)
    @PostMapping(value = "goods/off", name = "goodsInfo")
    public ApiResponse apiGoodsOff(@Validated @RequestBody ApiGoodsInfoOnOrOffReq apiGoodsInfoOnOrOffReq,
                                   HttpServletRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        String token = request.getHeader("token");
        SupplyInfo supplyInfo = getSupplyInfo(token);
        if (supplyInfo == null) {
            throw new ServiceException("未找到此供货商,请联系运营!");
        }
        try {
            Long goodsId = apiService.ApiGoodsOff(apiGoodsInfoOnOrOffReq, request, supplyInfo);
            Map res = new HashMap();
            res.put("goodsId", goodsId);
            apiResponse = ApiResponse.success(res);
        } catch (Exception exception) {
            exception.printStackTrace();
            apiResponse = ApiResponse.error("9999", exception.getMessage());
        }
        apiReqRecService.saveInRec(JSON.toJSONString(apiGoodsInfoOnOrOffReq), JSON.toJSONString(apiResponse),
                request, "goodsOff", supplyInfo, apiGoodsInfoOnOrOffReq.getGoodsId());
        return apiResponse;
    }


    private AccessToken saveToken(int tokenType, AppInfo appInfo, UserInfo userInfo) {
        String token = UUID.randomUUID().toString();

        // token有效期为2小时
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, 7200);
        Date expireTime = calendar.getTime();

        // 4. 保存token
        ValueOperations<String, TokenInfo> operations = redisTemplate.opsForValue();
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setTokenType(tokenType);
        tokenInfo.setAppInfo(appInfo);

        if (tokenType == 1) {
            tokenInfo.setUserInfo(userInfo);
        }

        operations.set(token, tokenInfo, 7200, TimeUnit.SECONDS);

        AccessToken accessToken = new AccessToken(token, expireTime);

        return accessToken;
    }

    SupplyInfo getSupplyInfo(String token) {
        TokenInfo tokenInfo = redisCache.getCacheObject(token);
        Assert.notNull(tokenInfo, "token错误");
        LambdaQueryWrapper<SupplyInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SupplyInfo::getAppId, tokenInfo.getAppInfo().getAppId()).eq(SupplyInfo::getIsEnble, 0);
        SupplyInfo supplyInfo = supplyInfoService.getOne(queryWrapper);
        return supplyInfo;
    }

    public static void main(String[] args) {

        //获取token 的 sign 签名
        long timestamp = System.currentTimeMillis();
        String appId = "1";
        String secret = "secret";
        System.out.println(timestamp);
        String tokenSign = timestamp + appId + secret;
        String sign = MD5Util.encode(tokenSign);
        System.out.println(sign);

        System.out.println("-------------------");

        //请求接口 的 sign签名
        String token = "6f9cce5a-8758-45d1-999d-0ca48a4db353";
        Map params = new HashMap();
        params.put("name", "hh");
        params.put("id", 1);
        System.out.println(ApiUtil.getCover(params));
        String signString = ApiUtil.getCover(params) + secret + token + timestamp;
        sign = MD5Util.encode(signString);
        System.out.println(sign);

        List<Map<String, Object>> s = new ArrayList<>();
        Map a = new HashMap();
        a.put("name", "111");
        a.put("url", "https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png");
        s.add(a);
        Map b = new HashMap();
        b.put("name", "222");
        b.put("url", "https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png");
        s.add(b);
        Map<String, Object> map = new HashMap();
        map.put("uul", s);
        for (String key : map.keySet()) {
            System.out.println(map.get(key));
        }
//        System.out.println(s.toString());
    }

    boolean checkMaxNum(SupplyInfo supplyInfo) {
        boolean flag = true;
        if (Objects.nonNull(supplyInfo.getGoodsMaxnum())) {
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setSupplyId(supplyInfo.getId());
            goodsInfo.setSaleState(1);
            int count = goodsInfoMapper.apiSelectGoodsCountBySupply(goodsInfo);
            if (count >= supplyInfo.getGoodsMaxnum()) {
                flag = false;
            }
        }
        return flag;
    }
}
