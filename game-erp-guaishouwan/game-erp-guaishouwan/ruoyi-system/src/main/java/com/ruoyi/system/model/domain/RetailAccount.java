package com.ruoyi.system.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 零售号对象 retail_account
 *
 * @author ruoyi
 * @date 2022-05-27
 */
@Data
public class RetailAccount extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;
    private List<Long> ids;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long createId;

    /** 账号ID */
    @Excel(name = "账号ID")
    private Long accountId;

    /** 原价格 */
    @Excel(name = "原价格")
    private BigDecimal oldPrice;

    /** 新价格 */
    @Excel(name = "新价格")
    private BigDecimal newPrice;

    private Long superId;

    private int rise;

    //主表字段 =========================》
    private String saleStateName;

    private String titleImg;

    /** 游戏名称 */
    private String gameName;
    private String supplyName;

    /** 游戏类型 */
    private String type;

    /** 游戏大区 */
    private String regional;

    /** 段位 */
    private String level;

    /** 英雄数量 */
    private Integer heroes;

    private String campId;

    /** 皮肤数量 */
    private Integer skins;

    /** 套装 */
    private Integer suit;

    /** 枪皮 */
    private Integer guns;

    /** 讲解 */
    private String des;

    /** 价格 */
    private BigDecimal price;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date saleTime;

    private Integer saleState;

    /**
     * g更新开始和结束时间
     */
    private Date updateTimeStart;
    private Date updateTimeEnd;


    /**
     * 导入开始和结束时间
     */
    private Date createTimeStart;
    private Date createTimeEnd;

    private BigDecimal priceStart;
    private BigDecimal priceEnd;

    private Integer heroStart;
    private Integer heroEnd;

    private Integer skinStart;
    private Integer skinEnd;

    private Integer suitStart;
    private Integer suitEnd;

    private Integer gunsStart;
    private Integer gunsEnd;

    private String sort;
    private String sortColumn;

    private List<String> regionalAddr;
    private List<String> levelAddr;
    private List<Long> supplyIdAddr;
    private List<Long> salesIdAddr;

    private Date saleTimeStart;
    private Date saleTimeEnd;


}
