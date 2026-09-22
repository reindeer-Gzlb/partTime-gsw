package com.ruoyi.system.V2.font.service;

import com.ruoyi.system.model.domain.Finance;

import java.util.List;
import java.util.Map;

/**
 * @author lz
 * @version 1.0
 * @date 2022/9/26 16:31
 */
public interface IDataCenterService {

    Map<String, Object> source();

    List<Map<String, Object>> hotGame();

    Map<String, Object> todayIncome();

    Map<String, Object> todayHSSC();

    List<Map<String, Object>> todaySCinfo(Finance finance);
}
