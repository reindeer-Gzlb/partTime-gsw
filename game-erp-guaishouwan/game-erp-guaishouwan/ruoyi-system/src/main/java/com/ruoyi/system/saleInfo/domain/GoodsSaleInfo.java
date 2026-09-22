package com.ruoyi.system.saleInfo.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 出售记录详情对象 goods_sale_info
 *
 * @author ruoyi
 * @date 2024-04-25
 */
@Data
public class GoodsSaleInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
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
     * 编号
     */
    @Excel(name = "编号")
    private String code;

    /**
     * 销售时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "销售时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date saleTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date saleTimeStart;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date saleTimeEnd;
    /**
     * 销售价格
     */
    @Excel(name = "销售价格")
    private BigDecimal salePrice;

    /**
     * 售后email
     */
    @Excel(name = "售后email")
    private String afterEmail;

    /**
     * 售后手机号
     */
    @Excel(name = "售后手机号")
    private String afterPhone;

    /**
     * 买家名字
     */
    @Excel(name = "买家名字")
    private String saleName;

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
