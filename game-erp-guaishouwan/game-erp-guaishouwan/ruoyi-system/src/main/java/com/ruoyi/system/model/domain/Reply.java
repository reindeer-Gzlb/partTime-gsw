package com.ruoyi.system.model.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 快捷回复对象 reply
 * 
 * @author ruoyi
 * @date 2023-08-01
 */
@Data
public class Reply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createId;

    /** 设置内容 */
    @Excel(name = "设置内容")
    private String content;

    /** 类型 */
    @Excel(name = "类型")
    private String type;


    private boolean isAll;

    private List<String> typeList;

}
