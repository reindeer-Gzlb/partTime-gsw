package com.ruoyi.system.outto.core;

import com.ruoyi.system.outto.core.constant.GameTypeConstant;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author lz
 * @version 1.0
 * @date 2022/6/7 21:33
 */
public class JYMRegionalCovert {


    public static Long getGameId(String gameType) {
        Map<String, Long> gameIds = new HashMap<>();
        gameIds.put(GameTypeConstant.WZRY, 1002416L);
        gameIds.put(GameTypeConstant.HPJY, 1006473L);
        gameIds.put(GameTypeConstant.CFMOBILE, 1002600L);
        gameIds.put(GameTypeConstant.YS, 1009609L);
        gameIds.put(GameTypeConstant.LOLMOBILE, 1011518L);
        gameIds.put(GameTypeConstant.LOL, 1005654L);
        return gameIds.get(gameType);

    }

    public static Long getClientId(String gameType, String region) {
        if (GameTypeConstant.WZRY.equals(gameType)) {
            switch (region) {
                case "安卓微信":
                    return 1470047033047733L;
                case "安卓QQ":
                    return 1470047031091455L;
                case "苹果微信":
                    return 1499667551454656L;
                case "苹果QQ":
                    return 1470047031280559L;
            }
        }
        return null;

    }

    public static Long getServerId(String gameType, String region) {
        if (GameTypeConstant.WZRY.equals(gameType)) {
            switch (region) {
                case "安卓微信":
                    return 1531903059072340l;
                case "安卓QQ":
                    return 937466L;
                case "苹果微信":
                case "苹果QQ":
                    return 1531903070868728L;
            }
        }
        return null;

    }

    public static Long getPlanformId(String gameType, String region) {
        if (GameTypeConstant.WZRY.equals(gameType)) {
            switch (region) {
                case "安卓微信":
                case "安卓QQ":
                    return 2L;
                case "苹果微信":
                case "苹果QQ":
                    return 3L;
            }
        }
        return null;

    }

    public static Long getSecondCategoryId(String gameType, String region) {
        if (GameTypeConstant.WZRY.equals(gameType)) {
            switch (region) {
                case "安卓微信":
                    return 1642579812094077L;
                case "安卓QQ":
                    return 1642579782516108L;
                case "苹果微信":
                    return 1642581061788074L;
                case "苹果QQ":
                    return 1642580017608072L;
            }
        }
        if (GameTypeConstant.HPJY.equals(gameType)) {
            switch (region) {
                case "安卓微信":
                    return 1645695786823202l;
                case "安卓QQ":
                    return 1645695622670122l;
                case "苹果微信":
                    return 1645695979530442l;
                case "苹果QQ":
                    return 1645695907124136l;
            }
        }
        return null;

    }

    public static String getCfServer(){
        return "[\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1679985914662116,\n" +
                "                    \"name\":\"高清竞技大区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502794170131901,\n" +
                "                    \"name\":\"教育专区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502794170131900,\n" +
                "                    \"name\":\"移动专区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502794134890016,\n" +
                "                    \"name\":\"河南一区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793781464216,\n" +
                "                    \"name\":\"河南二区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793781464213,\n" +
                "                    \"name\":\"北方大区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793781463212,\n" +
                "                    \"name\":\"山东二区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793781463211,\n" +
                "                    \"name\":\"山东一区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793568305748,\n" +
                "                    \"name\":\"山西一区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793568305747,\n" +
                "                    \"name\":\"河北一区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793568305746,\n" +
                "                    \"name\":\"北京四区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793568305745,\n" +
                "                    \"name\":\"北京三区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793568305744,\n" +
                "                    \"name\":\"北京二区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793568305743,\n" +
                "                    \"name\":\"北京一区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793528987110,\n" +
                "                    \"name\":\"黑龙江区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793528986109,\n" +
                "                    \"name\":\"吉林一区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793528986107,\n" +
                "                    \"name\":\"辽宁三区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793528986106,\n" +
                "                    \"name\":\"辽宁二区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793528986105,\n" +
                "                    \"name\":\"辽宁一区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793488311854,\n" +
                "                    \"name\":\"江苏二区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793488310852,\n" +
                "                    \"name\":\"江苏一区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793465928256,\n" +
                "                    \"name\":\"云南一区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793465928255,\n" +
                "                    \"name\":\"陕西一区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793465927253,\n" +
                "                    \"name\":\"重庆一区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793465927252,\n" +
                "                    \"name\":\"四川二区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793465927251,\n" +
                "                    \"name\":\"四川一区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793432508973,\n" +
                "                    \"name\":\"安徽一区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793432508972,\n" +
                "                    \"name\":\"福建一区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793432507970,\n" +
                "                    \"name\":\"浙江二区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793432507968,\n" +
                "                    \"name\":\"浙江一区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793432507967,\n" +
                "                    \"name\":\"上海二区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793432507966,\n" +
                "                    \"name\":\"上海一区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793358964061,\n" +
                "                    \"name\":\"南方大区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793358964059,\n" +
                "                    \"name\":\"江西一区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793358964058,\n" +
                "                    \"name\":\"湖北二区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793358963056,\n" +
                "                    \"name\":\"湖北一区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793358963055,\n" +
                "                    \"name\":\"湖南二区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793358963053,\n" +
                "                    \"name\":\"湖南一区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793115319926,\n" +
                "                    \"name\":\"广西一区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793115319925,\n" +
                "                    \"name\":\"广东四区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793115318924,\n" +
                "                    \"name\":\"广东三区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793115318922,\n" +
                "                    \"name\":\"广东二区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005668,\n" +
                "                    \"id\":1502793115318921,\n" +
                "                    \"name\":\"广东一区\"\n" +
                "                }\n" +
                "            ]";
    }

    public static String getLOLServer(){
       return  "[\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1517384142749498,\n" +
                "                    \"name\":\"峡谷之巅\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500544100659844,\n" +
                "                    \"name\":\"男爵领域\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500544100659842,\n" +
                "                    \"name\":\"教育网专区\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500530737314623,\n" +
                "                    \"name\":\"钢铁烈阳\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500530737313622,\n" +
                "                    \"name\":\"雷瑟守备\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500530737313621,\n" +
                "                    \"name\":\"皮城警备\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500530737313620,\n" +
                "                    \"name\":\"影流\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500530737313618,\n" +
                "                    \"name\":\"水晶之痕\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500530737313615,\n" +
                "                    \"name\":\"暗影岛\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500530737313614,\n" +
                "                    \"name\":\"裁决之地\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500530737313612,\n" +
                "                    \"name\":\"扭曲丛林\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500530737312610,\n" +
                "                    \"name\":\"无畏先锋\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500530737312608,\n" +
                "                    \"name\":\"德玛西亚\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500530737312607,\n" +
                "                    \"name\":\"巨龙之巢\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500530737312603,\n" +
                "                    \"name\":\"恕瑞玛\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500530737312601,\n" +
                "                    \"name\":\"弗雷尔卓德\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500450488530356,\n" +
                "                    \"name\":\"卡拉曼达\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500450488530355,\n" +
                "                    \"name\":\"征服之海\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500450488530354,\n" +
                "                    \"name\":\"守望之海\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500450488530353,\n" +
                "                    \"name\":\"均衡教派\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500450488530352,\n" +
                "                    \"name\":\"黑色玫瑰\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500450488529351,\n" +
                "                    \"name\":\"巨神峰\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500450488529349,\n" +
                "                    \"name\":\"战争学院\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500450488529347,\n" +
                "                    \"name\":\"皮尔特沃夫\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500450488529346,\n" +
                "                    \"name\":\"班德尔城\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500450488529344,\n" +
                "                    \"name\":\"诺克萨斯\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500450488529342,\n" +
                "                    \"name\":\"祖安\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500450488529341,\n" +
                "                    \"name\":\"比尔吉沃特\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"game_id\":1005654,\n" +
                "                    \"id\":1500450488528338,\n" +
                "                    \"name\":\"艾欧尼亚\"\n" +
                "                }\n" +
                "            ]";
    }

    public static Long getFirstCategoryId() {
        return 1L;
    }
}
