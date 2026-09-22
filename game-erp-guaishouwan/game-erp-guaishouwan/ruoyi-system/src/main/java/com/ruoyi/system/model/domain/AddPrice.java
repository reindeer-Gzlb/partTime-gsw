package com.ruoyi.system.model.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 批量加价对象 add_price
 *
 * @author ruoyi
 * @date 2022-07-17
 */
@Data
public class AddPrice extends BaseEntity
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
    private String updateName;

    /** 供货人ID */
    @Excel(name = "供货人ID")
    private Long superId;
    private String superName;

    /**
     * 0、不同步
     * 1、同步
     */
    private String ison;

    /** 加价比例 */
    @Excel(name = "加价比例")
    private Integer rise;

    /** 1、加价 2、减价 */
    @Excel(name = "1、加价 2、减价")
    private String type;
    private String typeName;

    //1、自营账号  2、终端账号
    private String roleType;
    private String roleTypeName;

    /**
     * '1、供货商加价 2、分销加价'
     */
    private String priceType;

    /**
     * 游戏
     */
    private String gtype;

    /**
     * 最大数量
     */
    private Integer maxcount;

}
