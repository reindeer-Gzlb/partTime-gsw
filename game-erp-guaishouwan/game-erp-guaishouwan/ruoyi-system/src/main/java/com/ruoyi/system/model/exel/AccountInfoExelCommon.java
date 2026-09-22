package com.ruoyi.system.model.exel;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 游戏账号对象 account_info
 *
 * @author ruoyi
 * @date 2022-05-04
 */
@Data
public class AccountInfoExelCommon
{


    /** 游戏大区 */
    @Excel(name = "区服")
    private String regional;

    /** 游戏名称 */
    @Excel(name = "自编号")
    private String gameName;

    /** 段位 */
    @Excel(name = "实名认证")
    private String level;

    @Excel(name = "营地ID")
    private String campId;

    /** 游戏类型 */
    @Excel(name = "游戏类型")
    private String type;

    /** 英雄数量 */
    @Excel(name = "属性一")
    private Integer heroes;

    /** 皮肤数量 */
    @Excel(name = "属性二")
    private Integer skins;

    /** 简介 */
    @Excel(name = "简介")
    private String des;

    /** 价格 */
    @Excel(name = "出售价格")
    private BigDecimal price;


    /** 提取账号 */
    @Excel(name = "提取账号")
    private String extractAccount;

    /** 提取密码 */
    @Excel(name = "提取密码")
    private String extractPwd;

    /** 提取手机号 */
    @Excel(name = "提取手机号")
    private String extractTel;

    /** 成本价格 */
    @Excel(name = "成本价格")
    private BigDecimal costPrice;


    /** 自编号 */
    @Excel(name = "账号备注")
    private String sinNum;


}
