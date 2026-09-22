package com.ruoyi.system.V2.font.service.impl;

import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoMapper;
import com.ruoyi.system.model.domain.GoodsInfo;
import com.ruoyi.system.model.mapper.AccountInfoMapper;
import com.ruoyi.system.V2.font.service.IIndexService;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author lz
 * @version 1.0
 * @date 2022/9/26 16:31
 */
@Service
public class IndexServiceImpl implements IIndexService {


    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
    @Resource
    private AccountInfoMapper accountInfoMapper;

    @Override
    public Map<String, Object> salesCount(GoodsInfo goodsInfo) {

        //当月销量  本周销量  当天销量  本年销量
        // select * from 表名 where TO_DAYS(时间字段)=TO_DAYS(NOW()); 当天
        // select * from 表名 where YEARWEEK(DATE_FORMAT(时间字段,‘%Y-%m-%d’))=YEARWEEK(NOW()); 本周销量
        // select * from 表名 where DATE_FORMAT(时间字段,‘%Y%m’)=DATE_FORMAT(CURDATE(),‘%Y%m’);  查询当月的数据
        // select * from 表名 where YEAR(时间字段) =YEAR(NOW()); 当年

        Map<String, Object> result = new HashMap<>();
        Map total = goodsInfoMapper.statistics(goodsInfo);
        goodsInfo.setSaleState(1);
        Map unsold = goodsInfoMapper.statistics(goodsInfo);
        goodsInfo.setSaleState(3);
        Map sold = goodsInfoMapper.statistics(goodsInfo);
        goodsInfo.setSaleState(5);
        Map problem = goodsInfoMapper.statistics(goodsInfo);
        goodsInfo.setSaleState(6);
        Map retrieve = goodsInfoMapper.statistics(goodsInfo);
        goodsInfo.setSaleState(null);
        goodsInfo.setCreateTime(new Date());
        Map Shangxin = goodsInfoMapper.statistics(goodsInfo);
        result.put("total", total);  //账号
        result.put("unsold", unsold); //未售
        result.put("sold", sold);//已售
        result.put("problem", problem); //问题
        result.put("retrieve", retrieve); //找回
        result.put("Shangxin", Shangxin); //上新
        return result;
    }

    @Override
    public Map<String, Object> salesSum() {

        Integer todaySum = 0;
        Integer weekSum = 0;
        Integer monthSum = 0;
        Integer yearSum = 0;
        Map<String, Object> result = new HashMap<>();
        todaySum = accountInfoMapper.todaySum();
        weekSum = accountInfoMapper.weekSum();
        monthSum = accountInfoMapper.monthSum();
        yearSum = accountInfoMapper.yearSum();
        result.put("todaySum", todaySum);
        result.put("weekSum", weekSum);
        result.put("monthSum", monthSum);
        result.put("yearSum", yearSum);
        return result;

    }

    @Override
    public Map<String, Object> gameTypeFor() {

        Map<String, Object> result = new HashMap<>();

        List<Map<String, Object>> gameTypeFor = accountInfoMapper.gameTypeFor();
        result.put("gameTypeFor", gameTypeFor);
        List<String> gameType = accountInfoMapper.getGameTpes();
        result.put("gameType", gameType);
        return result;
    }

    @Override
    public Map<String, Object> sevenDaysCount() {


       /* {
            name: '王者荣耀',
                    type: 'bar',
                stack: 'vistors',
                barWidth: '60%',
                data: [79, 52, 200, 334, 390, 330, 220],
            animationDuration
        }*/

        Map<String, Object> result = new HashMap<>();

        List<String> sevenDayDate = accountInfoMapper.sevenDayDate();
        result.put("sevenDayDate", sevenDayDate);
        List<String> gameType = accountInfoMapper.getGameTpes();
        List<Map<String, Object>> series = new ArrayList<>();
        for (String type : gameType) {
            Map<String, Object> serie = new HashMap<>();
            serie.put("name", type);
            serie.put("type", "bar");
            serie.put("stack", "vistors");
            serie.put("barWidth", "60%");
            List<String> gameTypeFor = accountInfoMapper.sevenDaysCount(type);
            serie.put("data", gameTypeFor);
            series.add(serie);
        }
        result.put("series", series);
        return result;
    }

    @Override
    public Map<String, Object> yearCount() {

        String[] colors = {"#ffff33", "#D52B6F", "#573CC4", "#3C72C4", "#6DDD22", "#D54D2B", "#4474BB", "#CD00CD",
                "#B3EE3A", "#98FB98",
                "#7FFF00", "#66CD00", "#00FA9A"};


        //总数
        List<Map<String, Object>> series = new ArrayList<>();

        List<String> yearDate = accountInfoMapper.yearDate();

        Map<String, Object> result = new HashMap<>();
        List<String> gameType = accountInfoMapper.getGameTpes();
        int i = 0;
        for (String type : gameType) {
            Map<String, Object> serie = new HashMap<>();
            serie.put("name", type);
            Map<String, Object> itemStyle = new HashMap<>();
            Map<String, Object> normal = new HashMap<>();
            normal.put("color", colors[i]);
            Map<String, Object> lineStyle = new HashMap<>();
            lineStyle.put("color", colors[i]);
            lineStyle.put("width", 2);
            normal.put("lineStyle", lineStyle);
            itemStyle.put("normal", normal);
            serie.put("smooth", true);
            serie.put("type", "line");
            serie.put("itemStyle", itemStyle);

            List<Integer> yearCount = accountInfoMapper.yearCountGroup(type);
            serie.put("data", yearCount);
            serie.put("animationDuration", 2800);
            serie.put("animationEasing", "cubicInOut");
            series.add(serie);
            i++;
        }
        result.put("dateGroup", yearDate);
        result.put("series", series);
        return result;
    }


    @Override
    public Map<String, Object> gameTypeSale(Map<String, Object> params) {


        //有游戏的供货商
        //游戏总数
        List<Map<String, Object>> gameUser = accountInfoMapper.gameUser();


        Set<String> userNames = new HashSet<>();
        for (Map<String, Object> stringObjectMap : gameUser) {
            userNames.add(MapUtils.getString(stringObjectMap, "nick_name"));
        }


        //游戏列表
//        List<String> gameType = accountInfoMapper.getGameTpes();

//        "销售金额",
//                "已售个数",
//                "新增成本",
//                "新增个数",
//                "问题个数",
//                "问题成本",
//                "找回个数",
//                "找回成本"
        List<String> title = new ArrayList<>();
        title.add("王者荣耀");
        title.add("和平精英");
        title.add("永劫无间");
        title.add("原神");
        title.add("CF端游");
        title.add("CF手游");
        title.add("火影忍者");

        List<Integer> wzCount = new ArrayList<>();
        List<Integer> hpCount = new ArrayList<>();
        List<Integer> yjCount = new ArrayList<>();
        List<Integer> ysCount = new ArrayList<>();
        List<Integer> cfdyCount = new ArrayList<>();
        List<Integer> cfsyCount = new ArrayList<>();
        List<Integer> hyCount = new ArrayList<>();

        List<Object> yDataList = new ArrayList<>();

        List<String> games = new ArrayList<>();

        for (String game : userNames) {

            for (String t : title) {
                if (Objects.equals("王者荣耀", t)) {

                }
            }


        }

        for (String t : title) {
            if (Objects.equals("王者荣耀", t)) {
                for (String uname : userNames) {
                    for (Map gm : gameUser) {
                        if (MapUtils.getString(gm, "type").equals(t) && uname.equals(MapUtils.getString(gm,
                                "nick_name"))) {
                            Integer ctt = MapUtils.getInteger(gm, "ctt");
                            if (Objects.isNull(ctt)) {
                                wzCount.add(0);
                            } else {
                                wzCount.add(ctt);
                            }
                        }
                    }
                }

            }
            if (Objects.equals("和平精英", t)) {
                for (String uname : userNames) {
                    for (Map gm : gameUser) {
                        if (MapUtils.getString(gm, "type").equals(t) && uname.equals(MapUtils.getString(gm,
                                "nick_name"))) {
                            Integer ctt = MapUtils.getInteger(gm, "ctt");
                            if (Objects.isNull(ctt)) {
                                wzCount.add(0);
                            } else {
                                wzCount.add(ctt);
                            }
                        }
                    }
                }
            }
            if (Objects.equals("永劫无间", t)) {

                for (String uname : userNames) {
                    for (Map gm : gameUser) {
                        if (MapUtils.getString(gm, "type").equals(t) && uname.equals(MapUtils.getString(gm,
                                "nick_name"))) {
                            Integer ctt = MapUtils.getInteger(gm, "ctt");
                            if (Objects.isNull(ctt)) {
                                yjCount.add(0);
                            } else {
                                yjCount.add(ctt);
                            }
                        }
                    }
                }
            }
            if (Objects.equals("原神", t)) {
                for (String uname : userNames) {
                    for (Map gm : gameUser) {
                        if (MapUtils.getString(gm, "type").equals(t) && uname.equals(MapUtils.getString(gm,
                                "nick_name"))) {
                            Integer ctt = MapUtils.getInteger(gm, "ctt");
                            if (Objects.isNull(ctt)) {
                                ysCount.add(0);
                            } else {
                                ysCount.add(ctt);
                            }
                        }
                    }
                }
            }
            if (Objects.equals("CF端游", t)) {

                for (String uname : userNames) {
                    for (Map gm : gameUser) {
                        if (MapUtils.getString(gm, "type").equals(t) && uname.equals(MapUtils.getString(gm,
                                "nick_name"))) {
                            Integer ctt = MapUtils.getInteger(gm, "ctt");
                            if (Objects.isNull(ctt)) {
                                cfdyCount.add(0);
                            } else {
                                cfdyCount.add(ctt);
                            }
                        }
                    }
                }
            }
            if (Objects.equals("CF手游", t)) {
                for (String uname : userNames) {
                    for (Map gm : gameUser) {
                        if (MapUtils.getString(gm, "type").equals(t) && uname.equals(MapUtils.getString(gm,
                                "nick_name"))) {
                            Integer ctt = MapUtils.getInteger(gm, "ctt");
                            if (Objects.isNull(ctt)) {
                                cfsyCount.add(0);
                            } else {
                                cfsyCount.add(ctt);
                            }
                        }
                    }
                }
            }
            if (Objects.equals("火影忍者", t)) {

                for (String uname : userNames) {
                    for (Map gm : gameUser) {
                        if (MapUtils.getString(gm, "type").equals(t) && uname.equals(MapUtils.getString(gm,
                                "nick_name"))) {
                            Integer ctt = MapUtils.getInteger(gm, "ctt");
                            if (Objects.isNull(ctt)) {
                                hyCount.add(0);
                            } else {
                                hyCount.add(ctt);
                            }
                        }
                    }
                }
            }
        }

        yDataList.add(wzCount);
        yDataList.add(hpCount);
        yDataList.add(yjCount);
        yDataList.add(ysCount);
        yDataList.add(cfdyCount);
        yDataList.add(cfsyCount);
        yDataList.add(hyCount);

//        for (String t : title) {
//            if (Objects.equals("已售", t)) {
//                for (String type : gameType) {
//                    Map<String, Object> map = accountInfoMapper.stateCount(SalerStatus.InTheSale.getCode(), type,
//                    startDate, endDate);
//                    //总数
//                    saleCount.add(MapUtils.getInteger(map, "countt"));
//                    //总金额
//                    salePrice.add(new BigDecimal( MapUtils.getDouble(map, "sumPrice")));
//                }
//            }
//            if (Objects.equals("未售", t)) {
//                for (String type : gameType) {
//                    Map<String, Object> map = accountInfoMapper.stateCount(SalerStatus.unsold.getCode(), type,
//                    startDate, endDate);
//                    //总数
//                    addCount.add(MapUtils.getInteger(map, "countt"));
//                    //总金额
//                    addPrice.add(new BigDecimal( MapUtils.getDouble(map, "sumPrice")));
//                }
//            }
//            if (Objects.equals("问题", t)) {
//                for (String type : gameType) {
//                    Map<String, Object> map = accountInfoMapper.stateCount(SalerStatus.problem.getCode(), type,
//                    startDate, endDate);
//                    //总数
//                    bugCount.add(MapUtils.getInteger(map, "countt"));
//                    //总金额
//                    bugPrice.add(new BigDecimal( MapUtils.getDouble(map, "sumPrice")));
//                }
//            }
//            if (Objects.equals("找回", t)) {
//                for (String type : gameType) {
//                    Map<String, Object> map = accountInfoMapper.stateCount(SalerStatus.back.getCode(), type,
//                    startDate, endDate);
//                    //总数
//                    backCount.add(MapUtils.getInteger(map, "countt"));
//                    //总金额
//                    backPrice.add(new BigDecimal( MapUtils.getDouble(map, "sumPrice")));
//                }
//            }
//        }

        Map<String, Object> result = new HashMap<>();
        result.put("xData", userNames);
        result.put("yDataList", yDataList);

        return result;
    }


    @Override
    public Map<String, Object> gameTypeGon() {


        Map<String, Object> result = new HashMap<>();

        //游戏列表
        List<String> gameType = accountInfoMapper.getGameTpes();
        List<Map<String, Object>> gameTypeResult = new ArrayList<>();
        List<String> salePriceList = new ArrayList<>();
        List<String> profitsPriceList = new ArrayList<>();
        List<String> saleCountList = new ArrayList<>();
        for (String type : gameType) {
            Map<String, Object> gameTypeMap = new HashMap<>();
            gameTypeMap.put("name", type);
            gameTypeResult.add(gameTypeMap);
            //销售额
            String salePrice = accountInfoMapper.salePrice(type);
            salePriceList.add(salePrice);
            result.put("salePriceList", salePriceList);
            //净利润
            String profitsPrice = accountInfoMapper.profitsPrice(type);
            profitsPriceList.add(profitsPrice);
            result.put("profitsPriceList", profitsPriceList);

            //销售量
            String saleCount = accountInfoMapper.saleCount(type);
            saleCountList.add(saleCount);
            result.put("saleCountList", saleCountList);
        }
        result.put("gameTypeResult", gameTypeResult);
        return result;
    }
}
