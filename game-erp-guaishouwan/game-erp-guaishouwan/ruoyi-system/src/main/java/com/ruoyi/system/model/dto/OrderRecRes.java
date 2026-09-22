package com.ruoyi.system.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lz
 * @version 1.0
 * @date 2022/8/6 12:23
 */
@Data
public class OrderRecRes {

    /**
     * 首图
     */
    private String titleImg;
    /**
     * 简介
     */
    private String des;

    /**
     * 拼接后的区服
     */
    private String qufu;

    /**
     * 销售价格
     */
    private String salePrice;

    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 订单id
     */
    private String orderId;

    /**
     * 订单创建时间
     */
    private String createTime;

    /**
     * 订单状态 1正常 2销毁
     */
    private String orderStatus;

    /**
     * 实名
     */
    private String shiming;

    /**
     * 脱敏手机号
     */
    private String dephone;
    /**
     * 1 自营 2代售
     */
    private Integer accountType;

    /**
     * 商品创建时间（上架时间）
     */
    private String goodsCreateTime;

    /**
     * 移动支付记录表id
     */
    private Long payRecordId;

    /**
     * 移动支付记录表生成的系统编号
     */
    private String no;
    /**
     * 支付完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payEndTime;

    /**
     * 完成订单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    /**
     * 取消订单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cancelTime;

    /**
     * 浏览次数
     */
    private Integer browseNum;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date closeTime;

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
     * 已付金额
     */
    private BigDecimal paidAmount;


    private String account;

    private String saleChannel;
}
