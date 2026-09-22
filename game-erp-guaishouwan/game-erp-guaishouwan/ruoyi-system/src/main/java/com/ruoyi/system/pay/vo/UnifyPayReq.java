package com.ruoyi.system.pay.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author zyy
 * @date 2023-09-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnifyPayReq {

    /**
     * 商户唯一标识
     */
    @NotBlank(message = "参数payMerchantId不能为空")
    private String payMerchantId;
    /**
     * 支付方式
     */
    @NotBlank(message = "参数payWay不能为空")
    private String payWay;
    /**
     * 支付场景
     */
    @NotBlank(message = "参数payScene不能为空")
    private String payScene;
    /**
     * 支付成功后异步访问地址
     */
    private String notifyUrl;
    /**
     * 支付后同步跳转地址
     */
    private String returnUrl;
    /**
     * 支付总金额
     */
    @NotNull(message = "参数totalAmount不能为空")
    private Double totalAmount;
    /**
     * 标题 不可使用特殊符号
     */
    @NotBlank(message = "参数title不能为空")
    private String title;

    /**
     * 订单编号唯一
     */
    @NotBlank(message = "参数no不能为空")
    private String no;
    /**
     * 生成订单的userId
     */
    @NotBlank(message = "参数userId不能为空")
    private String userId;


    /**
     * 2：订单码-跳转模式
     * 枚举值
     * 订单码-简约前置模式: 0
     * 订单码-前置模式: 1
     * 订单码-迷你前置模式: 3
     * 订单码-可定义宽度的嵌入式二维码: 4
     */
    private String qrPayMode;

    /**
     * 商户自定义二维码宽度。
     * 注：qr_pay_mode=4时该参数有效
     */
    private Integer qrcodeWidth;

    /**
     * 绝对超时时间
     */
    private Date csTime;
}
