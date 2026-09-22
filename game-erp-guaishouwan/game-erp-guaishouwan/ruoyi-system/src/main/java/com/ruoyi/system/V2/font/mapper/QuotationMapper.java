package com.ruoyi.system.V2.font.mapper;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.model.domain.Backlist;
import com.ruoyi.system.model.domain.Quotation;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author ruoyi
 * @date 2023-03-28
 */
public interface QuotationMapper {
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
    public List<Quotation> selectQuotationList(Quotation quotation);

    /**
     * 新增【请填写功能名称】
     *
     * @param quotation 【请填写功能名称】
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
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteQuotationById(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQuotationByIds(Long[] ids);

    /**
     * 查询【回收池报价】
     *
     * @param quotation 条件
     * @return 结果
     */
    List<Backlist> selectQuotationAndBack(Quotation quotation);

    /**
     * 查询【我的回收报价】
     *
     * @param quotation 条件
     * @return 结果
     */
    List<Backlist> selectMyQuotationAndBack(Quotation quotation);

    void bacthUpdate(@Param("quotations") List<Quotation> quotations);

    List<JSONObject> countOfferList(Map<String, Object> params);


    /**
     * 查询今天报价次数
     *
     * @param backlist
     * @return
     */
    Integer getTodayCount(Backlist backlist);


    List<Quotation> kefuList(Quotation quotation);
}
