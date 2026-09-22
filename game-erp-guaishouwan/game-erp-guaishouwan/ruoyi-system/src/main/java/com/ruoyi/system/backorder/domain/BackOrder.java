package com.ruoyi.system.backorder.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 回收订单表(back_order)表实体类
 *
 * @author zyy
 * @date 2023-12-02 13:17:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("back_order")
public class BackOrder {

    @TableId(type = IdType.AUTO)
    @Excel(name = "${column.comment}")
    private Long id;

    /**
     * 创建人id
     */
    @Excel(name = "创建人id")
    private Long createId;

    /**
     * 更新人id
     */
    @Excel(name = "更新人id")
    private Long updateId;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 商品id
     */
    @Excel(name = "商品id")
    private Long backlistId;

    /**
     * 游戏类型id
     */
    @Excel(name = "游戏类型id")
    private Long gameId;

    /**
     * 游戏名称
     */
    @Excel(name = "游戏名称")
    private String gameName;

    /**
     * 商品账号
     */
    @Excel(name = "商品账号")
    private String accountNumber;

    /**
     * 商品简介
     */
    @Excel(name = "商品简介")
    private String des;

    /**
     * 区服
     */
    @Excel(name = "区服")
    private String qufu;

    /**
     * 图片
     */
    @Excel(name = "图片")
    private String img;

    /**
     * 卖家id
     */
    @Excel(name = "卖家id")
    private Long sellerId;

    /**
     * 卖家名称
     */
    @Excel(name = "卖家名称")
    private String sellerName;

    /**
     * 买家id
     */
    @Excel(name = "买家id")
    private Long buyerId;

    /**
     * 买家名称
     */
    @Excel(name = "买家名称")
    private String buyerName;

    /**
     * 接受金额(商品金额) 只做展示
     */
    @Excel(name = "接受金额(商品金额) 只做展示")
    private BigDecimal sellerAcceptedAmount;

    /**
     * 押金金额
     */
    @Excel(name = "押金金额")
    private BigDecimal sellerDepositAmount;

    /**
     * 押金状态 1未退  2已退  3不退
     */
    @Excel(name = "押金状态")
    private Integer sellerDepositState;

    /**
     * 协商后商品金额
     */
    @Excel(name = "协商后商品金额")
    private BigDecimal changeAmount;

    /**
     * 包赔人id
     */
    @Excel(name = "包赔人id")
    private Long guaranteeId;

    /**
     * 包赔人名称
     */
    @Excel(name = "包赔人名称")
    private String guaranteeName;

    /**
     * 包赔类型
     */
    @Excel(name = "包赔类型")
    private Integer guaranteeType;

    /**
     * 包赔内容详情
     */
    @Excel(name = "包赔内容详情")
    private Long guaranteeInfoId;

    /**
     * 包赔服务费
     */
    @Excel(name = "包赔服务费")
    private BigDecimal guaranteeAmount;

    /**
     * 买家平台服务费比例
     */
    @Excel(name = "买家平台服务费比例")
    private BigDecimal buyerServiceRatio;

    /**
     * 买家平台服务费金额
     */
    @Excel(name = "买家平台服务费金额")
    private BigDecimal buyerServiceAmout;

    /**
     * 订单实付金额(协商后商品金额 + 包赔服务费 + 买家平台服务费)
     */
    @Excel(name = "订单实付金额(协商后商品金额 + 包赔服务费 + 买家平台服务费)")
    private BigDecimal buyerActualAmount;

    /**
     * 卖家平台服务费比例
     */
    @Excel(name = "卖家平台服务费比例")
    private BigDecimal sellerServiceRatio;

    /**
     * 卖家平台服务费金额
     */
    @Excel(name = "卖家平台服务费金额")
    private BigDecimal sellerServiceAmout;

    /**
     * 卖家结算金额(协商后商品金额-卖家平台服务费)
     */
    @Excel(name = "卖家结算金额(协商后商品金额-卖家平台服务费)")
    private BigDecimal sellerSettlementAmount;

    /**
     * 包赔商平台服务费比例
     */
    @Excel(name = "卖家包赔比例")
    private BigDecimal sellerGuaranteeRatio;

    /**
     * 包赔商平台服务费比例
     */
    @Excel(name = "包赔商平台服务费比例")
    private BigDecimal guaranteeServiceRatio;

    /**
     * 包赔商平台服务费金额
     */
    @Excel(name = "包赔商平台服务费金额")
    private BigDecimal guaranteeServiceAmout;

    /**
     * 包赔商结算金额(包赔服务费-包赔商平台服务费金额)
     */
    @Excel(name = "包赔商结算金额(包赔服务费-包赔商平台服务费金额)")
    private BigDecimal guaranteeSettlementAmount;

    /**
     * 利润(买家平台服务费金额+卖家平台服务费金额+包赔商平台服务费金额)
     */
    @Excel(name = "利润(买家平台服务费金额+卖家平台服务费金额+包赔商平台服务费金额)")
    private String profits;

    /**
     * 订单状态 1交易中 2待审核 3已审核 4交易完成 5交易失败
     */
    @Excel(name = "订单状态 1交易中 2待审核 3已审核 4交易完成 5交易失败")
    private Integer orderStatus;

    /**
     * 支付状态 1未支付 2已支付
     */
    @Excel(name = "支付状态 1未支付 2已支付")
    private Integer buyerPayStatus;

    /**
     * 订单号
     */
    @Excel(name = "订单号")
    private String no;

    /**
     * 订单支付完成时间
     */
    @Excel(name = "订单支付完成时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    /**
     * 订单完成时间
     */
    @Excel(name = "订单完成时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    /**
     * 订单取消时间
     */
    @Excel(name = "订单取消时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cancelTime;

    /**
     * 订单备注
     */
    @Excel(name = "订单备注")
    private String remark;

    /**
     * 买家移动支付记录id
     */
    @Excel(name = "买家移动支付记录id")
    private Long buyerPayRecordId;

//    /**
//     * 卖家移动支付记录id/pullGroup
//     */
//    @Excel(name = "卖家移动支付记录id")
//    private Long sellerPayRecordId;

    /**
     * 群组表id
     */
    @Excel(name = "群组表id")
    private Long msgGroupInfoId;

    /**
     * hx群组id
     */
    @Excel(name = "hx群组id")
    private String toGroup;

    /**
     * 预计关闭订单时间
     */
    @Excel(name = "预计关闭订单时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date closeTime;

    /**
     * 客服id
     */
    @Excel(name = "客服id")
    private Long serviceUserId;

    /**
     * 客服名称
     */
    @Excel(name = "客服名称")
    private String serviceUserName;

    /**
     * 买家是否结算：1未结算 2已结算
     */
    @Excel(name = "买家是否结算：1未结算 2已结算")
    private Integer buyerSettlementState;

    /**
     * 卖家是否结算：1未结算 2已结算
     */
    @Excel(name = "卖家是否结算：1未结算 2已结算")
    private Integer sellerSettlementState;

    /**
     * 包赔商是否结算：1未结算 2已结算
     */
    @Excel(name = "包赔商是否结算：1未结算 2已结算")
    private Integer guaranteeSettlementState;

    /**
     * 包赔商是否结算：1未结算 2已结算
     */
    @Excel(name = "包赔商是否结算：1未结算 2已结算")
    private Integer sellerDepositSettlementState;

    /**
     * 买家结算账单id
     */
    @Excel(name = "买家结算账单id")
    private Long buyerFinancialStatementId;

    /**
     * 卖家结算账单id
     */
    @Excel(name = "卖家结算账单id")
    private Long sellerFinancialStatementId;

    /**
     * 包赔商结算账单id
     */
    @Excel(name = "包赔商结算账单id")
    private Long guaranteeFinancialStatementId;

    /**
     * 包赔商结算账单id
     */
    @Excel(name = "包赔商结算账单id")
    private Long sellerDepositFinancialStatementId;

    /**
     * 来源场景 1:app 2:h5 3:pcweb 4:客服
     */
    @Excel(name = "来源场景 1:app 2:h5 3:pcweb 4:客服")
    private Integer sourceScen;

    /**
     * ip地址
     */
    @Excel(name = "ip地址")
    private String ip;
    /**
     * 客服名称
     */
    @Excel(name = "ip解析的城市地址")
    private String ipAddr;

    /**
     * all 查询全部  buy买单  sell卖单 guarant包赔
     */
    @TableField(exist = false)
    private String dataType;

    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeStart;
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeEnd;


    @TableField(exist = false)
    private Long createGroupUserId;

    /**
     * 是否退还押金 1 退 2不退
     */
    @TableField(exist = false)
    private Integer isReturn;
}

