package com.ruoyi.system.model.exel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 游戏账号对象 account_info
 *
 * @author ruoyi
 * @date 2022-05-04
 */
@Data
public class CmdAccountExel
{


    /** 账号 */
    @Excel(name = "编号")
    private Long id;

    /** 游戏类型 */
    @Excel(name = "游戏类型")
    private String type;

    /** 游戏大区 */
    @Excel(name = "区服")
    private String regional;

    /** 游戏名称 */
    @Excel(name = "自编号")
    private String gameName;

    /** 价格 */
    @Excel(name = "供货价")
    private BigDecimal price;


    /** 价格 */
    @Excel(name = "售价")
    private BigDecimal newPrice;


    /** 讲解 */
    @Excel(name = "简介")
    private String des;



    /** 真实售价 */
    @Excel(name = "真实售价")
    private BigDecimal realPrice;

    /** 购买备注 */
    @Excel(name = "购买备注")
    private String remake ;

    /** 供货人 */
    @Excel(name = "供货客服")
    private String supplyName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date saleTime;

    @Excel(name = "下单信息")
    private String buyId;

}
