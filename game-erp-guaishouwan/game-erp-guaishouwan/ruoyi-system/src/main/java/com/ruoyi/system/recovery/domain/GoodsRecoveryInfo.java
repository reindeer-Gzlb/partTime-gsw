package com.ruoyi.system.recovery.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;
/**
 * 回收管理对象 goods_recovery_info
 *
 * @author ruoyi
 * @date 2024-04-20
 */
@Data
public class GoodsRecoveryInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     */
    private Long id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     */
    private Long createId;

    /**
     * 编号
     */
    @Excel(name = "编号")
    private String code;

    /**
     * 回收日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "回收日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date recoveryTime;
    private Date recoveryTimeStart;
    private Date recoveryTimeEnd;

    /**
     * 价格
     */
    @Excel(name = "价格")
    private BigDecimal price;

    /**
     * 微信
     */
    @Excel(name = "微信")
    private String vx;

    /**
     * 打款日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "打款日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payTime;
    private Date payTimeStart;
    private Date payTimeEnd;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;


    /**
     * 回收人
     */
    private  String backUserName;

    /**
     * 填报人
     */
    private String leaderUserName;
}
