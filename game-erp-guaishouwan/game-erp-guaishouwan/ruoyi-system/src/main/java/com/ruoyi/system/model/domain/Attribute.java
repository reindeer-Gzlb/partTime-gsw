package com.ruoyi.system.model.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 游戏配置对象 attribute
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
@Data
public class Attribute extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 游戏类型 */
    @Excel(name = "游戏类型")
    private String type;

    /** 字段类型 */
    @Excel(name = "字段类型")
    private String fieldType;
    private List<String> fieldTypes;
    private List<String> ids;

    /** 字段名称 */
    @Excel(name = "字段名称")
    private String fieldName;

    /** 字段值，当单选或者多选时可以输入 */
    @Excel(name = "字段值，当单选或者多选时可以输入")
    private String fieldValue;
    private String formula;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 页面类型 */
    @Excel(name = "页面类型")
    private Long cfgType;

    /** 父id */
    @Excel(name = "父id")
    private Long parentId;

    /** 图标 */
    @Excel(name = "图标")
    private String imgUrl;

    /** 估价 */
    @Excel(name = "估价")
    private BigDecimal valuation;

    /** 级别 */
    @Excel(name = "级别")
    private Long level;

    List<Attribute> attributes;
}
