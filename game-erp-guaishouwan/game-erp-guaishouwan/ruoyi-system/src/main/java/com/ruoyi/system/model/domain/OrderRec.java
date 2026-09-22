package com.ruoyi.system.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售订单记录对象 order_rec
 *
 * @author ruoyi
 * @date 2022-10-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRec extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * $column.columnComment
     */
    private Long createId;
    private Long supllyId;

    /**
     * $column.columnComment
     */
    private Long updateId;
    private Long gameid;
    private Long salechanelid;

    /**
     * 销售渠道
     */
    @Excel(name = "销售渠道")
    private String saleChannel;
    private String remake;

    /**
     * 供货渠道
     */
    @Excel(name = "供货渠道")
    private String supplyChannel;

    private String saleTimeEnd;
    private String saleTimeStart;
    private String createName;

    /**
     * 销售折扣后金额
     */
    @Excel(name = "销售折扣后金额")
    private String saleDiscountPrice;

    /**
     * 销售折扣后金额
     */
    @Excel(name = "销售折扣前金额")
    private String saleDiscount;

    /**
     * 供货金额
     */
    @Excel(name = "供货金额")
    private String supplyDiscountPrice;

    /**
     * 供货折扣
     */
    @Excel(name = "供货折扣")
    private String supplyDiscount;

    /**
     * 销售金额
     */
    @Excel(name = "销售金额")
    private String salePrice;

    /**
     * 自编号
     */
    @Excel(name = "自编号")
    private String sumNum;

    private Long suplyid;
    private Integer saleid;


    /**
     * 利润
     */
    @Excel(name = "利润")
    private String profits;

    /**
     * 1交易成功 2已销毁 3待支付 4交易中 5已退款
     */
    private String oderstatus;

    /**
     * 游戏
     */
    @Excel(name = "游戏")
    private String games;

    /**
     * 是否支付
     */
    @Excel(name = "是否支付")
    private String ifPay;

    @Excel(name = "终端编号")
    private String accountid;

    private String snum;
    @Excel(name = "账号")
    private String account;
    private String supllypay;
    @Excel(name = "供货折扣前金额")
    private String suplyprice;

    /**
     * 销售渠道最低消费金额
     */
    private BigDecimal saleChannelMinamount;

    /**
     * 移动支付记录id
     */
    private Long payRecordId;

    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    /**
     * 取消时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cancelTime;

    /**
     * 商品金额
     */
    private BigDecimal goodsAmount;
    /**
     * 包赔类型 1找回包赔 2人脸包赔
     */
    private Integer guaranteeType;
    /**
     * 包赔金额
     */
    private BigDecimal guaranteeAmount;

    /**
     * 销售订单号
     */
    private String no;

    /**
     * 预计关闭订单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date closeTime;

    /**
     * 更改商品表的销售状态
     */
    private Integer changeGoodsSaleState;


    /**
     * 退款金额
     */
    private BigDecimal refundAmount;
    /**
     * 售后类型id
     */
    private Long afterSaleId;
    /**
     * 售后类型值
     */
    private String afterSaleValue;
    /**
     * 售后备注
     */
    private String afterSaleRemark;

    /**
     * 退款类型  1 仅退款   2 退款+改变订单状态+商品上架
     */
    private Integer refundType;

    /**
     * 已付金额
     */
    private BigDecimal paidAmount;

//    /**
//     * 1.订金 2.尾款
//     */
//    private Integer AmountType;

    /**
     * 不为空就强制查询
     */
    private String force;

    private Integer payState;

    /**
     * 支付中心单号
     */
    private String centerNo;
}
