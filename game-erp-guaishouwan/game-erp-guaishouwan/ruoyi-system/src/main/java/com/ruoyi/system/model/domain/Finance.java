package com.ruoyi.system.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.system.saleInfo.domain.GoodsSaleInfo;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务报表对象 finance
 *
 * @author ruoyi
 * @date 2023-02-24
 */
@Data
public class Finance extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long createId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long updateId;

    /** 收支类型 */
    @Excel(name = "收支类型")
    private String iotype;

    private Integer del;

    /** 收付款方 */
    @Excel(name = "收付款方")
    private String repay;

    /** 账单类型 */
    @Excel(name = "账单类型")
    private Long billType;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal price;

    /** 附件 */
    @Excel(name = "附件")
    private String atta;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;


    @Excel(name = "账号")
    private String account;

    private String sort;
    private String sortColumn;
    @Excel(name = "字段1")
    private String field1;
    @Excel(name = "字段2")
    private String field2;
    @Excel(name = "字段3")
    private String field3;
    @Excel(name = "字段4")
    private String field4;
    @Excel(name = "字段5")
    private String field5;
    @Excel(name = "字段6")
    private String field6;
    @Excel(name = "售卖类型")
    private String selltype;
    @Excel(name = "提交人")
    private String registeruser;
    private Date startTime;
    private Date endTime;
    //查询使用
    private Integer day;
    /**
     * 渠道来源
     */
    private String fromName;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startCreateTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endCreateTime;

    // 出售记录信息
    private GoodsSaleInfo goodsSaleInfo;
    // 财务记录
    private Finance finance;


}
