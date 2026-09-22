package com.ruoyi.system.shouFromName.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * 收入渠道对象 shou_from_name
 *
 * @author ruoyi
 * @date 2024-11-22
 */
@Data
public class ShouFromName implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String createName;
    /**
     * 渠道名称
     */
    @Excel(name = "渠道名称")
    private String name;

    /**
     * $column.columnComment
     */
    @Excel(name = "渠道名称")
    private Integer sort;

    /**
     * $column.columnComment
     */
    @Excel(name = "类型 1 支付 2 收入")
    private Integer type;
}
