package com.ruoyi.system.V2.font.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.V2.font.mapper.QuotationMapper;
import com.ruoyi.system.V2.font.service.IQuotationService;
import com.ruoyi.system.V2.tool.mapper.BacklistMapper;
import com.ruoyi.system.model.domain.Backlist;
import com.ruoyi.system.model.domain.Quotation;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@Service
public class QuotationServiceImpl implements IQuotationService {
    @Resource
    private QuotationMapper quotationMapper;
    @Resource
    private BacklistMapper backlistMapper;


    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Quotation selectQuotationById(Long id) {
        return quotationMapper.selectQuotationById(id);
    }

    /**
     * 查询【回收池报价】列表
     *
     * @param quotation 【报价】
     * @return 【结果】
     */
    @Override
    public List<Backlist> selectQuotationList(Quotation quotation) {

        if (Objects.equals("allQue",quotation.getSelectType())){

        }else {
            if (Objects.equals(1, quotation.getCreateId()) || null != quotation.getCreateId()) {

            } else {
                quotation.setCreateId(SecurityUtils.getUserId());
            }
        }



        String status = quotation.getStatus();
        if (!Objects.equals("5",status)){
            quotation.setNoStatus("5");
        }

        List<Backlist> backlistList = quotationMapper.selectQuotationAndBack(quotation);
        for (Backlist b : backlistList) {
            Quotation quotation1 = new Quotation();
            quotation1.setBackId(b.getId());
            quotation1.setNoStatus("5");
            List<Quotation> quotations = quotationMapper.selectQuotationList(quotation1);
            b.setBcountt(quotations.size());
            if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(quotations)) {
                Collections.sort(quotations,
                        (o1, o2) -> new Double(o2.getBprice()).compareTo(new Double(o1.getBprice())));
                b.setMaxPrice(quotations.get(0).getBprice());
            }
        }

        return backlistList;
    }

    /**
     * 查询【我的回收】列表
     *
     * @param quotation 【报价】
     * @return 【结果】
     */
    @Override
    public List<Backlist> selectMyQuotationList(Quotation quotation) {
        quotation.setCreateId(SecurityUtils.getUserId());
        List<Backlist> backlistList = quotationMapper.selectMyQuotationAndBack(quotation);

        return backlistList;
    }

    /**
     * @param quotation 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertQuotation(Quotation quotation) {
        Quotation select = new Quotation();
        select.setBackId(quotation.getBackId());
        select.setCreateId(SecurityUtils.getUserId());
        List<Quotation> quotations = quotationMapper.selectQuotationList(select);
        if (!CollectionUtils.isEmpty(quotations)) {
            throw new GlobalException("已提交报价");
        }
        quotation.setCreateTime(DateUtils.getNowDate());
        quotation.setCreateId(SecurityUtils.getUserId());
        return quotationMapper.insertQuotation(quotation);
    }

    /**
     * @return 结果
     */
    @Override
    public int updateQuotation(Quotation quotation) {
        quotation.setUpdateTime(DateUtils.getNowDate());
        quotation.setCreateId(SecurityUtils.getUserId());
        return quotationMapper.updateQuotation(quotation);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteQuotationByIds(Long[] ids) {
        return quotationMapper.deleteQuotationByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteQuotationById(Long id) {
        return quotationMapper.deleteQuotationById(id);
    }

    @Override
    public List<Quotation> mybprice(Quotation quotation) {
        List<Quotation> quotations = quotationMapper.selectQuotationList(quotation);
        for (Quotation quotation1 : quotations) {
            quotation1.setCreateName("报价人" + quotation1.getId());
        }

        return quotations;
    }

    @Override
    public List<JSONObject> countOfferList(Map<String, Object> params) {

        List<JSONObject> list = quotationMapper.countOfferList(params);

        return list;
    }

    @Override
    public int refuse(Map<String,Object> params) {

        /**
         * 1、新增一条拒绝报价
         */

        Long backId = MapUtils.getLong(params, "backId");
        String rmsg = MapUtils.getString(params, "rmsg");

        Backlist backlist = backlistMapper.selectBacklistById(backId);
        if (Objects.isNull(backlist)){
            throw new GlobalException("backId is error");
        }

        Quotation quotation = new Quotation();
        quotation.setStatus("5");
        quotation.setCreateId(SecurityUtils.getUserId());
        quotation.setBackId(backId);
        quotation.setRmsg(rmsg);
        quotation.setBprice(backlist.getBprice());

        return this.insertQuotation(quotation);

    }

    @Override
    public List<Quotation> kefuList(Quotation quotation) {
        if(StringUtils.isNull(quotation.getBackId())){
            throw new GlobalException("回收id不能为空");
        }
        List<Quotation> quotationList = quotationMapper.kefuList(quotation);
        return quotationList;
    }
}
