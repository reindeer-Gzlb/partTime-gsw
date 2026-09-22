package com.ruoyi.system.pay.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zyy
 * @date 2023-09-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnifyPayRes {

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
     * 订单状态 1：未支付 2：已支付  3：已撤销 4：已关闭
     */
    private Integer orderState;

    /**
     * 商户id
     */
    private Long payMerchantId;

    /**
     * 支付方式
     */
    private Long payWay;

    /**
     * 支付渠道
     */
    private Long payChannel;

    /**
     * 支付场景
     */
    private Long payScene;

    /**
     * 支付总金额
     */
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
     * 移动支付交易单号
     */
    private String payNo;

    /**
     * 商品标题
     */
    private String goodsTitle;

    /**
     * 支付结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payEndTime;

    /**
     * 支付配置id
     */
    private Long payArgumentId;

    /**
     * 下发状态 1：成功 2：失败
     */
    private Integer notifyState;

    /**
     * 下发地址
     */
    private String notifyUrl;

    /**
     * 下单生成的userId
     */
    private String userId;

    /**
     * 商户appId
     */
    private String payAppId;

    /**
     * 支付备注
     */
    private String remark;

    /**
     * 买家Id
     */
    private String buyerId;

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
     * 支付返回参数
     */
    private String payResponse;

}
