package com.ruoyi.system.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务账单统计(finance_statistics)表实体类
 *
 * @author zyy
 * @date 2023-09-09 11:22:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("finance_statistics")
public class FinanceStatistics {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 创建人
     */
    private Long createId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 统计日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date statisticsDate;

    /**
     * 收入金额decimal
     */
    private BigDecimal incomePrice;

    /**
     * 支出金额
     */
    private BigDecimal disbursePrice;

    /**
     * 支出金额
     */
    private BigDecimal profit;

    /**
     * 开始日期
     */
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date statisticsDateStart;

    /**
     * 结束日期
     */
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date statisticsDateEnd;

}
