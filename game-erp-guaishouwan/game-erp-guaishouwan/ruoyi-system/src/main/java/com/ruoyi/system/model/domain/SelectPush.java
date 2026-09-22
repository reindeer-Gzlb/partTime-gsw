package com.ruoyi.system.model.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 【请填写功能名称】对象 select_push
 * 
 * @author ruoyi
 * @date 2022-10-08
 */
@Data
public class SelectPush extends BaseEntity
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

    /** 供货人ID */
    @Excel(name = "供货人ID")
    private String superId;
    private String type;

    private List<String> creates;
    private List<Long> configs;
    private List<SalePartner> salePartnerList;
    private Long suplair;

    /** 游戏 */
    @Excel(name = "游戏")
    private String games;

    private String superIdLike;

    /** 选择的配置 */
    @Excel(name = "选择的配置")
    private String config;

    /** 排除的供货人 */
    @Excel(name = "排除的供货人")
    private String exids;
    private String state;

}
