package com.ruoyi.system.V2.font.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.model.domain.Backlist;
import com.ruoyi.system.model.domain.Quotation;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2023-03-28
 */
public interface IQuotationService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Quotation selectQuotationById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param quotation 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Backlist> selectQuotationList(Quotation quotation);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param quotation 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Backlist> selectMyQuotationList(Quotation quotation);

    /**
     *
     * @return 结果
     */
    public int insertQuotation(Quotation quotation);

    /**
     * 修改【请填写功能名称】
     * 
     * @param quotation 【请填写功能名称】
     * @return 结果
     */
    public int updateQuotation(Quotation quotation);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteQuotationByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteQuotationById(Long id);

    List<Quotation> mybprice(Quotation quotation);

    List<JSONObject> countOfferList(Map<String, Object> params);

    int refuse(Map<String,Object> params);

    public List<Quotation> kefuList(Quotation quotation);

}
