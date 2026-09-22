package com.ruoyi.system.V2.font.service.impl;

import com.ruoyi.system.V2.financial.mapper.FinanceMapper;
import com.ruoyi.system.V2.financial.service.IFinanceStatisticsService;
import com.ruoyi.system.V2.font.service.IDataCenterService;
import com.ruoyi.system.V2.goodInfo.mapper.GoodsInfoMapper;
import com.ruoyi.system.model.domain.Finance;
import com.ruoyi.system.model.domain.GoodsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lz
 * @version 1.0
 * @date 2022/9/26 16:31
 */
@Service
public class DataCenterServiceImpl implements IDataCenterService {

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
    @Autowired
    private FinanceMapper financeMapper;
    @Autowired
    private IFinanceStatisticsService financeStatisticsService;

    @Override
    public Map<String, Object> source() {
        Map<String, Object> result = new HashMap<>();
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setSaleState(1);
        goodsInfo.setCreateTime(new Date());
        List<Map<String, Object>> SXlist = goodsInfoMapper.goodsCount(goodsInfo);
        goodsInfo = new GoodsInfo();
        goodsInfo.setSaleState(3);
        List<Map<String, Object>> YSlist = goodsInfoMapper.goodsCount(goodsInfo);
        goodsInfo.setSaleState(6);
        List<Map<String, Object>> ZHlist = goodsInfoMapper.goodsCount(goodsInfo);
        goodsInfo.setSaleState(5);
        List<Map<String, Object>> WTlist = goodsInfoMapper.goodsCount(goodsInfo);

        result.put("shangxin", SXlist); //上新
        result.put("yishou", YSlist); //上新
        result.put("zhaohui", ZHlist); //找回
        result.put("wenti", WTlist); //问题
        return result;
    }

    @Override
    public List<Map<String, Object>> hotGame() {
        List<Map<String, Object>> list = goodsInfoMapper.hotGame(new GoodsInfo());
        return list;
    }

    @Override
    public Map<String, Object> todayIncome() {
        Finance finance = new Finance();
        finance.setDay(0);
        Map<String, Object> result = financeMapper.getData(finance);
        return result;
    }

    @Override
    public Map<String, Object> todayHSSC() {
        Map<String, Object> result = new HashMap<>();
        Finance finance = new Finance();
        finance.setBillType(9l);
        List<Map<String, Object>> SCList = financeMapper.todayHSSC(finance);
        finance.setBillType(13l);
        List<Map<String, Object>> HSList = financeMapper.todayHSSC(finance);
        result.put("shouchu", SCList);
        result.put("huishou", HSList);
        return result;
    }

    @Override
    public List<Map<String, Object>> todaySCinfo(Finance finance) {
        List<Map<String, Object>> list = financeMapper.todaySCinfo(finance);
        return list;
    }

    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal("0");
        BigDecimal bigDecimal1 = new BigDecimal("1");
        BigDecimal subtract = bigDecimal.subtract(bigDecimal1);
        System.out.println(subtract);
    }
}
