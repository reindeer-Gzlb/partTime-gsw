package com.ruoyi.system.V2.font.service;

import com.ruoyi.system.model.domain.GoodsInfo;

import java.util.Map;

/**
 * @author lz
 * @version 1.0
 * @date 2022/9/26 16:31
 */
public interface IIndexService {

    Map<String,Object> salesCount(GoodsInfo goodsInfo);

    Map<String, Object> salesSum();

    Map<String, Object> gameTypeFor();

    Map<String, Object> sevenDaysCount();

    Map<String, Object> yearCount();

    Map<String, Object> gameTypeGon();

    Map<String, Object> gameTypeSale(Map<String, Object> params);

}
