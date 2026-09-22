package com.ruoyi.system.model.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 【请填写功能名称】对象 skin
 *
 * @author ruoyi
 * @date 2022-07-03
 */
@Data
public class Skin extends BaseEntity
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

    /** 皮肤名称 */
    @Excel(name = "皮肤名称")
    private String title;

    /** 皮肤ID */
    @Excel(name = "皮肤ID")
    private Long skinId;

    /** 分类ID */
    @Excel(name = "分类ID")
    private Long parentId;

    private BigDecimal skinPrice;
    private BigDecimal classPrice;
    private String type;

    private Long configid;
    private String img;
}
