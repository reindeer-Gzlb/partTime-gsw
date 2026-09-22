package com.ruoyi.system.model.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 终端附表对象 cmd_account
 *
 * @author ruoyi
 * @date 2022-08-06
 */
@Data
public class CmdAccount extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long createId;

    /** 账号ID */
    @Excel(name = "账号ID")
    private Long accountId;
    private Long suplyId;

    private List<Long> suplyIds;

    /** 原价格 */
    @Excel(name = "原价格")
    private BigDecimal oldPrice;

    /** 新价格 */
    @Excel(name = "新价格")
    private BigDecimal newPrice;

    /** 上浮比例 */
    @Excel(name = "上浮比例")
    private Integer rise;

    private Long mainId;
    private String uuid;

}
