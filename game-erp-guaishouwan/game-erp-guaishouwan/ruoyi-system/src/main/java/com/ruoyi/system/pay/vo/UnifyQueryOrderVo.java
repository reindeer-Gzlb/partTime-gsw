package com.ruoyi.system.pay.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zyy
 * @date 2023-09-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnifyQueryOrderVo {

    /**
     * 商户唯一标识
     */
//    @NotBlank(message = "参数payMerchantId不能为空")
    private String payMerchantId;

    /**
     * 订单编号唯一
     */
//    @NotBlank(message = "参数no不能为空")
    private String no;
    /**
     * 生成订单的userId
     */
//    @NotBlank(message = "参数userId不能为空")
    private String userId;

    /**
     * 支付中心单号
     */
    private String centerNo;

//    /**
//     * 支付方式
//     */
//    @NotBlank(message = "参数payWay不能为空")
//    private String payWay;
//    /**
//     * 支付场景
//     */
//    @NotBlank(message = "参数payScene不能为空")
//    private String payScene;
//    /**
//     * 支付成功后异步访问地址
//     */
//    private String notifyUrl;
//    /**
//     * 支付后同步跳转地址
//     */
//    private String returnUrl;
//    /**
//     * 支付总金额
//     */
//    @NotNull(message = "参数totalAmount不能为空")
//    private Double totalAmount;
//    /**
//     * 标题 不可使用特殊符号
//     */
//    @NotBlank(message = "参数title不能为空")
//    private String title;


}
