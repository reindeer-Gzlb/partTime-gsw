package com.ruoyi.system.model.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 分销加价对象 retail_add_price
 *
 * @author lz
 * @date 2022-09-01
 */
@Data
public class RetailAddPrice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long createId;
    private String createName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long updateId;

    /** 供货人ID */
    @Excel(name = "供货人ID")
    private String superId;
    private String cmdsuper;

    private List<String> supplyIdAddr;
    private List<JSONObject> supplyIdAddrJson;

    private Integer odr;

    /** 加价百分比 */
    @Excel(name = "加价百分比")
    private Long rrise;

    /** 1、加价 2、减价 */
    @Excel(name = "1、加价 2、减价")
    private String type;

    /** 1、不同步 2、同步 */
    @Excel(name = "1、不同步 2、同步")
    private String push;

    private String gametype;

}
