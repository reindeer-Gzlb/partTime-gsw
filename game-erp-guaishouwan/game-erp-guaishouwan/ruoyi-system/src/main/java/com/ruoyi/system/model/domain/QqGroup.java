package com.ruoyi.system.model.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【请填写功能名称】对象 qq_group
 * 
 * @author ruoyi
 * @date 2022-10-04
 */
@Data
public class QqGroup extends BaseEntity
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

    /** qq群号 */
    @Excel(name = "qq群号")
    private String qq;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 已满  未满 */
    @Excel(name = "已满  未满")
    private String state;


    /**
     * 游戏Id
     */
    @Excel(name = "游戏Id")
    private Long gameId;

    /**
     * 游戏名称
     */
    @Excel(name = "游戏名称")
    private String gameName;

    /**
     * 1 主站   2 商户端
     */
    private Integer typeResource = 1;

    private Long businessId;
    private Integer sort;

}
