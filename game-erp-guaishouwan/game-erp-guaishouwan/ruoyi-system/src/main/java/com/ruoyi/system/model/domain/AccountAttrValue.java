package com.ruoyi.system.model.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 账号属性表对象 account_attr_value
 * sss
 *
 * @date 2023-04-04
 */
@Data
@ToString(includeFieldNames = false)
public class AccountAttrValue extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long createId;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long updateId;

    /**
     * account_info表的id即账号id
     */
    @Excel(name = "goods_info表的id")
    private Long accountInfoId;

    /**
     * 游戏id
     */
    @Excel(name = "游戏id")
    private Long gameId;

    /**
     * 游戏名称
     */
    @Excel(name = "游戏名称")
    private String gameName;

    /**
     * 自定义字段1
     */
    @Excel(name = "自定义字段1")
    private String f1;

    /**
     * 自定义字段2
     */
    @Excel(name = "自定义字段2")
    private String f2;

    /**
     * 自定义字段3
     */
    @Excel(name = "自定义字段3")
    private String f3;

    /**
     * 自定义字段4
     */
    @Excel(name = "自定义字段4")
    private String f4;

    /**
     * 自定义字段5
     */
    @Excel(name = "自定义字段5")
    private String f5;

    /**
     * 自定义字段6
     */
    @Excel(name = "自定义字段6")
    private String f6;

    /**
     * 自定义字段7
     */
    @Excel(name = "自定义字段7")
    private String f7;

    /**
     * 自定义字段8
     */
    @Excel(name = "自定义字段8")
    private String f8;

    /**
     * 自定义字段9
     */
    @Excel(name = "自定义字段9")
    private String f9;

    /**
     * 自定义字段10
     */
    @Excel(name = "自定义字段10")
    private String f10;

    /**
     * 自定义字段11
     */
    @Excel(name = "自定义字段11")
    private String f11;

    /**
     * 自定义字段12
     */
    @Excel(name = "自定义字段12")
    private String f12;

    /**
     * 自定义字段13
     */
    @Excel(name = "自定义字段13")
    private String f13;

    /**
     * 自定义字段14
     */
    @Excel(name = "自定义字段14")
    private String f14;

    /**
     * 自定义字段15
     */
    @Excel(name = "自定义字段15")
    private String f15;

    /**
     * 自定义字段16
     */
    @Excel(name = "自定义字段16")
    private String f16;

    /**
     * 自定义字段17
     */
    @Excel(name = "自定义字段17")
    private String f17;

    /**
     * 自定义字段18
     */
    @Excel(name = "自定义字段18")
    private String f18;

    /**
     * 自定义字段19
     */
    @Excel(name = "自定义字段19")
    private String f19;

    /**
     * 自定义字段20
     */
    @Excel(name = "自定义字段20")
    private String f20;

}
