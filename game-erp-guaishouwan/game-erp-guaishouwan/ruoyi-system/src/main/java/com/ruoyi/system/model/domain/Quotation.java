package com.ruoyi.system.model.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 【请填写功能名称】对象 quotation
 * 
 * @author ruoyi
 * @date 2023-03-28
 */
@Data
public class Quotation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long createId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String createName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long updateId;

    /** 状态 */
    /**
     * 报价详情   0  待接受报价
     *                  1 接受报价
     *                  2 已拒绝
     *                 3 已完成
     *                   4 取消交易
     *                   5 商家拒绝
     */
    @Excel(name = "状态")
    private String status;

    private String noStatus;

    private String account;

    /** 报价 */
    @Excel(name = "报价")
    private String bprice;

    /** 回收id */
    @Excel(name = "回收id")
    private Long backId;
    private String type;
    private String des;
    private String rmsg;
    private String selectType;
}
