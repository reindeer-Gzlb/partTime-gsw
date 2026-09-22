package com.ruoyi.system.pay.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 移动支付记录表(pay_record)表实体类
 *
 * @author zyy
 * @date 2023-09-15 16:23:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("pay_record")
public class PayRecord {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 修改人id
     */
    private Long updateId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 订单类型 1：商品销售,生成销售订单 6回收记录卖家支付押金 7回收订单买家支付
     */
    @NotNull(message = "参数orderType不能为空")
    private Integer orderType;

    /**
     * 根据订单类型,关联的表的主键id
     */
    private Long businessId;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 订单状态 1：未支付 2： 已支付 3：已撤销 4：已完成  5：已退款
     */
    private Integer orderState;

    /**
     * 生成订单的用户id
     */
    private Long userId;

    /**
     * 生成订单的用户昵称
     */
    private String userName;

    /**
     * 商户id
     */
    private Long payMerchantId;

    /**
     * 商户appId
     */
    private Long payAppId;

    /**
     * 支付方式 1微信 2支付宝
     */
    private Long payWay;

    /**
     * 支付渠道
     */
    private Long payChannel;

    /**
     * 支付场景 支付场景id  1 app支付  2 h5支付  3 pcweb支付
     */
    private Long payScene;

    /**
     * 支付总金额
     */
    @NotNull(message = "参数totalAmount不能为空")
    private Double payTotalMoney;

    /**
     * 业务系统单号
     */
    private String no;

    /**
     * 支付中心单号
     */
    private String centerNo;

    /**
     * 商品标题
     */
    @NotBlank(message = "参数goodsTitle不能为空")
    private String goodsTitle;

    /**
     * 买家Id
     */
    private String buyerId;

    /**
     * 移动支付交易单号
     */
    private String payNo;

    /**
     * 回调id
     */
    private String payNotifyId;

    /**
     * 支付订单回调时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payNotifyTime;

    /**
     * 支付订单创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payCreateTime;

    /**
     * 支付付款结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payEndTime;

    /**
     * 支付备注
     */
    private String remark;

    /**
     * 异步下发地址
     */
    private String notifyUrl;

    /**
     * 同步跳转地址
     */
    private String returnUrl;


    @TableField(exist = false)
    private Date payEndTimeStart;

    @TableField(exist = false)
    private Date payEndTimeEnd;

    /**
     * 根据业务类型订单其他参数
     */
    private String orderOther;

    /**
     * 返回的formUrl
     */
    private String formStr;

    /**
     * 商品金额
     */
    @TableField(exist = false)
    private BigDecimal goodsAmount;
    /**
     * 包赔类型 1找回包赔 2人脸包赔
     */
    @TableField(exist = false)
    private Integer guaranteeType;
    /**
     * 包赔金额
     */
    @TableField(exist = false)
    private BigDecimal guaranteeAmount;

    /**
     * 1.订金 2.尾款
     */
    @TableField(exist = false)
    private Integer amountType;

    /**
     * 2：订单码-跳转模式
     * 枚举值
     * 订单码-简约前置模式: 0
     * 订单码-前置模式: 1
     * 订单码-迷你前置模式: 3
     * 订单码-可定义宽度的嵌入式二维码: 4
     */
    @TableField(exist = false)
    private String qrPayMode;

    /**
     * 商户自定义二维码宽度。
     * 注：qr_pay_mode=4时该参数有效
     */
    @TableField(exist = false)
    private Integer qrcodeWidth;

    @TableField(exist = false)
    private Long supplyUserId;


    /**
     * 包赔人id
     */
    @TableField(exist = false)
    private Long guaranteeId;

    /**
     * 包赔人名称
     */
    @TableField(exist = false)
    private String guaranteeName;

    /**
     * 包赔内容详情
     */
    @TableField(exist = false)
    private Long guaranteeInfoId;
}
