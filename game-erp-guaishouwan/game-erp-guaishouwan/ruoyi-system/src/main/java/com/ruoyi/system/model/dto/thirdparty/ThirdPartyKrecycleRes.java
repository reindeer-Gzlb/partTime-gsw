package com.ruoyi.system.model.dto.thirdparty;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPartyKrecycleRes {


    /**
     * 订单id
     */
    private Long id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    /**
     * 账号
     */
    private String account;

    /**
     * 卖家手机号
     */
    private String tel;

    /**
     * 期望价格
     */
    private String expprice;

    /**
     * 同意的报价
     */
    private String bprice;

    /**
     * 最终成交价
     */
    private String tprice;

    /**
     * 状态
     * 2 交易中 3  已完成  4 取消交易
     */
    private String status;

    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sucessTime;

    /**
     * 取消时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date errorTime;

    /**
     * 备注
     */
//    private String remak;

}
