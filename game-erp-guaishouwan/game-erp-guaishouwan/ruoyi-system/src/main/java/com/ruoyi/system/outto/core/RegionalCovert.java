package com.ruoyi.system.outto.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.outto.core.constant.GameTypeConstant;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author lz
 * @version 1.0
 * @date 2022/6/7 21:33
 */
public class RegionalCovert {

    public static String dianxin = "电信,艾欧尼亚,暗影岛,班德尔城,裁决之地,钢铁烈阳,黑色玫瑰,巨神峰,均衡教派,卡拉曼达,雷瑟守备,诺克萨斯,皮城警备,皮尔特沃夫,守望之海,水晶之痕,影流,战争学院,征服之海,祖安";
    public static String wangtong = "网通,比尔吉沃特,德玛西亚,弗雷尔卓德,无畏先锋,恕瑞玛,扭曲丛林,巨龙之巢,教育网专区";
    public static String quanwangluo = "男爵领域,峡谷之巅";
    public static String cfdianxin  ="安徽一区,福建一区,广东一区,广东二区,广东三区,广东四区,广西一区,湖北一区,湖北二区,湖南一区,湖南二区,江苏一区,江苏二区,江西一区,南方大区,陕西一区,上海一区,上海二区,四川一区,四川二区,云南一区,浙江一区,浙江二区,重庆一区,预备大区";
    public static String cfwangtong  ="北方大区,北京一区,北京二区,北京三区,北京四区,河北一区,河南一区,河南二区,黑龙江一区,吉林一区,辽宁一区,辽宁二区,辽宁三区,山东一区,山东二区,山西一区";
    public static String waifu = "韩服,日服,美服,澳服,台服,西欧服";

    public static Object getZjyGameTypeMap(String gameType) {
        Map<String, Object> stringObjectMap = zjyGameTypeMap.get(gameType);
        if (CollectionUtils.isEmpty(stringObjectMap)) {
            return null;
        }
        Object gameId = stringObjectMap.get("gameId");
        return gameId;
    }

    public static Object getZjyRegionIdMap(String gameType, String region) {
        Map<String, Object> stringObjectMap = zjyGameTypeMap.get(gameType);
        if (CollectionUtils.isEmpty(stringObjectMap)) {
            return null;
        }
        Object gameId = stringObjectMap.get(region);
        return gameId;
    }

    //指间游游戏类型
    private static Map<String, Map<String, Object>> zjyGameTypeMap = new HashMap<>();

    static {
        HashMap<String, Object> regionMap = new HashMap<>();
        regionMap.put("gameId", 7);
        regionMap.put("安卓QQ", 15);
        regionMap.put("安卓微信", 16);
        regionMap.put("苹果QQ", 17);
        regionMap.put("苹果微信", 18);
        zjyGameTypeMap.put("和平精英", regionMap);
        regionMap = new HashMap<>();
        regionMap.put("gameId", 8);
        regionMap.put("安卓QQ", 19);
        regionMap.put("安卓微信", 20);
        regionMap.put("苹果QQ", 21);
        regionMap.put("苹果微信", 22);
        zjyGameTypeMap.put("王者荣耀", regionMap);
        regionMap = new HashMap<>();
        regionMap.put("gameId", 9);
        regionMap.put("安卓QQ", 23);
        regionMap.put("安卓微信", 24);
        regionMap.put("苹果QQ", 25);
        regionMap.put("苹果微信", 26);
        zjyGameTypeMap.put("枪战王者", regionMap);
        regionMap = new HashMap<>();
        regionMap.put("gameId", 10);
        regionMap.put("安卓QQ", 27);
        regionMap.put("安卓微信", 28);
        regionMap.put("苹果QQ", 29);
        regionMap.put("苹果微信", 30);
        zjyGameTypeMap.put("火影忍者", regionMap);
        regionMap = new HashMap<>();
        regionMap.put("gameId", 11);
        regionMap.put("安卓QQ", 31);
        regionMap.put("安卓微信", 32);
        regionMap.put("苹果QQ", 33);
        regionMap.put("苹果微信", 34);
        zjyGameTypeMap.put("QQ飞车手游", regionMap);
        regionMap = new HashMap<>();
        regionMap.put("gameId", 12);
        regionMap.put("东部战区", 126);
        regionMap.put("西部战区", 127);
        regionMap.put("南部战区", 128);
        regionMap.put("北部战区", 129);
        zjyGameTypeMap.put("穿越火线", regionMap);
        regionMap = new HashMap<>();
        regionMap.put("gameId", 13);
        regionMap.put("电信区", 35);
        regionMap.put("网通区", 36);
        regionMap.put("峡谷之巅", 37);
        regionMap.put("男爵领域", 38);
        regionMap.put("艾欧尼亚", 50);
        regionMap.put("暗影岛", 51);
        regionMap.put("班德尔城", 52);
        regionMap.put("裁决之地", 53);
        regionMap.put("钢铁烈阳", 54);
        regionMap.put("黑色玫瑰", 55);
        regionMap.put("巨神峰", 56);
        regionMap.put("均衡教派", 57);
        regionMap.put("卡拉曼达", 58);
        regionMap.put("雷瑟守备", 59);
        regionMap.put("诺克萨斯", 60);
        regionMap.put("皮城警备", 61);
        regionMap.put("皮尔特沃夫", 62);
        regionMap.put("守望之海", 63);
        regionMap.put("水晶之痕", 64);
        regionMap.put("影流", 65);
        regionMap.put("战争学院", 66);
        regionMap.put("征服之海", 67);
        regionMap.put("祖安", 68);
        regionMap.put("比尔吉沃特", 69);
        regionMap.put("德玛西亚", 70);
        regionMap.put("弗雷尔卓德", 71);
        regionMap.put("无畏先锋", 72);
        regionMap.put("恕瑞玛", 73);
        regionMap.put("扭曲丛林", 74);
        regionMap.put("巨龙之巢", 75);
        regionMap.put("试练之地", 76);
        regionMap.put("教育网专区", 77);
        zjyGameTypeMap.put("英雄联盟", regionMap);
        regionMap = new HashMap<>();
        regionMap.put("gameId", 14);
        regionMap.put("安卓QQ", 39);
        regionMap.put("安卓WX", 40);
        regionMap.put("苹果Q", 41);
        regionMap.put("苹果WX", 42);
        zjyGameTypeMap.put("QQ炫舞手游", regionMap);
        regionMap = new HashMap<>();
        regionMap.put("gameId", 46);
        zjyGameTypeMap.put("魔兽世界（弃用）", regionMap);
        regionMap = new HashMap<>();
        regionMap.put("gameId", 78);
        regionMap.put("地下城与勇士", 88);
        zjyGameTypeMap.put("地下城与勇士", regionMap);
        regionMap = new HashMap<>();
        regionMap.put("gameId", 79);
        regionMap.put("电信一", 82);
        regionMap.put("电信二", 83);
        regionMap.put("网通区", 84);
        zjyGameTypeMap.put("QQ飞车端游", regionMap);
        regionMap = new HashMap<>();
        regionMap.put("gameId", 80);
        regionMap.put("电信", 85);
        regionMap.put("网通", 86);
        zjyGameTypeMap.put("逆战", regionMap);
        regionMap = new HashMap<>();
        regionMap.put("gameId", 81);
        regionMap.put("炉石传说", 87);
        zjyGameTypeMap.put("炉石传说", regionMap);
        regionMap = new HashMap<>();
        regionMap.put("gameId", 113);
        regionMap.put("游戏官方", 114);
        regionMap.put("哔哩哔哩", 115);
        zjyGameTypeMap.put("原神", regionMap);
        regionMap = new HashMap<>();
        regionMap.put("gameId", 117);
        regionMap.put("网易", 118);
        regionMap.put("Steam", 119);
        zjyGameTypeMap.put("永劫无间", regionMap);
        regionMap = new HashMap<>();
        regionMap.put("gameId", 141);
        regionMap.put("官服", 142);
        zjyGameTypeMap.put("魔兽世界正式服", regionMap);
        regionMap = new HashMap<>();
        regionMap.put("gameId", 143);
        regionMap.put("燃烧的远征", 144);
        zjyGameTypeMap.put("魔兽世界怀旧服", regionMap);
    }

    public static String toLC(String r) {

        if (Objects.equals("安卓WX", r)) {
            return "安卓微信";
        } else if (Objects.equals("安卓Q", r)) {
            return "安卓QQ";
        } else if (Objects.equals("苹果WX", r)) {
            return "苹果微信";
        } else if (Objects.equals("苹果Q", r)) {
            return "苹果QQ";
        } else if (Objects.equals("官服", r)) {
            return "官方服";
        } else if (Objects.equals("Steam", r)) {
            return "steam";
        } else if (Objects.equals("安卓网易邮箱", r)) {
            return "安卓官服";
        } else if (Objects.equals("苹果网易邮箱", r)) {
            return "苹果官服";
        } else {
            return null;
        }
    }


    public static String getLCPareJson(String r) {
        String lcgamElIST = LCConstant.LCGAMElIST;
        JSONObject jsonObject = JSONObject.parseObject(lcgamElIST);
        String data = jsonObject.getString("data");
        JSONArray objects = JSONArray.parseArray(data);
        for (Object object : objects) {
            String jsonString = JSONObject.toJSONString(object);
            JSONObject jsonObject1 = JSONObject.parseObject(jsonString);
            if (Objects.equals(jsonObject1.getString("game_name"), r)) ;
            return JSONObject.toJSONString(jsonObject1);
        }
        return null;
    }


    public static String toMMTpye(String r) {

        if (Objects.equals("安卓WX", r)) {
            return "安卓微信";
        } else if (Objects.equals("安卓Q", r)) {
            return "安卓QQ";
        } else if (Objects.equals("苹果WX", r)) {
            return "苹果微信";
        } else if (Objects.equals("苹果Q", r)) {
            return "苹果QQ";
        } else {
            return null;
        }
    }

    public static String toPX(String r) {

        if (Objects.equals("安卓WX", r)) {
            return "2";
        } else if (Objects.equals("安卓Q", r)) {
            return "1";
        } else if (Objects.equals("苹果WX", r)) {
            return "4";
        } else if (Objects.equals("苹果Q", r)) {
            return "3";
        } else if (Objects.equals("官服", r)) {
            return "mihoyo";
        } else if (Arrays.asList(dianxin.split(",")).contains(r)) {
            return "1";
        } else if (Arrays.asList(wangtong.split(",")).contains(r)) {
            return "2";
        } else if (Objects.equals("男爵领域", r)) {
            return "5";
        } else if (Objects.equals("峡谷之巅", r)) {
            return "4";
        } else if (Objects.equals("教育网专区", r)) {
            return "6";
        } else if (Arrays.asList(waifu.split(",")).contains(r)) {
            return "国际服区";
        } else {
            return null;
        }
    }

    public static String toKJS(String r, String gameType) {
        if (GameTypeConstant.WZRY.equals(gameType)) {
            if (Objects.equals("安卓WX", r)) {
                return "3";
            } else if (Objects.equals("安卓Q", r)) {
                return "2";
            } else if (Objects.equals("苹果WX", r)) {
                return "5";
            } else if (Objects.equals("苹果Q", r)) {
                return "4";
            } else {
                return null;
            }
        } else if (GameTypeConstant.HPJY.equals(gameType)) {
            if (Objects.equals("安卓WX", r)) {
                return "44";

            } else if (Objects.equals("安卓Q", r)) {
                return "43";
            } else if (Objects.equals("苹果WX", r)) {
                return "46";
            } else if (Objects.equals("苹果Q", r)) {
                return "45";
            } else {
                return null;
            }
        } else if (GameTypeConstant.YS.equals(gameType)) {
            if (Objects.equals("官服", r)) {
                return "51";
            } else if (Objects.equals("B服", r)) {
                return "104";
            } else {
                return null;
            }
        } else if (GameTypeConstant.LOLMOBILE.equals(gameType)) {
            if (Objects.equals("安卓WX", r)) {
                return "218";
            } else if (Objects.equals("安卓Q", r)) {
                return "217";
            } else if (Objects.equals("苹果WX", r)) {
                return "200";
            } else if (Objects.equals("苹果Q", r)) {
                return "219";
            } else {
                return null;
            }
        } else if (GameTypeConstant.LOL.equals(gameType)) {
            String s = toKJSserver(r, gameType);
            if (StringUtils.isEmpty(s)) {
                return null;
            } else {
                Integer serverId = Integer.valueOf(s);
                if (serverId < 33) {
                    //电信
                    return "11";
                } else if (serverId > 32 && serverId < 41) {
                    //网通
                    return "12";
                } else {
                    //峡谷之巅
                    return "13";
                }
            }
        }
        return null;
    }

    //王者服务器
    public static String toKJSserver(String r, String gameType) {
        if (GameTypeConstant.WZRY.equals(gameType)) {
            if (Objects.equals("安卓WX", r)) {
                return "7";
            } else if (Objects.equals("安卓Q", r)) {
                return "6";
            } else if (Objects.equals("苹果WX", r)) {
                return "9";
            } else if (Objects.equals("苹果Q", r)) {
                return "8";
            } else {
                return null;
            }
        } else if (GameTypeConstant.YS.equals(gameType)) {
            if (Objects.equals("官服", r)) {
                return "52";
            } else if (Objects.equals("B服", r)) {
                return "105";
            } else {
                return null;
            }
        } else if (GameTypeConstant.HPJY.equals(gameType)) {
            String s = toKJS(r, gameType);
            if ("43".equals(s)) {
                return "300";
            } else {
                return "不用传";
            }
        } else if (GameTypeConstant.LOL.equals(gameType)) {
            if (Objects.equals("艾欧尼亚", r)) {
                return "14";
            } else if (Objects.equals("祖安", r)) {
                return "15";
            } else if (Objects.equals("诺克萨斯", r)) {
                return "16";
            } else if (Objects.equals("班德尔城", r)) {
                return "17";
            } else if (Objects.equals("皮尔特沃夫", r)) {
                return "18";
            } else if (Objects.equals("战争学院", r)) {
                return "19";
            } else if (Objects.equals("巨神峰", r)) {
                return "20";
            } else if (Objects.equals("雷瑟守备", r)) {
                return "21";
            } else if (Objects.equals("裁决之地", r)) {
                return "22";
            } else if (Objects.equals("黑色玫瑰", r)) {
                return "23";
            } else if (Objects.equals("暗影岛", r)) {
                return "24";
            } else if (Objects.equals("钢铁烈阳", r)) {
                return "25";
            } else if (Objects.equals("水晶之痕", r)) {
                return "26";
            } else if (Objects.equals("均衡教派", r)) {
                return "27";
            } else if (Objects.equals("影流", r)) {
                return "28";
            } else if (Objects.equals("守望之海", r)) {
                return "29";
            } else if (Objects.equals("征服之海", r)) {
                return "30";
            } else if (Objects.equals("卡拉曼达", r)) {
                return "31";
            } else if (Objects.equals("皮城警备", r)) {
                return "32";
            } else if (Objects.equals("比尔吉沃特", r)) {
                return "33";
            } else if (Objects.equals("德玛西亚", r)) {
                return "34";
            } else if (Objects.equals("弗雷尔卓德", r)) {
                return "35";
            } else if (Objects.equals("无畏先锋", r)) {
                return "36";
            } else if (Objects.equals("恕瑞玛", r)) {
                return "37";
            } else if (Objects.equals("扭曲丛林", r)) {
                return "38";
            } else if (Objects.equals("巨龙之巢", r)) {
                return "39";
            } else if (Objects.equals("男爵领域", r)) {
                return "40";
            } else if (Objects.equals("峡谷之巅", r)) {
                return "41";
            } else {
                return null;
            }
        }
        return null;
    }

    public static String toTQY(String r) {

        if (Objects.equals("安卓WX", r)) {
            return "213";
        } else if (Objects.equals("安卓Q", r)) {
            return "212";
        } else if (Objects.equals("苹果WX", r)) {
            return "211";
        } else if (Objects.equals("苹果Q", r)) {
            return "210";
        } else if (Objects.equals("官服", r)) {
            return "1971";
        } else if (Objects.equals("B服", r)) {
            return "1998";
        } else if (Objects.equals("国际服", r)) {
            return "1992";
        } else {
            return null;
        }
    }

    /*        1 DNF 2 英雄联盟 99 Steam游戏 100 LOL手游（日服）
                204 魔兽世界 205 王者荣耀 206 穿越火线 207 和平精英 208 枪战王者
                209 火影忍者 1970 原神 1973 QQ飞车 1974 QQ飞车手游
                1975 逆战 1976 球球大作战 1977 炉石传说 1978 使命召唤
                2005 英雄联盟手游 2006 永劫无间 2013 哈利波特 2061 天涯明月刀手游
                2062 秦时明月世界 2063 自由幻想手游 2064 征服与霸业 2065 妄想山海 2066
        荣耀新三国 2067 全民奇迹2 2068 鸿图之下 2069 全民冠军足球 2070 火影忍者新世代
        2071 乱世王者 2072 数码宝贝:新世纪 2085 斗罗大陆魂师对决 2087 灵魂潮汐 2089 爆裂魔女
        2091 三国志战略版 2093 梦想新大陆 2095 超激斗梦境 2096 幻塔 2097 泰亚史诗*/
    public static String typeToTQY(String r) {
        if (Objects.equals("英雄联盟", r)) {
            return "2";
        } else if (Objects.equals("原神", r)) {
            return "1970";
        } else if (Objects.equals("王者荣耀", r)) {
            return "205";
        } else if (Objects.equals("和平精英", r)) {
            return "207";
        } else if (Objects.equals("DNF", r)) {
            return "1";
        } else if (Objects.equals("英雄联盟手游", r)) {
            return "2005";
        } else if (Objects.equals("永劫无间", r)) {
            return "2006";
        } else {
            return null;
        }
    }


    public static Integer typeToHHB(String r) {
        if (Objects.equals("英雄联盟", r)) {
            return 2;
        } else if (Objects.equals("原神", r)) {
            return 1970;
        } else if (Objects.equals("王者荣耀", r)) {
            return 205;
        } else if (Objects.equals("和平精英", r)) {
            return 207;
        } else if (Objects.equals("DNF", r)) {
            return 1;
        } else if (Objects.equals("CF端游", r)) {
            return 206;
        } else if (Objects.equals("QQ飞车", r)) {
            return 1973;
        } else if (Objects.equals("使命召唤", r)) {
            return 1978;
        } else {
            return null;
        }
    }

    public static String toHHBArea(String gameType, String r) {

        if (GameTypeConstant.WZRY.equals(gameType)) {
            if (Objects.equals("安卓WX", r)) {
                return "213";
            } else if (Objects.equals("安卓Q", r)) {
                return "212";
            } else if (Objects.equals("苹果WX", r)) {
                return "211";
            } else if (Objects.equals("苹果Q", r)) {
                return "210";
            } else {
                return null;
            }
        } else if (GameTypeConstant.YS.equals(gameType)) {
            if (Objects.equals("官服", r)) {
                return "1972";
            } else if (Objects.equals("B服", r)) {
                return "1998";
            } else if (Objects.equals("国际服", r)) {
                return "1971";
            } else {
                return null;
            }
        } else if (GameTypeConstant.HPJY.equals(gameType)) {
            if (Objects.equals("安卓WX", r)) {
                return "1917";
            } else if (Objects.equals("安卓Q", r)) {
                return "1950";
            } else if (Objects.equals("苹果WX", r)) {
                return "1918";
            } else if (Objects.equals("苹果Q", r)) {
                return "1951";
            } else {
                return null;
            }
        } else if (GameTypeConstant.LOL.equals(gameType)) {
            return "37";
        }
        return null;
    }

    public static String toHHBserver(String gameType, String r) {


        if (GameTypeConstant.WZRY.equals(gameType)) {
            if (Objects.equals("安卓WX", r)) {
                return "213";
            } else if (Objects.equals("安卓Q", r)) {
                return "212";
            } else if (Objects.equals("苹果WX", r)) {
                return "211";
            } else if (Objects.equals("苹果Q", r)) {
                return "210";
            } else {
                return null;
            }
        } else if (GameTypeConstant.YS.equals(gameType)) {
            if (Objects.equals("官服", r)) {
                return "1972";
            } else if (Objects.equals("B服", r)) {
                return "1998";
            } else if (Objects.equals("国际服", r)) {
                return "1971";
            } else {
                return null;
            }
        } else if (GameTypeConstant.HPJY.equals(gameType)) {
            if (Objects.equals("安卓WX", r)) {
                return "1917";
            } else if (Objects.equals("安卓Q", r)) {
                return "1950";
            } else if (Objects.equals("苹果WX", r)) {
                return "1918";
            } else if (Objects.equals("苹果Q", r)) {
                return "1951";
            } else {
                return null;
            }
        } else if (GameTypeConstant.CF.equals(gameType)) {
            if (Objects.equals("北部大区", r)) {
                return "1835";
            } else if (Objects.equals("东部大区", r)) {
                return "2000";
            } else if (Objects.equals("西部大区", r)) {
                return "2001";
            } else if (Objects.equals("南部大区", r)) {
                return "2002";
            } else {
                return null;
            }
        } else if (GameTypeConstant.YJWJ.equals(gameType)) {
            if (Objects.equals("Steam", r)) {
                return "2007";
            } else if (Objects.equals("网易", r)) {
                return "2008";
            } else if (Objects.equals("西部战区", r)) {
                return null;
            }
        } else if (GameTypeConstant.LOL.equals(gameType)) {

            if (Arrays.asList(dianxin.split(",")).contains(r)) {
                return "34";
            } else if (Arrays.asList(wangtong.split(",")).contains(r)) {
                return "35";
            } else if (Arrays.asList(quanwangluo.split(",")).contains(r)) {
                return "37";
            } else if (Arrays.asList(waifu.split(",")).contains(r)) {
                return "38";
            }
        }
        return null;
    }

    public static String toMM(String r) {
        if (Objects.equals("安卓微信", r)) {
            return "安卓微信";
        } else if (Objects.equals("安卓QQ", r)) {
            return "安卓QQ";
        } else if (Objects.equals("苹果微信", r)) {
            return "苹果微信";
        } else if (Objects.equals("苹果QQ", r)) {
            return "苹果QQ";
        } else {
            return null;
        }
    }
    public static String toCF(String r,String des) {
        String daqu = "";
        String xiaoqu = "";
        if (des.contains("移动专区")) {
            daqu = "移动专区";
            xiaoqu = "移动专区";
        } else if (des.contains("教育网专区")) {
            daqu = "教育网专区";
            xiaoqu = "教育网专区";
        } else if (Objects.equals("东部战区", r)) {
            daqu = "东部战区";
        } else if (Objects.equals("西部战区", r)) {
            daqu = "西部战区";
        } else if (Objects.equals("北部战区", r)) {
            daqu = "北部战区";
        } else if (Objects.equals("南部战区", r)) {
            daqu = "南部战区";
        }
        String[] strings = cfdianxin.split(",");
        for(String s: strings){
            if(des.contains(s)) {
                daqu = "电信大区";
                xiaoqu = s;
            }
        }
        String[] strings1 = cfwangtong.split(",");
        for(String s: strings1){
            if(des.replace("黑龙江大区","黑龙江一区").contains(s)) {
                daqu = "网通大区";
                xiaoqu = s;
            }
        }

        return daqu+" "+xiaoqu;
    }
}
