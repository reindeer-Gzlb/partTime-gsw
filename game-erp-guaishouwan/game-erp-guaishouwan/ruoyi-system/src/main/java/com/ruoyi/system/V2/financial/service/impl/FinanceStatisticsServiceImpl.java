package com.ruoyi.system.V2.financial.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.V2.financial.mapper.FinanceStatisticsMapper;
import com.ruoyi.system.V2.financial.service.IFinanceStatisticsService;
import com.ruoyi.system.model.domain.FinanceStatistics;
import org.springframework.stereotype.Service;

/**
 * 财务账单统计(finance_statistics)表服务实现类
 *
 * @author zyy
 * @date 2023-09-09 11:22:18
 */
@Service("financeStatisticsService")
public class FinanceStatisticsServiceImpl extends ServiceImpl<FinanceStatisticsMapper, FinanceStatistics> implements IFinanceStatisticsService {

}

