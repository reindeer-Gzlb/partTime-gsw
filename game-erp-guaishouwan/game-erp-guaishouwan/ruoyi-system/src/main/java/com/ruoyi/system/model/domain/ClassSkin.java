package com.ruoyi.system.model.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 皮肤分类对象 class_skin
 * 
 * @author ruoyi
 * @date 2022-10-24
 */
@Data
public class ClassSkin extends BaseEntity
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

    /** 分类ID */
    @Excel(name = "分类ID")
    private Long skinId;

    /** 上级ID */
    @Excel(name = "上级ID")
    private Long parentId;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String brandname;

    /** 上级分类 */
    @Excel(name = "上级分类")
    private String pbrandname;

}
