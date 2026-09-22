package com.ruoyi.web.controller.V2.font.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.financial.service.IFinanceStatisticsService;
import com.ruoyi.system.V2.font.service.IDataCenterService;
import com.ruoyi.system.model.domain.Finance;
import com.ruoyi.system.model.domain.FinanceStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 数据中心Controller
 *
 * @author zyy
 * @date 2023-09-09
 */
@RestController
@RequestMapping("/data/center")
public class DataCenterController extends BaseController {

    @Autowired
    private IDataCenterService dataCenterService;
    @Autowired
    private IFinanceStatisticsService financeStatisticsService;

    /**
     * 货源统计
     */
    @GetMapping("/source")
    public AjaxResult source() {
        Map<String, Object> result = dataCenterService.source();
        return AjaxResult.success(result);
    }


    /**
     * 热门游戏
     */
    @GetMapping("/hotGame")
    public AjaxResult hotGame() {
        List<Map<String, Object>> result = dataCenterService.hotGame();
        return AjaxResult.success(result);
    }

    /**
     * 今日收入支出
     */
    @GetMapping("/today")
    public AjaxResult today() {
        Map<String, Object> result = dataCenterService.todayIncome();
        return AjaxResult.success(result);
    }

    /**
     * 今日回收和售出
     */
    @GetMapping("/todayHSSC")
    public AjaxResult todayHSSC() {
        Map<String, Object> result = dataCenterService.todayHSSC();
        return AjaxResult.success(result);
    }

    /**
     * 今日实时售出订单详情
     */
    @GetMapping("/todaySCinfo")
    public AjaxResult todaySCinfo(Finance finance) {
        finance.setBillType(9l);
        List<Map<String, Object>> result = dataCenterService.todaySCinfo(finance);
        return AjaxResult.success(result);
    }

    /**
     * 财务统计
     */
    @GetMapping("/finance")
    public AjaxResult finance(FinanceStatistics financeStatistics) {
        if (StringUtils.isNull(financeStatistics.getStatisticsDateStart())) {
            throw new GlobalException("start日期不能为空");
        }
        if (StringUtils.isNull(financeStatistics.getStatisticsDateEnd())) {
            throw new GlobalException("end日期不能为空");
        }
        LambdaQueryWrapper<FinanceStatistics> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.ge(FinanceStatistics::getStatisticsDate, financeStatistics.getStatisticsDateStart());
        queryWrapper.le(FinanceStatistics::getStatisticsDate, financeStatistics.getStatisticsDateEnd());
        queryWrapper.orderByAsc(FinanceStatistics::getStatisticsDate);
        List<FinanceStatistics> list = financeStatisticsService.list(queryWrapper);
        return AjaxResult.success(list);
    }

}
