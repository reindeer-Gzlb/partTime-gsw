package com.ruoyi.system.outto.core.region;

import com.ruoyi.system.outto.core.constant.GameTypeConstant;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lz
 * @version 1.0
 * @date 2022/6/7 21:33
 */
public class SDZRegionalCovert {


    //指间游游戏类型
    private static Map<String, Map<String, String>> sdzGameTypeMap = new HashMap<>();


    static {
        HashMap<String, String> regionMap = new HashMap<>();
        regionMap.put("gameId", "23");
        regionMap.put("安卓Q", "128");
        regionMap.put("安卓WX", "129");
        regionMap.put("苹果Q", "130");
        regionMap.put("苹果WX", "131");
        sdzGameTypeMap.put(GameTypeConstant.WZRY, regionMap);
        regionMap = new HashMap<>();
        regionMap.put("gameId", "33");
        regionMap.put("安卓Q", "210");
        regionMap.put("安卓WX", "212");
        regionMap.put("苹果Q", "211");
        regionMap.put("苹果WX", "213");
        sdzGameTypeMap.put(GameTypeConstant.HPJY, regionMap);
        regionMap = new HashMap<>();
        regionMap.put("gameId", "50");
        regionMap.put("官服", "276");
        regionMap.put("B服", "277");
        sdzGameTypeMap.put(GameTypeConstant.YS, regionMap);
        regionMap = new HashMap<>();
        regionMap.put("gameId", "62");
        regionMap.put("苹果官方", "278");
        sdzGameTypeMap.put(GameTypeConstant.YJWJ, regionMap);
        regionMap = new HashMap<>();
        regionMap.put("gameId", "55");
        regionMap.put("苹果官方", "278");
        sdzGameTypeMap.put(GameTypeConstant.LOL, regionMap);

    }

    public static String getSdzGameId(String gameType) {
        Map<String, String> stringObjectMap = sdzGameTypeMap.get(gameType);
        if (CollectionUtils.isEmpty(stringObjectMap)) {
            return null;
        }
        return stringObjectMap.get("gameId");
    }

    public static String getSdzRegionId(String gameType, String region) {
        Map<String, String> stringObjectMap = sdzGameTypeMap.get(gameType);
        if (CollectionUtils.isEmpty(stringObjectMap)) {
            return null;
        }
        return stringObjectMap.get(region);
    }
}
