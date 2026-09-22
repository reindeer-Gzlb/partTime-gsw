package com.ruoyi.system.model.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 折扣率对象 rateof
 * 
 * @author ruoyi
 * @date 2022-12-11
 */
@Data
public class Rateof extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 排序 */
    private Long sort;

    /** 折扣率 */
    @Excel(name = "折扣率")
    private String level;

    /** 价格区间 */
    @Excel(name = "价格区间")
    private String rangeo;

    /** 游戏 */
    @Excel(name = "游戏")
    private String type;
}
