package com.ruoyi.system.model.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 操作历史记录对象 operation_history
 *
 * @author ruoyi
 * @date 2022-05-16
 */
@Data
public class OperationHistory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 游戏id,account表主键 */
    @Excel(name = "游戏id,account表主键")
    private Long accountId;

    /** 操作人 */
    @Excel(name = "操作人")
    private String operator;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date operatTime;

    /** 操作类型:1、审核包赔,2、上架审核 */
    @Excel(name = "操作类型:1、审核包赔,2、上架审核")
    private String status;

    private String startTime;

    private String endTime;

    /** 操作原因 */
    @Excel(name = "操作原因")
    private String reason;

    /** 操作变动 */
    @Excel(name = "数据变动")
    private String alteration;
}
