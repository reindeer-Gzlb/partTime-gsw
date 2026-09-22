package com.ruoyi.system.V2.retail.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.model.domain.RetailAddPrice;

import java.util.List;
import java.util.Map;

/**
 * 分销加价Service接口
 *
 * @author lz
 * @date 2022-09-01
 */
public interface IRetailAddPriceService
{
    /**
     * 查询分销加价
     *
     * @param id 分销加价主键
     * @return 分销加价
     */
    public RetailAddPrice selectRetailAddPriceById(Long id);

    /**
     * 查询分销加价列表
     *
     * @param retailAddPrice 分销加价
     * @return 分销加价集合
     */
    public List<RetailAddPrice> selectRetailAddPriceList(RetailAddPrice retailAddPrice);

    /**
     * 新增分销加价
     *
     * @param retailAddPrice 分销加价
     * @return 结果
     */
    public int insertRetailAddPrice(RetailAddPrice retailAddPrice);

    /**
     * 修改分销加价
     *
     * @param retailAddPrice 分销加价
     * @return 结果
     */
    public int updateRetailAddPrice(RetailAddPrice retailAddPrice);

    /**
     * 批量删除分销加价
     *
     * @param ids 需要删除的分销加价主键集合
     * @return 结果
     */
    public int deleteRetailAddPriceByIds(Long[] ids);

    /**
     * 删除分销加价信息
     *
     * @param id 分销加价主键
     * @return 结果
     */
    public int deleteRetailAddPriceById(Long id);

    List<String> noAddRetailSuppply();

    List<Map<String, Object>> disrank();

    JSONObject show();
}
